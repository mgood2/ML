package com.meowster.omscs.ml.wekarun;

/**
 * Partial implementation of a {@link CsvGenerator}.
 */
public abstract class AbstractCsvGenerator implements CsvGenerator {

    protected static final String EOL = String.format("%n");
    protected static final String QUOTE = "\"";
    protected static final String COMMA = ",";
    protected static final String CSV = ".csv";
    protected static final String FORMAT_DOUBLE = "%.4f";
    protected static final String DEFAULT_FILE_NAME = "experiment";

    protected String filename = DEFAULT_FILE_NAME;
    protected boolean includeHeader = true;

    @Override
    public CsvGenerator setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    @Override
    public CsvGenerator excludeHeader() {
        includeHeader = false;
        return this;
    }

    @Override
    public String filename() {
        return filename + CSV;
    }


}
