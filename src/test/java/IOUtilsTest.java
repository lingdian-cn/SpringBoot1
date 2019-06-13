import java.io.*;

public class IOUtilsTest {

    public static String toString(InputStream input, String encoding) throws IOException {
        StringWriter sw = new StringWriter();
        copy((InputStream)input, (Writer)sw, encoding);
        return sw.toString();
    }

    public static void copy(InputStream input, Writer output, String encoding) throws IOException {
        if (encoding == null) {
            copy(input, output);
        } else {
            InputStreamReader in = new InputStreamReader(input, encoding);
            copy((Reader)in, (Writer)output);
        }
    }

    public static void copy(InputStream input, Writer output) throws IOException {
        InputStreamReader in = new InputStreamReader(input);
        copy((Reader)in, (Writer)output);
    }

    public static int copy(Reader input, Writer output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int)count;
    }

    public static long copyLarge(Reader input, Writer output) throws IOException {
        char[] buffer = new char[4096];
        long count = 0L;
        int n;
        for(boolean var5 = false; -1 != (n = input.read(buffer)); count += (long)n) {
            output.write(buffer, 0, n);
        }
        return count;
    }

    public static String readLine(InputStream inputStream, String encode) throws IOException {
        StringWriter sw = new StringWriter();
        InputStreamReader isr = new InputStreamReader(inputStream);
        if (encode != null) {
            isr = new InputStreamReader(inputStream, encode);
        }
        char[] buffer = new char[4096];
        int len ;
        while (-1 != (len = isr.read(buffer))) {
            sw.write(buffer, 0, len);
        }
        return sw.toString();
    }



}
