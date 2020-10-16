import java.lang.reflect.*;
import java.util.regex.*;

public class LoadClass{
	public static void main(String[] args){
		Pattern p = Pattern.compile("\\w+\\.");
		Base64ClassLoader bcl = new Base64ClassLoader();
		try{
			Class<?> ec = bcl.loadClass("EncrpytedClass");
			Method[] methods = ec.getMethods();
			Field[] fields = ec.getFields();
			System.out.println("Class Name:");
			System.out.println(ec.getName());
			System.out.println("Field:");
			for(Field f : fields){
				System.out.println(p.matcher(f.toString()).replaceAll(""));
			}
			System.out.println("Methods:");
			for(Method m : methods){
				System.out.println(p.matcher(m.toString()).replaceAll(""));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}