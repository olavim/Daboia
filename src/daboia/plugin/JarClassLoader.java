
package daboia.plugin;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.Attributes;
import daboia.util.ClassUtils;
import daboia.util.Pair;
import java.util.List;

public class JarClassLoader<T> {
    
    private URL[] urls;
    private boolean recursive;

    public JarClassLoader(String folderPath, boolean recursive) {
        this.recursive = recursive;
        
        try {
            this.urls = getFolderURLS(new File(folderPath));
        
            if (urls.length == 0) {
                System.out.println("No plugins found.");
            }
        } catch (MalformedURLException ex) {
            System.out.println("Cannot load plugins: " + ex.getMessage());
        }
    }
    
    private Attributes getMainAttributes(URL url) throws IOException {
	URL u = new URL("jar", "", url + "!/");
	JarURLConnection uc = (JarURLConnection) u.openConnection();
	Attributes attr = uc.getMainAttributes();
        return attr;
    }

    public List<Pair<T, Attributes>> getClassInstanceList() {
        List<Pair<T, Attributes>> classList = new ArrayList<>();
        URLClassLoader classLoader = URLClassLoader.newInstance(urls);
        
        for (URL url : urls) {        
            try {
                Attributes attr = getMainAttributes(url);
                Class clazz = classLoader.loadClass(attr.getValue(Attributes.Name.MAIN_CLASS));
                
                if (isAssignableFrom(clazz)) {
                    T instance = (T) clazz.newInstance();
                    classList.add(new Pair(instance, attr));
                }
            } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                System.out.println("Cannot load plugins: " + ex.getMessage());
            }
        }
        
        return classList;
    }
    
    private boolean isAssignableFrom(Class<?> clazz) {
        T cast = ClassUtils.<T>getClassObject();
        return cast.getClass().isAssignableFrom(clazz);
    }
    
    private URL[] getFolderURLS(final File folder) throws MalformedURLException {
        ArrayList<URL> urlList = new ArrayList<>();
        
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                if (recursive) {
                    getFolderURLS(fileEntry);
                }
            } else if (fileEntry.getName().endsWith(".jar")) {
                urlList.add(fileEntry.toURI().toURL());
            }
        }
        
        return urlList.toArray(new URL[]{});
    }
    
}
