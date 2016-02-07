package com.meowster.omscs.ml.wekarun.config;

/**
 * Credit Approval dataset.
 */
public class CreditApprovalGroup extends DataFileGroup {

    private static final String DATA_DIR = "data/uci";

    /**
     * Constructor defines the UCI data directory.
     */
    public CreditApprovalGroup() {
        super(DATA_DIR);
    }

    @Override
    protected void configure() {
        addFiles("credit-a");
    }
}
