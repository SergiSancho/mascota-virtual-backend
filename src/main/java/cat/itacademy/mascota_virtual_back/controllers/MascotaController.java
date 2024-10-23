package cat.itacademy.mascota_virtual_back.controllers;

import cat.itacademy.mascota_virtual_back.dto.MascotaDTO;
import cat.itacademy.mascota_virtual_back.services.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Mascota Controller", description = "Controlador per gestionar les mascotes virtuals dels usuaris.")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @PostMapping("/mascotes")
    @Operation(summary = "Crear una nova mascota", description = "Aquest endpoint permet a un usuari autenticat crear una nova mascota.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mascota creada correctament."),
            @ApiResponse(responseCode = "403", description = "Usuari no autenticat."),
            @ApiResponse(responseCode = "400", description = "Dades invàlides.")
    })
    public Mono<ResponseEntity<MascotaDTO>> createMascota(@RequestBody MascotaDTO mascotaDTO) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    String authenticatedUserId = (String) securityContext.getAuthentication().getDetails();
                    if (authenticatedUserId == null) {
                        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(null));
                    }
                    mascotaDTO.setPropietariId(authenticatedUserId);
                    return mascotaService.createMascota(mascotaDTO)
                            .map(mascota -> ResponseEntity.status(HttpStatus.CREATED).body(mascota));
                });
    }


    @GetMapping("/mascotes")
    @Operation(summary = "Obtenir totes les mascotes de l'usuari autenticat", description = "Aquest endpoint retorna totes les mascotes de l'usuari actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascotes retornades correctament."),
            @ApiResponse(responseCode = "403", description = "Usuari no autenticat.")
    })
    public Mono<ResponseEntity<Flux<MascotaDTO>>> getAllMascotesByUsuariId() {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    String authenticatedUserId = (String) securityContext.getAuthentication().getDetails();
                    if (authenticatedUserId == null) {
                        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(Flux.empty()));
                    }
                    Flux<MascotaDTO> mascotes = mascotaService.getAllMascotesByPropietariId(authenticatedUserId);
                    return Mono.just(ResponseEntity.ok(mascotes));
                });
    }

    @GetMapping("/mascotes/{mascotaId}")
    @Operation(summary = "Obtenir detalls d'una mascota", description = "Aquest endpoint permet a un usuari obtenir informació detallada d'una mascota específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota retornada correctament."),
            @ApiResponse(responseCode = "403", description = "Accés no autoritzat a la mascota.")
    })
    public Mono<ResponseEntity<MascotaDTO>> getMascotaById(@PathVariable String mascotaId) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    String authenticatedUserId = (String) securityContext.getAuthentication().getDetails();
                    return mascotaService.getMascotaById(mascotaId)
                            .filter(mascota -> mascota.getPropietariId().equals(authenticatedUserId))
                            .map(ResponseEntity::ok)
                            .defaultIfEmpty(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
                });
    }

    @PutMapping("/mascotes/{mascotaId}")
    @Operation(summary = "Actualitzar una mascota", description = "Aquest endpoint permet a un usuari actualitzar els detalls d'una mascota.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota actualitzada correctament."),
            @ApiResponse(responseCode = "403", description = "Accés no autoritzat."),
            @ApiResponse(responseCode = "400", description = "Dades invàlides.")
    })
    public Mono<ResponseEntity<MascotaDTO>> updateMascota(@PathVariable String mascotaId, @RequestBody MascotaDTO mascotaDTO) {
        if (!mascotaDTO.getId().equals(mascotaId)) {
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
        }
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    String authenticatedUserId = (String) securityContext.getAuthentication().getDetails();
                    return mascotaService.updateMascota(mascotaDTO)
                            .filter(mascota -> mascota.getPropietariId().equals(authenticatedUserId))
                            .map(ResponseEntity::ok)
                            .defaultIfEmpty(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
                });
    }

    @DeleteMapping("/mascotes/{mascotaId}")
    @Operation(summary = "Eliminar una mascota", description = "Aquest endpoint permet a un usuari eliminar una mascota del seu compte.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mascota eliminada correctament."),
            @ApiResponse(responseCode = "403", description = "Accés no autoritzat a la mascota.")
    })
    public Mono<ResponseEntity<Void>> deleteMascota(@PathVariable String mascotaId) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(securityContext -> {
                    String authenticatedUserId = (String) securityContext.getAuthentication().getDetails();
                    return mascotaService.getMascotaById(mascotaId)
                            .filter(mascota -> mascota.getPropietariId().equals(authenticatedUserId))
                            .flatMap(mascota -> mascotaService.deleteMascota(mascotaId)
                                    .then(Mono.just(ResponseEntity.noContent().<Void>build())))
                            .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).build()));
                });
    }

}
