package com.meowster.omscs.ml.wekarun.config;

import weka.core.OptionHandler;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

/**
 * Filter group for Unsupervised and Supervised resampling.
 */
public class VaryingRemovePercentFilterGroup extends FilterGroup {

    // VaryingPercentFilterGroup

    private final int max;
    private final int min;
    private final int step;

    /**
     * Creates a varying percent removal filter group for the given
     * parameters, starting with max, ending with min, by incrementing
     * each step.
     *<p/>
     * For example,
     * <pre>
     *     new VaryingRemovePercentFilterGroup(90, 10, 40)
     * </pre>
     * will iterate with percentages:
     * <pre>
     *     90, 50, 10
     * </pre>
     *
     * @param max percent maximum
     * @param min percent minimum
     * @param step increment step
     */
    public VaryingRemovePercentFilterGroup(int max, int min, int step) {
        this.max = max;
        this.min = min;
        this.step = step;
    }


    @Override
    protected void configure() {
        for (int percent = max; percent >= min; percent -= step) {
            addParameterizedFilter(percent);
        }
    }

    private void addParameterizedFilter(int percent) {
        Filter f = new RemovePercentage();
        try {
            ((OptionHandler) f).setOptions(getOptions(percent));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addFilters(f);
    }

    private String[] getOptions(int percent) {
        try {
            return Utils.splitOptions(String.format(FMT_OPTIONS, percent));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    private static final String FMT_OPTIONS = "-P %d";
}
