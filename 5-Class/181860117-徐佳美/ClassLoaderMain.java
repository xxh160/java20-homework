
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
// import java.text.Format.Field;
import java.util.Date;

public class ClassLoaderMain {

    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
            SecurityException, IllegalArgumentException, InvocationTargetException {
        // 这个类class的路径
        String classPath = "/home/njucs/Class3_fall/JAVAClass/Javahomework/java20-homework/5-Class/181860117-徐佳美/People.class";
        System.out.println(classPath);

        CustomClassLoader myClassLoader = new CustomClassLoader(classPath);

        // 加载这个class文件
        Class<?> Testclass = Class.forName("People", true, myClassLoader);
        System.out.println("good trap");

        Object obj = Testclass.newInstance();

        System.out.println(obj);

        System.out.println("类加载器是:" + Testclass.getClassLoader());

        Field[] field = obj.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        for (int i = 0; i < field.length; i++) { // 遍历所有属性
            String name = field[i].getName(); // 获取属性的名字
            String type = field[i].getGenericType().toString(); // 获取属性的类型
            System.out.println(type + " " + name);

        }

        Method m[] = Testclass.getDeclaredMethods();
        for (int i = 0; i < m.length; i++)
            System.out.println(m[i].toString());

        ///////////
    }

}