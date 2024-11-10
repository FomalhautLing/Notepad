package pvt.sanae.notepad.util;

import java.io.IOException;
import java.io.InputStream;

public class IOUtil {

    private static final long MAX_READ = 1024 * 1024;

    public static String readToString(InputStream in, String charset) throws IOException {
        byte[] b = new byte[in.available()];
        in.read(b);
        return new String(b, charset);
    }
}
