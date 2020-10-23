package MyClassLoaderTest;

import java.util.Base64;

public class MyClassLoader extends ClassLoader {
    private byte[] getDate(String classString) {
        return Base64.getDecoder().decode(classString);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classDate = getDate(name);
            if (classDate == null) {}
            else
                return defineClass(null, classDate, 0, classDate.length);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }
}
