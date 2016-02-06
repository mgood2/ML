package com.meowster.omscs.ml.wekarun.config;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;

/**
 * A single J48 tree classifier.
 */
public class SingleJ48 extends ClassifierGroup {

    @Override
    protected void configure() {
        add(WekaClassifier.Type.J48,
                opt(_C, 0.25),
                opt(_M, 2)
        );

    }
}
