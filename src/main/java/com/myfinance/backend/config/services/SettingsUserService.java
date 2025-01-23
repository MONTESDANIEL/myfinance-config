package com.myfinance.backend.config.services;

import java.util.Objects;

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
import com.myfinance.backend.config.entities.UserSettings;
import com.myfinance.backend.config.repositories.UserSettingsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettingsUserService {

    private final UserSettingsRepository userSettingsRepository;
    private final RestTemplate restTemplate;

    public ResponseEntity<?> viewUserSettings(String token) {
        try {
            Long userId = getUserId(token);

            // Verificación temprana de la existencia del usuario
            if (userId == null) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pudo cargar el usuario.", null);
            }

            if (userSettingsRepository.findById(userId).isEmpty()) {
                return createApiResponse(HttpStatus.BAD_REQUEST,
                        "No se encuentran las configuraciones del usuario.", null);
            }

            UserSettings userSettings = userSettingsRepository.findById(userId).get();

            return createApiResponse(HttpStatus.OK, "Consulta de las configuraciones del usuario exitosa.",
                    userSettings);

        } catch (Exception e) {
            return createApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al intentar consultar los colores.", null);
        }
    }

    public ResponseEntity<?> newUserSettings(String token) {
        try {

            Long userId = getUserId(token);

            System.out.println("Usuario del token" + userId);
            // Verificación temprana de la existencia del usuario
            if (userId == null) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pudo cargar el usuario.", null);
            }

            if (userSettingsRepository.findById(userId).isPresent()) {
                return createApiResponse(HttpStatus.BAD_REQUEST,
                        "Ya existen unas configuraciones para este usuario.", null);
            }

            UserSettings userSettings = new UserSettings(userId, false, "email", "email");

            userSettingsRepository.save(userSettings);
            // Respuesta exitosa
            return createApiResponse(HttpStatus.CREATED, "Nuevas configuraciones de usuario registrdas con éxito.",
                    userSettings);

        } catch (Exception e) {
            // Manejo de errores
            return createApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al intentar registrar las configuraciones de usuario: " + e.getMessage(),
                    null);
        }
    }

    public ResponseEntity<?> updateUserSettings(UserSettings updateUserSettings, String token) {
        try {

            Long userId = getUserId(token);

            // Verificación temprana de la existencia del usuario
            if (userId == null) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pudo cargar el usuario.", null);
            }

            if (!Objects.equals(userId, updateUserSettings.getUserId())) {
                return createApiResponse(HttpStatus.UNAUTHORIZED, "Acceso no autorizado.", null);
            }

            if (userSettingsRepository.findById(userId).isEmpty()) {
                return createApiResponse(HttpStatus.BAD_REQUEST, "No se pueden actualizar las configuraciones.",
                        null);
            }

            UserSettings userSettings = userSettingsRepository.findById(userId).get();

            userSettings.setTwoFactorAuth(updateUserSettings.getTwoFactorAuth());
            userSettings.setPreferredAuthMethod(updateUserSettings.getPreferredAuthMethod());
            userSettings.setPreferredPasswordRecovery(updateUserSettings.getPreferredPasswordRecovery());

            userSettingsRepository.save(userSettings);

            return createApiResponse(HttpStatus.OK, "Las configuraciones de usuario fueron actualizados con éxito.",
                    null);
        } catch (Exception e) {
            // Manejo de errores
            return createApiResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ocurrió un error al intentar actualizar las configuraciones de usuario.", null);
        }
    }

    // Metodo para solicitar el id del usuario correspondiente segun el id
    private Long getUserId(String token) {
        try {

            String userServiceUrl = "http://192.168.1.2:8081/api/users/view";

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
