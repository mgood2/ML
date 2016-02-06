package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;

/**
 * SMO classifier.
 */
public class SmoWekaClassifier extends WekaClassifier {

    SmoWekaClassifier(Option... options) {
        super(Type.SMO, options);
    }

    @Override
    public Classifier classifier() {
        // TODO: apply options to classifier instance
        return new SMO();
    }
}
