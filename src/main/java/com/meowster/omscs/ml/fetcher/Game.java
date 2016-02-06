package com.meowster.omscs.ml.fetcher;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A board game instance.
 */
public class Game implements Comparable<Game> {

    private final int id;

    private String name;
    private String description;
    private int yearPublished;

    private int minPlayers;
    private int maxPlayers;

    private int time;
    private int minTime;
    private int maxTime;

    private final Set<String> mechanics = new TreeSet<>();
    private final Set<String> categories = new TreeSet<>();
    private final Set<String> subdomains = new TreeSet<>();
    private final Set<String> families = new TreeSet<>();
    private final Set<String> designers = new TreeSet<>();

    private GameRatings ratings;

    public Game(int id) {
        this.id = id;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;
        return id == game.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(Game o) {
        return this.id - o.id;
    }

    public Game name(String name) {
        this.name = name;
        return this;
    }

    public Game description(String description) {
        this.description = description;
        return this;
    }

    public Game yearPublished(int year) {
        yearPublished = year;
        return this;
    }

    public Game addMechanics(List<String> mechanics) {
        this.mechanics.addAll(mechanics);
        return this;
    }

    public Game addCategories(List<String> categories) {
        this.categories.addAll(categories);
        return this;
    }

    public Game addSubdomains(List<String> subdomains) {
        this.subdomains.addAll(subdomains);
        return this;
    }

    public Game addFamilies(List<String> families) {
        this.families.addAll(families);
        return this;
    }

    public Game addDesigners(List<String> designers) {
        this.designers.addAll(designers);
        return this;
    }

    public Game players(int minPlayers, int maxPlayers) {
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        return this;
    }

    public Game playtime(int time, int minTime, int maxTime) {
        this.time = time;
        this.minTime = minTime;
        this.maxTime = maxTime;
        return this;
    }

    private String mkPlayers() {
        StringBuilder sb = new StringBuilder("players=").append(minPlayers);
        if (maxPlayers != minPlayers) {
            sb.append("..").append(maxPlayers);
        }
        return sb.toString();
    }

    private String mkTime() {
        StringBuilder sb = new StringBuilder("time=").append(time);
        if (minTime != time || maxTime != time) {
            sb.append("[").append(minTime).append("..").append(maxTime).append("]");
        }
        return sb.toString();
    }

    public Game setRatings(GameRatings ratings) {
        this.ratings = ratings;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("year published", yearPublished)
                .addValue(mkPlayers())
                .addValue(mkTime())
                .add("designers", designers)

                .add("\ndesc", description)
                .add("\nmechanics", mechanics)
                .add("\ncategories", categories)
                .add("\nsubdomains", subdomains)
                .add("\nfamilies", families)
                .add("\nratings", ratings)
                .toString();
    }

    public int gameId() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public int yearPublished() {
        return yearPublished;
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

    public int minTime() {
        return minTime;
    }

    public int maxTime() {
        return maxTime;
    }

    public Set<String> mechanics() {
        return mechanics;
    }

    public Set<String> categories() {
        return categories;
    }

    public Set<String> subdomains() {
        return subdomains;
    }

    public Set<String> families() {
        return families;
    }

    public Set<String> designers() {
        return designers;
    }

    public GameRatings ratings() {
        return ratings;
    }


    public String shortDesc() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("year", yearPublished)
                .add("designers", designers)
                .toString();
    }
}
