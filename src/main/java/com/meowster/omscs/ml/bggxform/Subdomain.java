package com.meowster.omscs.ml.bggxform;

import java.util.HashMap;
import java.util.Map;

/**
 * Designates the possible subdomains a game might belong to.
 */
public enum Subdomain {

    ABSTRACT("Abstract Games"),
    CHILDRENS("Children's Games"),
    CUSTOMIZABLE("Customizable Games"),
    FAMILY("Family Games"),
    PARTY("Party Games"),
    STRATEGY("Strategy Games"),
    THEMATIC("Thematic Games"),
    WARGAMES("Wargames"),
    ;

    private final String text;

    Subdomain(String t) {
        text = t;
    }

    public String text() {
        return text;
    }

    public static Subdomain fromString(String s) {
        return LOOKUP.get(s);
    }

    private static final Map<String, Subdomain> LOOKUP = new HashMap<>();
    static {
        for (Subdomain s: Subdomain.values()) {
            LOOKUP.put(s.text, s);
        }
    }

}
