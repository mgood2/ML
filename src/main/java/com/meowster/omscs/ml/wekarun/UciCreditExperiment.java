package com.meowster.omscs.ml.wekarun;

import com.meowster.omscs.ml.wekarun.config.ClassifierGroup;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup;
import weka.core.Instances;

import java.util.List;

/**
 * WEKA Experiment tailored to the UCI credit approval dataset,
 * (credit-a.arff).
 */
public class UciCreditExperiment extends WekaExperiment {
    /**
     * Constructs a WEKA experiment configured with the data sets,
     * classifiers and filters to use.
     *
     * @param datasets    the data sets
     * @param classifiers the classifiers
     * @param filters     the data filters
     */
    public UciCreditExperiment(DataFileGroup datasets,
                               ClassifierGroup classifiers,
                               List<FilterType> filters) {
        super(datasets, classifiers, filters);
    }

    @Override
    protected Instances preProcessInstances(Instances instances) {

        // Note that each delete shifts the other attributes left.
        // It is best to delete from the highest index to lowest.
        /*
            %     A1:	b, a.
            %     A2:	continuous.
            %     A3:	continuous.
            %     A4:	u, y, l, t.
            %     A5:	g, p, gg.
            %     A6:	c, d, cc, i, j, k, m, r, q, w, x, e, aa, ff.
            %     A7:	v, h, bb, j, n, z, dd, ff, o.
            %     A8:	continuous.
            %     A9:	t, f.
            %     A10:	t, f.
            %     A11:	continuous.
            %     A12:	t, f.
            %     A13:	g, p, s.
            %     A14:	continuous.
            %     A15:	continuous.
            %     A16: +,-         (class attribute)
         */

//        instances.deleteAttributeAt(12);    // no-domain

        // make sure the class index is set to the last column
        if (instances.classIndex() == -1) {
            instances.setClassIndex(instances.numAttributes() - 1);
        }

        return instances;
    }

    @Override
    protected String resultsDirectory() {
        return "data/results/genUci";
    }
}

