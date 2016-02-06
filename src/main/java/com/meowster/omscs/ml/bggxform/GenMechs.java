package com.meowster.omscs.ml.bggxform;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.google.common.base.Charsets.UTF_8;

/**
 * Generate Unique Mechanics from stripped text file.
 */
public class GenMechs {
    private static final File DATA_DIR = new File("data/bgg-extra");

    private static final File RAW_MECHS = new File(DATA_DIR, "mechs.txt");
    private static final File MECHS_OUT = new File(DATA_DIR, "mechs.out");


    private final Set<String> mechs = new TreeSet<>();

    public void go() {
        try {
            List<String> lines = Files.readLines(RAW_MECHS, UTF_8);

            for (String line: lines) {
                Collections.addAll(mechs, line.trim().replace("\"", "").split(", "));
            }

            outputSet();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void outputSet() throws IOException {
        StringBuilder out = new StringBuilder();
        out.append(String.format(
                "%n%nUnique Mechanic strings... (%d of them)%n", mechs.size()));

        for (String s: mechs) {
            out.append(String.format("%s%n", s));
        }

        Files.write(out.toString().getBytes(), MECHS_OUT);
    }

}
