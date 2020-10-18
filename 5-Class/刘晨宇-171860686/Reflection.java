import java.lang.reflect.Constructor;

public class Reflection {
    static final String encodedClassName = "Monster";

    public static void main(String[] args) {
        Base64ClassLoader customClassLoader = new Base64ClassLoader();
        Class<?> encodedClass;
        try {
            encodedClass = Class.forName(encodedClassName, true, customClassLoader);
            Constructor<?>[] constructors = encodedClass.getDeclaredConstructors();
        } catch(ClassNotFoundException e) {
            System.out.println("Class loading failed.");
        }
    }
}
