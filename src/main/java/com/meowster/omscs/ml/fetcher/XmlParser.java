package com.meowster.omscs.ml.fetcher;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Parses BGG XML format data to something more palatable.
 */
public class XmlParser {

    private static final String TRUE = "true";
    private static final String NO_NAME = "(no name)";
    private static final String NO_DESCRIPTION = "(no description)";
    private static final int NEG_ONE = -1;
    private static final int ZERO = 0;
    private static final double NEG_ONE_DOUBLE = -1.0;
    private static final double ZERO_DOUBLE = 0.0;

    private static final String DOT_SPACE = ". ";
    private static final String DOT = ".";

    private static final String BOARDGAMES = "boardgames";
    private static final String BOARDGAME = "boardgame";

    private static final String A_OBJECTID = "[@objectid]";
    private static final String A_PRIMARY = "[@primary]";
    private static final String A_TYPE = "[@type]";
    private static final String A_NAME = "[@name]";
    private static final String A_FRIENDLYNAME = "[@friendlyname]";
    private static final String A_VALUE = "[@value]";
    private static final String A_BAYESAVERAGE = "[@bayesaverage]";


    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String YEARPUBLISHED = "yearpublished";

    private static final String MINPLAYERS = "minplayers";
    private static final String MAXPLAYERS = "maxplayers";
    private static final String PLAYINGTIME = "playingtime";
    private static final String MINPLAYTIME = "minplaytime";
    private static final String MAXPLAYTIME = "maxplaytime";


    private static final String BOARDGAMEMECHANIC = "boardgamemechanic";
    private static final String BOARDGAMECATEGORY = "boardgamecategory";
    private static final String BOARDGAMESUBDOMAIN = "boardgamesubdomain";
    private static final String BOARDGAMEFAMILY = "boardgamefamily";
    private static final String BOARDGAMEDESIGNER = "boardgamedesigner";

    private static final String STATISTICS = "statistics";
    private static final String RATINGS = "ratings";
    private static final String USERSRATED = "usersrated";
    private static final String AVERAGE = "average";
    private static final String BAYESAVERAGE = "bayesaverage";
    private static final String RANKS = "ranks";
    private static final String RANK = "rank";
    private static final String STDDEV = "stddev";
    private static final String OWNED = "owned";
    private static final String TRADING = "trading";
    private static final String WANTING = "wanting";
    private static final String WISHING = "wishing";
    private static final String NUMCOMMENTS = "numcomments";
    private static final String NUMWEIGHTS = "numweights";
    private static final String AVERAGEWEIGHT = "averageweight";


    private XMLConfiguration cfg = new XMLConfiguration();

    public XmlParser(InputStream content) {
        cfg.setRootElementName(BOARDGAMES);
        cfg.setAttributeSplittingDisabled(true);
        cfg.setDelimiterParsingDisabled(true);

        try {
            cfg.load(content);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Set<Game> generateGames() {
        Set<Game> games = new TreeSet<>();
        for (HierarchicalConfiguration hc : cfg.configurationsAt(BOARDGAME)) {
            int id = Integer.valueOf(hc.getString(A_OBJECTID));
            Game game = new Game(id);
            games.add(game);
            populateGame(game, hc);
        }
        return games;
    }

    private void populateGame(Game game, HierarchicalConfiguration hc) {
        game.name(retrieveName(hc))
                .description(retrieveDescription(hc))
                .yearPublished(retrieveInt(hc, YEARPUBLISHED))
                .players(
                        retrieveInt(hc, MINPLAYERS),
                        retrieveInt(hc, MAXPLAYERS)
                )
                .playtime(
                        retrieveInt(hc, PLAYINGTIME),
                        retrieveInt(hc, MINPLAYTIME),
                        retrieveInt(hc, MAXPLAYTIME)
                )
                .addMechanics(retrieveTags(hc, BOARDGAMEMECHANIC))
                .addCategories(retrieveTags(hc, BOARDGAMECATEGORY))
                .addSubdomains(retrieveTags(hc, BOARDGAMESUBDOMAIN))
                .addFamilies(retrieveTags(hc, BOARDGAMEFAMILY))
                .addDesigners(retrieveTags(hc, BOARDGAMEDESIGNER))
                .setRatings(retrieveRatings(hc, game.gameId()));
    }

    private int retrieveInt(HierarchicalConfiguration hc, String tag) {
        HierarchicalConfiguration cfg = first(hc, tag);
        if (cfg == null) {
            return NEG_ONE;
        }

        try {
            return Integer.valueOf(cfg.getRoot().getValue().toString());
        } catch (NumberFormatException nfe) {
            return ZERO;
        }
    }

    private double retrieveDouble(HierarchicalConfiguration hc, String tag) {
        HierarchicalConfiguration cfg = first(hc, tag);
        if (cfg == null) {
            return NEG_ONE_DOUBLE;
        }

        try {
            return Double.valueOf(cfg.getRoot().getValue().toString());
        } catch (NumberFormatException nfe) {
            return ZERO_DOUBLE;
        }
    }

    private String retrieveAttr(HierarchicalConfiguration hc, String attrTag) {
        return hc.getString(attrTag);
    }

    private String retrieveName(HierarchicalConfiguration hc) {
        for (HierarchicalConfiguration cfg : hc.configurationsAt(NAME)) {
            String bool = cfg.getString(A_PRIMARY);
            if (TRUE.equals(bool)) {
                return cfg.getRoot().getValue().toString();
            }
        }
        return NO_NAME;
    }

    private String retrieveDescription(HierarchicalConfiguration hc) {
        HierarchicalConfiguration cfg = first(hc, DESCRIPTION);
        if (cfg == null) {
            return NO_DESCRIPTION;
        }

        String desc = cfg.getRoot().getValue().toString();
        int dotAt = desc.indexOf(DOT_SPACE);
        if (dotAt > 0) {
            return desc.substring(0, dotAt + 1);
        }
        dotAt = desc.indexOf(DOT);
        if (dotAt > 0) {
            return desc.substring(0, dotAt + 1);
        }
        return desc;
    }

    private HierarchicalConfiguration first(HierarchicalConfiguration hc, String tag) {
        HierarchicalConfiguration result = null;
        for (HierarchicalConfiguration cfg : hc.configurationsAt(tag)) {
            if (result == null) {
                result = cfg;
            }
        }
        return result;
    }

    private List<String> retrieveTags(HierarchicalConfiguration hc, String tag) {
        List<String> strings = new ArrayList<>();
        for (HierarchicalConfiguration tagCfg : hc.configurationsAt(tag)) {
            Object v = tagCfg.getRoot().getValue();
            strings.add(v.toString());
        }
        return strings;
    }

    private GameRatings retrieveRatings(HierarchicalConfiguration hc, int id) {
        GameRatings result = new GameRatings(id);
        HierarchicalConfiguration cfg = first(hc, STATISTICS);
        if (cfg != null) {
            HierarchicalConfiguration ratings = first(cfg, RATINGS);
            if (ratings != null) {
                result.usersRated(retrieveInt(ratings, USERSRATED))
                        .average(retrieveDouble(ratings, AVERAGE))
                        .bayesAverage(retrieveDouble(ratings, BAYESAVERAGE))
                        .stdDev(retrieveDouble(ratings, STDDEV))
                        .owned(retrieveInt(ratings, OWNED))
                        .trading(retrieveInt(ratings, TRADING))
                        .wanting(retrieveInt(ratings, WANTING))
                        .wishing(retrieveInt(ratings, WISHING))
                        .numComments(retrieveInt(ratings, NUMCOMMENTS))
                        .numWeights(retrieveInt(ratings, NUMWEIGHTS))
                        .averageWeight(retrieveDouble(ratings, AVERAGEWEIGHT));

                addRankings(result, ratings);
            }
        }
        return result;
    }

    private void addRankings(GameRatings gameRatings, HierarchicalConfiguration ratings) {
        HierarchicalConfiguration ranks = first(ratings, RANKS);

        for (HierarchicalConfiguration cfg: ranks.configurationsAt(RANK)) {
            GameRatings.Rank rank = new GameRatings.Rank();
            rank.type(retrieveAttr(cfg, A_TYPE))
                    .name(retrieveAttr(cfg, A_NAME))
                    .friendly(retrieveAttr(cfg, A_FRIENDLYNAME))
                    .value(retrieveAttr(cfg, A_VALUE))
                    .bayesAverage(retrieveAttr(cfg, A_BAYESAVERAGE));
            gameRatings.addRank(rank);
        }
    }

}
