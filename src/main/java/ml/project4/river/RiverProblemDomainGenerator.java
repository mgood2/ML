package ml.project4.river;

import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;

import java.util.Arrays;
import java.util.List;

/**
 * Generates Burlap Domains for the "River Problem".
 * <p>
 * A farmer has to cross a (shallow) river. He has with him a fox, a chicken,
 * and a sack of grain. The problem is, he can only carry one item at a time.
 * He cannot leave the fox and chicken alone together because the fox will eat
 * the chicken. He cannot leave the chicken and grain alone together because
 * the chicken will eat the grain. How can the farmer get all three safely
 * across the river?
 */
public class RiverProblemDomainGenerator implements DomainGenerator {

    /**
     * Defines the states in the problem. Each string represents the three
     * locations (left bank, river, right bank) and the positions of the
     * farmer (*), fox (F), chicken (C), and sack of grain (G).
     */
    private static final String[] STATES = {
            "[*FCG| | ]",   // ( 0) start state
            "[CG|*F| ]",    // ( 1) farmer takes fox: FAIL - chicken eats grain
            "[FG|*C| ]",    // ( 2) farmer takes chicken
            "[FC|*G| ]",    // ( 3) farmer takes grain: FAIL - fox eats chicken
            "[FG| |*C]",    // ( 4) chicken on right bank
            "[FG|*|C]",     // ( 5) farmer heads back
            "[*FG| |C]",    // ( 6)
            "[G|*F|C]",     // ( 7) farmer takes fox
            "[F|*G|C]",     // ( 8) farmer takes grain
            "[G| |*FC]",    // ( 9) fox and chicken with farmer
            "[F| |*CG]",    // (10) chicken and grain with farmer
            "[G|*|FC]",     // (11) farmer heads back: FAIL - fox eats chicken
            "[G|*C|F]",     // (12) farmer heads back WITH CHICKEN
            "[F|*C|G]",     // (13) farmer heads back WITH CHICKEN
            "[F|*|CG]",     // (14) farmer heads back: FAIL - chicken eats grain
            "[*CG| |F]",    // (15) farmer with chicken and grain
            "[*FC| |G]",    // (16) farmer with fox and chicken
            "[C|*G|F]",     // (17) farmer heads off with grain, leaving chicken
            "[C|*F|G]",     // (18) farmer heads off with fox, leaving chicken
            "[C| |*FG]",    // (19) farmer leaves fox with grain
            "[C|*|FG]",     // (20) farmer heads back
            "[*C| |FG]",    // (21) farmer picks up chicken once again
            "[ |*C|FG]",    // (22) nearly there
            "[ | |*FCG]",   // (23) Success!
    };

    private static final int NUM_STATES = STATES.length;

    /**
     * Major index is source state ID; minor index is destination state ID.
     */
    private static final int[][] TRANSITIONS = {
            {1, 2, 3},      // ( 0) -- start
            {},             // ( 1) -- fail
            {0, 4},         // ( 2)
            {},             // ( 3) -- fail
            {2, 5},         // ( 4)
            {4, 6},         // ( 5)
            {5, 7, 8},      // ( 6)
            {6, 9},         // ( 7)
            {6, 10},        // ( 8)
            {7, 11, 12},    // ( 9)
            {8, 13, 14},    // (10)
            {},             // (11) -- fail
            {9, 15},        // (12)
            {10, 16},       // (13)
            {},             // (14) -- fail
            {12, 17},       // (15)
            {13, 18},       // (16)
            {15, 19},       // (17)
            {16, 19},       // (18)
            {17, 18, 20},   // (19)
            {19, 21},       // (20)
            {20, 22},       // (21)
            {21, 23},       // (22)
            {},             // (23) -- success
    };


    private static final double CERTAINTY = 1.0;

    private static final int START_STATE_ID = 0;
    private static final int SUCCESS_STATE_ID = 23;
    private static final List<Integer> FAIL_STATE_IDS = Arrays.asList(1, 3, 11, 14);

    private static final int R_ZERO = 0;
    private static final int R_FAIL = -100;
    private static final int R_DEFAULT = -1;
    private static final int R_SUCCESS = 100;


    @Override
    public Domain generateDomain() {
        GraphDefinedDomain gdd = new GraphDefinedDomain(NUM_STATES);

        // set up the transitions...
        for (int state = 0; state < NUM_STATES; state++) {
            int[] xns = TRANSITIONS[state];
            for (int action = 0; action < xns.length; action++) {
                int destState = xns[action];
                gdd.setTransition(state, action, destState, CERTAINTY);
            }
        }
        return gdd.generateDomain();
    }

    /**
     * Returns the reward function for this problem.
     *
     * @return reward function
     */
    public RewardFunction generateRewardFunction() {
        return new RiverRewardFunction();
    }

    /**
     * Returns the terminal function for this problem.
     *
     * @return terminal function
     */
    public TerminalFunction generateTerminalFunction() {
        return new RiverTerminalFunction();
    }

    /**
     * Implements the reward function.
     */
    private final class RiverRewardFunction implements RewardFunction {
        @Override
        public double reward(State from, GroundedAction ga, State dest) {
            final int destId = GraphDefinedDomain.getNodeId(dest);
            if (destId == START_STATE_ID) {
                return R_ZERO;
            }
            if (destId == SUCCESS_STATE_ID) {
                return R_SUCCESS;
            }
            if (FAIL_STATE_IDS.contains(destId)) {
                return R_FAIL;
            }
            return R_DEFAULT;
        }
    }

    /**
     * Implements the terminal function.
     */
    private final class RiverTerminalFunction implements TerminalFunction {
        @Override
        public boolean isTerminal(State state) {
            final int id = GraphDefinedDomain.getNodeId(state);
            return FAIL_STATE_IDS.contains(id) || id == SUCCESS_STATE_ID;
        }
    }
}
