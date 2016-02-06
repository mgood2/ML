package com.meowster.omscs.ml.wekarun;

import com.google.common.collect.ImmutableList;
import com.meowster.omscs.ml.wekarun.config.ClassifierGroup;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup;
import com.meowster.omscs.ml.wekarun.config.SingleJ48;
import com.meowster.omscs.ml.wekarun.config.bgg.LearningBgWeight500_5000;
import com.meowster.omscs.ml.wekarun.config.bgg.MixedBgWeight;
import com.meowster.omscs.ml.wekarun.config.bgg.SingleBgWeight500;

import java.util.List;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Main launch point for running WEKA analysis over the BGG data files.
 */
public class MainWekaRun {

    static final DataFileGroup DFG_MIXED = new MixedBgWeight();
    static final DataFileGroup DFG_SINGLE = new SingleBgWeight500();
    static final DataFileGroup DFG_LEARN5000 = new LearningBgWeight500_5000();

    static final ClassifierGroup CG_SINGLE_J48 = new SingleJ48();

    // =================================================
    // === Parameters for configuring the Experiment ===
    // =================================================

    // the source data files (.arff) containing instance data
    private static final DataFileGroup DATASETS = DFG_MIXED;

    // the group of classifiers to run against the data sets
    private static final ClassifierGroup CLASSIFIERS = CG_SINGLE_J48;

    // the filters to use for splitting train/test data subsets
    private static final List<FilterType> FILTERS = ImmutableList.of(
            FilterType.UNSUPERVISED_RESAMPLE,
            FilterType.SUPERVISED_RESAMPLE
    );

    // =================================================

    /**
     * Main launch point.
     *
     * @param args command line args (ignored)
     */
    public static void main(String[] args) {
        print("%n%n-- Starting WEKA Experiment --");

        new BggDataExperiment(DATASETS, CLASSIFIERS, FILTERS).run();

        print("%n%n-- End of Run --");
    }
}
