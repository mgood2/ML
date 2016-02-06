package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.rules.ZeroR;

/**
 * Zero R classifier.
 * Class for building and using a 0-R classifier. Predicts the mean
 * (for a numeric class) or the mode (for a nominal class).
 *
 * <pre>
 * No options.
 * </pre>
 *
 * @see ZeroR
 */
public class ZeroRWekaClassifier extends WekaClassifier {

    ZeroRWekaClassifier(Option... options) {
        super(Type.ZERO_R, options);
    }

    @Override
    public Classifier newClassifier() {
        return setOptions(new ZeroR());
    }

    @Override
    public int adjustRunCount(int numRuns) {
        // We'll always generate the same result, no matter how many runs
        // So let's force just a single run.
        return 1;
    }
}
