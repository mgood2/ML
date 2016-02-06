package com.meowster.omscs.ml.fetcher;

import com.google.common.base.MoreObjects;

import java.util.Set;
import java.util.TreeSet;

/**
 * Encapsulates game ratings data.
 */

public class GameRatings {

    private final int gameId;

    private int usersRated;
    private double average;
    private double bayesAverage;
    private double stdDev;

    private int owned;
    private int trading;
    private int wanting;
    private int wishing;
    private int numComments;
    private int numWeights;
    private double averageWeight;

    private final Set<Rank> ranks = new TreeSet<>();


    public GameRatings(int gameId) {
        this.gameId = gameId;
    }

    public GameRatings usersRated(int n) {
        usersRated = n;
        return this;
    }

    public GameRatings average(double av) {
        average = av;
        return this;
    }

    public GameRatings bayesAverage(double av) {
        bayesAverage = av;
        return this;
    }

    public GameRatings stdDev(double sd) {
        stdDev = sd;
        return this;
    }

    public GameRatings owned(int n) {
        owned = n;
        return this;
    }

    public GameRatings trading(int n) {
        trading = n;
        return this;
    }

    public GameRatings wanting(int n) {
        wanting = n;
        return this;
    }

    public GameRatings wishing(int n) {
        wishing = n;
        return this;
    }

    public GameRatings numComments(int n) {
        numComments = n;
        return this;
    }

    public GameRatings numWeights(int n) {
        numWeights = n;
        return this;
    }

    /*
        Weight Scale...
        1 : Light
        2 : Medium Light
        3 : Medium
        4 : Medium Heavy
        5 : Heavy
    */
    public GameRatings averageWeight(double av) {
        averageWeight = av;
        return this;
    }

    public GameRatings addRank(Rank rank) {
        ranks.add(rank);
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("gameId", gameId)
                .add("usersRated", usersRated)
                .add("average", average)
                .add("bayesAverage", bayesAverage)
                .add("stdDev", stdDev)
                .add("owned", owned)
                .add("trading", trading)
                .add("wanting", wanting)
                .add("wishing", wishing)
                .add("#cmt", numComments)
                .add("#wt", numWeights)
                .add("av.wt", averageWeight)
                .add("rankings", ranks)
                .toString();
    }

    public int usersRated() {
        return usersRated;
    }

    public double average() {
        return average;
    }

    public double bayesAverage() {
        return bayesAverage;
    }

    public double stdDev() {
        return stdDev;
    }

    public int owned() {
        return owned;
    }

    public int trading() {
        return trading;
    }

    public int wanting() {
        return wanting;
    }

    public int wishing() {
        return wishing;
    }

    public int numComments() {
        return numComments;
    }

    public int numWeights() {
        return numWeights;
    }

    public double averageWeight() {
        return averageWeight;
    }

    public Set<Rank> ranks() {
        return ranks;
    }

    /*
      Ranking Scale:

      10 - Outstanding. Always want to play and expect this will never change.
       9 - Excellent game. Always want to play it.
       8 - Very good game. I like to play. Probably suggest it; never turn down.
       7 - Good game. Usually willing to play.
       6 - Ok game, some fun or challenge at least, will play if in mood.
       5 - Average game, slightly boring, take it or leave it.
       4 - Not so good, it doesn't get me.
       3 - Likely won't play this again. Bad.
       2 - Extremely annoying game, won't ever play this again.
       1 - Defies description of a game. Clearly broken.r

     */

    public static final int NOT_RANKED_INT = -1;
    public static final double NOT_RANKED_DOUBLE = -1.0;

    /**
     * Encapsulates a game ranking.
     */
    public static class Rank implements Comparable<Rank> {

        private String type;
        private String name;
        private String friendly;
        private int value;
        private double bayesAverage;

        @Override
        public int compareTo(Rank o) {
            return name.compareTo(o.name);
        }

        public Rank type(String type) {
            this.type = type;
            return this;
        }

        public Rank name(String name) {
            this.name = name;
            return this;
        }

        public Rank friendly(String friendly) {
            this.friendly = friendly;
            return this;
        }

        public Rank value(String valueStr) {
            int value = NOT_RANKED_INT;
            try {
                value = Integer.valueOf(valueStr);
            } catch (NumberFormatException nfe) {
                // not ranked
            }
            this.value = value;
            return this;
        }

        public Rank bayesAverage(String bayesAverageStr) {
            double av = NOT_RANKED_DOUBLE;
            try {
                av = Double.valueOf(bayesAverageStr);
            } catch (NumberFormatException nfe) {
                // not ranked
            }
            this.bayesAverage = av;
            return this;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("type", type)
                    .add("name", name)
                    .add("friendly", friendly)
                    .add("value", value)
                    .add("bayesAv", bayesAverage)
                    .toString();
        }

        public String type() {
            return type;
        }
        public String name() {
            return name;
        }
        public String friendly() {
            return friendly;
        }
        public int value() {
            return value;
        }
        public double bayesAverage() {
            return bayesAverage;
        }
    }
}
