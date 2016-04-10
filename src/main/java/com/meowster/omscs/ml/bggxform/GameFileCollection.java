package com.meowster.omscs.ml.bggxform;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Encapsulates the raw game data files :
 *   ~/data/bgg/kNN/games-NNNNNN-NNNNNN.json
 *
 *   We can return an iterator over those files.
 */
public class GameFileCollection {

    private static final File BGG_DATA = new File("data/bgg");

    /**
     * Create an iterator across the game files.
     *
     * @return game file iterator
     */
    public static Iterator<File> iterator() {
        return new FileIterator();
    }

    // -- class implementing the iterator
    private static class FileIterator implements Iterator<File> {

        private final FileFilter gameFF = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().startsWith("games-");
            }
        };

        private final List<File> loadedFiles = new ArrayList<>();

        private int kdir = 0;

        private File nextDir() {
            String kname = String.format("k%02d", kdir++);
            return new File(BGG_DATA, kname);
        }

        private boolean attemptToLoadGameFiles() {
            File subdir = nextDir();

            if (subdir.exists()) {
                List<File> gfiles = Arrays.asList(subdir.listFiles(gameFF));
                if (gfiles.isEmpty()) {
                    return false;
                }
                loadedFiles.addAll(gfiles);
                return true;
            }
            return false;
        }

        private boolean filesLoaded() {
            return loadedFiles.size() > 0;
        }


        @Override
        public boolean hasNext() {
            return filesLoaded() || attemptToLoadGameFiles();
        }

        @Override
        public File next() {
            return loadedFiles.remove(0);
        }

        @Override
        public void remove() {

        }
    }
}
