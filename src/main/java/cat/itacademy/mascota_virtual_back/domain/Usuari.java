package cat.itacademy.mascota_virtual_back.domain;

import cat.itacademy.mascota_virtual_back.domain.enums.RolUsuari;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuari {

    private String id;
    private String nomUsuari;
    private String contrasenya;
    private RolUsuari rol;
}
