package cat.itacademy.mascota_virtual_back.security;

import cat.itacademy.mascota_virtual_back.persistence.repositories.UsuariRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuariRepository usuariRepository;

    @Bean
    public SecurityWebFilterChain jwtSecurityFilterChain(ServerHttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        //cambiar lo de swagger. cerrar acceso o no??
                     /*   .pathMatchers("/swagger-ui.html", "/v3/api-docs/**").permitAll() // Permite acceso a Swagger
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/api/admin/**").hasRole("ADMIN")
                        .pathMatchers("/api/users/**").authenticated()*/
                        .anyExchange().permitAll()
                )
                /*.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)*/
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return username -> usuariRepository.findByNomUsuari(username)
                .map(usuari -> {
                    String[] roles = new String[] { usuari.getRol().name() };
                    return User.withUsername(usuari.getNomUsuari())
                            .password(usuari.getContrasenya())
                            .roles(roles)
                            .build();
                })
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Usuari no trobat: " + username)));
    }

}

