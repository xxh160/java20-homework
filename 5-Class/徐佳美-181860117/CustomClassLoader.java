import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Base64;

import sun.misc.BASE64Decoder;

public class CustomClassLoader extends ClassLoader {
    private String pathName;
    private int inmethod;
    byte[] buffer;
    public CustomClassLoader() {
    }

    public CustomClassLoader(String classPath,int inme) {
        pathName = classPath;
        inmethod = inme;
    }

    public static void main(String[] args) {

    }

    @Override
    // para1: name是类的全路径
    public Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("run myown findclass");
        try {
            loadClassFromFile();
            // defineClass方法可以把二进制流字节组成的文件转换为一个java.lang.Class
            Class<?> c = this.defineClass(name, buffer, 0, buffer.length);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    // 将class文件转成字节码数组
    private void loadClassFromFile() throws IOException {
        System.out.println("run myown loadclass");
       
        // 从class文件构建
        if (inmethod == 0) {
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream(pathName.replace('.', File.separatorChar) + ".class");
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = 0;
            try {
                while ((nextValue = inputStream.read()) != -1) {
                    byteStream.write(nextValue);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer = byteStream.toByteArray();
            System.out.println("good run loadclass");
            // return buffer;
        }
        //从base64编码的文件构建
        if (inmethod == 1) {
            BASE64Decoder decoder = new BASE64Decoder();

            try {
                if (null != pathName && !pathName.equals("")) {
                    buffer = decoder.decodeBuffer(pathName);
                }
            } catch (IOException e) {
                System.out.println("pathname Exception");
            }
        }
       

        
    }
}