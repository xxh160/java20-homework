import java.io.*;
import java.lang.reflect.Field; 
import java.lang.reflect.Method; 
import java.util.*;

class CustomClassLoader extends ClassLoader {
    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassFromFile(name);
        return defineClass(name, b, 0, b.length);
    }
    private byte[] loadClassFromFile(String fileName)  {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test.class");
                //fileName.replace('.', File.separatorChar) + ".class");
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = 0;
        try {
            while ( (nextValue = inputStream.read()) != -1 ) {
                byteStream.write(nextValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        buffer = byteStream.toByteArray();
        Base64.Decoder base64Decoder = Base64.getMimeDecoder();
        buffer=base64Decoder.decode(buffer);

        try (FileOutputStream decoded = new FileOutputStream("Monster.txt")) {
            decoded.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;       
    }
}

public class ClassIdentity {  

    public static void main(String[] args) {  
        new ClassIdentity().testClassIdentity();  
    }  

    
    public void testClassIdentity() {  
        String file = "Monster";  
        CustomClassLoader classloader = new CustomClassLoader();
        try {  
            Class<?> c=classloader.loadClass(file);
            //Object obj = c.newInstance(); 
            Field f[] = c.getDeclaredFields();
            for (int i = 0; i < f.length; i++)
                System.out.println(f[i].toString());
            Method m[] = c.getDeclaredMethods();
            for (int i = 0; i < m.length; i++)
                System.out.println(m[i].toString());
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }  
}