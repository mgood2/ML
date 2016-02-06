package com.meowster.omscs.ml.wekarun.config.bgg;

import com.meowster.omscs.ml.wekarun.config.DataFileGroup;

/**
 * Base class for Board Game Weight data files.
 */
public abstract class BgWeightGroup extends DataFileGroup {

    private static final String DATA_DIR = "data/bgg-out";
    private static final String FILENAME_FORMAT = "bgweights-%s";


    /**
     * Constructor defines the BGG data directory.
     */
    protected BgWeightGroup() {
        super(DATA_DIR);
    }

    /**
     * Sets the filename format for the board game weights data files.
     */
    @Override
    protected void configure() {
        setFilenameFormat(FILENAME_FORMAT);
    }
}
