package com.myfinance.backend.config.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettings {

    @Id
    @NotNull(message = "El ID de usuario no puede ser nulo")
    @Column(nullable = false, unique = true)
    private Long userId;

    // Configuración de autenticación de dos factores
    @NotNull(message = "La configuración de autenticación de dos factores no puede ser nula")
    private Boolean twoFactorAuth = false;

    // Método preferido para autenticación (sms o correo electrónico)
    @NotNull(message = "El método de autenticación preferido no puede ser nulo")
    @Pattern(regexp = "sms|email", message = "El método de autenticación debe ser 'sms' o 'email'")
    private String preferredAuthMethod;

    // Método preferido para recuperación de contraseña (sms o correo electrónico)
    @NotNull(message = "El método de recuperación de contraseña preferido no puede ser nulo")
    @Pattern(regexp = "sms|email", message = "El método de recuperación de contraseña debe ser 'sms' o 'email'")
    private String preferredPasswordRecovery;
}