package com.meowster.omscs.ml.wekarun.config;

import com.meowster.omscs.ml.fetcher.Utils;
import com.meowster.omscs.ml.wekarun.config.DataFileGroup.DataFileInfo;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link DataFileGroup}.
 */
public class DataFileGroupTest {

    private static final String DATA_DIR = "data/bgg-out";
    private static final String SLASH = "/";
    private static final String PREFIX = "bgweights-";
    private static final String SUFFIX = ".arff";

    private static final String FILENAME_FORMAT = "bgweights-%s";

    private static final String[] IDS = {
            "0001",
            "0002",
            "0003"
    };

    private static final String[] EXP_NAMES = {
            DATA_DIR + SLASH + PREFIX + "0001" + SUFFIX,
            DATA_DIR + SLASH + PREFIX + "0002" + SUFFIX,
            DATA_DIR + SLASH + PREFIX + "0003" + SUFFIX,
    };

    private static class TestGroup extends DataFileGroup {

        protected TestGroup() {
            super(DATA_DIR);
        }

        @Override
        protected void configure() {
            setFilenameFormat(FILENAME_FORMAT);
            addFiles(IDS);
        }
    }

    @Test
    public void basic() {
        DataFileGroup group = new TestGroup();
        Iterator<DataFileInfo> iter = group.iterator();
        for (int i = 0, n = IDS.length; i < n; i++) {
            check(EXP_NAMES[i], iter.next());
        }
    }

    private void check(String expName, DataFileInfo info) {
        Utils.print("ID '%s' -> '%s'", info.id(), info.path());
        assertEquals("wrong name " + info.id(), expName, info.path());
    }
}
