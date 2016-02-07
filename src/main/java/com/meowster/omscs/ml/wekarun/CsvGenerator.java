package com.meowster.omscs.ml.wekarun;

import com.meowster.omscs.ml.wekarun.CvTestResults.Results;
import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup.DataFileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Captures results from runs and produces CSV output.
 */
public class CsvGenerator {

    private static final String FIRST_CSV_COLS =
            "Dataset,Filter,Classifier,Instances";
    private static final String EOL = String.format("%n");
    private static final String QUOTE = "\"";
    private static final String COMMA = ",";
    private static final String CSV = ".csv";
    private static final String DOUBLE_FORMAT = "%.4f";
    private static final String DEFAULT_FILE_NAME = "experiment";

    private String filename = DEFAULT_FILE_NAME;
    private final List<Row> rows = new ArrayList<>();
    private boolean includeHeader = true;

    /**
     * Sets the filename to use when writing out the CSV file.
     * If not changed, the default of "experiment" is used.
     *
     * @param filename the file name
     * @return self, for chaining
     */
    public CsvGenerator setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * Sets the option to exclude the header row in the output.
     *
     * @return self, for chaining
     */
    public CsvGenerator excludeHeader() {
        includeHeader = false;
        return this;
    }

    /**
     * Adds a data row.
     *
     * @param info       the source data file information
     * @param results    results of the run
     * @param classifier the classifier used
     * @param filter     the filter used for splitting data
     */
    public void addRow(DataFileInfo info, CvTestResults results,
                       WekaClassifier classifier, FilterType filter) {
        rows.add(new Row(info, results, classifier, filter));
    }

    /**
     * Returns the filename to use when writing this data to disk.
     *
     * @return the filename
     */
    public String filename() {
        return filename + CSV;
    }

    /**
     * Returns the CSV data in a form that can be written out as a .csv file.
     */
    public String csvData() {
        StringBuilder sb = new StringBuilder();
        addHeader(sb);
        for (Row row : rows) {
            sb.append(row);
        }
        return sb.toString();
    }

    private void addHeader(StringBuilder sb) {
        if (includeHeader) {
            sb.append(FIRST_CSV_COLS)
                    .append(resultsHeaders(Results.CV))
                    .append(resultsHeaders(Results.TEST))
                    .append(EOL);

        }
    }

    private String resultsHeaders(Results type) {
        StringBuilder sb = new StringBuilder();
        List<String> cols = CvTestResults.columnHeaders(type);
        for (String c : cols) {
            sb.append(COMMA).append(c);
        }
        return sb.toString();
    }

    /**
     * Captures information about a row.
     */
    private static class Row {

        private final String dataSetTag;
        private final String filterTag;
        private final String classifierTag;
        private final CvTestResults results;

        private Row(DataFileInfo info, CvTestResults results,
                    WekaClassifier classifier, FilterType filter) {
            dataSetTag = QUOTE + info.id() + QUOTE;
            filterTag = filter.tag();
            classifierTag = classifier.tag();
            this.results = results;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(dataSetTag)
                    .append(COMMA)
                    .append(filterTag).append(COMMA)
                    .append(classifierTag).append(COMMA)
                    .append(results.numInstances());

            appendResults(sb, results.results(Results.CV));
            appendResults(sb, results.results(Results.TEST));
            return sb.append(EOL).toString();
        }

        private void appendResults(StringBuilder sb, List<Double> results) {
            for (double d : results) {
                sb.append(COMMA).append(String.format(DOUBLE_FORMAT, d));
            }
        }
    }
}
