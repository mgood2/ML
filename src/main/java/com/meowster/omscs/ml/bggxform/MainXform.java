package com.meowster.omscs.ml.bggxform;

import java.util.concurrent.TimeUnit;

import static com.meowster.omscs.ml.fetcher.Utils.print;
import static java.lang.System.currentTimeMillis;

/**
 * Main launch point for transforming BGG raw data into .arff files for WEKA.
 */
public class MainXform {

    private static final int GAME_STEP = 500;

    private static String timeTaken(long start, long finish) {
        long duration = finish - start;
        print("DONE : (duration in ms: %d)", duration);
        long asHours = TimeUnit.MILLISECONDS.toHours(duration);
        long asMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long minutesInTheHours = TimeUnit.HOURS.toMinutes(asHours);
        long asSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        long secondsInTheMinutes = TimeUnit.MINUTES.toSeconds(asMinutes);

        return String.format("%02d:%02d:%02d",
                asHours,
                asMinutes - minutesInTheHours,
                asSeconds - secondsInTheMinutes);
    }

    public static void main(String[] args) {

        print("XForm BGG Data");
        long start = currentTimeMillis();

        int numGames = 0;
        boolean done = false;
        DataBuilder builder;

        while (!done) {
            numGames += GAME_STEP;
            builder = new DataBuilder().build(numGames);
            done = builder.ranOutOfFiles();
        }

        print("Time Taken: %s", timeTaken(start, currentTimeMillis()));
    }
}
