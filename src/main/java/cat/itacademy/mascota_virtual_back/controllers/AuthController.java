package cat.itacademy.mascota_virtual_back.controllers;

import cat.itacademy.mascota_virtual_back.dto.AuthRequest;
import cat.itacademy.mascota_virtual_back.dto.AuthResponse;
import cat.itacademy.mascota_virtual_back.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticació Controller", description = "Controlador per a l'autenticació d'usuaris (registre i inici de sessió).")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Registrar(crear) un nou usuari", description = "Aquest endpoint permet registrar(crear) un nou usuari a l'app.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuari registrat correctament."),
            @ApiResponse(responseCode = "400", description = "Dades de registre invàlides."),
            @ApiResponse(responseCode = "409", description = "El nom d'usuari ja està en ús.")
    })
    public Mono<ResponseEntity<AuthResponse>> register(@Valid @RequestBody AuthRequest authRequest) {
        return authService.registerUser(authRequest)
                .map(authResponse -> ResponseEntity.status(HttpStatus.CREATED).body(authResponse));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sessió", description = "Aquest endpoint permet a un usuari iniciar sessió a l'app.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inici de sessió correcte."),
            @ApiResponse(responseCode = "401", description = "Credencials invàlides.")
    })
    public Mono<ResponseEntity<AuthResponse>> login(@Valid @RequestBody AuthRequest authRequest) {
        return authService.loginUser(authRequest)
                .map(ResponseEntity::ok);
    }
}

