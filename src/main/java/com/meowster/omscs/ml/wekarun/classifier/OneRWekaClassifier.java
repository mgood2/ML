package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.rules.OneR;

/**
 * One R classifier.
 * Class for building and using a 1R classifier; in other words, uses the
 * minimum-error attribute for prediction, discretizing numeric attributes.
 *
 * <pre>
 * Options:
 *
 * -B {minimum bucket size}
 *    The minimum number of objects in a bucket. (default: 6)
 * </pre>
 *
 * @see OneR
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
