package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;

/**
 * Multilayer Perceptron classifier.
 * A Classifier that uses backpropagation to classify instances.
 * This network can be built by hand, created by an algorithm or both.
 * The network can also be monitored and modified during training time.
 * The nodes in this network are all sigmoid (except for when the class
 * is numeric in which case the the output nodes become unthresholded
 * linear units).
 *
 * <pre>
 * Options:
 *
 * -L {learning rate}
 *    Learning rate for the backpropagation algorithm.
 *    (Value between 0..1, default = 0.3)
 *
 * -M {momentum}
 *    Momentum rate for the backpropagation algorithm.
 *    (Value between 0..1, default = 0.2)
 *
 * -N {number of epochs}
 *    Number of epochs to train through.
 *    (default = 500)
 *
 * -V {percentage size of validation set}
 *    Percentage size of validation set to use to terminate training
 *    (if this is non zero it can pre-empt num of epochs).
 *    (Value should be between 0..100, default = 0)
 *
 * -S {seed}
 *    The value used to seed the random number generator
 *    (Value should be &gt;= 0 and a long, default = 0)
 *
 * -E {threshold for number of consecutive errors}
 *    The consecutive number of errors allowed for validation
 *    testing before the network terminates.
 *    (Value should be &gt; 0, default = 20)
 *
 * -B
 *    A NominalToBinary filter will NOT automatically be used.
 *
 * -H {comma separated numbers for nodes on each layer}
 *    The hidden layers to be created for the network.
 *    (Value should be a list of comma separated natural numbers, or
 *    the letters: 'a' = (attribs + classes) / 2
 *                 'i' = attribs
 *                 'o' = classes
 *                 't' = attribs + classes
 *    for wildcard values, default = 'a')
 *
 * -C
 *    Normalizing a numeric class will NOT be done.
 *
 * -I
 *    Normalizing the attributes will NOT be done.
 *
 * -R
 *    Resetting the network will NOT be allowed.
 *
 * -D
 *    Learning rate decay will occur.
 * </pre>
 *
 * @see MultilayerPerceptron
 */
public class MultiLayerPerceptronWekaClassifier extends WekaClassifier {

    MultiLayerPerceptronWekaClassifier(String tag, Option... options) {
        super(tag, Type.MULTILAYER_PERCEPTRON, options);
    }

    @Override
    public Classifier newClassifier() {
        return setOptions(new MultilayerPerceptron());
    }

    @Override
    public int adjustRunCount(int numRuns) {
        // Neural Nets can be slow to run
        // So let's drop the number of CV runs to just 3.
        return 3;
    }

}
