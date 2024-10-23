package cat.itacademy.mascota_virtual_back.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull(message = "El nom no pot ser nul")
    @NotEmpty(message = "El nom no pot estar buit")
    private String nomUsuari;

    @NotNull(message = "La contrasenya no pot ser nul")
    @NotEmpty(message = "La contrasenya no pot estar buida")
    private String contrasenya;
}
