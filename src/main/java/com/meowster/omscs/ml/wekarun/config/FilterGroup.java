package com.meowster.omscs.ml.wekarun.config;

import weka.filters.Filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Abstract base class that encapsulates a list of filters to be used
 * on the dataset in the experiment.
 */
public abstract class FilterGroup {

    private final List<Filter> filters = new ArrayList<>();
    private boolean configured = false;

    /**
     * Subclasses should configure their list of filters
     */
    protected abstract void configure();

    /**
     * Adds the specified filters to the group.
     *
     * @param filters filters to add
     */
    protected void addFilters(Filter... filters) {
        if (configured) {
            throw new IllegalStateException("No longer mutable!");
        }
        for (Filter f : filters) {
            this.filters.add(f);
            print("  ..adding filter: %s", f);
        }
    }


    /**
     * Returns an iterator over the filters in this group.
     * Note that calling this method will mark the group as no longer
     * mutable.
     *
     * @return iterator over the filters in this group
     */
    public Iterator<Filter> iterator() {
        // lazy invocation of configure
        if (!configured) {
            print("%nConfiguring FilterGroup: %s...", getClass().getName());
            configure();
            configured = true;
        }

        return new FilterIterator();
    }


    /**
     * Iterator implementation.
     */
    private class FilterIterator implements Iterator<Filter> {
        int index = 0;

        @Override
        public boolean hasNext() {
            return index < filters.size();
        }

        @Override
        public Filter next() {
            return filters.get(index++);
        }
    };
}
