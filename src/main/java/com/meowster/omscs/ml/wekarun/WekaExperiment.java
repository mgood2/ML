package com.meowster.omscs.ml.wekarun;

import com.google.common.io.Files;
import com.meowster.omscs.ml.wekarun.CvTestResults.Results;
import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import com.meowster.omscs.ml.wekarun.classifier.ZeroRWekaClassifier;
import com.meowster.omscs.ml.wekarun.config.ClassifierGroup;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup.DataFileInfo;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static com.meowster.omscs.ml.fetcher.Utils.print;
import static com.meowster.omscs.ml.fetcher.Utils.printNoEol;

/**
 * Runs a WEKA experiment for a given configuration of
 * data sets, classifiers and data filters. The default values for
 * splitting data, CV runs and CV folds can be overridden by use of the
 * {@link #dataSplit}, {@link #cvRunCount}, and {@link #cvFolds} methods.
 */
public class WekaExperiment {

    /**
     * Default split percentage; 66.6% Training data, 33.4% Test data.
     */
    public static final double DEFAULT_PERCENT_SPLIT = 66.6;

    /**
     * Default random seed used for splitting the data.
     */
    public static final int DEFAULT_RANDOM_SEED = 42;

    /**
     * Default number of Cross Validation runs to perform.
     * Results are averaged over the runs.
     */
    public static final int DEFAULT_NUM_RUNS = 10;

    /**
     * Default number of folds to use in Cross Validation.
     */
    public static final int DEFAULT_NUM_FOLDS = 10;

    private final DataFileGroup datasets;
    private final ClassifierGroup classifiers;
    private final List<FilterType> filters;

    private final CsvGenerator csv = new CsvGenerator();

    private double percentSplit = DEFAULT_PERCENT_SPLIT;
    private int randomSeed = DEFAULT_RANDOM_SEED;
    private int numRuns = DEFAULT_NUM_RUNS;
    private int numFolds = DEFAULT_NUM_FOLDS;

    /**
     * Constructs a WEKA experiment configured with the data sets,
     * classifiers and filters to use.
     *
     * @param datasets    the data sets
     * @param classifiers the classifiers
     * @param filters     the data filters
     */
    public WekaExperiment(DataFileGroup datasets, ClassifierGroup classifiers,
                          List<FilterType> filters) {
        this.datasets = datasets;
        this.classifiers = classifiers;
        this.filters = filters;
    }


    /**
     * Sets the percentage and random seed used in splitting the data
     * into train and test sets. Default values are:
     * <pre>
     *     percentSplit = 66.6
     *     randomSeed   = 42
     * </pre>
     *
     * @param percentSplit amount of data to use for training (1.0 .. 99.0)
     * @param randomSeed   the random seed (1 or more)
     * @return self, for chaining
     */
    public WekaExperiment dataSplit(double percentSplit, int randomSeed) {
        checkArgument(percentSplit >= 1.0 && percentSplit <= 99.0,
                "Percentage must be in the range {1.0 .. 99.0}");
        checkArgument(randomSeed >= 1, "random seed must be 1 or more");
        this.percentSplit = percentSplit;
        this.randomSeed = randomSeed;
        return this;
    }


    /**
     * Sets the (default) number of Cross Validation runs to execute for
     * training each classifier. The training results produced will be
     * an average over each of these runs. Default value is:
     * <pre>
     *    numRuns = 10
     * </pre>
     * <p/>
     * Note that a specific classifier may override this value; for example
     * the {@link ZeroRWekaClassifier} insists on a single run, since repeated
     * runs just produce the same results.
     *
     * @param numRuns the number of CV runs (1 or more)
     * @return self, for chaining
     */
    public WekaExperiment cvRunCount(int numRuns) {
        this.numRuns = numRuns;
        return this;
    }

    /**
     * Sets the number of folds to use for Cross Validation. Default value is:
     * <pre>
     *    numRuns = 10
     * </pre>
     *
     * @param numFolds the number of CV runs (1 or more)
     * @return self, for chaining
     */
    public WekaExperiment cvFolds(int numFolds) {
        this.numFolds = numFolds;
        return this;
    }

    /**
     * Sets the name of the output CSV file. Note that the directory is
     * defined by overriding {@link #resultsDirectory()}, if necessary.
     *
     * @param name name of CSV file (without .csv suffix)
     * @return self, for chaining
     */
    public WekaExperiment csvFileName(String name) {
        csv.setFilename(name);
        return this;
    }

    /**
     * Returns the path of the directory to which CSV files should be written.
     * This default implmentation returns {@code "."}.
     * <p>
     * Subclasses can override this method to define an alternate path.
     *
     * @return path of results directory
     */
    protected String resultsDirectory() {
        return ".";
    }


    /**
     * Runs the experiment.
     */
    public void run() {
        print("[Start time: %s]", new Date());
        print("[Experiment class: %s]", getClass().getName());

        // outer iteration over file sets
        Iterator<DataFileInfo> dfIter = datasets.iterator();
        while (dfIter.hasNext()) {
            processDataFile(dfIter.next(), classifiers, filters);
        }
        persistResults(csv);
    }

    private void persistResults(CsvGenerator csv) {
        File dir = new File(resultsDirectory());
        File out = new File(dir, csv.filename());

        try {
            Files.write(csv.csvData().getBytes(), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processDataFile(DataFileInfo info, ClassifierGroup classifiers,
                                 List<FilterType> filters) {
        print("%nProcessing data file: %s ...", info.path());

        Instances instances = loadInstances(info.path());
        print("%nInstances Info: %s", getInfo(instances));

        // middle iteration over classifiers
        Iterator<WekaClassifier> cIter = classifiers.iterator();
        while (cIter.hasNext()) {
            processClassifier(info, instances, cIter.next(), filters);
        }
    }

    private String getInfo(Instances instances) {
        return "#instances=" + instances.numInstances() +
                ", #attributes=" + instances.numAttributes();
    }

    private void processClassifier(DataFileInfo info,
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

    private void classifierCrossValidate(DataFileInfo info,
                                         Instances instances,
                                         WekaClassifier classifier,
                                         FilterType filter) {

        // performs cross validation with given classifier
        CvTestResults results =
                trainCrossValidateAndTest(instances, classifier, filter);

        outputResults(info, results);
        csv.addRow(info, results, classifier, filter);
    }

    private Instances loadInstances(String path) {
        try {
            DataSource source = new DataSource(path);
            return preProcessInstances(source.getDataSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Preprocesses the instances loaded from the .arff data file.
     * <p>
     * Subclasses can override this method to tweak the data before passing
     * it on to be processed.
     * <p>
     * This default implementation does nothing
     * (passes the instances back untweaked).
     *
     * @param instances the instances read from the data file
     * @return tweaked instances
     */
    protected Instances preProcessInstances(Instances instances) {
        return instances;
    }

    private CvTestResults trainCrossValidateAndTest(Instances dataSet,
                                                    WekaClassifier wekaClassifier,
                                                    FilterType filter) {
        // Results to be returned
        CvTestResults results = new CvTestResults(dataSet.numInstances(),
                dataSet.numAttributes(), wekaClassifier, filter);

        // intermediate results - averaged later
        CvMetrics metrics = new CvMetrics();


        // Split data into training set and test set.
        // The training set will be used for cross validation.
        TrainAndTestData data =
                new TrainAndTestData(randomSeed, percentSplit, dataSet, filter);

        final int runCount = wekaClassifier.adjustRunCount(numRuns);

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
                        numFolds,
                        new Random(i)
                );

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

    private List<Double> extractPerformanceMetrics(Evaluation eval) {
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

    private void outputResults(DataFileInfo info, CvTestResults results) {
        print("%n** Results for data file: %s...%n", info.path());
        print("%s", results.toString());
    }
}
