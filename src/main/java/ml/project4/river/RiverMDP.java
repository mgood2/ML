package ml.project4.river;

import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.domain.singleagent.graphdefined.GraphDefinedDomain;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import ml.project4.grid.util.AnalysisRunner;

import static ml.project4.OutputUtils.print;

/**
 * Encapsulates the MDP for the River problem.
 */
public class RiverMDP {

    private static final double ITERATION_EPSILON = 0.0001;
    private static final int MAX_ITERATIONS = 1000;
    private static final double GAMMA = 0.99;

    private static final int S0 = 0;

    private RiverProblemDomainGenerator gen;
    private Domain domain;
    private State initialState;
    private RewardFunction rf;
    private TerminalFunction tf;
    private HashableStateFactory hashFactory = new SimpleHashableStateFactory();


    public RiverMDP() {
        gen = new RiverProblemDomainGenerator();
        domain = gen.generateDomain();
        // no intrinsic notion of initial state in the domain object
        // we have to inject this notion from outside the domain
        initialState = GraphDefinedDomain.getState(domain, S0);
        rf = gen.generateRewardFunction();
        tf = gen.generateTerminalFunction();
    }

    private ValueIteration createValueIteration(double gamma) {
        ValueIteration vi = new ValueIteration(domain, rf, tf, gamma,
                hashFactory, ITERATION_EPSILON, MAX_ITERATIONS);
        vi.planFromState(initialState);
        return vi;
    }

    /**
     * Runs the value iteration, policy iteration, and Q-learning algorithms
     * on the river problem state graph.
     */
    void runExperiments() {

        print("Running experiments on %s", gen);
        print("Max iterations: %d, iteration epsilon: %.5f",
                MAX_ITERATIONS, ITERATION_EPSILON);

        RiverAnalysisRunner runner =
                new RiverAnalysisRunner(MAX_ITERATIONS, ITERATION_EPSILON);

        runner.runValueIteration(gen, domain, initialState, rf, tf);

        // TODO: Policy Iteration
        // TODO: Q-Learning
    }
}
