package cat.itacademy.mascota_virtual_back.dto;


import cat.itacademy.mascota_virtual_back.domain.enums.RolUsuari;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuariDTO {

    private String id;

    @NotNull(message = "El nom no pot ser nul")
    @NotEmpty(message = "El nom no pot estar buit")
    private String nomUsuari;

    private RolUsuari rol;
}
