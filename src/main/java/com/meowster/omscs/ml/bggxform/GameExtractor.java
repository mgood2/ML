package com.meowster.omscs.ml.bggxform;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.meowster.omscs.ml.fetcher.Game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads in JSON file of game data and extracts Game instances.
 */
public class GameExtractor {

    private List<Game> games;

    public GameExtractor(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            String raw = new String(bytes);
            games = GameFactory.createGames(raw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Game> games() {
        return games;
    }
}
