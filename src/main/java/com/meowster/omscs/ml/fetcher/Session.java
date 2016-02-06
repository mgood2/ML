package com.meowster.omscs.ml.fetcher;


import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Creates a session to fetch data from BGG.
 */
public class Session {

    //    private static final int BATCH_SIZE = 50;
    private static final int QUERY_SIZE = 10;

    private static final long SLEEP_FOR_MS = 2000;

    private int startId;
    private int currentId;

    private final int batchSize;

    private int currentBatch = 0;

    public Session(int batchSize) {
        this.batchSize = batchSize;
    }

    private void sleep() {
        try {
            Thread.sleep(SLEEP_FOR_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startFrom(int startId) {
        this.startId = startId;
        this.currentId = startId;

        while (currentBatch < batchSize) {
            BggQuery bq = new BggQuery(currentId, QUERY_SIZE);
            bq.query();

            new Persister(bq);

            currentBatch++;
            currentId += QUERY_SIZE;
            sleep();
        }

        print("%n----------------- DONE%n >> Next ID:: %d", currentId);
    }

}
