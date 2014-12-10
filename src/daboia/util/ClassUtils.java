
package daboia.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassUtils {
    
    public static <E> E getClassObject() {
        Object obj = new Object();
        return (E) obj;
    }
    
    public static <E> E getClassInstance(Class<E> clazz) {
        try {
            E instance = clazz.newInstance();
            return instance;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ClassUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
