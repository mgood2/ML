package com.meowster.omscs.ml.wekarun;

import com.meowster.omscs.ml.wekarun.CvTestResults.Results;
import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup.DataFileInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSV generator to arrange data to allow comparison of classifiers
 * with respect to their learning curves.
 */
public class ClassifierLearningCsvGenerator extends AbstractCsvGenerator {

    private static final String HEADER_LINE =
            "#inst,#attr" +
                    ",J48-cv,J48-test" +
                    ",J48-Boost-cv,J48-Boost-test" +
//                    ",ANN-cv,ANN-test" +
                    ",SVM-cv,SVM-test" +
                    ",kNN-cv,kNN-test" +
                    "\n";
    private static final WekaClassifier.Type[] C_TYPES = {
            WekaClassifier.Type.J48,
            WekaClassifier.Type.ADA_BOOST_M1,
//            WekaClassifier.Type.MULTILAYER_PERCEPTRON,
            WekaClassifier.Type.SMO,
            WekaClassifier.Type.IBK
    };

    private final Map<WekaClassifier.Type, Boolean> seen = new HashMap<>();
    private final List<Row> rows = new ArrayList<>();

    private Row currentRow = null;

    private boolean newRowRequired(WekaClassifier.Type type) {
        Boolean bool = seen.get(type);
        boolean alreadyInMap = bool != null && bool;
        if (alreadyInMap) {
            seen.clear();
        }
        seen.put(type, true);
        return alreadyInMap;
    }

    @Override
    public void addRow(DataFileInfo info, CvTestResults results,
                       WekaClassifier classifier, FilterType filter) {
        WekaClassifier.Type type = classifier.type();

        int numInst = results.numInstances();
        int numAttr = results.numAttibutes();

        if (currentRow == null) {
            currentRow = new Row(numInst, numAttr);
        }

        if (newRowRequired(type)) {
            rows.add(currentRow);
            currentRow = new Row(numInst, numAttr);
        }

        currentRow.addData(results, type);
    }

    @Override
    public String csvData() {
        // finish up the last row
        rows.add(currentRow);
        // now create the CSV
        StringBuilder sb = new StringBuilder(HEADER_LINE);
        for (Row r: rows) {
            sb.append(r);
        }
        return sb.toString();
    }


    /**
     * Captures information about a row.
     */
    private static class Row {
        private final int numInst;
        private final int numAttr;

        private final Map<WekaClassifier.Type, CvTestResults> map =
                new HashMap<>();

        Row(int numInst, int numAttr) {
            this.numInst = numInst;
            this.numAttr = numAttr;
        }

        void addData(CvTestResults results, WekaClassifier.Type type) {
            map.put(type, results);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(numInst).append(COMMA).append(numAttr);
            for (WekaClassifier.Type type : C_TYPES) {
                CvTestResults r = map.get(type);
                sb.append(getCvTestErrors(r));
            }
            return sb.append(EOL).toString();
        }

        private String getCvTestErrors(CvTestResults r) {
            StringBuilder sb = new StringBuilder();
            sb.append(COMMA)
                    .append(getError(r, Results.CV))
                    .append(COMMA)
                    .append(getError(r, Results.TEST));
            return sb.toString();
        }

        private String getError(CvTestResults r, Results type) {
            return String.format(FORMAT_DOUBLE, r.results(type).get(IDX_ERROR));
        }
    }

    private static final int IDX_ERROR = 1;
}
