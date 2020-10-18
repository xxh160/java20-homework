
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

        // 利用反射获取main方法
        // Method method = Testclass.getDeclaredMethod("main", String[].class);
        // Object object = Testclass.newInstance();
        // String[] arg = { "ad" };
        // method.invoke(object, (Object) arg);
        //////////////////
        Field[] field = obj.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (int i = 0; i < field.length; i++) { // 遍历所有属性
                String name = field[i].getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field[i].getGenericType().toString(); // 获取属性的类型
                System.out.println(type + " " + name + "   probegin");
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = obj.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(obj); // 调用getter方法获取属性值
                    
                    if (value == null) {
                        m = obj.getClass().getMethod("set" + name, String.class);
                        m.invoke(obj, "");
                    }
                    System.out.println(type + " " + value);
                }
                if (type.equals("class java.lang.Integer")) {
                    Method m = obj.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set" + name, Integer.class);
                        m.invoke(obj, 0);
                    }
                    System.out.println(type + " " + value);
                }
                if (type.equals("class java.lang.Boolean")) {
                    Method m = obj.getClass().getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set" + name, Boolean.class);
                        m.invoke(obj, false);
                    }
                    System.out.println(type + " " + value);
                }
                if (type.equals("class java.util.Date")) {
                    Method m = obj.getClass().getMethod("get" + name);
                    Date value = (Date) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set" + name, Date.class);
                        m.invoke(obj, new Date());
                    }
                    System.out.println(type + " " + value);
                }

            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        ///////////
    }

}