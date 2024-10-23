package cat.itacademy.mascota_virtual_back.services;

import cat.itacademy.mascota_virtual_back.domain.Mascota;
import cat.itacademy.mascota_virtual_back.domain.enums.EntornMascota;
import cat.itacademy.mascota_virtual_back.domain.enums.EstatMascota;
import cat.itacademy.mascota_virtual_back.dto.MascotaDTO;
import cat.itacademy.mascota_virtual_back.exceptions.ResourceNotFoundException;
import cat.itacademy.mascota_virtual_back.mappers.MascotaMapper;
import cat.itacademy.mascota_virtual_back.persistence.entities.MascotaEntity;
import cat.itacademy.mascota_virtual_back.persistence.repositories.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;


@Service
@RequiredArgsConstructor
public class MascotaService {

    private final MascotaMapper mascotaMapper;
    private final MascotaRepository mascotaRepository;

    public Mono<MascotaDTO> createMascota(MascotaDTO mascotaDTO) {
        Mascota mascota = mascotaMapper.toDomain(mascotaDTO);
        mascota.setEntorn(EntornMascota.CASA);
        mascota.setEnergia(50);
        mascota.setAnim(50);
        mascota.setEstat(EstatMascota.BE);

        MascotaEntity entity = mascotaMapper.toEntity(mascota);
        return mascotaRepository.save(entity).map(mascotaMapper::toDto);
    }

    public Mono<MascotaDTO> getMascotaById(String id) {
        return mascotaRepository.findById(id)
                .map(mascotaMapper::toDto)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Mascota no trobada amb ID: " + id)));
    }

    public Flux<MascotaDTO> getAllMascotesByPropietariId(String propietariId) {
        return mascotaRepository.findByPropietariId(propietariId)
                .map(mascotaMapper::toDto)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No trobades mascotes pel propietari amb ID: " + propietariId)));
    }

    public Flux<MascotaDTO> getAllMascotes() {
        return mascotaRepository.findAll()
                .map(mascotaMapper::toDto);
    }


        public Mono<MascotaDTO> updateMascota(MascotaDTO mascotaDTO) {
        return mascotaRepository.findById(mascotaDTO.getId())
                .flatMap(mascotaEntity -> {
                    Mascota mascota = mascotaMapper.toDomain(mascotaEntity);

                    if (mascotaDTO.getAccio() == null) {
                        throw new IllegalArgumentException("L'acció no pot ser nula.");
                    }

                    switch (mascotaDTO.getAccio()) {
                        case ALIMENTAR -> mascota.alimentar();
                        case JUGAR -> mascota.jugar();
                        case MIMS -> mascota.donarMims();
                        case CANVI_ENTORN -> {
                            if (mascotaDTO.getNouEntorn() == null) {
                                throw new IllegalArgumentException("El nou entorn no pot ser nul per l'acció CANVI_ENTORN");
                            }
                            mascota.canviarEntorn(mascotaDTO.getNouEntorn());
                        }
                        default -> throw new IllegalArgumentException("Acció no válida: " + mascotaDTO.getAccio());
                    }

                    return mascotaRepository.save(mascotaMapper.toEntity(mascota))
                            .map(mascotaMapper::toDto);
                })
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Mascota no trobada amb ID: " + mascotaDTO.getId())));
    }

    public Mono<Void> deleteMascota(String id) {
        return mascotaRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Mascota no trobada amb ID: " + id)))
                .flatMap(mascotaRepository::delete);
    }

}
