package cat.itacademy.mascota_virtual_back.persistence.entities;

import cat.itacademy.mascota_virtual_back.domain.enums.RolUsuari;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuaris")
public class UsuariEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String nomUsuari;

    private String contrasenya;
    private RolUsuari rol;

}