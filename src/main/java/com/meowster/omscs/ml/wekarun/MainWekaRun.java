package com.meowster.omscs.ml.wekarun;

import com.google.common.collect.ImmutableList;
import com.meowster.omscs.ml.wekarun.config.ClassifierGroup;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup;
import com.meowster.omscs.ml.wekarun.config.SingleBoostedJ48;
import com.meowster.omscs.ml.wekarun.config.SingleIBk;
import com.meowster.omscs.ml.wekarun.config.SingleJ48;
import com.meowster.omscs.ml.wekarun.config.SinglePerceptron;
import com.meowster.omscs.ml.wekarun.config.SingleSmo;
import com.meowster.omscs.ml.wekarun.config.bgg.LearningBgWeight10k;
import com.meowster.omscs.ml.wekarun.config.bgg.MixedBgWeight;
import com.meowster.omscs.ml.wekarun.config.bgg.SingleBgWeight500;
import org.apache.commons.lang.time.StopWatch;

import java.util.List;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Main launch point for running WEKA analysis over the BGG data files.
 */
public class MainWekaRun {

    static final DataFileGroup DFG_MIXED = new MixedBgWeight();
    static final DataFileGroup DFG_SINGLE = new SingleBgWeight500();
    static final DataFileGroup DFG_LEARN10k = new LearningBgWeight10k();

    static final ClassifierGroup CG_SINGLE_J48 = new SingleJ48();
    static final ClassifierGroup CG_SINGLE_PERCEPTRON = new SinglePerceptron();
    static final ClassifierGroup CG_SINGLE_BOOSTED_J48 = new SingleBoostedJ48();
    static final ClassifierGroup CG_SINGLE_SMO = new SingleSmo();
    static final ClassifierGroup CG_SINGLE_IBK = new SingleIBk();

    // the filters to use for splitting train/test data subsets
    private static final List<FilterType> FILTERS = ImmutableList.of(
//            FilterType.UNSUPERVISED_RESAMPLE,
            FilterType.SUPERVISED_RESAMPLE
    );

    // =================================================
    // === Parameters for configuring the Experiment ===
    // =================================================

    // the source data files (.arff) containing instance data
    private static final DataFileGroup DATASETS = DFG_LEARN10k;

    // the group of classifiers to run against the data sets
    private static final ClassifierGroup CLASSIFIERS = CG_SINGLE_IBK;

    // the name of the CSV file
    private static final String CSV_FILE_NAME = "learningIBk";

    // =================================================

    /**
     * Main launch point.
     *
     * @param args command line args (ignored)
     */
    public static void main(String[] args) {
        print("%n%n-- Starting WEKA Experiment --");
        StopWatch sw = new StopWatch();
        sw.start();

        new BggDataExperiment(DATASETS, CLASSIFIERS, FILTERS)
                .csvFileName(CSV_FILE_NAME)
                .run();

        sw.stop();
        print("%n%n-- End of Run -- [%s]", sw);
    }
}
