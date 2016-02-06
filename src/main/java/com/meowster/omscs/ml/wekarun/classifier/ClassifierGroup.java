package com.meowster.omscs.ml.wekarun.classifier;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier.Option;
import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.meowster.omscs.ml.fetcher.Utils.print;
import static com.meowster.omscs.ml.wekarun.classifier.WekaClassifier.createClassifier;

/**
 * Encapsulates the classifiers that we want to run against the data.
 */
public class ClassifierGroup {

    private static final String _B = "-B";


    private final List<WekaClassifier> classifiers = new ArrayList<>();


    /**
     * Creates a classifier group with preconfigured classifiers.
     */
    public ClassifierGroup() {
        add(Type.ZERO_R);
        add(Type.ONE_R);
        add(Type.ONE_R, option(_B, 12));

        // TODO : add more classifiers

        print("Ready to run!%n-------------%n%n");
    }

    private void add(Type type, Option... options) {
        WekaClassifier wc = createClassifier(type, options);
        print("adding %s", wc);
        classifiers.add(wc);
    }

    private Option option(String key, int value) {
        return new Option(key, String.valueOf(value));
    }

    /**
     * Returns an iterator for the classifiers we want to run in this group.
     *
     * @return iterator over our set of classifiers
     */
    public Iterator<WekaClassifier> iterator() {
        return new ClassifierIterator();
    }


    /**
     * Iterator implementation.
     */
    private class ClassifierIterator implements Iterator<WekaClassifier> {

        int index = 0;

        @Override
        public boolean hasNext() {
            return index < classifiers.size();
        }

        @Override
        public WekaClassifier next() {
            return classifiers.get(index++);
        }
    }
}
