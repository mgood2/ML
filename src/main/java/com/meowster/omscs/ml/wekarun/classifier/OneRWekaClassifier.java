package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.rules.OneR;

/**
 * One R classifier
 */
public class OneRWekaClassifier extends WekaClassifier {

    OneRWekaClassifier(Option[] options) {
        super(Type.ONE_R, options);
    }

    @Override
    public Classifier newClassifier() {
        return setOptions(new OneR());
    }

    @Override
    public int adjustRunCount(int numRuns) {
        // We'll always generate the same result, no matter how many runs
        // So let's force just a single run.
        return 1;
    }
}
