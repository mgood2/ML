package ml.project4.grid.util;

import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.RewardFunction;
import ml.project4.grid.BasicGridWorld;

/**
 * Grid world reward function; big positive for the goal, big negative for
 * the trap, small negative everywhere else.
 */
public class BasicRewardFunction implements RewardFunction {

    private static final int GOAL_REWARD = 100;
    private static final int TRAP_REWARD = -100;
    private static final int DEFAULT_REWARD = -1;

    int goalX;
    int goalY;
    int trapX;
    int trapY;

    public BasicRewardFunction(int trapX, int trapY, int goalX, int goalY) {
        this.trapX = trapX;
        this.trapY = trapY;
        this.goalX = goalX;
        this.goalY = goalY;
    }

    @Override
    public double reward(State s, GroundedAction a, State sprime) {

        // get location of agent in next state
        ObjectInstance agent = sprime.getFirstObjectOfClass(BasicGridWorld.CLASS_AGENT);
        int ax = agent.getIntValForAttribute(BasicGridWorld.ATTX);
        int ay = agent.getIntValForAttribute(BasicGridWorld.ATTY);

        // should they get a non-default reward?
        if (ax == goalX && ay == goalY) {
            return GOAL_REWARD;
        } else if (ax == trapX && ay == trapY) {
            return TRAP_REWARD;
        }
        return DEFAULT_REWARD;
    }

}
