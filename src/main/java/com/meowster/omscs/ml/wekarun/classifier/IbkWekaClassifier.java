package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;

/**
 * IBK classifier.
 */
public class IbkWekaClassifier extends WekaClassifier {

    IbkWekaClassifier(Option... options) {
        super(Type.IBK, options);
    }

    @Override
    public Classifier classifier() {
        // TODO: apply options to classifier instance
        return new IBk();
    }
}
