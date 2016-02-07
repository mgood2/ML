package com.meowster.omscs.ml.wekarun.classifier;

import com.google.common.base.MoreObjects;
import weka.classifiers.Classifier;
import weka.core.Utils;

/**
 * Instances encapsulate a classifier and its parameters.
 */
public abstract class WekaClassifier {

    private static final String SPACE = " ";
    private static final String EMPTY = "";

    /**
     * Base type of classifier.
     */
    public enum Type {
        ZERO_R,
        ONE_R,
        J48,
        IBK,
        SMO,
        MULTILAYER_PERCEPTRON,
        ADA_BOOST_M1
    }


    protected final String tag;
    protected final Type type;
    protected final Option[] options;
    protected Classifier lastInstance;

    /**
     * Constructs a weka classifier wrapper of the given type and options.
     * The supplied tag is used to identify this particular instance of
     * classifier in the CSV file output.
     *
     * @param tag     identifying tag
     * @param type    classifier type
     * @param options classifier options
     */
    WekaClassifier(String tag, Type type, Option... options) {
        this.tag = tag;
        this.type = type;
        this.options = options;
    }

    /**
     * Returns a new instance of the classifier implementation for this type,
     * pre-configured with the appropriate options.
     * Subclasses should store a reference to the last generated instance in
     * the {@link #lastInstance} field.
     *
     * @return the pre-configured classifier implementation
     */
    public abstract Classifier newClassifier();

    /**
     * Sets the options on the classifier instance, stores a reference to the
     * instance in {@link #lastInstance} and returns it. This convenience
     * method should be invoked from subclasses {@link #newClassifier()}
     * method.
     *
     * @param impl the new classifier implementation
     * @return the same classifier implementation (but now configured)
     */
    protected Classifier setOptions(Classifier impl) {
        try {
            impl.setOptions(getOptionStringArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        lastInstance = impl;
        return impl;
    }

    private String[] getOptionStringArray() {
        // yeah, kinda convoluted creating a string and then breaking it
        //  apart again, but the WEKA Utils.splitOptions() seems to do some
        //  funky processing; probably best to rely on it for now
        //  (without deeper investigation).
        try {
            return Utils.splitOptions(stringify(options));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    private String stringify(Option[] options) {
        if (options.length == 0) {
            return EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        for (Option o : options) {
            sb.append(o).append(SPACE);
        }
        final int len = sb.length();
        sb.delete(len - 1, len);
        return sb.toString();
    }


    /**
     * By default, classifiers are happy to run as many times as instructed.
     *
     * @param numRuns num requested runs
     * @return actual number of runs to make
     */
    public int adjustRunCount(int numRuns) {
        return numRuns;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tag", tag)
                .add("type", type)
                .add("options", options)
                .toString();
    }

    /**
     * Returns the tag associated with this instance.
     *
     * @return the classifier tag
     */
    public String tag() {
        return tag;
    }

    /**
     * Returns the string representation of the (last generated)
     * underlying classifier instance.
     *
     * @return string representation of weka classifier
     */
    public String wekaToString() {
        return lastInstance == null ? "(no instance generated)"
                : lastInstance.toString();
    }

    /**
     * Creates a classifier instance of the given type with the specified options.
     * The tag is used for identifying this classifier instance in the CSV file.
     *
     * @param tag     identifying tag
     * @param type    classifier type
     * @param options options for the classifier
     * @return classifier instance
     */
    public static WekaClassifier createClassifier(String tag, Type type,
                                                  Option... options) {
        switch (type) {
            case ZERO_R:
                return new ZeroRWekaClassifier(tag, options);
            case ONE_R:
                return new OneRWekaClassifier(tag, options);
            case J48:
                return new J48WekaClassifier(tag, options);
            case IBK:
                return new IbkWekaClassifier(tag, options);
            case SMO:
                return new SmoWekaClassifier(tag, options);
            case MULTILAYER_PERCEPTRON:
                return new MultiLayerPerceptronWekaClassifier(tag, options);
            case ADA_BOOST_M1:
                return new AdaBoostM1WekaClassifier(tag, options);
            default:
                return null;
        }
    }


    /**
     * Encapsulates an option.
     */
    public static class Option {
        private final String key;
        private final String value;

        /**
         * Creates an option instance.
         *
         * @param key   option key
         * @param value option value
         */
        public Option(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Option(String key) {
            this.key = key;
            this.value = null;
        }

        @Override
        public String toString() {
            return value == null ? key : key + SPACE + value;
        }
    }

}
