package com.myfinance.backend.config.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myfinance.backend.config.entities.ApiResponse;
import com.myfinance.backend.config.entities.AppUser;
import com.myfinance.backend.config.entities.FavoriteColors;
import com.myfinance.backend.config.repositories.FavoriteColorsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingsColorsService {

    private final FavoriteColorsRepository colorsRepository;
    private final RestTemplate restTemplate;

    public ResponseEntity<?> viewUserFavoriteColors(String token) {
        try {

            Long userId = getUserId(token);

            if (userId == null) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pudo cargar el usuario.", null);
            }

            if (colorsRepository.findById(userId).isEmpty()) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pudieron consultar los colores del usuario.",
                        null);
            }

            FavoriteColors userFavoriteColors = colorsRepository.findById(userId).get();

            return createApiResponse(HttpStatus.OK, "Consulta de los colores del usuario exitosa.", userFavoriteColors);

        } catch (Exception e) {
            return createApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al intentar consultar los colores del usuario.", null);
        }
    }

    public ResponseEntity<?> newFavoriteColors(String token) {
        try {

            Long userId = getUserId(token);

            if (userId == null) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pudo cargar el usuario.", null);
            }

            if (colorsRepository.findById(userId).isPresent()) {
                return createApiResponse(HttpStatus.UNAUTHORIZED, "Ya existen unos colores definidos para el usurio.",
                        null);
            }

            FavoriteColors favoriteColors = new FavoriteColors(userId, "teal", "red", "cyan");
            colorsRepository.save(favoriteColors);

            return createApiResponse(HttpStatus.CREATED, "Colores registrados con éxito.", null);

        } catch (Exception e) {
            return createApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al intentar registrar los colores: " + e.getMessage(),
                    null);
        }
    }

    public ResponseEntity<?> updateFavoriteColors(FavoriteColors updateFavoriteColors, String token) {
        try {

            Long userId = getUserId(token);

            if (userId == null) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pudo cargar el usuario.", null);
            }

            if (colorsRepository.findById(userId).isEmpty()) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pueden actualizar los colores.",
                        null);
            }

            FavoriteColors favoriteColors = colorsRepository.findById(userId).get();

            favoriteColors.setUserId(userId);
            favoriteColors.setIncomeColor(updateFavoriteColors.getIncomeColor());
            favoriteColors.setSavingsColor(updateFavoriteColors.getSavingsColor());
            favoriteColors.setExpenseColor(updateFavoriteColors.getExpenseColor());

            colorsRepository.save(favoriteColors);

            return createApiResponse(HttpStatus.OK, "Los colores fueron actualizados con éxito.", null);
        } catch (Exception e) {
            // Manejo de errores
            return createApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al intentar actualizar los colores.", null);
        }
    }

    // Restablecer a colores predeterminados
    public ResponseEntity<?> resetToDefaultColors(String token) {
        try {

            Long userId = getUserId(token);

            if (userId == null) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pudo cargar el usuario.", null);
            }

            if (colorsRepository.findById(userId).isEmpty()) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pudo restablecer los colores del usuario",
                        null);
            }

            // Obtener el movimiento existente
            FavoriteColors favoriteColors = colorsRepository.findById(userId).get();

            // Actualizar los campos del movimiento
            favoriteColors.setIncomeColor("teal");
            favoriteColors.setSavingsColor("cyan");
            favoriteColors.setExpenseColor("red");

            // Guardar el movimiento actualizado
            colorsRepository.save(favoriteColors);

            // Respuesta exitosa
            return createApiResponse(HttpStatus.OK, "Los colores restablecidos con éxito.", null);
        } catch (Exception e) {
            // Manejo de errores
            return createApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al intentar restablecer los colores.", null);
        }
    }

    // Metodo para solicitar el id del usuario correspondiente segun el id
    private Long getUserId(String token) {
        try {

            String userServiceUrl = "http://192.168.1.9:8081/api/users/view";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<ApiResponse<AppUser>> response = restTemplate.exchange(
                    userServiceUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<ApiResponse<AppUser>>() {
                    });

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                ApiResponse<AppUser> apiResponse = response.getBody();

                if (apiResponse != null && apiResponse.getData() != null) {
                    AppUser user = apiResponse.getData();
                    return user.getId();
                }
            }

            return null;

        } catch (Exception e) {
            return null;
        }
    }

    // Metodo para generar el formato de respuesta adecuado
    private ResponseEntity<?> createApiResponse(HttpStatus status, String message, Object data) {
        ApiResponse<Object> response = new ApiResponse<>(message, data);
        return ResponseEntity.status(status).body(response);
    }

}
