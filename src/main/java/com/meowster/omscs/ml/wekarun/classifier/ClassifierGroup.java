package com.meowster.omscs.ml.wekarun.classifier;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.meowster.omscs.ml.wekarun.classifier.WekaClassifier.createClassifier;

/**
 * Encapsulates the classifiers that we want to run against the data.
 */
public class ClassifierGroup {

    private final List<WekaClassifier> classifiers = new ArrayList<>();


    /**
     * Creates a new classifier group.
     */
    public ClassifierGroup() {
        // specify the classifiers (and their options) we want to run...
        classifiers.add(createClassifier(Type.ZERO_R));
        // TODO : add more classifiers
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
