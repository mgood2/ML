package com.meowster.omscs.ml.wekarun.config;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import weka.classifiers.functions.supportVector.PolyKernel;

/**
 * A single Support Vector Machine (SMO) classifier.
 */
public class SingleSmo extends ClassifierGroup {

    @Override
    protected void configure() {
        add("SMO", WekaClassifier.Type.SMO,
                opt(_C, 1.0),
                opt(_L, 0.001),
                opt(_P, 1.0E-12),
                opt(_N, 0),
                opt(_V, -1),
                opt(_W, 1),
                opt(_K, classWithOptions(PolyKernel.class,
                        opt(_C, 250007),
                        opt(_E, 1.0))
                )
        );
    }
}
