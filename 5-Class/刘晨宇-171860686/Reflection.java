import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Reflection {
    static final String encodedClassName = "Monster";

    public static void main(String[] args) {
        Base64ClassLoader customClassLoader = new Base64ClassLoader();
        Class<?> encodedClass;
        try {
            // load base64 encoded class from file
            encodedClass = Class.forName(encodedClassName, true, customClassLoader);
            // create an object of encoded class
            Constructor<?> constructor = encodedClass.getDeclaredConstructor(String.class, Integer.TYPE, Integer.TYPE);
            constructor.setAccessible(true);
            Object instance = constructor.newInstance("Godzilla", 1919810, 114514);
            System.out.println("Created an object of encoded class: " + instance.toString());
            // find out all methods and fields of encoded class
            ArrayList<Method> allMethods = new ArrayList<>(Arrays.asList(encodedClass.getDeclaredMethods()));
            ArrayList<Field> allFields = new ArrayList<>(Arrays.asList(encodedClass.getDeclaredFields()));
            Class<?> superClass = encodedClass.getSuperclass();
            while(superClass != null) {
                for(Method method: superClass.getDeclaredMethods()) {
                    int modifier = method.getModifiers();
                    if(Modifier.isPublic(modifier) || Modifier.isProtected(modifier)) {
                        allMethods.add(method);
                    }
                }
                for(Field field: superClass.getDeclaredFields()) {
                    int modifier = field.getModifiers();
                    if(Modifier.isPublic(modifier) || Modifier.isProtected(modifier)) {
                        allFields.add(field);
                    }
                }
                superClass = superClass.getSuperclass();
            }
            System.out.println("Class canonical name:");
            System.out.println("\t" + encodedClass.getCanonicalName());
            System.out.println("All methods of class:");
            allMethods.forEach(method -> System.out.println("\t" + method));
            System.out.println("All fields of class:");
            allFields.forEach(field -> System.out.println("\t" + field));
        } catch(ClassNotFoundException e) {
            System.out.println("Class loading failed.");
        } catch(NoSuchMethodException e) {
            System.out.println("Required constructor not found.");
        } catch (IllegalAccessException e) {
            System.out.println("Illegal access to required constructor.");
        } catch (InstantiationException e) {
            System.out.println("Error while instantiating with the constructor.");
        } catch (InvocationTargetException e) {
            System.out.println("Unhandled exception in constructor.");
        }
    }
}
