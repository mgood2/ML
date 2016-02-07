package com.meowster.omscs.ml.wekarun;

import com.meowster.omscs.ml.wekarun.config.ClassifierGroup;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup;
import weka.core.Instances;

import java.util.List;

/**
 * WEKA Experiment tailored to the BGG board game data files.
 * This implementation removes some of the attributes, to see what
 * effect this might have in results.
 */
public class BggReducedDataExperiment extends WekaExperiment {
    /**
     * Constructs a WEKA experiment configured with the data sets,
     * classifiers and filters to use.
     *
     * @param datasets    the data sets
     * @param classifiers the classifiers
     * @param filters     the data filters
     */
    public BggReducedDataExperiment(DataFileGroup datasets,
                                    ClassifierGroup classifiers,
                                    List<FilterType> filters) {
        super(datasets, classifiers, filters);
    }

    @Override
    protected Instances preProcessInstances(Instances instances) {

        // Note that each delete shifts the other attributes left.
        // It is best to delete from the highest index to lowest.
        /*
                              index    delete
            game id         :    0     **
            min p           :    1
            max p           :    2
            time            :    3
            abstract        :    4
            childrens       :    5
            customizable    :    6     **
            family          :    7     **
            party           :    8
            strategy        :    9
            thematic        :   10     **
            wargame         :   11
            no-domain       :   12     **
            class           :   13
         */

        instances.deleteAttributeAt(12);    // no-domain
        instances.deleteAttributeAt(10);    // thematic
        instances.deleteAttributeAt(7);     // family
        instances.deleteAttributeAt(6);     // customizable
        instances.deleteAttributeAt(0);     // game id

        /*
          This should leave:

            min p           :    0
            max p           :    1
            time            :    2
            abstract        :    3
            childrens       :    4
            party           :    5
            strategy        :    6
            wargame         :    7
            class           :    8
         */


        // make sure the class index is set to the last column
        if (instances.classIndex() == -1) {
            instances.setClassIndex(instances.numAttributes() - 1);
        }

        return instances;
    }

    @Override
    protected String resultsDirectory() {
        return "data/results/generated";
    }
}

