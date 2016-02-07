package com.meowster.omscs.ml.wekarun.config;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import weka.classifiers.trees.J48;

/**
 * A single boosted J48 classifier.
 */
public class SingleBoostedJ48 extends ClassifierGroup {

    @Override
    protected void configure() {
        add("BoostedJ48", WekaClassifier.Type.ADA_BOOST_M1,
                opt(_P, 100),
                opt(_S, 1),
                opt(_I, 10),
                opt(_W, classWithOptions(J48.class))
        );
    }
}
