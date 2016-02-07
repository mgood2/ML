package com.meowster.omscs.ml.wekarun;

import com.meowster.omscs.ml.wekarun.CvTestResults.Results;
import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup.DataFileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Captures results from runs and produces CSV output.
 */
public class DefaultCsvGenerator extends AbstractCsvGenerator {

    private static final String FIRST_CSV_COLS =
            "Dataset,Filter,Classifier,nInst,nAttr";

    private final List<Row> rows = new ArrayList<>();

    @Override
    public void addRow(DataFileInfo info, CvTestResults results,
                       WekaClassifier classifier, FilterType filter) {
        rows.add(new Row(info, results, classifier, filter));
    }

    @Override
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
                    .append(results.numInstances()).append(COMMA)
                    .append(results.numAttibutes());

            appendResults(sb, results.results(Results.CV));
            appendResults(sb, results.results(Results.TEST));
            return sb.append(EOL).toString();
        }

        private void appendResults(StringBuilder sb, List<Double> results) {
            for (double d : results) {
                sb.append(COMMA).append(String.format(FORMAT_DOUBLE, d));
            }
        }
    }
}
