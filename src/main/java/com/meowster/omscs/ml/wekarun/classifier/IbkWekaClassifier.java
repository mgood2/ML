package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;

/**
 * IBK classifier.
 * K-nearest neighbours classifier. Can select appropriate value of K based
 * on cross-validation. Can also do distance weighting.
 *
 * <pre>
 * Options:
 *
 * -I
 *    Weight neighbors by the inverse of their distance.
 *    (use when k &gt; 1)
 *
 * -F
 *    Weight neigbors by 1 - their distance
 *    (use when k &gt; 1)
 *
 * -K {number of neighbors}
 *    Number of nearest neighbors (k) used in classification, (default = 1)
 *
 * -E
 *    Minimize mean squared error rather than mean absolute error when
 *    using -X option with numeric prediction.
 *
 * -W {window size}
 *    Maximum number of training instances maintained. Training instances
 *    are dropped FIFO, (default = no window)
 *
 * -X
 *    Select the number of nearest neighbors between 1 and the k value
 *    specified using hold-one-out evaluation on the training data
 *    (use when k &gt; 1)
 *
 * -A {search class and parameters}
 *    The nearest neighbor search algorithm to use
 *    (default = weka.core.neighboursearch.LinearNNSearch)
 * </pre>
 *
 * @see IBk
 */
public class IbkWekaClassifier extends WekaClassifier {

    IbkWekaClassifier(String tag, Option... options) {
        super(tag, Type.IBK, options);
    }

    @Override
    public Classifier newClassifier() {
        return setOptions(new IBk());
    }
}
