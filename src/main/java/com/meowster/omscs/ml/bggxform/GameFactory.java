package com.meowster.omscs.ml.bggxform;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meowster.omscs.ml.fetcher.Game;
import com.meowster.omscs.ml.fetcher.GameRatings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates {@link Game} instances from JSON.
 */
public class GameFactory {

    /**
     * Creates a list of games from the given JSON data file.
     *
     * @param json the JSON
     * @return list of extracted games
     */
    public static List<Game> createGames(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        List<Game> result = new ArrayList<>();
        ArrayNode games = (ArrayNode) root.get("games");

        for (JsonNode game : games) {
            result.add(fromJson(game));
        }
        return result;

    }

    private static Game fromJson(JsonNode node) {
        int id = node.get("id").asInt();

        return new Game(id)
                .name(node.get("name").asText())
                .description(node.get("description").asText())
                .yearPublished(node.get("yearPublished").asInt())
                .players(
                        node.get("minPlayers").asInt(),
                        node.get("maxPlayers").asInt()
                )
                .playtime(
                        node.get("time").asInt(),
                        node.get("minTime").asInt(),
                        node.get("maxTime").asInt()
                )
                .addMechanics(extractStringList(node, "mechanics"))
                .addCategories(extractStringList(node, "categories"))
                .addSubdomains(extractStringList(node, "subdomains"))
                .addFamilies(extractStringList(node, "families"))
                .addDesigners(extractStringList(node, "designers"))
                .setRatings(extractRatings(node, id));
    }

    private static GameRatings extractRatings(JsonNode node, int id) {
        JsonNode rNode = node.get("ratings");

        GameRatings ratings = new GameRatings(id)
                .usersRated(rNode.get("usersRated").asInt())
                .average(rNode.get("average").asDouble())
                .bayesAverage(rNode.get("bayesAverage").asDouble())
                .stdDev(rNode.get("stdDev").asDouble())
                .owned(rNode.get("owned").asInt())
                .trading(rNode.get("trading").asInt())
                .wanting(rNode.get("wanting").asInt())
                .wishing(rNode.get("wishing").asInt())
                .numComments(rNode.get("numComments").asInt())
                .numWeights(rNode.get("numWeights").asInt())
                .averageWeight(rNode.get("averageWeight").asDouble());

        for (JsonNode jn: rNode.get("ranks")) {
            ObjectNode o = (ObjectNode) jn;
            GameRatings.Rank rank = new GameRatings.Rank()
                    .type(o.get("type").asText())
                    .name(o.get("name").asText())
                    .friendly(o.get("friendly").asText())
                    .value(o.get("value").asText())
                    .bayesAverage(o.get("bayesAverage").asText());
            ratings.addRank(rank);
        }

        return ratings;
    }

    private static List<String> extractStringList(JsonNode node, String tag) {
        ArrayNode array = (ArrayNode) node.get(tag);
        List<String> result = new ArrayList<>(array.size());
        for (JsonNode o: array) {
            result.add(o.asText());
        }
        return result;
    }
}
