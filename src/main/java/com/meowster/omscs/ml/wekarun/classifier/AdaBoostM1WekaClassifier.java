package com.meowster.omscs.ml.wekarun.classifier;

import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;

/**
 * ADA Boost M1 classifier.
 * Class for boosting a nominal class classifier using the Adaboost M1 method.
 * Only nominal class problems can be tackled. Often dramatically improves
 * performance, but sometimes overfits.
 *
 * <pre>
 * Options:
 *
 * -P {num}
 *    Percentage of weight mass to base training on.
 *    (default 100, reduce to around 90 to speed up)
 *
 * -Q
 *    Use resampling for boosting
 *
 * -S {num}
 *    Random number seed (default = 1)
 *
 * -I {num}
 *    Number of iteration (default = 10)
 *
 * -W {name of classifier class}
 *    Full name of base classifier.
 *    (default = weka.classifiers.trees.DecisionStump)
 * </pre>
 *
 * @see AdaBoostM1
 */
public class AdaBoostM1WekaClassifier extends WekaClassifier {

    AdaBoostM1WekaClassifier(String tag, Option... options) {
        super(tag, Type.ADA_BOOST_M1, options);
    }

    @Override
    public Classifier newClassifier() {
        return setOptions(new AdaBoostM1());
    }
}
