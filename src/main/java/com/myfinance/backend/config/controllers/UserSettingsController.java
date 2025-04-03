package com.myfinance.backend.config.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myfinance.backend.config.entities.FavoriteColors;
import com.myfinance.backend.config.entities.UserSettings;
import com.myfinance.backend.config.services.SettingsColorsService;
import com.myfinance.backend.config.services.SettingsUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class UserSettingsController {

    private final SettingsColorsService settingsColors;
    private final SettingsUserService settingsUser;

    // User Settings
    @GetMapping("/viewUserSettings")
    public ResponseEntity<?> viewUserSettings(@RequestHeader("Authorization") String authorizationToken) {
        ResponseEntity<?> response = settingsUser.viewUserSettings(authorizationToken);
        return response;
    }

    @PostMapping("/newUserSettings")
    public ResponseEntity<?> newUserSettings(@RequestHeader("Authorization") String authorizationToken) {
        ResponseEntity<?> response = settingsUser.newUserSettings(authorizationToken);
        return response;
    }

    @PutMapping("/updateUserSettings")
    public ResponseEntity<?> updateUserSettings(
            @Valid @RequestBody UserSettings userSettings,
            @RequestHeader("Authorization") String authorizationToken) {
        ResponseEntity<?> response = settingsUser.updateUserSettings(userSettings, authorizationToken);
        return response;
    }

    @PutMapping("/resetToDefaultSettings")
    public ResponseEntity<?> resetToDefaultSettings(@RequestHeader("Authorization") String authorizationToken) {
        ResponseEntity<?> response = settingsUser.resetToDefaultSettings(authorizationToken);
        return response;
    }

    // Favorite Colors
    @GetMapping("/viewUserFavoriteColors")
    public ResponseEntity<?> viewUserFavoriteColors(@RequestHeader("Authorization") String authorizationToken) {
        ResponseEntity<?> response = settingsColors.viewUserFavoriteColors(authorizationToken);
        return response;
    }

    @PostMapping("/newFavoriteColors")
    public ResponseEntity<?> newFavoriteColors(@RequestHeader("Authorization") String authorizationToken) {
        ResponseEntity<?> response = settingsColors.newFavoriteColors(authorizationToken);
        return response;
    }

    @PutMapping("/updateFavoriteColors")
    public ResponseEntity<?> updateFavoriteColors(
            @Valid @RequestBody FavoriteColors favoriteColors,
            @RequestHeader("Authorization") String authorizationToken) {
        ResponseEntity<?> response = settingsColors.updateFavoriteColors(favoriteColors, authorizationToken);
        return response;
    }

    @PutMapping("/resetToDefaultColors")
    public ResponseEntity<?> resetToDefaultColors(@RequestHeader("Authorization") String authorizationToken) {
        ResponseEntity<?> response = settingsColors.resetToDefaultColors(authorizationToken);
        return response;
    }

}
