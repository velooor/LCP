package by.training.zakharchenya.courseproject.manager;

import java.util.ResourceBundle;
public class ConfigurationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.config");
    private ConfigurationManager() {  }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}