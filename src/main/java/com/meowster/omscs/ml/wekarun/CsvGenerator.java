package com.meowster.omscs.ml.wekarun;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup.DataFileInfo;

/**
 * Designates a class capable of capturing run data and outputting
 * the data in CSV format.
 */
public interface CsvGenerator {

    /**
     * Sets the filename to use when writing out the CSV file.
     * If not changed, the default of "experiment" is used.
     *
     * @param filename the file name
     * @return self, for chaining
     */
    CsvGenerator setFilename(String filename);

    /**
     * Sets the option to exclude the header row in the output.
     *
     * @return self, for chaining
     */
    CsvGenerator excludeHeader();

    /**
     * Adds a data row.
     *
     * @param info       the source data file information
     * @param results    results of the run
     * @param classifier the classifier used
     * @param filter     the filter used for splitting data
     */
    void addRow(DataFileInfo info, CvTestResults results,
                WekaClassifier classifier, FilterType filter);

    /**
     * Returns the filename to use when writing this data to disk.
     *
     * @return the filename
     */
    String filename();

    /**
     * Returns the CSV data in a form that can be written out as a .csv file.
     */
    String csvData();

}
