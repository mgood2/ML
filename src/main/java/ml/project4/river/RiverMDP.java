package ml.project4.river;

import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;

import static ml.project4.PrintUtils.print;

/**
 * Encapsulates the MDP for the River problem.
 */
public class RiverMDP {

    private static final double ITERATION_EPSILON = 0.00001;
    private static final int MAX_ITERATIONS = 1000;

    private static final int S0 = 0;

    private RiverProblemDomainGenerator riverDg;
    private Domain domain;
    private State initialState;
    private RewardFunction rf;
    private TerminalFunction tf;
    private HashableStateFactory hashFactory = new SimpleHashableStateFactory();


    public RiverMDP() {
        riverDg = new RiverProblemDomainGenerator();
        domain = riverDg.generateDomain();
        // no intrinsic notion of initial state in the domain object
        // we have to inject this notion from outside the domain
        initialState = GraphDefinedDomain.getState(domain, S0);
        rf = riverDg.generateRewardFunction();
        tf = riverDg.generateTerminalFunction();
    }

    private ValueIteration createValueIteration(double gamma) {
        ValueIteration vi = new ValueIteration(domain, rf, tf, gamma,
                hashFactory, ITERATION_EPSILON, MAX_ITERATIONS);
        vi.planFromState(initialState);
        return vi;
    }

    public void solve() {
        double gamma = 0.5;

        ValueIteration vi = createValueIteration(gamma);
        print(vi);
    }
}
