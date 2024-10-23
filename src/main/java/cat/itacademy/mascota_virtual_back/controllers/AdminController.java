package cat.itacademy.mascota_virtual_back.controllers;

import cat.itacademy.mascota_virtual_back.dto.MascotaDTO;
import cat.itacademy.mascota_virtual_back.dto.UsuariDTO;
import cat.itacademy.mascota_virtual_back.services.MascotaService;
import cat.itacademy.mascota_virtual_back.services.UsuariService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Controlador d'Administració", description = "Controlador de l'administrador per gestionar mascotes i usuaris.")
public class AdminController {

    private final MascotaService mascotaService;
    private final UsuariService usuariService;

    @GetMapping("/mascotes")
    @Operation(summary = "Obtenir totes les mascotes", description = "Aquest endpoint a l'administrador permet obtenir una llista de totes les mascotes registrades.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascotes retornades correctament.")
    })
    public Mono<ResponseEntity<Flux<MascotaDTO>>> getAllMascotas() {
        Flux<MascotaDTO> mascotas = mascotaService.getAllMascotes();
        return Mono.just(ResponseEntity.ok(mascotas));
    }

    @GetMapping("/mascotes/{mascotaId}")
    @Operation(summary = "Obtenir detalls d'una mascota", description = "Aquest endpoint permet a l'administrador obtenir informació detallada d'una mascota específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mascota retornada correctament."),
            @ApiResponse(responseCode = "404", description = "Mascota no trobada.")
    })
    public Mono<ResponseEntity<MascotaDTO>> getMascotaById(@PathVariable String mascotaId) {
        return mascotaService.getMascotaById(mascotaId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/mascotes/{mascotaId}")
    @Operation(summary = "Eliminar una mascota", description = "Aquest endpoint permet a l'administrador eliminar una mascota específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mascota eliminada correctament."),
            @ApiResponse(responseCode = "404", description = "Mascota no trobada.")
    })
    public Mono<ResponseEntity<Void>> deleteMascota(@PathVariable String mascotaId) {
        return mascotaService.deleteMascota(mascotaId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @GetMapping("/users")
    @Operation(summary = "Obtenir tots els usuaris", description = "Aquest endpoint permet a l'administrador obtenir una llista de tots els usuaris registrats.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuaris retornats correctament."),
            @ApiResponse(responseCode = "404", description = "No s'han trobat usuaris.")
    })
    public Mono<ResponseEntity<Flux<UsuariDTO>>> getAllUsuaris() {
        Flux<UsuariDTO> usuaris = usuariService.getAllUsuaris();
        return Mono.just(ResponseEntity.ok(usuaris));
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "Eliminar un usuari", description = "Aquest endpoint permet a l'administrador eliminar un usuari específic.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuari eliminat correctament.")
    })
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String userId) {
        return usuariService.deleteUsuari(userId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

}
