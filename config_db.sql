-- Crear la base de datos config_db
CREATE DATABASE IF NOT EXISTS config_db;

-- Usar la base de datos recién creada
USE config_db;

-- Crear la tabla FavoriteColors
CREATE TABLE IF NOT EXISTS favorite_colors (
    user_id BIGINT NOT NULL,
    income_color VARCHAR(20) NOT NULL,
    expense_color VARCHAR(20) NOT NULL,
    savings_color VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id),
    CHECK (income_color IN ('blue', 'purple', 'pink', 'red', 'orange', 'yellow', 'green', 'teal', 'cyan')),
    CHECK (expense_color IN ('blue', 'purple', 'pink', 'red', 'orange', 'yellow', 'green', 'teal', 'cyan')),
    CHECK (savings_color IN ('blue', 'purple', 'pink', 'red', 'orange', 'yellow', 'green', 'teal', 'cyan'))
);

-- Crear la tabla user_settings
CREATE TABLE IF NOT EXISTS user_settings (
    user_id BIGINT NOT NULL,
    two_factor_auth BOOLEAN NOT NULL DEFAULT FALSE,
    preferred_auth_method ENUM('sms', 'email') NOT NULL,
    preferred_password_recovery ENUM('sms', 'email') NOT NULL,
    PRIMARY KEY (user_id),
    CONSTRAINT fk_user_settings_user_id FOREIGN KEY (user_id)
        REFERENCES favorite_colors(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);