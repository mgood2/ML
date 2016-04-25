package ml.project4.river;

import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.MDPSolver;
import burlap.behavior.singleagent.auxiliary.StateReachability;
import burlap.debugtools.DPrint;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import ml.project4.augment.AugmentedPolicyIteration;
import ml.project4.augment.AugmentedQLearning;
import ml.project4.augment.AugmentedValueIteration;

import static ml.project4.OutputUtils.print;
import static ml.project4.river.RiverPrinter.printRiverSolution;

/**
 * Runs the analyses on the grid world.
 */
public class RiverAnalysisRunner {

    private static final double GAMMA = 0.99;

    private static final double MAX_DELTA = .1;

    /*
       ran with QL iterations at 1000, 100, and 10.
     */
    private static final int NUM_QL_ITERATIONS = 10;

    private static final int MAX_EVAL_ITERATIONS = 1;
    private static final double Q_INIT = 0.99;
    private static final double Q_LEARNING_RATE = 0.99;


    private final SimpleHashableStateFactory hashingFactory =
            new SimpleHashableStateFactory();

    private final int maxIterations;
    private final double epsilon;

    /**
     * Creates a new river analysis runner.
     *
     * @param maxIterations    maximum number of iterations to run
     * @param iterationEpsilon policy change must be smaller than this to
     *                         terminate the search
     */
    public RiverAnalysisRunner(int maxIterations, double iterationEpsilon) {
        this.maxIterations = maxIterations;
        this.epsilon = iterationEpsilon;
    }

    private void switchOffDebugOutput(MDPSolver solver) {
        DPrint.toggleCode(solver.getDebugCode(), false);
        DPrint.toggleCode(StateReachability.debugID, false);
    }

    /**
     * Runs value iteration experiment.
     *
     * @param gen          domain generator
     * @param domain       domain
     * @param initialState initial state
     * @param rf           reward function
     * @param tf           terminal function
     */
    public void runValueIteration(RiverProblemDomainGenerator gen, Domain domain,
                                  State initialState, RewardFunction rf,
                                  TerminalFunction tf) {
        print("//Value Iteration Analysis//");

        final long startTime = System.nanoTime();

        AugmentedValueIteration vi = new AugmentedValueIteration(domain, rf, tf,
                GAMMA, hashingFactory, epsilon, maxIterations);

        switchOffDebugOutput(vi);

        Policy p = vi.planFromState(initialState);
        final long durationMs = durationInMs(startTime);
        final int convergeIn = vi.getIterations();

        EpisodeAnalysis ea = p.evaluateBehavior(initialState, rf, tf);
        final double reward = calcRewardInEpisode(ea);
        final int numSteps = ea.numTimeSteps();

        print("Duration %d ms", durationMs);
        print("Converged in %d iterations", convergeIn);
        print("Summed Reward is %.2f", reward);
        print("Number of steps is %d", numSteps);

        printRiverSolution(gen, initialState, p);
    }

    /**
     * Runs policy iteration experiment.
     *
     * @param gen          domain generator
     * @param domain       domain
     * @param initialState initial state
     * @param rf           reward function
     * @param tf           terminal function
     */
    public void runPolicyIteration(RiverProblemDomainGenerator gen, Domain domain,
                                   State initialState, RewardFunction rf,
                                   TerminalFunction tf) {
        print("//Policy Iteration Analysis//");

        final long startTime = System.nanoTime();

        AugmentedPolicyIteration pi = new AugmentedPolicyIteration(domain, rf, tf,
                GAMMA, hashingFactory, epsilon, MAX_EVAL_ITERATIONS, maxIterations);

        switchOffDebugOutput(pi);

        Policy p = pi.planFromState(initialState);
        final long durationMs = durationInMs(startTime);
        final int convergeIn = pi.getIterations();

        EpisodeAnalysis ea = p.evaluateBehavior(initialState, rf, tf);
        final double reward = calcRewardInEpisode(ea);
        final int numSteps = ea.numTimeSteps();

        print("Duration %d ms", durationMs);
        print("Converged in %d iterations", convergeIn);
        print("Summed Reward is %.2f", reward);
        print("Number of steps is %d", numSteps);

        printRiverSolution(gen, initialState, p);
    }


    /**
     * Runs Q-Learning experiment
     *
     * @param gen          domain generator
     * @param domain       domain
     * @param initialState initial state
     * @param rf           reward function
     * @param tf           terminal function
     * @param env          simulated environment
     */
    public void runQLearning(RiverProblemDomainGenerator gen, Domain domain,
                             State initialState, RewardFunction rf,
                             TerminalFunction tf, SimulatedEnvironment env) {
        print("//Q Learning Analysis//");

        final long startTime = System.nanoTime();

        AugmentedQLearning agent =
                new AugmentedQLearning(domain, GAMMA, hashingFactory,
                        Q_INIT, Q_LEARNING_RATE);
        agent.setMaxQChangeForPlanningTerminaiton(MAX_DELTA);
        agent.setMaximumEpisodesForPlanning(NUM_QL_ITERATIONS);

        switchOffDebugOutput(agent);

        EpisodeAnalysis ea = null;
        for (int i = 0; i < NUM_QL_ITERATIONS; i++) {
            ea = agent.runLearningEpisode(env);
            env.resetEnvironment();
        }
        agent.initializeForPlanning(rf, tf, NUM_QL_ITERATIONS);
        Policy p = agent.planFromState(initialState);

        final long durationMs = durationInMs(startTime);
        final int convergeIn = agent.getIterations();

        ea = p.evaluateBehavior(initialState, rf, tf);
        final double reward = calcRewardInEpisode(ea);
        final int numSteps = ea.numTimeSteps();

        print("Duration %d ms", durationMs);
        print("Converged in %d iterations", convergeIn);
        print("Summed Reward is %.2f", reward);
        print("Number of steps is %d", numSteps);

        printRiverSolution(gen, initialState, p);
    }


    private int durationInMs(double startTime) {
        return (int) (System.nanoTime() - startTime) / 1_000_000;
    }

    private double calcRewardInEpisode(EpisodeAnalysis ea) {
        double myRewards = 0.0;
        for (int i = 0; i < ea.rewardSequence.size(); i++) {
            myRewards += ea.rewardSequence.get(i);
        }
        return myRewards;
    }

}
