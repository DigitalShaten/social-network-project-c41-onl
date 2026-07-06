package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class ConnectionManager {

    private static final String PROPERTIES_FILE = "db.properties";
    private static final String DB_URL = "db.url";
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_POOL_MAXIMUM_POOL_SIZE = "db.pool.maximumPoolSize";
    private static final String DB_POOL_MINIMUM_IDLE = "db.pool.minimumIdle";
    private static final String DB_POOL_CONNECTION_TIMEOUT = "db.pool.connectionTimeout";
    private static final String DB_POOL_IDLE_TIMEOUT = "db.pool.idleTimeout";
    private static final String DB_POOL_MAX_LIFETIME = "db.pool.maxLifetime";
    private static final String DB_POOL_INITIALIZATION_FAIL_TIMEOUT = "db.pool.initializationFailTimeout";

    private static final int DEFAULT_MINIMUM_IDLE = 2;
    private static final long DEFAULT_CONNECTION_TIMEOUT = 30000L;
    private static final long DEFAULT_IDLE_TIMEOUT = 600000L;
    private static final long DEFAULT_MAX_LIFETIME = 1800000L;
    private static final long DEFAULT_INITIALIZATION_FAIL_TIMEOUT = 1L;

    private static final HikariDataSource DATA_SOURCE = createDataSource();

    private ConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new SQLException("Failed to get database connection from HikariCP pool. Check db.properties.", e);
        }
    }

    private static HikariDataSource createDataSource() {
        Properties properties = loadProperties();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getRequiredProperty(properties, DB_URL));
        config.setUsername(getRequiredProperty(properties, DB_USER));
        config.setPassword(getRequiredProperty(properties, DB_PASSWORD));
        config.setMaximumPoolSize(getRequiredPositiveIntProperty(properties, DB_POOL_MAXIMUM_POOL_SIZE));
        config.setMinimumIdle(getNonNegativeIntProperty(properties, DB_POOL_MINIMUM_IDLE, DEFAULT_MINIMUM_IDLE));
        config.setConnectionTimeout(getPositiveLongProperty(properties, DB_POOL_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT));
        config.setIdleTimeout(getPositiveLongProperty(properties, DB_POOL_IDLE_TIMEOUT, DEFAULT_IDLE_TIMEOUT));
        config.setMaxLifetime(getPositiveLongProperty(properties, DB_POOL_MAX_LIFETIME, DEFAULT_MAX_LIFETIME));
        config.setInitializationFailTimeout(getLongProperty(properties, DB_POOL_INITIALIZATION_FAIL_TIMEOUT, DEFAULT_INITIALIZATION_FAIL_TIMEOUT));

        return new HikariDataSource(config);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = ConnectionManager.class
                .getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException(PROPERTIES_FILE + " not found in classpath");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read " + PROPERTIES_FILE, e);
        }

        return properties;
    }

    private static String getRequiredProperty(Properties properties, String key) {
        String value = getProperty(properties, key, "");
        if (value.isEmpty()) {
            throw new IllegalStateException("Property " + key + " is required");
        }
        return value;
    }

    private static String getProperty(Properties properties, String key, String defaultValue) {
        return properties.getProperty(key, defaultValue).trim();
    }

    private static int getRequiredPositiveIntProperty(Properties properties, String key) {
        String value = getRequiredProperty(properties, key);
        int number = parseIntProperty(key, value);

        if (number <= 0) {
            throw new IllegalStateException("Property " + key + " must be positive");
        }

        return number;
    }

    private static int getNonNegativeIntProperty(Properties properties, String key, int defaultValue) {
        String value = getProperty(properties, key, String.valueOf(defaultValue));
        int number = parseIntProperty(key, value);

        if (number < 0) {
            throw new IllegalStateException("Property " + key + " must not be negative");
        }

        return number;
    }

    private static long getPositiveLongProperty(Properties properties, String key, long defaultValue) {
        long number = getLongProperty(properties, key, defaultValue);

        if (number <= 0) {
            throw new IllegalStateException("Property " + key + " must be positive");
        }

        return number;
    }

    private static long getLongProperty(Properties properties, String key, long defaultValue) {
        String value = getProperty(properties, key, String.valueOf(defaultValue));

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Property " + key + " must be a number", e);
        }
    }

    private static int parseIntProperty(String key, String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Property " + key + " must be a number", e);
        }
    }
}
