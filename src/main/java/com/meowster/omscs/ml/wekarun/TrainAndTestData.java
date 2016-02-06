package com.meowster.omscs.ml.wekarun;

import weka.core.Instances;
import weka.filters.Filter;

/**
 * Encapsulates a data set that has been separated into train and test data.
 */
public class TrainAndTestData {

    private static final boolean TRAIN = false;
    private static final boolean TEST = true;

    private final int seed;
    private final double percent;
    private final int totalInstanceCount;
    private final FilterType filterType;
    private final Instances trainSet;
    private final Instances testSet;

    /**
     * Divides the given instance data into a training set and a test set.
     * The seed is used for the random number generator. The percentage split
     * determines how much data is slopped into each bucket.
     *
     * @param seed       random seed
     * @param percent    percentage to use for training
     * @param instances  instance data
     * @param filterType filter to apply
     */
    public TrainAndTestData(int seed, double percent, Instances instances,
                            FilterType filterType) {
        this.seed = seed;
        this.percent = percent;
        this.filterType = filterType;

        totalInstanceCount = instances.numInstances();

        // make a copy of the instances
        Instances copy = new Instances(instances);

        trainSet = getFilteredInstances(filterType, copy, seed, percent, TRAIN);
        testSet = getFilteredInstances(filterType, copy, seed, percent, TEST);
    }

    private Instances getFilteredInstances(FilterType ft, Instances source,
                                           int seed, double percent,
                                           boolean isTest) {
        try {
            Filter f = ft.getFilter(seed, percent, isTest);
            f.setInputFormat(source);
            return Filter.useFilter(source, f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int seed() {
        return seed;
    }

    public double percent() {
        return percent;
    }

    public FilterType filterType() {
        return filterType;
    }

    public Instances trainSet() {
        return trainSet;
    }

    public Instances testSet() {
        return testSet;
    }

    public int totalInstanceCount() {
        return totalInstanceCount;
    }
}
