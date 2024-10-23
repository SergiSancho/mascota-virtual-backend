package cat.itacademy.mascota_virtual_back.mappers;

import cat.itacademy.mascota_virtual_back.domain.Mascota;
import cat.itacademy.mascota_virtual_back.domain.Usuari;
import cat.itacademy.mascota_virtual_back.dto.MascotaDTO;
import cat.itacademy.mascota_virtual_back.dto.UsuariDTO;
import cat.itacademy.mascota_virtual_back.persistence.entities.MascotaEntity;
import cat.itacademy.mascota_virtual_back.persistence.entities.UsuariEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuariMapper {

    UsuariDTO toDto(Usuari usuari);

    UsuariDTO toDto(UsuariEntity usuariEntity);

    Usuari toDomain(UsuariDTO usuariDTO);

    UsuariEntity toEntity(Usuari usuari);

    Usuari toDomain(UsuariEntity usuariEntity);
}
