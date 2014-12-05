
package daboia.plugin;

import java.util.jar.Attributes;
import daboia.DaboiaLogic;
import daboia.util.Pair;
import java.util.List;

public class PluginLoader {
    
    public static List<Pair<DaboiaLogic, Attributes>> loadPlugins() {
        try {
            JarClassLoader<DaboiaLogic> jcl = new JarClassLoader<>("./logic", true);
            return jcl.getClassInstanceList();
        } catch (SecurityException ex) {
            System.out.println("Cannot load plugins: " + ex.getMessage());
        }
        
        return null;
    }

}
