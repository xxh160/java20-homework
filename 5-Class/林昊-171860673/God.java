import java.lang.reflect.*;
import java.util.*;

public class God {
    /*
    Question: No Default Constructor??? if you uncomment line 23 and line 31-35,
    you will find the following Exception:

    java.lang.InstantiationException: Monster
	    at java.lang.Class.newInstance(Class.java:427)
	    at God.main(God.java:20)
    Caused by: java.lang.NoSuchMethodException: Monster.<init>()
	    at java.lang.Class.getConstructor0(Class.java:3082)
	    at java.lang.Class.newInstance(Class.java:412)
	    ... 1 more

	This is mainly caused by the absence of non-parameter constructor in Monster.
    */
    public static void main(String[] args) {
        try {
            CustomClassLoader clsLoader = new CustomClassLoader(args[0]);
            Class<?> cls = clsLoader.loadClass("Monster");
//            Object obj = cls.newInstance();
            System.out.printf("Class Name: %s\n", cls.getName());
            printConstructors(cls.getDeclaredConstructors());
            printFields(cls.getDeclaredFields());
            printMethods(cls.getDeclaredMethods());
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
//        catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
    }

    private static void printConstructors(Constructor[] constructors){
        System.out.println("Class Constructors:");
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }
    }

    private static void printFields(Field[] fields) {
        System.out.println("Class Fields:");
        for (Field field : fields) {
            System.out.println(field);
        }
    }

    private static void printMethods(Method[] methods) {
        System.out.println("Class Methods:");
        for (Method method : methods) {
            System.out.println(method);
        }
    }
}
