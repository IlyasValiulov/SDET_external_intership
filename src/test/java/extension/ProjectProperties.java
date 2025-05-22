package extension;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectProperties {
    private static Properties prop = new Properties();

    public static String getProperty(String key) throws IOException {
        prop.load(new FileInputStream("src/test/resources/data.properties"));
        return prop.getProperty(key);
    }
}
