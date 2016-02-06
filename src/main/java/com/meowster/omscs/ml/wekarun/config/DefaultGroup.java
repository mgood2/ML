package com.meowster.omscs.ml.wekarun.config;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.trees.J48;
import weka.core.neighboursearch.LinearNNSearch;

/**
 * A default selection of classifiers to test with.
 */
public class DefaultGroup extends ClassifierGroup {

    @Override
    protected void configure() {
        add(WekaClassifier.Type.ZERO_R);

        add(WekaClassifier.Type.ONE_R);

        add(WekaClassifier.Type.ONE_R,
                opt(_B, 12)
        );

        add(WekaClassifier.Type.J48,
                opt(_C, 0.25),
                opt(_M, 2)
        );

        add(WekaClassifier.Type.MULTILAYER_PERCEPTRON,
                opt(_L, 0.3),
                opt(_M, 0.2),
                opt(_N, 500),
                opt(_V, 0),
                opt(_S, 0),
                opt(_E, 20),
                opt(_H, "a")
        );

        add(WekaClassifier.Type.ADA_BOOST_M1,
                opt(_P, 100),
                opt(_S, 1),
                opt(_I, 10),
                opt(_W, classWithOptions(J48.class))
        );

        add(WekaClassifier.Type.SMO,
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

        add(WekaClassifier.Type.IBK,
                opt(_K, 1),
                opt(_W, 0),
                opt(_A, classWithOptions(LinearNNSearch.class,
                        opt(_A, EUCLIDEAN_DISTANCE))
                )
        );
    }
}
