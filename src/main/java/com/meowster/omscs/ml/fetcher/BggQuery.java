package com.meowster.omscs.ml.fetcher;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Collections;
import java.util.Set;

/**
 * A query to the BGG API.
 */
public class BggQuery {

    private static final Set<Game> EMPTY_GAME_SET = Collections.emptySet();

    private static final HttpClient httpClient =
            HttpClientBuilder.create().build();

    private static final String BASE_URL =
            "www.boardgamegeek.com/xmlapi/boardgame/";

    private static final String COMMA = ",";
    private static final String QUERY = "?";

    private static final String OPTS = "stats=1";

    private final int start;
    private final int count;
    private Fetcher fetcher;
    private XmlParser parser;


    /**
     * Create a BGG query for start game ID and count of games.
     *
     * @param start start ID
     * @param count how many games to fetch
     */
    public BggQuery(int start, int count) {
        this.start = start;
        this.count = count;
    }

    /**
     * Do the query.
     */
    public void query() {
        fetcher = new Fetcher(buildUrl(start, count));
        fetcher.fetch(httpClient);
        parser = new XmlParser(fetcher.inputStream());

    }

    private String buildUrl(int start, int count) {
        StringBuilder sb = new StringBuilder(BASE_URL);
        for (int i = 0, n = start; i < count; i++, n++) {
            sb.append(n).append(COMMA);
        }
        int len = sb.length();
        sb.replace(len-1, len, QUERY);
        sb.append(OPTS);

        return sb.toString();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("first", first())
                .add("last", last())
                .add("count", count)
                .toString();
    }

    public int first() {
        return start;
    }

    public int last() {
        return start + count - 1;
    }

    public String filename() {
        return String.format("games-%06d-%06d.json", first(), last());
    }

    public Set<Game> games() {
        return parser == null ? EMPTY_GAME_SET : parser.generateGames();
    }

}
