
package com.github.tilastokeskus.daboia.config;

import java.io.File;
import java.io.IOException;
import org.ini4j.Ini;

public class ConfigurationManager {
    
    private static Configuration config;

    public static void loadConfigs() throws IOException {
        config = new Configuration();
        
        Ini ini = new Ini(new File("daboia.conf"));
        Ini.Section networkConfig = ini.get("network");
        
        for (String key : networkConfig.keySet()) {
            String value = networkConfig.get(key);
            config.setIgnoreNull(key, value);
        }
    }
    
    public static Configuration getConfig() {
        return config;
    }
    
}
