package ml.project4;

/**
 * Output utilities.
 */
public class OutputUtils {

    /**
     * System independent end-of-line character.
     */
    public static final String EOL = String.format("%n");

    /**
     * Prints the given string.
     *
     * @param s the string
     */
    public static void print(String s) {
        System.out.println(s);
    }

    /**
     * Prints the string representation of the given object.
     *
     * @param o the object
     */
    public static void print(Object o) {
        print(o.toString());
    }

    /**
     * Prints the given formatted string.
     *
     * @param fmt    the format string
     * @param params parameters to the format string
     */
    public static void print(String fmt, Object... params) {
        print(String.format(fmt, params));
    }

}
