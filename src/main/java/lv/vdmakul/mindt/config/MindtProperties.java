package lv.vdmakul.mindt.config;

import java.io.IOException;
import java.util.Properties;

public class MindtProperties {

    public static final String URL_PROPERTY = "service.calculator.url";

    private static Properties instance;

    private static Properties getProperties() { //not thread safe
        if (instance == null) {
            instance = loadProperties();
        }
        return instance;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(MindtProperties.class.getResourceAsStream("/mindt.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    public static void setProperty(String key, String value) {
        getProperties().setProperty(key, value);
    }


}
