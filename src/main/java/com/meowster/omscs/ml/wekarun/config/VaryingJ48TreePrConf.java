package com.meowster.omscs.ml.wekarun.config;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;

/**
 * A configurable group of J48 decision tree classifiers with varying
 * Pruning Confidence.
 */
public class VaryingJ48TreePrConf extends ClassifierGroup {

    private final double min;
    private final double max;
    private final double step;

    /**
     * Creates a varying J48 decision tree classifier group for the
     * given parameters.
     * <p/>
     * For example, one might write:
     * <pre>
     *     new VaryingJ48TreePrConf(0.2, 0.8, 0.2)
     * </pre>
     * to creates classifiers with pruning confidences of:
     * <pre>
     *     0.2, 0.4, 0.6, 0.8
     * </pre>
     *
     * @param min the minimum pruning confidence
     * @param max the maximum pruning confidence
     * @param step the increment step
     */
    public VaryingJ48TreePrConf(double min, double max, double step) {
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    protected void configure() {
        for (double d = min; d <= max; d += step) {
            addParameterizedClassifier(d);
        }
    }

    private void addParameterizedClassifier(double d) {
        String tag = String.format("J48-pc-%.2f", d);
        add(tag, WekaClassifier.Type.J48,
                opt(_C, d),
                opt(_M, 2)
        );
    }
}
