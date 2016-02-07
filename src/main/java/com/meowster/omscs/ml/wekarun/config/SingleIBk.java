package com.meowster.omscs.ml.wekarun.config;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import weka.core.neighboursearch.LinearNNSearch;

/**
 * A single k-NN nearest neighbor (IBk) classifier.
 */
public class SingleIBk extends ClassifierGroup {

    @Override
    protected void configure() {
        add("IBk", WekaClassifier.Type.IBK,
                opt(_K, 1),
                opt(_W, 0),
                opt(_A, classWithOptions(LinearNNSearch.class,
                        opt(_A, EUCLIDEAN_DISTANCE))
                )
        );
    }
}
