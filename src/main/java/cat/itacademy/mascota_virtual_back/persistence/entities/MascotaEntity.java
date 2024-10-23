package cat.itacademy.mascota_virtual_back.persistence.entities;

import cat.itacademy.mascota_virtual_back.domain.enums.ColorMascota;
import cat.itacademy.mascota_virtual_back.domain.enums.EntornMascota;
import cat.itacademy.mascota_virtual_back.domain.enums.EstatMascota;
import cat.itacademy.mascota_virtual_back.domain.enums.TipusMascota;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "mascotes")
public class MascotaEntity {

    private String id;
    private String nom;
    private TipusMascota tipus;
    private ColorMascota color;
    private EntornMascota entorn;
    private int energia;
    private int anim;
    private EstatMascota estat;
    private String propietariId;
}
