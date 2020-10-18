import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader {
    private String pathName;

    public CustomClassLoader() {}
    public CustomClassLoader(String classPath){
        pathName=classPath;
    }

    @Override
    // para1: name是类的全路径
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassFromFile();
        return defineClass(name, b, 0, b.length);
    }

    //将class文件转成字节码数组
    private byte[] loadClassFromFile()  {
        System.out.println("run loadclass");
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                pathName.replace('.', File.separatorChar) + ".class");
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
        return buffer;
    }
}