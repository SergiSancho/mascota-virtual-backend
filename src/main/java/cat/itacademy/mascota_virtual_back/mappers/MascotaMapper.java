package cat.itacademy.mascota_virtual_back.mappers;

import cat.itacademy.mascota_virtual_back.domain.Mascota;
import cat.itacademy.mascota_virtual_back.dto.MascotaDTO;
import cat.itacademy.mascota_virtual_back.persistence.entities.MascotaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MascotaMapper {

    MascotaDTO toDto(Mascota mascota);

    MascotaDTO toDto(MascotaEntity mascotaEntity);

    Mascota toDomain(MascotaDTO mascotaDto);

    MascotaEntity toEntity(Mascota mascota);

    Mascota toDomain(MascotaEntity mascotaEntity);

}

