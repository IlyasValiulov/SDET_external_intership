package extension;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProjectProperties {
    private static final Properties prop = new Properties();
    private static final String propertyPath = "data.properties";

    public static String getProperty(String key) throws IOException {
        try (InputStream input = ProjectProperties.class.getClassLoader().getResourceAsStream(propertyPath)) {
            prop.load(input);
        }
        return prop.getProperty(key);
    }
}
