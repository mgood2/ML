package com.meowster.omscs.ml.wekarun;

import com.google.common.collect.ImmutableList;
import com.meowster.omscs.ml.wekarun.config.ClassifierGroup;
import com.meowster.omscs.ml.wekarun.config.CreditApprovalGroup;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup;
import com.meowster.omscs.ml.wekarun.config.Project1ClassifierGroup;
import com.meowster.omscs.ml.wekarun.config.SingleBoostedJ48;
import com.meowster.omscs.ml.wekarun.config.SingleIBk;
import com.meowster.omscs.ml.wekarun.config.SingleJ48;
import com.meowster.omscs.ml.wekarun.config.SinglePerceptron;
import com.meowster.omscs.ml.wekarun.config.SingleSmo;
import com.meowster.omscs.ml.wekarun.config.VaryingIBk;
import com.meowster.omscs.ml.wekarun.config.VaryingJ48TreePrConf;
import com.meowster.omscs.ml.wekarun.config.bgg.LearningBgWeight10k;
import com.meowster.omscs.ml.wekarun.config.bgg.MixedBgWeight;
import com.meowster.omscs.ml.wekarun.config.bgg.SingleBgWeight2000;
import com.meowster.omscs.ml.wekarun.config.bgg.SingleBgWeight500;
import org.apache.commons.lang.time.StopWatch;

import java.util.List;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Main launch point for running WEKA analysis over the BGG data files.
 */
public class MainWekaRun {

    private static final String RED = "-RED";

    static final DataFileGroup DFG_MIXED = new MixedBgWeight();
    static final DataFileGroup DFG_SINGLE = new SingleBgWeight500();
    static final DataFileGroup DFG_LEARN10k = new LearningBgWeight10k();
    static final DataFileGroup DFG_2K = new SingleBgWeight2000();

    static final ClassifierGroup CG_PROJECT1 = new Project1ClassifierGroup();
    static final ClassifierGroup CG_SINGLE_J48 = new SingleJ48();
    static final ClassifierGroup CG_SINGLE_PERCEPTRON = new SinglePerceptron();
    static final ClassifierGroup CG_SINGLE_BOOSTED_J48 = new SingleBoostedJ48();
    static final ClassifierGroup CG_SINGLE_SMO = new SingleSmo();
    static final ClassifierGroup CG_SINGLE_IBK = new SingleIBk();
    static final ClassifierGroup CG_VARYING_IBK = new VaryingIBk(1, 21, 2);
    static final ClassifierGroup CG_VARYING_J48 =
            new VaryingJ48TreePrConf(0.1, 0.5, 0.05);

    // the filters to use for splitting train/test data subsets
    private static final List<FilterType> FILTERS = ImmutableList.of(
//            FilterType.UNSUPERVISED_RESAMPLE,
            FilterType.SUPERVISED_RESAMPLE
    );

    // -----
    // Parameters for alternate dataset:: Credit Approval
    static final DataFileGroup DFG_CREDIT = new CreditApprovalGroup();


    // =================================================
    // === Parameters for configuring the Experiment ===
    // =================================================

    // the source data files (.arff) containing instance data
    private static final DataFileGroup DATASETS = DFG_CREDIT;

    // the group of classifiers to run against the data sets
    private static final ClassifierGroup CLASSIFIERS = CG_PROJECT1;

    // the name of the CSV file
    private static final String CSV_FILE_NAME = "comparisonProj1";

    // run credit rating dataset tests (instead of BGG data tests)
    private static final boolean RUN_CREDIT_RATING_TESTS = true;

    // if running BGG data tests...
    //   ..use reduced attributes datasets (9 attribs vs. 13 attribs)
    private static final boolean USE_REDUCED = true;


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

        if (RUN_CREDIT_RATING_TESTS) {
            new UciCreditExperiment(DATASETS, CLASSIFIERS, FILTERS)
                    .csvFileName(CSV_FILE_NAME)
                    .run();

        } else if (USE_REDUCED) {
            new BggReducedDataExperiment(DATASETS, CLASSIFIERS, FILTERS)
                    .csvFileName(CSV_FILE_NAME + RED)
                    .run();

        } else {
            new BggDataExperiment(DATASETS, CLASSIFIERS, FILTERS)
                    .csvFileName(CSV_FILE_NAME)
                    .run();

        }

        sw.stop();
        print("%n%n-- End of Run -- [%s]", sw);
    }
}
