package practice;

import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.auxiliary.common.NullTermination;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

/**
 * From Udacity RL class.
 */
public class FirstMDP {

    private static final int S0 = 0;
    private static final int S1 = 1;
    private static final int S2 = 2;
    private static final int S3 = 3;
    private static final int S4 = 4;
    private static final int S5 = 5;

    private static final int ACTION_A = 0;
    private static final int ACTION_B = 1;
    private static final int ACTION_C = 2;

    private static final int ONLY_ACTION = 0;


    private static final double CERTAINTY = 1.0;
    private static final double ZERO = 0.0;

//    DomainGenerator dg;
    GraphDefinedDomain gdd;
    Domain domain;
    State initialState;
    RewardFunction rf;
    TerminalFunction tf;
    HashableStateFactory hashFactory = new SimpleHashableStateFactory();
    int numStates;


    private FirstMDP(double p1, double p2, double p3, double p4) {
        numStates = 6;
        gdd = new GraphDefinedDomain(numStates);

        gdd.setTransition(S0, ACTION_A, S1, CERTAINTY);
        gdd.setTransition(S0, ACTION_B, S2, CERTAINTY);
        gdd.setTransition(S0, ACTION_C, S3, CERTAINTY);

        gdd.setTransition(S1, ONLY_ACTION, S1, CERTAINTY);

        gdd.setTransition(S2, ONLY_ACTION, S4, CERTAINTY);
        gdd.setTransition(S4, ONLY_ACTION, S2, CERTAINTY);

        gdd.setTransition(S3, ONLY_ACTION, S5, CERTAINTY);
        gdd.setTransition(S5, ONLY_ACTION, S5, CERTAINTY);

        domain = gdd.generateDomain();
        // no intrinsic notion of initial state in the domain object
        // we have to inject this notion from outside the domain
        initialState = GraphDefinedDomain.getState(domain, S0);

        rf = new FourParamRF(p1, p2, p3, p4);

        tf = new NullTermination();
    }


    private ValueIteration computeValue(double gamma) {
        double maxDelta = 0.0001;
        int maxIterations = 1000;
        ValueIteration vi = new ValueIteration(domain, rf, tf, gamma,
                hashFactory, maxDelta, maxIterations);
        vi.planFromState(initialState);
        return vi;
    }

    private String bestFirstAction(double gamma) {
        ValueIteration vi = computeValue(gamma);

        double [] v = new double[numStates];
        for (int i=0; i<numStates; i++) {
            State s = GraphDefinedDomain.getState(domain, i);
            v[i] = vi.value(s);
        }

        String which = null;
        if (v[1] >= v[2] && v[1] >= v[3]) which = BEST_A;
        else if (v[2] >= v[1] && v[2] >= v[3]) which = BEST_B;
        else if (v[3] >= v[1] && v[3] >= v[2]) which = BEST_C;

        return which;
    }

    private static final String BEST_A = "action a";
    private static final String BEST_B = "action b";
    private static final String BEST_C = "action c";

    // Custom reward function
    private static class FourParamRF implements RewardFunction {

        final double p1;
        final double p2;
        final double p3;
        final double p4;

        FourParamRF(double p1, double p2, double p3, double p4) {
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.p4 = p4;
        }

        @Override
        public double reward(State leaving, GroundedAction ga, State arriving) {
            final int leaveId = GraphDefinedDomain.getNodeId(leaving);

            switch (leaveId) {
                case S0: return ZERO;
                case S1: return p1;
                case S2: return p2;
                case S3: return ZERO;
                case S4: return p3;
                case S5: return p4;
                default:
                    throw new IllegalArgumentException("unknown state " + leaveId);
            }
        }
    }

    /**
     * Run the MDP solver.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        FirstMDP fmdp = new FirstMDP(3.0, 4.0, 5.0, 6.0);

        System.out.println(fmdp.bestFirstAction(0.5));
    }
}
