package com.myfinance.backend.config.repositories;

import org.springframework.data.repository.CrudRepository;

import com.myfinance.backend.config.entities.FavoriteColors;

public interface FavoriteColorsRepository extends CrudRepository<FavoriteColors, Long> {

}
