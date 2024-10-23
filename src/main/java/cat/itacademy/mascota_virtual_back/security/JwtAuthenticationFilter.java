package cat.itacademy.mascota_virtual_back.security;

import cat.itacademy.mascota_virtual_back.exceptions.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        // Extraer el token JWT de la solicitud
        String token = extractJwtFromRequest(exchange);
        // Verificar si el token no es nulo
        if (token != null) {
            // Extraer el nombre de usuario del token
            String username = jwtUtil.extractUsername(token);
            // Extraer el rol del token
            //String role = jwtUtil.extractRole(token); es necesario comprobar aqui algo con rol??
            // Verificar si el nombre de usuario no es nulo
            String userId = jwtUtil.extractUserId(token); // Extraer el userId del token
            if (username == null) {
                return Mono.error(new InvalidTokenException("JWT token no vàlid: usuari no trobat"));
            }
            // Buscar detalles del usuario por nombre de usuario
            return userDetailsService.findByUsername(username)
                    .flatMap(userDetails -> {
                        // Validar el token con los detalles del usuario
                        if (jwtUtil.validateToken(token, userDetails)) {
                            // Crear un token de autenticación y agregarlo al contexto de seguridad
                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails, null, userDetails.getAuthorities());
                            authToken.setDetails(userId); // Añadir el userId a los detalles ESTO ES CORECTO PARA LUEGO USAR UN @PREAUTORIZE CON USERID??
                            // Aquí puedes establecer el rol si lo necesitas
                            return chain.filter(exchange)
                                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
                        } else {
                            return Mono.error(new InvalidTokenException("JWT token no vàlid"));
                        }
                    })
                    .switchIfEmpty(Mono.error(new UsernameNotFoundException("Usuari no trobat: " + username)));
        }
        return chain.filter(exchange);
    }


    private String extractJwtFromRequest(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}


