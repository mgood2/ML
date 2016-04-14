package ml.project4;

/**
 * Print utilities.
 */
public class PrintUtils {

    public static final String EOL = String.format("%n");

    public static void print(String s) {
        System.out.println(s);
    }

    public static void print(Object o) {
        print(o.toString());
    }

    public static void print(String fmt, Object... params) {
        print(String.format(fmt, params));
    }
}
