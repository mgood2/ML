package com.meowster.omscs.ml.wekarun.config.bgg;

/**
 * A single BGG Game Weight data set of 500 instances.
 */
public class SingleBgWeight500 extends BgWeightGroup {

    @Override
    protected void configure() {
        super.configure();
        addFiles("000500");
    }
}
