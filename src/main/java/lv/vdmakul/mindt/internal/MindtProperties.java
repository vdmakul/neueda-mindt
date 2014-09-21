package lv.vdmakul.mindt.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

@Deprecated
public class MindtProperties {

    private static final Logger logger = LoggerFactory.getLogger(MindtProperties.class);

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
            properties.load(MindtProperties.class.getResourceAsStream("application.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return properties;
    }

    @Deprecated
    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    @Deprecated
    public static void setProperty(String key, String value) {
        getProperties().setProperty(key, value);
    }


}
