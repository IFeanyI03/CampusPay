package com.example.campusPayApp;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            // 2a. Check if the file was found
            if (input == null) {
                System.err.println("Sorry, unable to find config.properties");
                 // Stop loading if the file is missing
            }
            // 2b. Read the settings from the file into the 'properties' object
            properties.load(input);
        } catch (IOException ex) {
            // 2c. Handle any errors that occur while reading the file
            ex.printStackTrace();
        }
    }

    // 3. A way to get a specific setting by its key
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
