package ml.project4.grid;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.singleagent.explorer.VisualExplorer;
import burlap.oomdp.visualizer.Visualizer;
import ml.project4.grid.util.BasicRewardFunction;
import ml.project4.grid.util.BasicTerminalFunction;
import ml.project4.grid.util.MapPrinter;

import static ml.project4.grid.BasicGridWorld.GOAL_X;
import static ml.project4.grid.BasicGridWorld.GOAL_Y;
import static ml.project4.grid.BasicGridWorld.TRAP_X;
import static ml.project4.grid.BasicGridWorld.TRAP_Y;

/**
 * Encapsulates the MDP for the grid world problem.
 */
public class GridWorldMDP {

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
    public GridWorldMDP() {

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
    public void visualize() {
        Visualizer v = gen.getVisualizer();
        VisualExplorer exp = new VisualExplorer(domain, env, v);
        exp.addKeyAction(KEY_W, BasicGridWorld.ACTION_NORTH);
        exp.addKeyAction(KEY_A, BasicGridWorld.ACTION_WEST);
        exp.addKeyAction(KEY_S, BasicGridWorld.ACTION_SOUTH);
        exp.addKeyAction(KEY_D, BasicGridWorld.ACTION_EAST);
        exp.initGUI();
    }

}
