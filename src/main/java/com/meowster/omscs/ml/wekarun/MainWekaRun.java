package com.meowster.omscs.ml.wekarun;

import com.google.common.collect.ImmutableList;
import com.meowster.omscs.ml.wekarun.CvTestResults.Results;
import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import com.meowster.omscs.ml.wekarun.config.ClassifierGroup;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup.DataFileInfo;
import com.meowster.omscs.ml.wekarun.config.SingleBgWeightFile;
import com.meowster.omscs.ml.wekarun.config.SingleJ48;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.meowster.omscs.ml.fetcher.Utils.print;
import static com.meowster.omscs.ml.fetcher.Utils.printNoEol;

/**
 * Main launch point for running WEKA analysis over the BGG data files.
 * Configuration of which classifiers we want to run is defined in
 * {@link ClassifierGroup}.
 */
public class MainWekaRun {

    // =================================================
    // === Parameters for configuring the Experiment ===
    // =================================================

    // the source data files (.arff) containing instance data
    private static final DataFileGroup DATASETS = new SingleBgWeightFile();

    // the group of classifiers to run against the data sets
    private static final ClassifierGroup CLASSIFIERS = new SingleJ48();

    // the filters to use for splitting train/test data subsets
    private static final List<FilterType> FILTERS = ImmutableList.of(
            FilterType.UNSUPERVISED_RESAMPLE,
            FilterType.SUPERVISED_RESAMPLE
    );

    // how much data to use for training (vs. test)
    private static final double PERCENT_SPLIT = 66.6;

    // number of runs (CV metrics are averaged over those runs)
    //  (unless the classifier overrides this, for example ZERO_R, ONE_R)
    private static final int NUM_RUNS = 10;

    // number of folds for cross validation
    private static final int NUM_FOLDS = 10;

    // default random seed used by WEKA
    private static final int WEKA_SEED = 42;


    // =================================================

    /**
     * Main launch point for running the weka analysis of classifiers
     * against the data sets.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        print("%n%n-- Starting WEKA Experiment --");
        runExperiment(DATASETS, CLASSIFIERS, FILTERS);
        print("%n%n-- End of Run --");
    }

    private static void runExperiment(DataFileGroup dataFiles,
                                      ClassifierGroup classifiers,
                                      List<FilterType> filters) {
        // outer iteration over file sets
        Iterator<DataFileInfo> dfIter = dataFiles.iterator();
        while (dfIter.hasNext()) {
            processDataFile(dfIter.next(), classifiers, filters);
        }
    }

    private static void processDataFile(DataFileInfo info,
                                        ClassifierGroup classifiers,
                                        List<FilterType> filters) {
        print("%nProcessing data file: %s ...", info.path());

        Instances instances = loadInstances(info.path());

        // middle iteration over classifiers
        Iterator<WekaClassifier> cIter = classifiers.iterator();
        while (cIter.hasNext()) {
            processClassifier(info, instances, cIter.next(), filters);
        }
    }

    private static void processClassifier(DataFileInfo info,
                                          Instances instances,
                                          WekaClassifier classifier,
                                          List<FilterType> filters) {
        print("%n  Processing with classifier: %s", classifier);

        // inner iteration over filters
        for (FilterType filter : filters) {
            print("%n    Processing with filter: %s", filter);
            classifierCrossValidate(info, instances, classifier, filter);
        }
    }

    private static void classifierCrossValidate(DataFileInfo info,
                                                Instances instances,
                                                WekaClassifier classifier,
                                                FilterType filter) {

        // performs cross validation with given classifier
        CvTestResults results =
                trainCrossValidateAndTest(instances, classifier, filter);

        outputResults(info, results);
        persistResults(info, results, classifier, filter);
    }

    private static Instances loadInstances(String path) {
        Instances instances = null;
        try {
            DataSource source = new DataSource(path);

            instances = source.getDataSet();
            // remove the game ID, (first attribute), which is not useful data
            instances.deleteAttributeAt(0);

            // make sure the class index is set to the last column
            if (instances.classIndex() == -1) {
                instances.setClassIndex(instances.numAttributes() - 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return instances;
    }

    private static CvTestResults
    trainCrossValidateAndTest(Instances dataSet, WekaClassifier wekaClassifier,
                              FilterType filter) {

        // Results to be returned
        CvTestResults results = new CvTestResults(dataSet.numInstances(),
                wekaClassifier, filter);

        // intermediate results - averaged later
        CvMetrics metrics = new CvMetrics();


        // Split data into training set and test set.
        // The training set will be used for cross validation.
        TrainAndTestData data =
                new TrainAndTestData(WEKA_SEED, PERCENT_SPLIT, dataSet, filter);

        final int runCount = wekaClassifier.adjustRunCount(NUM_RUNS);

        try {
            printNoEol("%n>CV Run: ");

            // run the Cross Validation N times, and produce average results
            for (int i = 1; i <= runCount; i++) {

                results.startTiming(Results.CV, i);

                // Cross validate - N fold
                Evaluation eval = new Evaluation(data.trainSet());

                eval.crossValidateModel(
                        wekaClassifier.newClassifier(),
                        new Instances(data.trainSet()),
                        NUM_FOLDS,
                        new Random(i));

                results.stopTiming();

                metrics.add(extractPerformanceMetrics(eval));
                printNoEol("..%d", i);
            }
            print(".. DONE");
            results.saveResults(Results.CV, metrics.average());


            // run the trained model against the test data
            results.startTiming(Results.TEST);

            // have to use a new instance of the classifier and train it (?)
            Classifier trainedUp = wekaClassifier.newClassifier();
            trainedUp.buildClassifier(data.trainSet());

            // now run against the test data
            Evaluation eval = new Evaluation(data.testSet());

            eval.evaluateModel(trainedUp, data.testSet());
            results.stopTiming();

            results.saveResults(Results.TEST, extractPerformanceMetrics(eval));
            print("%n>Test Run >>  %n%n%s", wekaClassifier.wekaToString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    private static void persistResults(DataFileInfo info,
                                       CvTestResults results,
                                       WekaClassifier classifier,
                                       FilterType filter) {
        // TODO persist results to disk
    }

    private static List<Double> extractPerformanceMetrics(Evaluation eval) {
        return new ArrayList<>(
                Arrays.asList(
                        eval.pctCorrect(),
                        eval.pctIncorrect(),
                        eval.weightedPrecision(),
                        eval.weightedRecall(),
                        eval.weightedFMeasure()
                )
        );
    }

    private static void outputResults(DataFileInfo info, CvTestResults results) {
        print("%n** Results for data file: %s...%n", info.path());
        print("%s", results.toString());
    }

}
