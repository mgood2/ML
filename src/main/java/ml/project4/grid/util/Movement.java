package ml.project4.grid.util;

import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TransitionProbability;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.FullActionModel;
import burlap.oomdp.singleagent.GroundedAction;
import burlap.oomdp.singleagent.common.SimpleAction;
import ml.project4.grid.BasicGridWorld;

import java.util.ArrayList;
import java.util.List;

import static ml.project4.grid.BasicGridWorld.CLASS_AGENT;

/**
 * Movement action.
 */
public class Movement extends SimpleAction implements FullActionModel {

    private static final int WALL = 1;

    private static final int IDX_X = 0;
    private static final int IDX_Y = 1;

    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;

    private static final int N_DIRS = 4;

    private static final double CERTAINTY = 1.0;

    private final double[] directionProbs = new double[N_DIRS];

    protected final int[][] map;
    private final int width;
    private final int height;


    /**
     * Create a movement action which moves in the intended direction with
     * the specified probability of success.
     *
     * @param actionName  name of action
     * @param domain      our domain instance to attach it to
     * @param direction   encoded direction
     * @param map         reference to our map
     * @param successProb probability of movement in the intended direction
     */
    public Movement(String actionName, Domain domain, int direction, int[][] map,
                    double successProb) {
        super(actionName, domain);

        final double otherProb = (CERTAINTY - successProb) / (double) (N_DIRS - 1);

        for (int i = 0; i < N_DIRS; i++) {
            if (i == direction) {
                directionProbs[i] = successProb;
            } else {
                directionProbs[i] = otherProb;
            }
        }

        this.map = map;
        width = this.map.length;
        height = this.map[0].length;
    }

    @Override
    protected State performActionHelper(State s, GroundedAction groundedAction) {
        // get agent and current position
        ObjectInstance agent = s.getFirstObjectOfClass(CLASS_AGENT);
        int curX = agent.getIntValForAttribute(BasicGridWorld.ATTX);
        int curY = agent.getIntValForAttribute(BasicGridWorld.ATTY);

        // sample direction with random roll
        double r = Math.random();
        double sumProb = 0.0;
        int dir = NORTH;
        for (int i = 0; i < N_DIRS; i++) {
            sumProb += this.directionProbs[i];
            if (r < sumProb) {
                dir = i;
                break; //found direction
            }
        }

        // get resulting position
        int[] newPos = moveResult(curX, curY, dir);

        // set the new position
        agent.setValue(BasicGridWorld.ATTX, newPos[IDX_X]);
        agent.setValue(BasicGridWorld.ATTY, newPos[IDX_Y]);

        //return the state we just modified
        return s;
    }

    @Override
    public List<TransitionProbability> getTransitions(State s, GroundedAction ga) {
        // get agent and current position
        ObjectInstance agent = s.getFirstObjectOfClass(CLASS_AGENT);
        int curX = agent.getIntValForAttribute(BasicGridWorld.ATTX);
        int curY = agent.getIntValForAttribute(BasicGridWorld.ATTY);

        List<TransitionProbability> tps = new ArrayList<>(N_DIRS);
        TransitionProbability noChangeTransition = null;
        for (int i = 0; i < this.directionProbs.length; i++) {
            int[] newPos = this.moveResult(curX, curY, i);
            if (newPos[0] != curX || newPos[1] != curY) {
                // new possible outcome
                State ns = s.copy();
                ObjectInstance nagent = ns.getFirstObjectOfClass(CLASS_AGENT);
                nagent.setValue(BasicGridWorld.ATTX, newPos[IDX_X]);
                nagent.setValue(BasicGridWorld.ATTY, newPos[IDX_Y]);

                // create transition probability object and add to our list
                // of outcomes
                tps.add(new TransitionProbability(ns, this.directionProbs[i]));
            } else {
                // this direction didn't lead anywhere new
                // if there are existing possible directions
                // that wouldn't lead anywhere, aggregate with them
                if (noChangeTransition != null) {
                    noChangeTransition.p += this.directionProbs[i];
                } else {
                    // otherwise create this new state and transition
                    noChangeTransition = new TransitionProbability(s.copy(),
                            this.directionProbs[i]);
                    tps.add(noChangeTransition);
                }
            }
        }

        return tps;
    }

    private int[] moveResult(int curX, int curY, int direction) {
        // first get change in x and y from direction
        int xdelta = 0;
        int ydelta = 0;

        switch (direction) {
            case NORTH:
                ydelta = 1;
                break;

            case SOUTH:
                ydelta = -1;
                break;

            case EAST:
                xdelta = 1;
                break;

            case WEST:
                xdelta = -1;
                break;
        }

        int nx = curX + xdelta;
        int ny = curY + ydelta;

        // make sure new position is valid (not a wall or out-of-bounds)
        if (nx < 0 || nx >= width || ny < 0 || ny >= height ||
                this.map[nx][ny] == WALL) {
            nx = curX;
            ny = curY;
        }

        return new int[]{nx, ny};
    }

}

