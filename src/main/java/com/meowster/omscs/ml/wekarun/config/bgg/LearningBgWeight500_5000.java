package com.meowster.omscs.ml.wekarun.config.bgg;

/**
 * Ten Board Game Weight datasets, (500..5000 instances).
 */
public class LearningBgWeight500_5000 extends BgWeightGroup {

    @Override
    protected void configure() {
        super.configure();
        addFiles(
                "000500",
                "001000",
                "001500",
                "002000",
                "002500",
                "003000",
                "003500",
                "004000",
                "004500",
                "005000"
        );
    }
}
