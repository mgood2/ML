package com.meowster.omscs.ml.wekarun;

import com.meowster.omscs.ml.wekarun.CvTestResults.Results;
import com.meowster.omscs.ml.wekarun.classifier.ClassifierGroup;
import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import org.apache.commons.lang.time.StopWatch;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Main launch point for running WEKA analysis over the BGG data files.
 */
public class MainWekaRun {

    private static final String DATA_ID = "000500";

    private static final int WEKA_SEED = 42;


    /**
     * Main launch point for running the weka analysis of classifiers
     * against the data sets.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        print("WEKA run");
        // TODO: possibly vary the input data
        Instances instances = loadInstances(DATA_ID);

        if (instances != null) {
            runExperiment(instances, DATA_ID);
        } else {
            print("Woah!! No Instances!!");
        }
        print("All DONE");
    }

    private static Instances loadInstances(String dataFileId) {
        Instances instances = null;
        try {
            DataSource source = new DataSource(mkFileName(dataFileId));

            instances = source.getDataSet();
            // remove the game ID, which is not useful data
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

    private static String mkFileName(String name) {
        return String.format("data/bgg-out/bgweights-%s.arff", name);
    }

    private static void runExperiment(Instances instances, String dataId) {
        StopWatch sw = new StopWatch();

        ClassifierGroup group = new ClassifierGroup();
        Iterator<WekaClassifier> iter = group.iterator();
        while (iter.hasNext()) {
            WekaClassifier classifier = iter.next();
            print("Processing with classifier %s", classifier);

            // todo : each type of filter....
            FilterType filter = FilterType.UNSUPERVISED_RESAMPLE;

            sw.start();
            classifierCrossValidate(dataId, instances, classifier, filter);
            sw.stop();
            print("Exp: %s: time: %s", classifier, sw);
        }
    }

    private static void classifierCrossValidate(String dataId,
                                                Instances instances,
                                                WekaClassifier classifier,
                                                FilterType filter) {

        // performs cross validation with given classifier
        CvTestResults results =
                trainCrossValidateAndTest(instances, classifier, filter);

        outputResults(dataId, results);
        persistResults(results, dataId, classifier, filter);

    }

    private static void persistResults(CvTestResults results,
                                       String dataId,
                                       WekaClassifier classifier,
                                       FilterType filter) {
        // TODO persist results to disk
    }

    private static CvTestResults trainCrossValidateAndTest(
            Instances dataSet, WekaClassifier wekaClassifier, FilterType filter) {


        // Results to be returned
        CvTestResults results = new CvTestResults(dataSet.numInstances());

        // intermediate results - averaged later
        CvMetrics metrics = new CvMetrics();


        // Split data into training set and test set.
        // The training set will be used for cross validation.
        TrainAndTestData data =
                new TrainAndTestData(WEKA_SEED, PERCENT_SPLIT, dataSet, filter);

        final int runCount = wekaClassifier.adjustRunCount(NUM_RUNS);

        try {
            // run the Cross Validation N times, and produce average results
            for (int i = 1; i <= runCount; i++) {
                // Cross validate - N fold
                Evaluation eval = new Evaluation(data.trainSet());

                eval.crossValidateModel(
                        wekaClassifier.classifier(),
                        new Instances(data.trainSet()),
                        NUM_FOLDS,
                        new Random(i));

                metrics.add(extractPerformanceMetrics(eval));
            }
            results.saveResults(Results.CV, metrics.average());

            // now run against the test data
            Evaluation eval = new Evaluation(data.testSet());

            eval.evaluateModel(wekaClassifier.classifier(), data.testSet());
            results.saveResults(Results.TEST, extractPerformanceMetrics(eval));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
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

    private static void outputResults(String dataId, CvTestResults results) {
        print("%nResults for data file: %s...", dataId);
        print("%s", results.toString());
    }

    /* configuration parameters */

    // how much data to use for training...
    private static final double PERCENT_SPLIT = 66.6;

    private static final int NUM_FOLDS = 10;

    private static final int NUM_RUNS = 10;  // changes random seed for every run
}
