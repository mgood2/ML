package com.meowster.omscs.ml.wekarun.config;

/**
 * A single BGG Game Weight data set.
 */
public class SingleBgWeightFile extends BgWeightGroup {

    @Override
    protected void configure() {
        super.configure();
        addFiles("000500");
    }
}
