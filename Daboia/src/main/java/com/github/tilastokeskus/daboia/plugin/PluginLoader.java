
package com.github.tilastokeskus.daboia.plugin;

import com.github.tilastokeskus.daboia.ResourceManager;
import java.util.jar.Attributes;
import com.github.tilastokeskus.daboia.core.DaboiaLogic;
import com.github.tilastokeskus.daboia.util.Pair;
import java.io.IOException;
import java.util.List;

public class PluginLoader {
    
    public static List<Pair<DaboiaLogic, Attributes>> loadPlugins() {
        try {
            String logicFolderPath = ResourceManager.getExternalLogicDirectoryPath();
            JarClassLoader<DaboiaLogic> jcl = new JarClassLoader<>(logicFolderPath, true);
            return jcl.getClassInstanceList();
        } catch (IOException ex) {
            System.out.println("Cannot load plugins: " + ex.getMessage());
        }
        
        return null;
    }

}
