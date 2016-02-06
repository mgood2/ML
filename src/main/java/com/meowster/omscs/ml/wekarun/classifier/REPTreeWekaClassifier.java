package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.trees.REPTree;

/**
 * REP tree classifier.
 */
public class REPTreeWekaClassifier extends WekaClassifier {

    REPTreeWekaClassifier(Option... options) {
        super(Type.REP_TREE, options);
    }

    @Override
    public Classifier classifier() {
        // TODO: apply options to classifier instance
        return new REPTree();
    }
}