import java.lang.reflect.*;
public class Main{
    public static Class loaderClass(String Filename){
        MyLoader loader=new MyLoader();
        Class obj=null;
        try{
            obj = loader.findClass("unknown.txt");  

        }
        catch(ClassNotFoundException I){
            System.out.println("Class Not Found Exception");
        }
        return obj;
    }
    public static void printClassInfo(Class obj){
        System.out.println("Class Name:");
        String S=obj.getName();
        System.out.println('\t'+S);

        System.out.println("Class Fields:");
        Field F[] = obj.getDeclaredFields();
        for (int i = 0; i < F.length; i++)
            System.out.println('\t'+F[i].toString());
        
        System.out.println("Class Constructors:");
        Constructor C[]=obj.getDeclaredConstructors();
        for (int i = 0; i < C.length; i++)
            System.out.println('\t'+C[i].toString());
        
        System.out.println("Class Methods:");
        Method M[] = obj.getDeclaredMethods();
        for (int i = 0; i < M.length; i++)
            System.out.println('\t'+M[i].toString());
        

    }
    public static void main(String[] args) {
        Class obj=loaderClass("unknow.txt");
        printClassInfo(obj);
    }
}