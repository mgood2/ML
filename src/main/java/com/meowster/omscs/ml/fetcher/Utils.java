package com.meowster.omscs.ml.fetcher;

/**
 * Utility methods.
 */
public class Utils {

    /**
     * Print stuff ending with a new line.
     *
     * @param fmt format string
     * @param args arguments
     */
    public static void print(String fmt, Object... args) {
        System.out.println(String.format(fmt, args));
    }

    /**
     * Print stuff but not with a new line.
     *
     * @param fmt format string
     * @param args arguments
     */
    public static void printNoEol(String fmt, Object... args) {
        System.out.print(String.format(fmt, args));
    }
}
