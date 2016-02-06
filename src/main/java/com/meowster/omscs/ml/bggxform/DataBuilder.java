package com.meowster.omscs.ml.bggxform;

import com.google.common.io.Files;
import com.meowster.omscs.ml.fetcher.Game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Builds data file in ARFF format for WEKA
 */
public class DataBuilder {

    private static final String FILE_TEMPLATE =
            "data/bgg-out/bgweights-%06d.arff";


    private static final String EOL = String.format("%n");

    private static final String NUMERIC = "NUMERIC";
    private static final String BOOL_SET = "{Y,N}";
    private static final String WEIGHT_SET =
            "{LIGHT,MEDIUM_LIGHT,MEDIUM,MEDIUM_HEAVY,HEAVY}";

    private StringBuilder out = new StringBuilder();
    private StringBuilder data = new StringBuilder();

    private int section = 0;

    private long nGames = 0;
    private Map<GameWeight, Tally> weightDistrib = new TreeMap<>();
    private File outputFile;

    private boolean outOfFiles = false;


    private void initTally() {
        for (GameWeight w: GameWeight.values()) {
            weightDistrib.put(w, new Tally());
        }
    }


    private void addSection(String header, String... contents) {
        addSectionHeader(++section, header);
        for (String c: contents) {
            addSectionContent(c);
        }
        addComment();
    }

    private void addSectionContent(String c) {
        addComment("     ", c);
    }

    private void addSectionHeader(int n, String header) {
        addComment(String.valueOf(n), ". ", header, ":");
    }

    private void addComment(String... strings) {
        out.append("% ");
        for (String s: strings) {
            out.append(s);
        }
        out.append(EOL);
    }

    private void writeHeader() {
        addSection("Title", "Board Game Weight Classification");

        addSection("Sources",
                "(a) Board Game Geek (https://www.boardgamegeek.com/xmlapi)",
                "(b) Data extracted 02/01/2016");

        addSection("Past Usage", "- none known");

        addSection("Relevant Information",
                "- BGG is a living database of all known published boardgames.",
                "- This data set is just a small fraction of the total.");

        addSection("Number of Instances", String.valueOf(nGames));

        addSection("Number of attributes (14 total)",
                "1 (numeric) identifier",
                "3 (numeric) game parameters",
                "9 (boolean) subdomain classifications",
                "1 (nominal) classification of weight");

        addSection("Attribute Information",
                "1.  game identifier",
                "2.  minimum players",
                "3.  maximum players",
                "4.  typical duration (in minutes)",
                "5.  subdomain: abstract game",
                "6.  subdomain: childrens game",
                "7.  subdomain: customizable game",
                "8.  subdomain: family game",
                "9.  subdomain: party game",
                "10. subdomain: strategy game",
                "11. subdomain: thematic game",
                "12. subdomain: wargame",
                "13. (no subdomain)",
                "14. class:",
                "    - LIGHT",
                "    - MEDIUM_LIGHT",
                "    - MEDIUM",
                "    - MEDIUM_HEAVY",
                "    - HEAVY");

        addSection("Missing Attribute Values", "(none)");

//        addSection("Summary Statistics",
//                "[ watch this space ]");

        addSection("Class Distribution", getDistribution());
        blank();
    }

    private String[] getDistribution() {
        List<String> lines = new ArrayList<>();
        for (GameWeight w: GameWeight.values()) {
            lines.add(distribStat(w, nGames));
        }
        return lines.toArray(new String[lines.size()]);
    }

    private String distribStat(GameWeight w, long nGames) {
        int count = weightDistrib.get(w).count();
        double percent = (double) count / (double) nGames * 100.0;
        return String.format("%-12s : %5d  (%6.3f%%)", w.name(), count, percent);
    }


    private void blank() {
        out.append(EOL);
    }


    private void data() {
        out.append("@DATA").append(EOL);
    }

    private void relation(String name) {
        out.append("@RELATION ").append(name).append(EOL);
    }

    private void attribute(String name, String type) {
        out.append("@ATTRIBUTE ").append(name)
                .append(" ").append(type)
                .append(EOL);
    }


    private void boolAttribute(String name) {
        attribute(name, BOOL_SET);
    }

    private List<GameRecord> processFile(File file) {
        List<GameRecord> records = new ArrayList<>();
        List<Game> games = new GameExtractor(file).games();

        for (Game g: games) {
            records.add(new GameRecord(g));
        }
        return records;
    }

    private void writeAttributes() {
        relation("boardgames");
        blank();

        attribute("id", NUMERIC);
        attribute("minp", NUMERIC);
        attribute("maxp", NUMERIC);
        attribute("time", NUMERIC);
        for (Subdomain s: Subdomain.values()) {
            boolAttribute(s.name().toLowerCase());
        }
        boolAttribute("no-subdomain");
        attribute("weight", WEIGHT_SET);

        blank();
        data();
    }

    private void finishOutput() {
        out.append(data);
        addComment();
        addComment();
        addComment("-- end of data --");
    }

    private void writeOutputFile(long nGames) {
        outputFile = new File(String.format(FILE_TEMPLATE, nGames));
        try {
            Files.write(out.toString().getBytes(), outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printStats() {
        print("%nWriting output file: %s", outputFile.getName());
        print("%nTotal Game Records: %d", nGames);
        print("%nClass Distribution:");
        for (GameWeight w: GameWeight.values()) {
            print("  %-12s : %5d", w, weightDistrib.get(w).count());
        }
        print("------------------------");
    }

    public DataBuilder build(int numGames) {
        print("Collecting data on %d games...", numGames);
        initTally();

        boolean done = false;
        Iterator<File> iter = GameFileCollection.iterator();

        int nFiles = 0;
        while (!done && iter.hasNext()) {
            nFiles++;
            List<GameRecord> records = processFile(iter.next());

            while (nGames < numGames && !records.isEmpty()) {
                GameRecord record = records.remove(0);
                data.append(record).append(EOL);
                weightDistrib.get(record.weightClass()).inc();
                nGames++;
            }

            if (nGames == numGames) {
                done = true;
            }
        }
        print("%n=== %d Files Processed ===", nFiles);

        if (nGames < numGames) {
            outOfFiles = true;
            print("%n [[ out of files ]]");
        }

        writeHeader();
        writeAttributes();
        finishOutput();
        writeOutputFile(nGames);
        printStats();
        return this;
    }


    public boolean ranOutOfFiles() {
        return outOfFiles;
    }


    // == more efficient tallying with a custom tally value ==
    //   rather than putting and getting immutable Integer values in the map
    private static class Tally {
        private int count = 0;

        public void inc() {
            count++;
        }

        public int count() {
            return count;
        }
    }

}
