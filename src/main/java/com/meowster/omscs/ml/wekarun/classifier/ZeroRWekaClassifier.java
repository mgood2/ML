package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.rules.ZeroR;

/**
 * Zero R classifier.
 */
public class ZeroRWekaClassifier extends WekaClassifier {

    ZeroRWekaClassifier(Option... options) {
        super(Type.ZERO_R, options);
    }

    @Override
    public Classifier classifier() {
        // TODO: apply options to classifier instance
        return new ZeroR();
    }

    @Override
    public int adjustRunCount(int numRuns) {
        // We'll always generate the same result, no matter how many runs
        // So let's force just a single run.
        return 1;
    }
}
