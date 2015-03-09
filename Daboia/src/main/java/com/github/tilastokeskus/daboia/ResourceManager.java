
package com.github.tilastokeskus.daboia;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author tilastokeskus
 */
public class ResourceManager {
    
    private static final String PATH = ResourceManager.class
                                        .getProtectionDomain().getCodeSource()
                                        .getLocation().getPath();
    
    public static void exportConfigIfNeeded() throws IOException {
        exportResource(getConfigFilePath());
    }
    
    public static void exportLogicsIfNeeded() throws IOException {
        exportResource(getLogicFilePath());
    }
    
    public static void exportResource(String resourceName) throws IOException {        
        File parentFile = new File(PATH).getParentFile();
        String jarFolder = parentFile.getPath().replace('\\', '/');
        
        File resourceFile = new File(jarFolder + resourceName);
        resourceFile.getParentFile().mkdirs();
        resourceFile.createNewFile();
        
        try (InputStream inStream = ResourceManager.class.getResourceAsStream(resourceName);
                OutputStream outStream = new FileOutputStream(resourceFile);) {            
            int readBytes;
            byte[] buffer = new byte[4096];
            while ((readBytes = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, readBytes);
            }
        }
    }

    public static String getExternalConfigFilePath() {
        return new File(PATH).getParentFile().getPath() + "/config/daboia.conf";
    }
    
    public static String getConfigFilePath() {
        return "/config/daboia.conf";
    }

    public static String getExternalLogicDirectoryPath() {
        return new File(PATH).getParentFile().getPath() + "/logic/";
    }
    
    public static String getLogicFilePath() {
        return "/logic/inner/Flood.jar";
    }
    
}
