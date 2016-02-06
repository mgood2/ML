package com.meowster.omscs.ml.bggxform;

/**
 * Designates estimated game weight; that is, is this a light game
 * (e.g. party/trivia/...) or a heavy game (war/strategy/...) or something
 * in between?
 */
public enum GameWeight {

    LIGHT(1.0),
    MEDIUM_LIGHT(2.0),
    MEDIUM(3.0),
    MEDIUM_HEAVY(4.0),
    HEAVY(5.0);

    private static final double HALF = 0.5;

    private final double maxThreshold;

    GameWeight(double level) {
        this.maxThreshold = level + HALF;
    }

    /**
     * Returns the weight nearest to the given real value.
     *
     * @param value the weight value
     * @return nearest weight classification
     */
    public static GameWeight nearest(double value) {
        for (GameWeight weight: values()) {
            if (value < weight.maxThreshold)
                return weight;
        }
        return HEAVY;
    }
}
