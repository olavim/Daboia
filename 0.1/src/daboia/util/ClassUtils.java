
package daboia.util;

public class ClassUtils {
    
    public static <E> E getClassObject() {
        Object obj = new Object();
        return (E) obj;
    }
    
    public static <E> E getClassInstance(Class<E> clazz) throws InstantiationException, IllegalAccessException {
        E instance = clazz.newInstance();
        return instance;
    }

}
