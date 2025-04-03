package com.myfinance.backend.config.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteColors {

    @Id
    @Column(nullable = false, unique = true)
    private Long userId;

    // Color para ingresos
    @NotNull(message = "El color del ingreso no puede ser nulo")
    @Pattern(regexp = "blue|purple|pink|red|orange|yellow|green|teal|cyan", message = "El color del ingreso no es correcto")
    private String incomeColor;

    // Color para gastos
    @NotNull(message = "El color del gasto no puede ser nulo")
    @Pattern(regexp = "blue|purple|pink|red|orange|yellow|green|teal|cyan", message = "El color del gasto no es correcto")
    private String expenseColor;

    // Color para ahorros
    @NotNull(message = "El color del ahorro no puede ser nulo")
    @Pattern(regexp = "blue|purple|pink|red|orange|yellow|green|teal|cyan", message = "El color del ahorro no es correcto")
    private String savingsColor;
}