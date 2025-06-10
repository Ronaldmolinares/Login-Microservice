package edu.uptc.swii.login_service.infrastructure.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uptc.swii.login_service.domain.model.Login;
import edu.uptc.swii.login_service.domain.ports.in.LoginUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticación", description = "API para gestión de autenticación de usuarios")
public class LoginController {

    private final LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @Operation(summary = "Registrar nuevo usuario", 
               description = "Crea un nuevo usuario en el sistema con las credenciales proporcionadas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", 
                     content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Login loginRequest) {
        try {
            loginUseCase.register(loginRequest.getUserId(), loginRequest.getPassword());
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Error: " + e.getMessage());
        }
    }

    @Operation(summary = "Iniciar sesión", 
               description = "Autentica al usuario y devuelve un token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa", 
                     content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody Login loginRequest) {
        String token = loginUseCase.login(loginRequest.getUserId(), loginRequest.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}