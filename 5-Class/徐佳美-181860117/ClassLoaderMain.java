
import java.lang.reflect.Constructor;
import java.lang.reflect.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderMain {

    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
            SecurityException, IllegalArgumentException, InvocationTargetException {

              

        // 这个类class的路径
        String classPath = "/home/njucs/Class3_fall/JAVAClass/Javahomework/java20-homework/5-Class/徐佳美-181860117/People.class";
        System.out.println(classPath);

        CustomClassLoader myClassLoader = new CustomClassLoader(classPath,0);

        // 加载这个class文件
        Class<?> Testclass = Class.forName("People", true, myClassLoader);
        System.out.println("good trap");
        printInformation(Testclass);
        Object obj = Testclass.newInstance();


        System.out.println("类加载器是:" + Testclass.getClassLoader());

       

        ///////////
    }
    private static void printInformation(Class<?> Testclass){
        Constructor[] con = Testclass.getDeclaredConstructors();
        for (int i = 0; i < con.length; i++)
            System.out.println(con[i]);

        Field[] field = Testclass.getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        for (int i = 0; i < field.length; i++) { // 遍历所有属性
            String name = field[i].getName(); // 获取属性的名字
            String type = field[i].getGenericType().toString(); // 获取属性的类型
            System.out.println(type + " " + name);

        }

        Method m[] = Testclass.getDeclaredMethods();
        for (int i = 0; i < m.length; i++)
            System.out.println(m[i].toString());
    }

}