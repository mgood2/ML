package ml.project4.grid.util;

import burlap.behavior.policy.Policy;
import burlap.behavior.singleagent.EpisodeAnalysis;
import burlap.behavior.singleagent.MDPSolver;
import burlap.behavior.singleagent.auxiliary.StateReachability;
import burlap.behavior.singleagent.auxiliary.valuefunctionvis.ValueFunctionVisualizerGUI;
import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.behavior.valuefunction.ValueFunction;
import burlap.debugtools.DPrint;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.SADomain;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.HashableStateFactory;
import burlap.oomdp.statehashing.SimpleHashableStateFactory;
import ml.project4.augment.AugmentedPolicyIteration;
import ml.project4.augment.AugmentedQLearning;
import ml.project4.augment.AugmentedValueIteration;
import ml.project4.grid.BasicGridWorld;

import java.util.List;

import static ml.project4.grid.OutputUtils.print;
import static ml.project4.grid.util.AnalysisAggregator.addConvergencePolicyIteration;
import static ml.project4.grid.util.AnalysisAggregator.addConvergenceQLearning;
import static ml.project4.grid.util.AnalysisAggregator.addConvergenceValueIteration;
import static ml.project4.grid.util.AnalysisAggregator.addMillisecondsToFinishPolicyIteration;
import static ml.project4.grid.util.AnalysisAggregator.addMillisecondsToFinishQLearning;
import static ml.project4.grid.util.AnalysisAggregator.addMillisecondsToFinishValueIteration;
import static ml.project4.grid.util.AnalysisAggregator.addNumberOfIterations;
import static ml.project4.grid.util.AnalysisAggregator.addPolicyIterationReward;
import static ml.project4.grid.util.AnalysisAggregator.addQLearningReward;
import static ml.project4.grid.util.AnalysisAggregator.addStepsToFinishPolicyIteration;
import static ml.project4.grid.util.AnalysisAggregator.addStepsToFinishQLearning;
import static ml.project4.grid.util.AnalysisAggregator.addStepsToFinishValueIteration;
import static ml.project4.grid.util.AnalysisAggregator.addValueIterationReward;
import static ml.project4.grid.util.AnalysisAggregator.printPolicyIterationResults;
import static ml.project4.grid.util.AnalysisAggregator.printQLearningResults;
import static ml.project4.grid.util.AnalysisAggregator.printValueIterationResults;
import static ml.project4.grid.util.MapPrinter.printPolicyMap;

/**
 * Runs the analyses on the grid world.
 */
public class AnalysisRunner {

    private static final double GAMMA = 0.99;

    /*
        Very high delta number in order to guarantee that value iteration
        occurs the max number of iterations for comparison with the other
        algorithms.
     */
//    private static final double MAX_DELTA = -1.0;
    private static final double MAX_DELTA = .1;

    private static final int MAX_EVAL_ITERATIONS = 1;

    private static final double ALL_STATES_MAX_DELTA = 0.5;
    private static final int ALL_STATES_MAX_ITERATIONS = 100;
    private static final double Q_INIT = 0.99;
    private static final double Q_LEARNING_RATE = 0.99;
    private static final int NUM_EPISODES_FOR_PLANNING = 1;


    private final SimpleHashableStateFactory hashingFactory =
            new SimpleHashableStateFactory();

    private final int maxIterations;
    private final int numIntervals;
    private final int increment;

    /**
     * Creates a new analysis runner.
     *
     * @param maxIterations maximum number of iterations to run
     * @param numIntervals  number of intervals
     */
    public AnalysisRunner(int maxIterations, int numIntervals) {
        this.maxIterations = maxIterations;
        this.numIntervals = numIntervals;
        increment = maxIterations / numIntervals;

        for (int nIter = increment; nIter <= maxIterations; nIter += increment) {
            addNumberOfIterations(nIter);
        }
    }

    private void switchOffDebugOutput(MDPSolver solver) {
        DPrint.toggleCode(solver.getDebugCode(), false);
        DPrint.toggleCode(StateReachability.debugID, false);
    }

    /**
     * Runs value iteration experiment.
     *
     * @param gen           domain generator
     * @param domain        domain
     * @param initialState  initial state
     * @param rf            reward function
     * @param tf            terminal function
     * @param showPolicyMap set true to visualize the policy map
     */
    public void runValueIteration(BasicGridWorld gen, Domain domain,
                                  State initialState, RewardFunction rf,
                                  TerminalFunction tf, boolean showPolicyMap) {
        print("//Value Iteration Analysis//");
        AugmentedValueIteration vi = null;
        Policy p = null;
        EpisodeAnalysis ea = null;
        for (int nIter = increment; nIter <= maxIterations; nIter += increment) {
            final long startTime = System.nanoTime();
            vi = new AugmentedValueIteration(domain, rf, tf, GAMMA,
                    hashingFactory, MAX_DELTA, nIter);

            // stop the insanity... and switch off debugging messages
            switchOffDebugOutput(vi);

            // run planning from our initial state
            p = vi.planFromState(initialState);
            addMillisecondsToFinishValueIteration(durationInMs(startTime));
            addConvergenceValueIteration(vi.getIterations());

            // evaluate the policy with one roll out visualize the trajectory
            ea = p.evaluateBehavior(initialState, rf, tf);
            addValueIterationReward(calcRewardInEpisode(ea));
            addStepsToFinishValueIteration(ea.numTimeSteps());
        }

        // visualize the episodes...
//        Visualizer v = gen.getVisualizer();
//        new EpisodeSequenceVisualizer(v, domain, Arrays.asList(ea));

        printValueIterationResults();

        //
        printPolicyMap(vi.getAllStates(), p, gen.getMap());

        if (showPolicyMap) {
            simpleValueFunctionVis(vi, p, initialState, domain, hashingFactory);
        }
    }

    /**
     * Runs policy iteration experiment.
     *
     * @param gen           domain generator
     * @param domain        domain
     * @param initialState  initial state
     * @param rf            reward function
     * @param tf            terminal function
     * @param showPolicyMap set true to visualize the policy map
     */
    public void runPolicyIteration(BasicGridWorld gen, Domain domain,
                                   State initialState, RewardFunction rf,
                                   TerminalFunction tf, boolean showPolicyMap) {
        print("//Policy Iteration Analysis//");
        AugmentedPolicyIteration pi = null;
        Policy p = null;
        EpisodeAnalysis ea = null;
        for (int nIter = increment; nIter <= maxIterations; nIter += increment) {
            final long startTime = System.nanoTime();
            pi = new AugmentedPolicyIteration(domain, rf, tf, GAMMA, hashingFactory,
                    MAX_DELTA, MAX_EVAL_ITERATIONS, nIter);

            // stop the insanity... and switch off debugging messages
            switchOffDebugOutput(pi);

            // run planning from our initial state
            p = pi.planFromState(initialState);
            addMillisecondsToFinishPolicyIteration(durationInMs(startTime));
            addConvergencePolicyIteration(pi.getIterations());

            // evaluate the policy with one roll out visualize the trajectory
            ea = p.evaluateBehavior(initialState, rf, tf);
            addPolicyIterationReward(calcRewardInEpisode(ea));
            addStepsToFinishPolicyIteration(ea.numTimeSteps());
        }

        // visualize the episodes...
//        Visualizer v = gen.getVisualizer();
//        new EpisodeSequenceVisualizer(v, domain, Arrays.asList(ea));


        printPolicyIterationResults();

        printPolicyMap(getAllStates(domain, rf, tf, initialState), p, gen.getMap());

        //visualize the value function and policy.
        if (showPolicyMap) {
            simpleValueFunctionVis(pi, p, initialState, domain, hashingFactory);
        }
    }

    private void simpleValueFunctionVis(ValueFunction valueFunction, Policy p,
                                        State initialState, Domain domain,
                                        HashableStateFactory hashingFactory) {

        List<State> allStates = StateReachability.getReachableStates(initialState,
                (SADomain) domain, hashingFactory);
        ValueFunctionVisualizerGUI gui =
                GridWorldDomain.getGridWorldValueFunctionVisualization(
                        allStates, valueFunction, p);
        gui.initGUI();
    }

    /**
     * Runs Q-Learning experiment
     *
     * @param gen           domain generator
     * @param domain        domain
     * @param initialState  initial state
     * @param rf            reward function
     * @param tf            terminal function
     * @param env           simulated environment
     * @param showPolicyMap set true to visualize the policy map
     */
    public void runQLearning(BasicGridWorld gen, Domain domain,
                             State initialState, RewardFunction rf,
                             TerminalFunction tf, SimulatedEnvironment env,
                             boolean showPolicyMap) {
        print("//Q Learning Analysis//");

        AugmentedQLearning agent = null;
        Policy p = null;
        EpisodeAnalysis ea = null;
        for (int nIter = increment; nIter <= maxIterations; nIter += increment) {
            final long startTime = System.nanoTime();

            agent = new AugmentedQLearning(domain, GAMMA, hashingFactory,
                    Q_INIT, Q_LEARNING_RATE);
            agent.setMaxQChangeForPlanningTerminaiton(MAX_DELTA);
            agent.setMaximumEpisodesForPlanning(nIter);

            // stop the insanity... and switch off debugging messages
            switchOffDebugOutput(agent);

            for (int i = 0; i < nIter; i++) {
                ea = agent.runLearningEpisode(env);
                env.resetEnvironment();
            }
            agent.initializeForPlanning(rf, tf, nIter);
            p = agent.planFromState(initialState);

            addMillisecondsToFinishQLearning(durationInMs(startTime));
            addConvergenceQLearning(agent.getIterations());

            addQLearningReward(calcRewardInEpisode(ea));
            addStepsToFinishQLearning(ea.numTimeSteps());
        }
        printQLearningResults();
        printPolicyMap(getAllStates(domain, rf, tf, initialState), p, gen.getMap());

        //visualize the value function and policy.
        if (showPolicyMap) {
            simpleValueFunctionVis(agent, p, initialState, domain, hashingFactory);
        }
    }


    private int durationInMs(double startTime) {
        return (int) (System.nanoTime() - startTime) / 1_000_000;
    }

    private static List<State> getAllStates(Domain domain, RewardFunction rf,
                                            TerminalFunction tf, State initialState) {
        ValueIteration vi = new ValueIteration(domain, rf, tf, GAMMA,
                new SimpleHashableStateFactory(), ALL_STATES_MAX_DELTA,
                ALL_STATES_MAX_ITERATIONS);

        vi.planFromState(initialState);
        return vi.getAllStates();
    }

    private double calcRewardInEpisode(EpisodeAnalysis ea) {
        double myRewards = 0.0;
        for (int i = 0; i < ea.rewardSequence.size(); i++) {
            myRewards += ea.rewardSequence.get(i);
        }
        return myRewards;
    }

}
