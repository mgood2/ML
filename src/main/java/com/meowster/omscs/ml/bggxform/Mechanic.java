package com.meowster.omscs.ml.bggxform;

import java.util.HashMap;
import java.util.Map;

/**
 * Designates game mechanics.
 */
public enum Mechanic {

    ACTING("Acting"),
    ACTION_MOVE_PROG("Action / Movement Programming"),
    AP_ALLOWANCE("Action Point Allowance System"),
    AREA_CONTROL("Area Control / Area Influence"),
    AREA_ENCLOSURE("Area Enclosure"),
    AREA_MOVEMENT("Area Movement"),
    AREA_IMPULSE("Area-Impulse"),
    AUCTION_BIDDING("Auction/Bidding"),
    BETTING_WAGERING("Betting/Wagering"),
    BATTLE_CARD_DRIVEN("Campaign / Battle Card Driven"),
    CARD_DRAFTING("Card Drafting"),
    CHIT_PULL("Chit-Pull System"),
    CO_OP_PLAY("Co-operative Play"),
    COMMODITY_SPECULATION("Commodity Speculation"),
    CRAYON_RAIL("Crayon Rail System"),
    DECK_POOL_BUILDING("Deck / Pool Building"),
    DICE_ROLLING("Dice Rolling"),
    GRID_MOVEMENT("Grid Movement"),
    HAND_MANAGEMENT("Hand Management"),
    HEX_COUNTER("Hex-and-Counter"),
    LINE_DRAWING("Line Drawing"),
    MEMORY("Memory"),
    MODULAR_BOARD("Modular Board"),
    PAPER_PENCIL("Paper-and-Pencil"),
    PARTNERSHIPS("Partnerships"),
    PATTERN_BUILDING("Pattern Building"),
    PATTERN_RECOGNITION("Pattern Recognition"),
    PICKUP_DELIVER("Pick-up and Deliver"),
    PLAYER_ELIMINATION("Player Elimination"),
    POINT_TO_POINT_MOVE("Point to Point Movement"),
    PRESS_YOUR_LUCK("Press Your Luck"),
    ROCK_PAPER_SCISSORS("Rock-Paper-Scissors"),
    ROLE_PLAYING("Role Playing"),
    ROLL_SPIN_MOVE("Roll / Spin and Move"),
    ROUTE_BUILDING("Route/Network Building"),
    SECRET_UNITS("Secret Unit Deployment"),
    SET_COLLECTION("Set Collection"),
    SIMULATION("Simulation"),
    SIMULTANEOUS_ACTIONS("Simultaneous Action Selection"),
    SINGING("Singing"),
    STOCK_HOLDING("Stock Holding"),
    STORYTELLING("Storytelling"),
    TAKE_THAT("Take That"),
    TILE_PLACEMENT("Tile Placement"),
    TIME_TRACK("Time Track"),
    TRADING("Trading"),
    TRICK_TAKING("Trick-taking"),
    VARIABLE_PHASE_ORDER("Variable Phase Order"),
    VARIABLE_PLAYER_POWERS("Variable Player Powers"),
    VOTING("Voting"),
    WORKER_PLACEMENT("Worker Placement"),
    ;

    private final String text;

    Mechanic(String t) {
        text = t;
    }

    public String text() {
        return text;
    }

    public static Mechanic fromString(String s) {
        return LOOKUP.get(s);
    }

    private static final Map<String, Mechanic> LOOKUP = new HashMap<>();
    static {
        for (Mechanic m: Mechanic.values()) {
            LOOKUP.put(m.text, m);
        }
    }
}
