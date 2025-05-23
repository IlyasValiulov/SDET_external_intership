package extension;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectProperties {
    private static final Properties prop = new Properties();
    private static final String propertyPath = "src/test/resources/data.properties";

    public static String getProperty(String key) throws IOException {
        prop.load(new FileInputStream(propertyPath));
        return prop.getProperty(key);
    }
}
