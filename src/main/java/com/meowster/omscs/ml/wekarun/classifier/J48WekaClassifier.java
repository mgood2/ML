package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

/**
 * J48 tree classifier.
 */
public class J48WekaClassifier extends WekaClassifier {

    J48WekaClassifier(Option... options) {
        super(Type.J48, options);
    }

    @Override
    public Classifier newClassifier() {
        return setOptions(new J48());
    }
}
