package ml.project4.grid;

import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import ml.project4.grid.util.BasicRewardFunction;
import ml.project4.grid.util.BasicTerminalFunction;
import ml.project4.grid.util.MapPrinter;

/**
 * Encapsulates the MDP for the grid world problem.
 */
public class GridWorldMDP {

    private static final double PROB_SUCCEED = 0.9;

    // NOTE: this is indexed so that the walls appear where you'd expect 'em
    private static final int[][] WORLD_MAP = {
            {0, 1, 0, 0},
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 0, 0},
    };

    private static final int GRID_H = WORLD_MAP.length;
    private static final int GRID_W = WORLD_MAP[0].length;

    private static final int ZERO = 0;

    private static final int LOC_GOAL = 0;


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

        // create grid world domain
        gen = new BasicGridWorld(map);
        domain = gen.generateDomain();

        initialState = BasicGridWorld.getInitialState(domain);

        rf = new BasicRewardFunction(BasicGridWorld.GOAL_X, BasicGridWorld.GOAL_Y);
        tf = new BasicTerminalFunction(BasicGridWorld.GOAL_X, BasicGridWorld.GOAL_Y);

        env = new SimulatedEnvironment(domain, rf, tf, initialState);
    }


    @Override
    public String toString() {
        return MapPrinter.mapAsString(MapPrinter.matrixToMap(map));
    }
}
