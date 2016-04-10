package com.meowster.omscs.ml.wekarun.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Abstract base class that encapsulates a list of data files to be used
 * in the experiment. This implementation assumes:
 * <ul>
 * <li>
 * all data files are in the same directory
 * </li>
 * <li>
 * all data files have a ".arff" extension
 * </li>
 * </ul>
 */
public abstract class DataFileGroup {

    private static final String DEFAULT_FORMAT = "%s";
    private static final String ARFF = ".arff";

    private final String dataDirPath;
    private final List<String> filePaths = new ArrayList<>();
    private final List<String> fileIds = new ArrayList<>();
    private String fileNameFormat = DEFAULT_FORMAT;
    private boolean configured = false;


    /**
     * Constructs a data file group using the specified path as the data
     * directory. The constructor calls {@link #configure()} to allow the
     * subclass to define the datafiles.
     *
     * @param dataDirPath data directory path
     */
    protected DataFileGroup(String dataDirPath) {
        this.dataDirPath = dataDirPath;
    }

    /**
     * Subclasses should configure their list of data files
     */
    protected abstract void configure();

    /**
     * Sets the format string for generating file names. It is expected that
     * a single '%s' argument will be defined in the string to indicate where
     * the variable part of the name is.
     * <p/>
     * For example,
     * <pre>setFilenameFormat("data-%s")</pre>
     *
     * The default pattern (if this method is not called) is "%s".
     *
     * @param format the format string
     */
    protected void setFilenameFormat(String format) {
        fileNameFormat = format;
    }

    /**
     * Adds the specified files to the group. Note that file names are
     * generated using the configured data directory, and the pattern
     * for the filename format, ending with the ".arff" file suffix.
     *
     * @param ids file name identifiers
     * @throws IllegalStateException if the group is no longer mutable
     */
    protected void addFiles(String... ids) {
        if (configured) {
            throw new IllegalStateException("No longer mutable!");
        }
        for (String id : ids) {
            String path = mkFileName(id);
            fileIds.add(id);
            filePaths.add(path);
            print(" ..adding path: %s", path);
        }
    }

    private String mkFileName(String id) {
        return String.format("%s/" + fileNameFormat + ARFF, dataDirPath, id);
    }


    /**
     * Returns an iterator over the data file paths in this group.
     * Note that calling this method will mark the group as no longer
     * mutable.
     *
     * @return iterator over our set of data file paths
     */
    public Iterator<DataFileInfo> iterator() {

        // lazy invocation of configure
        if (!configured) {
            print("%nConfiguring DataFileGroup: %s...", getClass().getName());
            configure();
            configured = true;
        }

        return new DataFileIterator();
    }

    /**
     * Iterator implementation.
     */
    private class DataFileIterator implements Iterator<DataFileInfo> {
        int index = 0;

        @Override
        public boolean hasNext() {
            return index < filePaths.size();
        }

        @Override
        public DataFileInfo next() {
            String id = fileIds.get(index);
            String path = filePaths.get(index);
            index++;
            return new DataFileInfo(id, path);
        }

        @Override
        public void remove() {

        }
    }


    /**
     * Encapsulates info about a data file.
     */
    public static class DataFileInfo {
        private final String id;
        private final String path;

        private DataFileInfo(String id, String path) {
            this.id = id;
            this.path = path;
        }

        /**
         * Returns the data file identifier.
         *
         * @return the identifier
         */
        public String id() {
            return id;
        }

        /**
         * Returns the data file path.
         *
         * @return the path
         */
        public String path() {
            return path;
        }

    }

}
