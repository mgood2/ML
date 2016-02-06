package com.meowster.omscs.ml.wekarun.classifier;

import com.google.common.base.MoreObjects;
import weka.classifiers.Classifier;

/**
 * Instances encapsulate a classifier and its parameters.
 */
public abstract class WekaClassifier {


    /** Base type of classifier. */
    public enum Type {
        ZERO_R, ONE_R, J48, REP_TREE, IBK, SMO, MULTILAYER_PERCEPTRON, ADA_BOOST_M1
    }


    protected final Type type;
    protected final Option[] options;


    WekaClassifier(Type type, Option... options) {
        this.type = type;
        this.options = options;
    }

    /**
     * Returns the classifier implementation for this type.
     *
     * @return the classifier implementation
     */
    public abstract Classifier classifier();

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
                .add("type", type)
                .add("options", options)
                .toString();
    }

    /**
     * Creates a classifier instance of the given type with the specified options.
     *
     * @param type classifier type
     * @param options options for the classifier
     * @return classifier instance
     */
    public static WekaClassifier createClassifier(Type type, Option... options) {

        switch (type) {
            case ZERO_R:
                return new ZeroRWekaClassifier(options);
            case ONE_R:
                return new OneRWekaClassifier(options);
            case J48:
                return new J48WekaClassifier(options);
            case REP_TREE:
                return new REPTreeWekaClassifier(options);
            case IBK:
                return new IbkWekaClassifier(options);
            case SMO:
                return new SmoWekaClassifier(options);
            case MULTILAYER_PERCEPTRON:
                return new MultiLayerPerceptronWekaClassifier(options);
            case ADA_BOOST_M1:
                return new AdaBoostM1WekaClassifier(options);
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
         * @param key option key
         * @param value option value
         */
        public Option(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }


}
