package by.training.zakharchenya.courseproject.database;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/** Class is responsible for initializing parameters for driver manager.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
class DBInitializer {
    private static final Logger LOG = LogManager.getLogger();

    final String DRIVER;
    final String URL;
    final String LOGIN;
    final int POOL_SIZE;
    final int RECONNECTION_AMOUNT;
    final int RECONNECTION_TIMEOUT;
    final String PASSWORD;

    DBInitializer() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.database");
            DRIVER = resourceBundle.getString("db.driver");
            URL = resourceBundle.getString("db.url");
            LOGIN = resourceBundle.getString("db.user");
            POOL_SIZE = Integer.valueOf(resourceBundle.getString("db.pool.size"));
            if (POOL_SIZE < 1) {
                LOG.log(Level.FATAL, "Pool size can't be non positive.");
                throw new RuntimeException("Pool size can't be non positive.");
            }
            RECONNECTION_AMOUNT = Integer.valueOf(resourceBundle.getString("db.reconnection.amount"));
            if (RECONNECTION_AMOUNT < 0) {
                LOG.log(Level.FATAL, "Reconnection amount can't be negative.");
                throw new RuntimeException("Reconnection amount can't be negative.");
            }
            RECONNECTION_TIMEOUT = Integer.parseInt(resourceBundle.getString("db.reconnection.timeout"));
            if (RECONNECTION_TIMEOUT < 0) {
                LOG.log(Level.FATAL, "Reconnection timeout can't be negative.");
                throw new RuntimeException("Reconnection timeout can't be negative.");
            }
            PASSWORD = resourceBundle.getString("db.password");
        } catch (NumberFormatException | MissingResourceException e) {
            LOG.log(Level.FATAL, "Cannot initialize the database connection.", e);
            throw new RuntimeException("Cannot initialize the database connection.", e);
        }
    }

}
