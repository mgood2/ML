package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

/**
 * J48 tree classifier.
 * Class for generating a pruned or unpruned C4.5 decision tree.
 *
 * <pre>
 * Options:
 *
 * -U
 *    Use unpruned tree
 *
 * -O
 *    Do not collapse tree
 *
 * -C {pruning confidence}
 *    Set confidence threshold for pruning. (default: 0.25)
 *
 * -M {minimum number of instances}
 *    Set minimum number of instances per leaf. (default: 2)
 *
 * -R
 *    Use reduced error pruning.
 *
 * -N {number of folds}
 *    Set number of folds for reduced error pruning.
 *    One fold is used as a pruning set. (default 3)
 *
 * -B
 *    Use binary splits only.
 *
 * -S
 *    Don't perform subtree raising.
 *
 * -L
 *    Do not clean up after the tree has been built.
 *
 * -A
 *    Laplace smoothing for predicted probabilities
 *
 * -J
 *    Do not use MDL correction for info gain on numeric attributes.
 *
 * -Q {seed}
 *    Seed for random data shuffling. (default: 1)
 *
 * -doNotMakeSplitPointActualValue
 *    Do not make split point actual value.
 * </pre>
 *
 * @see J48
 */
public class J48WekaClassifier extends WekaClassifier {

    J48WekaClassifier(Option... options) {
        super(Type.J48, options);
    }

    @Override
    public Classifier newClassifier() {
        return setOptions(new J48());
    }
}
