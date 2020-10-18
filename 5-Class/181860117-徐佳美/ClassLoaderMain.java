
import java.lang.reflect.Method;
// import jdk.internal.org.objectweb.asm.commons.Method;




public class ClassLoaderMain {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        //这个类class的路径
        String classPath = "/home/njucs/Class3_fall/JAVAClass/Javahomework/java20-homework/5-Class/181860117-徐佳美/test.class";
    
        CustomClassLoader myClassLoader = new CustomClassLoader(classPath);
  
    
        //加载这个class文件
        Class<?> Testclass = Class.forName("Test", true, myClassLoader);
        Object obj = Testclass.newInstance();
    
        System.out.println("类加载器是:" + Testclass.getClassLoader());
    
        //利用反射获取main方法
        // Method method = Testclass.getDeclaredMethod("main", String[].class);
        // Object object = Testclass.newInstance();
        // String[] arg = {"ad"};
        // method.invoke(object, (Object) arg);
    }
    

 
 
}