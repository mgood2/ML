package ml.project4.grid.util;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.PropositionalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import ml.project4.grid.BasicGridWorld;

/**
 * Propositional function that returns true if the agent is at the goal.
 */

public class AtLocation extends PropositionalFunction {

    public AtLocation(Domain domain) {
        super(BasicGridWorld.PFAT, domain, new String[] {
                BasicGridWorld.CLASS_AGENT,
                BasicGridWorld.CLASS_GOAL
        });
    }

    @Override
    public boolean isTrue(State s, String... params) {
        ObjectInstance agent = s.getObject(params[0]);
        ObjectInstance goal = s.getObject(params[1]);

        int ax = agent.getIntValForAttribute(BasicGridWorld.ATTX);
        int ay = agent.getIntValForAttribute(BasicGridWorld.ATTY);

        int gx = goal.getIntValForAttribute(BasicGridWorld.ATTX);
        int gy = goal.getIntValForAttribute(BasicGridWorld.ATTY);

        return ax == gx && ay == gy;
    }

}
