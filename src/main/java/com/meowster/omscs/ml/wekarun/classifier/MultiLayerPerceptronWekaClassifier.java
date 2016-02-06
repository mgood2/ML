package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;

/**
 * Multilayer Perceptron classifier.
 */
public class MultiLayerPerceptronWekaClassifier extends WekaClassifier {

    MultiLayerPerceptronWekaClassifier(Option... options) {
        super(Type.MULTILAYER_PERCEPTRON, options);
    }

    @Override
    public Classifier newClassifier() {
        return setOptions(new MultilayerPerceptron());
    }
}
