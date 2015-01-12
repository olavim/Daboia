
package com.github.tilastokeskus.daboia.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Configuration {
    
    private Map<String, String> configs;
    
    public Configuration() {
        this.configs = new HashMap<>();
        this.configs.put("username", "user" + (new Random().nextInt(10000) + 1));
    }
    
    public boolean set(String key, String value) {
        if (!this.configs.containsKey(key)) {
            return false;
        }
        
        this.configs.replace(key, value);
        return true;
    }
    
    public boolean setIgnoreNull(String key, String value) {
        if (value != null && !value.isEmpty()) {
            return set(key, value);
        }
        
        return false;
    }
    
    public Object get(String key) {
        if (!this.configs.containsKey(key)) {
            throw new IllegalArgumentException("Key does not exist");
        }
        
        return this.configs.get(key);
    }

}
