package cat.itacademy.mascota_virtual_back.services;

import cat.itacademy.mascota_virtual_back.domain.Usuari;
import cat.itacademy.mascota_virtual_back.domain.enums.RolUsuari;
import cat.itacademy.mascota_virtual_back.mappers.UsuariMapper;
import cat.itacademy.mascota_virtual_back.persistence.repositories.UsuariRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UsuariRepository usuariRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuariMapper usuariMapper;


    @Value("${admin.password}")
    private String adminPassword;

    @PostConstruct
    public void init() {
        createAdminUserIfNotExists();
    }

    private void createAdminUserIfNotExists() {
        usuariRepository.findByNomUsuari("admin").switchIfEmpty(
                Mono.defer(() -> {
                    Usuari adminUser = new Usuari();
                    adminUser.setNomUsuari("admin");
                    adminUser.setContrasenya(passwordEncoder.encode(adminPassword));
                    adminUser.setRol(RolUsuari.ADMIN);
                    return usuariRepository.save(usuariMapper.toEntity(adminUser));
                })
        ).subscribe();
    }
}
