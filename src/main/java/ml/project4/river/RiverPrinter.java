package ml.project4.river;

import burlap.behavior.policy.Policy;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.core.AbstractGroundedAction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.common.SimpleGroundedAction;

import java.util.ArrayList;
import java.util.List;

import static ml.project4.OutputUtils.print;

/**
 * Utility class for printing river results.
 */
public class RiverPrinter {

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
        while (!done) {
            AbstractGroundedAction action = p.getAction(currentState);

            SimpleGroundedAction sga = (SimpleGroundedAction) action;
            currentState = sga.executeIn(currentState);
            stateId = GraphDefinedDomain.getNodeId(currentState);

            path.add(stateId);

            if (RiverProblemDomainGenerator.isTerminalId(stateId)) {
                done = true;
            }
        }
        return path;
    }

    /**
     * Prints the path over the state nodes for the given policy.
     *
     * @param initialState initial state
     * @param p            policy
     */
    public static void printRiverPath(State initialState, Policy p) {
        List<Integer> ids = nodePath(initialState, p);

        StringBuilder sb = new StringBuilder("[").append(ids.remove(0));
        for (int id : ids) {
            sb.append(" -> ").append(id);
        }
        sb.append("]");

        print(sb);
    }
}
