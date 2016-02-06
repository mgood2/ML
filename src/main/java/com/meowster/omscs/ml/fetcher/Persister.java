package com.meowster.omscs.ml.fetcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Takes data from a BGG Query and persists it to local data file (as JSON).
 */
public class Persister {
    private static final File DATA_DIR = new File("data/bgg-test");

    private static final String TIMESTAMP = "timestamp";
    private static final String DATE = "date";
    private static final String EPOCH = "epoch";

    private static final String GAMES = "games";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String YEAR_PUBLISHED = "yearPublished";
    private static final String MIN_PLAYERS = "minPlayers";
    private static final String MAX_PLAYERS = "maxPlayers";
    private static final String TIME = "time";
    private static final String MIN_TIME = "minTime";
    private static final String MAX_TIME = "maxTime";

    private static final String MECHANICS = "mechanics";
    private static final String CATEGORIES = "categories";
    private static final String SUBDOMAINS = "subdomains";
    private static final String FAMILIES = "families";
    private static final String DESIGNERS = "designers";

    private static final String RATINGS = "ratings";

    private static final String USERS_RATED = "usersRated";
    private static final String AVERAGE = "average";
    private static final String BAYES_AVERAGE = "bayesAverage";
    private static final String STD_DEV = "stdDev";
    private static final String OWNED = "owned";
    private static final String TRADING = "trading";
    private static final String WANTING = "wanting";
    private static final String WISHING = "wishing";
    private static final String NUM_COMMENTS = "numComments";
    private static final String NUM_WEIGHTS = "numWeights";
    private static final String AVERAGE_WEIGHT = "averageWeight";
    private static final String RANKS = "ranks";

    private static final String TYPE = "type";
    private static final String FRIENDLY = "friendly";
    private static final String VALUE = "value";


    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleDateFormat DF_DATE =
            new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat DF_TIME =
            new SimpleDateFormat("HH:mm:ss");


    public Persister(BggQuery bq) {
        print("%nPersisting: %s to %s", bq, bq.filename());
        ObjectNode root = objectNode();
        root.set(TIMESTAMP, jsonTimestamp());
        root.set(GAMES, jsonStringSet(bq));
        writeToFile(root, bq.filename());
    }

    private JsonNode jsonTimestamp() {
        Date now = new Date();
        String date = DF_DATE.format(now);
        String time = DF_TIME.format(now);
        long epoch = now.getTime();

        ObjectNode root = objectNode();
        root.put(DATE, date)
                .put(TIME, time)
                .put(EPOCH, epoch);
        return root;
    }

    private String prettyJson(JsonNode node) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Bleagh!! " + e.getMessage();
        }
    }

    private void writeToFile(ObjectNode root, String filename) {
        File output = new File(DATA_DIR, filename);
        try {
            Files.write(prettyJson(root), output, Charsets.UTF_8);
        } catch (IOException e) {
            print("*** FAILED TO WRITE FILE: %s", e.getMessage());
        }
    }

    private JsonNode jsonStringSet(BggQuery bq) {
        ArrayNode gameList = arrayNode();
        for (Game game : bq.games()) {
            print("  %d: %s", game.gameId(), game.name());
            gameList.add(jsonStringSet(game));
        }
        return gameList;
    }

    private JsonNode jsonStringSet(Game game) {
        ObjectNode root = objectNode();
        root.put(ID, game.gameId())
                .put(NAME, game.name())
                .put(DESCRIPTION, game.description())
                .put(YEAR_PUBLISHED, game.yearPublished())
                .put(MIN_PLAYERS, game.minPlayers())
                .put(MAX_PLAYERS, game.maxPlayers())
                .put(TIME, game.time())
                .put(MIN_TIME, game.minTime())
                .put(MAX_TIME, game.maxTime());

        root.set(MECHANICS, jsonStringSet(game.mechanics()));
        root.set(CATEGORIES, jsonStringSet(game.categories()));
        root.set(SUBDOMAINS, jsonStringSet(game.subdomains()));
        root.set(FAMILIES, jsonStringSet(game.families()));
        root.set(DESIGNERS, jsonStringSet(game.designers()));

        root.set(RATINGS, jsonStringSet(game.ratings()));


        return root;
    }

    private JsonNode jsonStringSet(GameRatings ratings) {
        ObjectNode root = objectNode();
        root.put(USERS_RATED, ratings.usersRated())
                .put(AVERAGE, ratings.average())
                .put(BAYES_AVERAGE, ratings.bayesAverage())
                .put(STD_DEV, ratings.stdDev())
                .put(OWNED, ratings.owned())
                .put(TRADING, ratings.trading())
                .put(WANTING, ratings.wanting())
                .put(WISHING, ratings.wishing())
                .put(NUM_COMMENTS, ratings.numComments())
                .put(NUM_WEIGHTS, ratings.numWeights())
                .put(AVERAGE_WEIGHT, ratings.averageWeight());

        root.set(RANKS, jsonRankSet(ratings.ranks()));

        return root;
    }

    private JsonNode jsonRankSet(Set<GameRatings.Rank> ranks) {
        ArrayNode a = arrayNode();
        for (GameRatings.Rank rank: ranks) {
            a.add(json(rank));
        }
        return a;
    }

    private JsonNode json(GameRatings.Rank rank) {
        ObjectNode root = objectNode();
        root.put(TYPE, rank.type())
                .put(NAME, rank.name())
                .put(FRIENDLY, rank.friendly())
                .put(VALUE, rank.value())
                .put(BAYES_AVERAGE, rank.bayesAverage());
        return root;
    }

    private JsonNode jsonStringSet(Set<String> strings) {
        ArrayNode a = arrayNode();
        for (String s: strings) {
            a.add(s);
        }
        return a;
    }

    private ObjectNode objectNode() {
        return MAPPER.createObjectNode();
    }

    private ArrayNode arrayNode() {
        return MAPPER.createArrayNode();
    }
}
