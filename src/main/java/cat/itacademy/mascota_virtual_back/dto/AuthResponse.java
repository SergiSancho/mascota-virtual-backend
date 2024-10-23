package cat.itacademy.mascota_virtual_back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    UsuariDTO usuariDTO;
    String token;
}
