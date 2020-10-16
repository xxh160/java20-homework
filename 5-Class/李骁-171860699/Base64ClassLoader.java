import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.io.FileOutputStream;

public class Base64ClassLoader extends ClassLoader {

    private String base64;
    private byte[] buffer;

    public Base64ClassLoader(String base64) throws Exception {
        this.base64 = base64;
        buffer = Base64.getDecoder().decode(base64);
    }

    @Override
    public Class findClass(String name) throws ClassNotFoundException {
        return defineClass(name, buffer, 0, buffer.length);
    }
}