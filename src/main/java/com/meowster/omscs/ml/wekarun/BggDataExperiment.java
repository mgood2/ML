package com.meowster.omscs.ml.wekarun;

import com.meowster.omscs.ml.wekarun.config.ClassifierGroup;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup;
import weka.core.Instances;

import java.util.List;

/**
 * WEKA Experiment tailored to the BGG board game data files.
 */
public class BggDataExperiment extends WekaExperiment {
    /**
     * Constructs a WEKA experiment configured with the data sets,
     * classifiers and filters to use.
     *
     * @param datasets    the data sets
     * @param classifiers the classifiers
     * @param filters     the data filters
     */
    public BggDataExperiment(DataFileGroup datasets,
                             ClassifierGroup classifiers,
                             List<FilterType> filters) {
        super(datasets, classifiers, filters);
    }

    @Override
    protected Instances preProcessInstances(Instances instances) {
        // remove the game ID, (first attribute), which is not useful data
        instances.deleteAttributeAt(0);

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
