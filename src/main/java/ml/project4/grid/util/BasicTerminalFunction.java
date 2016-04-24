package ml.project4.grid.util;

import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import ml.project4.grid.BasicGridWorld;

/**
 * Designates ...
 */
public class BasicTerminalFunction implements TerminalFunction {

    int goalX;
    int goalY;

    public BasicTerminalFunction(int goalX, int goalY) {
        this.goalX = goalX;
        this.goalY = goalY;
    }

    @Override
    public boolean isTerminal(State s) {

        // get location of agent in next state
        ObjectInstance agent = s.getFirstObjectOfClass(BasicGridWorld.CLASSAGENT);
        int ax = agent.getIntValForAttribute(BasicGridWorld.ATTX);
        int ay = agent.getIntValForAttribute(BasicGridWorld.ATTY);

        // are they at goal location?
        if (ax == this.goalX && ay == this.goalY) {
            return true;
        }

        return false;
    }

}
