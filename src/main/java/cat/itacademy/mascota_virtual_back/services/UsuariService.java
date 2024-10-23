package cat.itacademy.mascota_virtual_back.services;

import cat.itacademy.mascota_virtual_back.dto.UsuariDTO;
import cat.itacademy.mascota_virtual_back.exceptions.ResourceNotFoundException;
import cat.itacademy.mascota_virtual_back.mappers.UsuariMapper;
import cat.itacademy.mascota_virtual_back.persistence.repositories.UsuariRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UsuariService {

    private final UsuariMapper usuariMapper;
    private final UsuariRepository usuariRepository;

    public Flux<UsuariDTO> getAllUsuaris() {
        return usuariRepository.findAll()
                .map(usuariMapper::toDto);
    }

    public Mono<Void> deleteUsuari(String id) {
        return usuariRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Usuari no trobat amb ID: " + id)))
                .flatMap(usuariRepository::delete);
    }
}
