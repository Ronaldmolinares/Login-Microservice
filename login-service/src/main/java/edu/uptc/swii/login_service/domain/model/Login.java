package edu.uptc.swii.login_service.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de autenticación del usuario")
public class Login {
    private Long id;
    @Schema(description = "Identificador único del usuario", example = "Juan Pedro")
    private Long userId;
    @Schema(description = "Contraseña del usuario", example = "Juan123")
    private String password;

    public Login(){

    }

    public Login(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
