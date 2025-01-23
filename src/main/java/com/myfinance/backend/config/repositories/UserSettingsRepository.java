package com.myfinance.backend.config.repositories;

import org.springframework.data.repository.CrudRepository;

import com.myfinance.backend.config.entities.UserSettings;

public interface UserSettingsRepository extends CrudRepository<UserSettings, Long> {

}
