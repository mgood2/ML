package com.meowster.omscs.ml.wekarun;

import org.apache.commons.lang.StringUtils;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.filters.Filter;

/**
 * Designates filters that can be applied to the data before processing.
 */
public enum FilterType {

    /**
     * Unsupervised resampling filter.
     */
    UNSUPERVISED_RESAMPLE("UR") {
        @Override
        public Filter getFilter(int seed, double percent, boolean isTest) {
            Filter f = new weka.filters.unsupervised.instance.Resample();
            applyOptions(f, makeOptions(false, seed, percent, isTest));
            return f;
        }
    },

    /**
     * Supervised resampling filter.
     */
    SUPERVISED_RESAMPLE("SR") {
        @Override
        public Filter getFilter(int seed, double percent, boolean isTest) {
            Filter f = new weka.filters.supervised.instance.Resample();
            applyOptions(f, makeOptions(true, seed, percent, isTest));
            return f;
        }
    };

    private final String tag;


    FilterType(String tag) {
        this.tag = tag;
    }

    private static void applyOptions(Filter f, String options) {
        try {
            String[] optsArray = Utils.splitOptions(options);
            ((OptionHandler) f).setOptions(optsArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String optBias(boolean isSupervised) {
        // bias factor:: 0 == distribution matches input
        return isSupervised ? " -B 0" : "";
    }

    private static String optSeed(int seed) {
        return String.format(" -S %d", seed);
    }

    private static String optPercentSize(double percent) {
        return String.format(" -Z %f", percent);
    }

    private static final String NO_REPLACEMENT = " -no-replacement";

    private static String optInverted(boolean isTest) {
        return isTest ? " -V" : "";
    }


    private static String makeOptions(boolean isSupervised, int seed,
                                      double percent, boolean isTest) {
        return optBias(isSupervised) +
                optSeed(seed) +
                optPercentSize(percent) +
                NO_REPLACEMENT +
                optInverted(isTest);
    }

    /**
     * Returns the filter implementation.
     *
     * @param seed    random seed
     * @param percent percentage split (how much to use for train data)
     * @param isTest  true to generate test data, false for training data
     * @return filter
     */
    public abstract Filter getFilter(int seed, double percent, boolean isTest);

    @Override
    public String toString() {
        return StringUtils.lowerCase(name());
    }

    /**
     * Returns a short tag for this filter.
     *
     * @return the tag
     */
    public String tag() {
        return tag;
    }
}
