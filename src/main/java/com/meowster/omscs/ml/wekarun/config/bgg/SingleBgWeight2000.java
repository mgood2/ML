package com.meowster.omscs.ml.wekarun.config.bgg;

/**
 * A single BGG Game Weight data set of 2000 instances.
 */
public class SingleBgWeight2000 extends BgWeightGroup {

    @Override
    protected void configure() {
        super.configure();
        addFiles("002000");
    }
}
