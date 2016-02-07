package com.meowster.omscs.ml.wekarun.config;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;

/**
 * A single MultiLayer Perceptron classifier.
 */
public class SinglePerceptron extends ClassifierGroup {

    @Override
    protected void configure() {
        add("Perceptron", WekaClassifier.Type.MULTILAYER_PERCEPTRON,
                opt(_L, 0.3),
                opt(_M, 0.2),
                opt(_N, 500),
                opt(_V, 0),
                opt(_S, 0),
                opt(_E, 20),
                opt(_H, "a")
        );
    }
}
