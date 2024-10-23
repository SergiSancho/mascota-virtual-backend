package cat.itacademy.mascota_virtual_back.persistence.repositories;

import cat.itacademy.mascota_virtual_back.persistence.entities.MascotaEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MascotaRepository extends ReactiveMongoRepository<MascotaEntity, String> {

    Flux<MascotaEntity> findByPropietariId(String propietariId);
}