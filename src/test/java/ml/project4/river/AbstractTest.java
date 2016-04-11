package ml.project4.river;

/**
 * Base class for unit tests.
 */
public abstract class AbstractTest {

    protected void print(String s) {
        System.out.println(s);
    }

    protected void print(Object o) {
        print(o.toString());
    }

    protected void print(String fmt, Object... params) {
        print(String.format(fmt, params));
    }

    protected void title(String s) {
        print("%n== %s ==", s);
    }
}
