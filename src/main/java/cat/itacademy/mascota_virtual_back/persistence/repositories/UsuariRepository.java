package cat.itacademy.mascota_virtual_back.persistence.repositories;

import cat.itacademy.mascota_virtual_back.persistence.entities.UsuariEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsuariRepository extends ReactiveMongoRepository<UsuariEntity, String> {

    Mono<UsuariEntity> findByNomUsuari(String nomUsuari);
}