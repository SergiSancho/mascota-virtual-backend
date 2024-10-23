package cat.itacademy.mascota_virtual_back.services;

import cat.itacademy.mascota_virtual_back.domain.Usuari;
import cat.itacademy.mascota_virtual_back.domain.enums.RolUsuari;
import cat.itacademy.mascota_virtual_back.dto.AuthResponse;
import cat.itacademy.mascota_virtual_back.dto.AuthRequest;
import cat.itacademy.mascota_virtual_back.dto.UsuariDTO;
import cat.itacademy.mascota_virtual_back.mappers.UsuariMapper;
import cat.itacademy.mascota_virtual_back.persistence.repositories.UsuariRepository;
import cat.itacademy.mascota_virtual_back.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuariRepository usuariRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UsuariMapper usuariMapper;

    public Mono<AuthResponse> registerUser(AuthRequest authRequest) {
        Usuari usuari = new Usuari();
        usuari.setNomUsuari(authRequest.getNomUsuari());
        usuari.setContrasenya(passwordEncoder.encode(authRequest.getContrasenya()));
        usuari.setRol(RolUsuari.USER);

        return usuariRepository.save(usuariMapper.toEntity(usuari))
                .map(savedUser -> {
                    String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getNomUsuari(), savedUser.getRol().name());
                    UsuariDTO responseDTO = usuariMapper.toDto(savedUser);
                    return new AuthResponse(responseDTO, token);
                })
                .onErrorResume(e -> Mono.error(new Exception("Error registrant usuari: " + e.getMessage())));
    }

    public Mono<AuthResponse> loginUser(AuthRequest authRequest) {
        return usuariRepository.findByNomUsuari(authRequest.getNomUsuari())
                .filter(usuari -> passwordEncoder.matches(authRequest.getContrasenya(), usuari.getContrasenya()))
                .map(usuari -> {
                    String token = jwtUtil.generateToken(usuari.getId(), usuari.getNomUsuari(), usuari.getRol().name());
                    UsuariDTO responseDTO = usuariMapper.toDto(usuari);
                    return new AuthResponse(responseDTO, token);
                })
                .switchIfEmpty(Mono.error(new AuthenticationException("Credencials incorrectes.")));
    }

}
