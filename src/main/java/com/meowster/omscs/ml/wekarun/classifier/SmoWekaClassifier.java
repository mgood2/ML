package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;

/**
 * SMO classifier.
 * <p>
 * Implements John Platt's sequential minimal optimization algorithm for
 * training a support vector classifier. This implementation globally
 * replaces all missing values and transforms nominal attributes into
 * binary ones. It also normalizes all attributes by default.
 * (In that case the coefficients in the output are based on the normalized
 * data, not the original data --- this is important for interpreting
 * the classifier.)
 * <p>
 * Multi-class problems are solved using pairwise classification
 * (1-vs-1 and if logistic models are built pairwise coupling according
 * to Hastie and Tibshirani, 1998).
 * <p>
 * To obtain proper probability estimates, use the option that fits logistic
 * regression models to the outputs of the support vector machine. In the
 * multi-class case the predicted probabilities are coupled using Hastie
 * and Tibshirani's pairwise coupling method.
 * <p>
 * Note: for improved speed normalization should be turned off when
 * operating on SparseInstances.
 *
 * <pre>
 * Options:
 *
 * -C {double}
 *    The complexity constant C, (default = 1)
 *
 * -N {n}
 *    Whether to normalize(0), standardize(1), or neither(2) (default = 0)
 *
 * -L {double}
 *    The tolerance parameter, (default = 1.0e-3)
 *
 * -P {double}
 *    The epsilon for round-off error, (default = 1.0e-12)
 *
 * -M
 *    Fit logistic models to SVM outputs
 *
 * -V {double}
 *    The number of folds for the internal cross-validation.
 *    (default = -1: use training data)
 *
 * -W {double}
 *    The random number seed, (default = 1)
 *
 * -K {classname and parameters}
 *    The kernel to use.
 *    (default = weka.classifiers.functions.supportVector.PolyKernel)
 *
 * Options specific to weka.classifiers.functions.supportVector.PolyKernel:
 *        K(x, y) = {x, y} ^ p   or   K(x, y) = ({x, y} + 1) ^ p
 *
 * -C {num}
 *    The size of the cache (a prime number):
 *     0 = full cache
 *    -1 = turn cache off
 *    default = 250007
 *
 * -E {num}
 *    The exponent to use, (default = 1.0)
 *
 * -L
 *    Use lower-order terms, (default = no)
 *
 * Options specific to weka.classifiers.functions.supportVector.RBFKernel:
 *        K(x, y) = e ^ -(gamma * {x-y, x-y})
 *
 * -C {num}
 *    The size of the cache (a prime number):
 *     0 = full cache
 *    -1 = turn cache off
 *    default = 250007
 *
 * -G {num}
 *    The gamma parameter, (default = 0.01)
 *
 * </pre>
 *
 * @see SMO
 */
public class SmoWekaClassifier extends WekaClassifier {

    SmoWekaClassifier(String tag, Option... options) {
        super(tag, Type.SMO, options);
    }

    @Override
    public Classifier newClassifier() {
        return setOptions(new SMO());
    }
}
