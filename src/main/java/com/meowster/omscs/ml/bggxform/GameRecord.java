package com.meowster.omscs.ml.bggxform;

import com.meowster.omscs.ml.fetcher.Game;
import com.meowster.omscs.ml.fetcher.GameRatings;

import java.util.HashSet;
import java.util.Set;

/**
 * Cut down version of a {@link Game}.
 */
public class GameRecord {

    private static final String COMMA = ",";
    private static final String Y = "Y";
    private static final String N = "N";

    private final int id;
    private final String name;
    private final int minPlayers;
    private final int maxPlayers;
    private final int time;
    //    private final Set<Mechanic> mechanics;
    private final Set<Subdomain> subdomains;
    private final double weight;
    private final int rankPosition;
    private final double rankAverage;

    private final GameWeight weightClass;

    private final String asString;

    public GameRecord(Game game) {
        id = game.gameId();
        name = game.name();
        minPlayers = game.minPlayers();
        maxPlayers = game.maxPlayers();
        time = game.time();
//        mechanics = createMechanics(game.mechanics());
        subdomains = createSubdomains(game.subdomains());

        GameRatings ratings = game.ratings();

        weight = ratings.averageWeight();
        weightClass = GameWeight.nearest(weight);

        GameRatings.Rank rank = findRank(ratings);
        rankPosition = positionOf(rank);
        rankAverage = averageOf(rank);

        asString = generateString();
    }


    private String generateString() {
        StringBuilder sb = new StringBuilder().append(id);
        appendInts(sb, minPlayers, maxPlayers, time);
        // deal with mechanics some other time
        appendSubdomains(sb, subdomains);
        appendWeights(sb, weight, weightClass);
        return sb.toString();
    }

    private void appendSubdomains(StringBuilder sb, Set<Subdomain> subdomains) {
        // converted to binary flags (is/is-not)
        for (Subdomain sd: Subdomain.values()) {
            sb.append(COMMA).append(subdomains.contains(sd) ? Y : N);
        }
        // doesn't have any subdomains assigned to it...
        sb.append(COMMA).append(subdomains.isEmpty() ? Y : N);
    }

    private void appendInts(StringBuilder sb, int... values) {
        for (int v: values) {
            sb.append(COMMA).append(v);
        }
    }
    private void appendWeights(StringBuilder sb, double weight, GameWeight cls) {
        // for now, let's omit the actual weight value from the record:
//        sb.append(COMMA).append(weight);
        sb.append(COMMA).append(cls);
    }


    private double averageOf(GameRatings.Rank rank) {
        return rank == null ? -1.0 : rank.bayesAverage();
    }

    private int positionOf(GameRatings.Rank rank) {
        return rank == null ? -1 : rank.value();
    }

    private GameRatings.Rank findRank(GameRatings ratings) {
        for (GameRatings.Rank r: ratings.ranks()) {
            if (r.name().equals("boardgame")) {
                return r;
            }
        }
        return null;
    }

    private Set<Mechanic> createMechanics(Set<String> mechanics) {
        Set<Mechanic> result = new HashSet<>();
        for (String s: mechanics) {
            Mechanic m = Mechanic.fromString(s);
            if (m != null) {
                result.add(m);
            }
        }
        return result;
    }

    private Set<Subdomain> createSubdomains(Set<String> subdomains) {
        Set<Subdomain> result = new HashSet<>();
        for (String s: subdomains) {
            Subdomain sd = Subdomain.fromString(s);
            if (sd != null) {
                result.add(sd);
            }
        }
        return result;
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public int minPlayers() {
        return minPlayers;
    }

    public int maxPlayers() {
        return maxPlayers;
    }

    public int time() {
        return time;
    }

//    public Set<Mechanic> mechanics() {
//        return mechanics;
//    }

    public Set<Subdomain> subdomains() {
        return subdomains;
    }

    public double weight() {
        return weight;
    }

    public int rankPosition() {
        return rankPosition;
    }

    public double rankAverage() {
        return rankAverage;
    }

    @Override
    public String toString() {
        return asString;
    }

    public GameWeight weightClass() {
        return weightClass;
    }
}
