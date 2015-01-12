
package com.github.tilastokeskus.daboia.config;

import java.io.IOException;
import java.net.URL;
import org.ini4j.Ini;

public class ConfigurationManager {
    
    private static Configuration config;

    public static void loadConfigs() throws IOException {
        config = new Configuration();
        
        String path = "/config/daboia.conf";
        URL url = ConfigurationManager.class.getResource(path);
        System.out.println(url.getPath());
        Ini ini = new Ini(url);
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
