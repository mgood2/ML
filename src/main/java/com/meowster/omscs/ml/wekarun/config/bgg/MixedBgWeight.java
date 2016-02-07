package com.meowster.omscs.ml.wekarun.config.bgg;

/**
 * A mixture of data set sizes.
 */
public class MixedBgWeight extends BgWeightGroup {

    @Override
    protected void configure() {
        super.configure();
        addFiles(
                "000500",
                "002500",
                "010000",
                "017500",
                "025000"
        );
    }
}
