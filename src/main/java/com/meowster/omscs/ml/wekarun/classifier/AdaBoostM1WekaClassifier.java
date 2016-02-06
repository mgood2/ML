package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;

/**
 * ADA Boost M1 classifier.
 */
public class AdaBoostM1WekaClassifier extends WekaClassifier {

    AdaBoostM1WekaClassifier(Option... options) {
        super(Type.ADA_BOOST_M1, options);
    }

    @Override
    public Classifier classifier() {
        // TODO: apply options to classifier instance
        return new AdaBoostM1();
    }
}
