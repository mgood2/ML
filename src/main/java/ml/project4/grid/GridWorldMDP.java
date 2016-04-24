package ml.project4.grid;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.singleagent.explorer.VisualExplorer;
import burlap.oomdp.visualizer.Visualizer;
import ml.project4.grid.util.AnalysisRunner;
import ml.project4.grid.util.BasicRewardFunction;
import ml.project4.grid.util.BasicTerminalFunction;
import ml.project4.grid.util.MapPrinter;

import static ml.project4.grid.BasicGridWorld.GOAL_X;
import static ml.project4.grid.BasicGridWorld.GOAL_Y;
import static ml.project4.grid.BasicGridWorld.TRAP_X;
import static ml.project4.grid.BasicGridWorld.TRAP_Y;
import static ml.project4.OutputUtils.print;
import static ml.project4.grid.util.AnalysisAggregator.printAggregateAnalysis;

/**
 * Encapsulates the MDP for the grid world problem.
 */
class GridWorldMDP {

    // parameterization of experiment runs.
    private static final int MAX_ITERATIONS = 100;
    private static final int MAX_INTERVALS = 100;

    // key bindings
    private static final String KEY_W = "w";
    private static final String KEY_A = "a";
    private static final String KEY_S = "s";
    private static final String KEY_D = "d";

    // NOTE: this is indexed so that the walls appear where you'd expect 'em
    private static final int[][] WORLD_MAP = {
            {0, 1, 0, 0},
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 0, 0},
    };

    private static final double MIN_PROB = 0.5;
    private static final double CERTAINTY = 1.0;
    private static final String E_PROB_VALUE =
            "Success probability must bein the range 0.5 to 1.0";

    private int[][] map = MapPrinter.mapToMatrix(WORLD_MAP);

    private BasicGridWorld gen;
    private Domain domain;
    private State initialState;
    private RewardFunction rf;
    private TerminalFunction tf;
    private SimulatedEnvironment env;


    /**
     * Constructs the gridworld MDP problem.
     */
    GridWorldMDP() {
        gen = new BasicGridWorld(map);
        domain = gen.generateDomain();
        initialState = BasicGridWorld.getInitialState(domain);
        rf = new BasicRewardFunction(TRAP_X, TRAP_Y, GOAL_X, GOAL_Y);
        tf = new BasicTerminalFunction(GOAL_X, GOAL_Y);
        env = new SimulatedEnvironment(domain, rf, tf, initialState);
    }


    @Override
    public String toString() {
        return MapPrinter.mapAsString(MapPrinter.matrixToMap(map));
    }

    /**
     * Creates a visualizer for the world.
     */
    void visualize() {
        Visualizer v = gen.getVisualizer();
        VisualExplorer exp = new VisualExplorer(domain, env, v);
        exp.addKeyAction(KEY_W, BasicGridWorld.ACTION_NORTH);
        exp.addKeyAction(KEY_A, BasicGridWorld.ACTION_WEST);
        exp.addKeyAction(KEY_S, BasicGridWorld.ACTION_SOUTH);
        exp.addKeyAction(KEY_D, BasicGridWorld.ACTION_EAST);
        exp.initGUI();
    }

    /**
     * Runs the value iteration, policy iteration, and Q-learning algorithms
     * on a grid world instance, parameterized with the given probability
     * of intended action success.
     *
     * @param successProb probability of requested action occurring
     * @throws IllegalArgumentException if probability not 0.5 to 1.0
     */
    void runExperiments(double successProb) {
        if (successProb > CERTAINTY || successProb < MIN_PROB) {
            throw new IllegalArgumentException(E_PROB_VALUE);
        }
        gen.setProbSuccess(successProb);
        domain = gen.generateDomain();
        print("Running experiments on %s", gen);
        print("Max iterations: %d, max intervals: %d",
                MAX_ITERATIONS, MAX_INTERVALS);

        AnalysisRunner runner = new AnalysisRunner(MAX_ITERATIONS, MAX_INTERVALS);

        runner.runValueIteration(gen, domain, initialState, rf, tf, true);
        runner.runPolicyIteration(gen, domain, initialState, rf, tf, true);
        runner.runQLearning(gen, domain, initialState, rf, tf, env, true);

        printAggregateAnalysis();
    }
}
