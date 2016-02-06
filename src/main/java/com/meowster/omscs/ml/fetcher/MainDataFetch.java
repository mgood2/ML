package com.meowster.omscs.ml.fetcher;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Main method for fetching data from BGG.
 */
public class MainDataFetch {

    private static final String VERSION = "1.0";

    private MainDataFetch(int startId, int batchSize) {
        print("Data Fetcher System: version %s", VERSION);
        print("Start Game ID: %d%nBatch Size: %d%n", startId, batchSize);
        new Session(batchSize).startFrom(startId);
    }

    private static void usage() {
        System.out.println("Usage: Main <start-game-id> <batch-size>");
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            usage();
        }

        try {
            int startId = Integer.valueOf(args[0]);
            int batchSize = Integer.valueOf(args[1]);
            new MainDataFetch(startId, batchSize);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            usage();
        }
    }
}
