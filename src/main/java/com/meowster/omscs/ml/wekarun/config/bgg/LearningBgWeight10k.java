package com.meowster.omscs.ml.wekarun.config.bgg;

/**
 * Ten Board Game Weight datasets, (1,000..10,000 instances).
 */
public class LearningBgWeight10k extends BgWeightGroup {

    @Override
    protected void configure() {
        super.configure();
        addFiles(
                "001000",
                "002000",
                "003000",
                "004000",
                "005000",
                "006000",
                "007000",
                "008000",
                "009000",
                "010000"
        );
    }
}
