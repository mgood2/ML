package ml.project4.river;

import burlap.behavior.policy.Policy;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.core.AbstractGroundedAction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.common.SimpleGroundedAction;

import java.util.ArrayList;
import java.util.List;

import static ml.project4.OutputUtils.EOL;
import static ml.project4.OutputUtils.print;

/**
 * Utility class for printing river results.
 */
public class RiverPrinter {

    private static final int FAILSAFE = 25;

    /**
     * Returns the node path in the given domain, for the specified policy.
     *
     * @param initialState initial state
     * @param p            the policy
     * @return the ordered list of node identifiers on the policy path
     */
    public static List<Integer> nodePath(State initialState, Policy p) {
        State currentState = initialState;
        int stateId = GraphDefinedDomain.getNodeId(initialState);

        List<Integer> path = new ArrayList<>();
        path.add(stateId);

        boolean done = false;
        int count = 0;
        while (!done) {
            AbstractGroundedAction action = p.getAction(currentState);

            SimpleGroundedAction sga = (SimpleGroundedAction) action;
            currentState = sga.executeIn(currentState);
            stateId = GraphDefinedDomain.getNodeId(currentState);

            path.add(stateId);

            if (RiverProblemDomainGenerator.isTerminalId(stateId) ||
                    ++count >= FAILSAFE) {
                done = true;
            }
        }
        return path;
    }

    /**
     * Prints the path .
     *
     * @param ids list of state identifiers
     */
    public static void printRiverPath(List<Integer> ids) {
        StringBuilder sb = new StringBuilder("[").append(ids.remove(0));
        for (int id : ids) {
            sb.append(" -> ").append(id);
        }
        sb.append("]");
        print(sb);
    }

    /**
     * Prints the verbose list of states for the given policy.
     *
     * @param gen generator
     * @param initialState initial state
     * @param p policy
     */
    public static void printRiverSolution(RiverProblemDomainGenerator gen,
                                          State initialState, Policy p) {
        List<Integer> ids = nodePath(initialState, p);

        print("");
        // make a copy, because the first item gets deleted
        printRiverPath(new ArrayList<>(ids));

        StringBuilder sb = new StringBuilder(EOL);
        for (int id: ids) {
            sb.append(gen.stateAsString(id)).append(EOL);
        }
        print(sb);
    }
}
