package com.meowster.omscs.ml.wekarun.config;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier.Option;
import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.meowster.omscs.ml.fetcher.Utils.print;
import static com.meowster.omscs.ml.wekarun.classifier.WekaClassifier.createClassifier;

/**
 * Abstract base class that encapsulates a list of classifiers
 * which should be run against a data set.
 */
public abstract class ClassifierGroup {

    private static final String QUOTE = "\"";
    private static final String SPACE = " ";

    // option keys
    protected static final String _A = "-A";
    protected static final String _B = "-B";
    protected static final String _C = "-C";
    protected static final String _E = "-E";
    protected static final String _H = "-H";
    protected static final String _I = "-I";
    protected static final String _K = "-K";
    protected static final String _L = "-L";
    protected static final String _M = "-M";
    protected static final String _N = "-N";
    protected static final String _P = "-P";
    protected static final String _S = "-S";
    protected static final String _V = "-V";
    protected static final String _W = "-W";

    /**
     * Hand-crafted option value string to ensure correct escaping in quotes
     * To be fixed when {@link Option} can handle recursion.
     */
    protected static final String EUCLIDEAN_DISTANCE =
            "\\\"weka.core.EuclideanDistance -R first-last\\\"";


    private final List<WekaClassifier> classifiers = new ArrayList<>();
    private boolean configured = false;

    /**
     * Subclasses should configure their list of classifiers by using the
     * {@link #add} and {@link #opt} methods.
     */
    protected abstract void configure();

    /**
     * Adds the specified classifier (with the given options) to our list.
     * The tag is to identify this particular classifier instance in the
     * CSV output file.
     * <p/>
     * Calling this method is only valid before the first invocation of
     * {@link #iterator()}.
     *
     * @param tag     identifying tag
     * @param type    classifier type
     * @param options classifier options
     * @throws IllegalStateException if the group is no longer mutable
     */
    protected void add(String tag, Type type, Option... options) {
        if (configured) {
            throw new IllegalStateException("No longer mutable!");
        }
        WekaClassifier wc = createClassifier(tag, type, options);
        print(" ..adding classifier: %s", wc);
        classifiers.add(wc);
    }

    /**
     * Creates a key-only option (no associated value).
     * For example, when the option is "-V" one
     * might write:
     * <pre>
     *     opt(_V);
     * </pre>
     *
     * @param key the option key
     * @return the option
     */
    protected Option opt(String key) {
        return new Option(key);
    }

    /**
     * Creates an option with a numeric value.
     *
     * @param key   option key
     * @param value option value
     * @return the option
     */
    protected Option opt(String key, Number value) {
        return new Option(key, String.valueOf(value));
    }

    /**
     * Creates an option with a string value.
     *
     * @param key   option key
     * @param value option value
     * @return the option
     */
    protected Option opt(String key, String value) {
        return new Option(key, value);
    }

    /**
     * Creates a string consisting of the fully qualified class name of the
     * given class, followed by the list of specified options. The assumption
     * is that this method is used as a convenience when a class and its
     * options need to be passed in as an option value.
     *
     * @param cls     the class
     * @param options the options for the class
     * @return a string representation of the class and its options
     */
    protected String classWithOptions(Class<?> cls, Option... options) {

        // NOTE: Unfortunately, this method does not handle recursion correctly.
        //       For example: IBk -> LinearNNSearch -> EuclideanDistance
        //       The quotes won't be escaped correctly.
        //       Fix it another time; for now just hard-code the strings instead.
        // This approach does have some promise though.

        StringBuilder sb = new StringBuilder(QUOTE)
                .append(cls.getName());

        if (options.length > 0) {
            for (Option o : options) {
                sb.append(SPACE).append(o);
            }
        }
        sb.append(QUOTE);
        return sb.toString();
    }

    /**
     * Returns an iterator over the classifiers in this group.
     * Note that calling this method will mark the group as no longer
     * mutable.
     *
     * @return iterator over our set of classifiers
     */
    public Iterator<WekaClassifier> iterator() {
        // lazy invocation of configure
        if (!configured) {
            print("%nConfiguring ClassifierGroup: %s...", getClass().getName());
            configure();
            configured = true;
        }

        return new ClassifierIterator();
    }


    /**
     * Iterator implementation.
     */
    private class ClassifierIterator implements Iterator<WekaClassifier> {
        int index = 0;

        @Override
        public boolean hasNext() {
            return index < classifiers.size();
        }

        @Override
        public WekaClassifier next() {
            return classifiers.get(index++);
        }
    }
}
