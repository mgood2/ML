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
 * Generate Unique Subdomains from stripped text file.
 */
public class GenDomains {
    private static final File DATA_DIR = new File("data/bgg-extra");

    private static final File RAW_DATA = new File(DATA_DIR, "subdomains.txt");
    private static final File UNIQUE_OUT = new File(DATA_DIR, "subdomains.out");


    private final Set<String> unique = new TreeSet<>();

    public void go() {
        try {
            List<String> lines = Files.readLines(RAW_DATA, UTF_8);

            for (String line: lines) {
                Collections.addAll(unique, line.trim().replace("\"", "").split(", "));
            }

            outputSet();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void outputSet() throws IOException {
        StringBuilder out = new StringBuilder();
        out.append(String.format(
                "%n%nUnique Subdomain strings... (%d of them)%n", unique.size()));

        for (String s: unique) {
            out.append(String.format("%s%n", s));
        }

        Files.write(out.toString().getBytes(), UNIQUE_OUT);
    }

}
