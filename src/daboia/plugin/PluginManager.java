
package daboia.plugin;

import java.util.ArrayList;
import java.util.jar.Attributes;
import daboia.DaboiaLogic;
import daboia.util.Pair;
import java.util.List;

public class PluginManager {

    private static List<DaboiaLogic> logicHandlers;
    
    public static void loadPlugins() {
        List<Pair<DaboiaLogic, Attributes>> plugins = PluginLoader.loadPlugins();
        logicHandlers = new ArrayList<>();
        for (Pair<DaboiaLogic, Attributes> plugin : plugins) {
            handlePlugin(plugin);
        }
    }

    private static void handlePlugin(Pair<DaboiaLogic, Attributes> plugin) {
        DaboiaLogic logicHandler = plugin.first;
        logicHandlers.add(logicHandler);
        
        String title = plugin.second.getValue(Attributes.Name.SPECIFICATION_TITLE);
        logicHandler.setTitle(title);
        
        if (logicHandler.title() == null || logicHandler.title().isEmpty()) {
            String canonicalName = logicHandler.getClass().getCanonicalName();
            int lastDot = canonicalName.lastIndexOf(".");
            logicHandler.setTitle(canonicalName.substring(lastDot + 1));
        }
    }
    
    public static List<DaboiaLogic> getLogicHandlers() {
        return logicHandlers;
    }
    
}