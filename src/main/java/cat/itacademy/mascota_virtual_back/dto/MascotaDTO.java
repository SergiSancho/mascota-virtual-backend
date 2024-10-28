package cat.itacademy.mascota_virtual_back.dto;

import cat.itacademy.mascota_virtual_back.domain.enums.*;
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
public class MascotaDTO {

    private String id;

    @NotNull(message = "El nom no pot ser nul")
    @NotEmpty(message = "El nom no pot estar buit")
    private String nom;

    @NotNull(message = "El tipus no pot ser nul")
    private TipusMascota tipus;

    @NotNull(message = "El color no pot ser nul")
    private ColorMascota color;

    private EntornMascota entorn;
    private int energia;
    private int anim;
    private EstatMascota estat;

    //@NotNull(message = "El propietariId no pot ser nul")
    private String propietariId;

    private AccioMascota accio;

    private EntornMascota nouEntorn;
}
