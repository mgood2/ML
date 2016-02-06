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
    public Classifier classifier() {
        // TODO: apply options to classifier instance
        return new OneR();
    }
}
