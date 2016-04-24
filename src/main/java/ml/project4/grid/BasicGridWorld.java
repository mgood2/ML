package ml.project4.grid;

import burlap.oomdp.auxiliary.DomainGenerator;
import burlap.oomdp.core.Attribute;
import burlap.oomdp.core.Attribute.AttributeType;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.ObjectClass;
import burlap.oomdp.core.objects.MutableObjectInstance;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.MutableState;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.SADomain;
import burlap.oomdp.visualizer.StateRenderLayer;
import burlap.oomdp.visualizer.Visualizer;
import ml.project4.grid.util.AgentPainter;
import ml.project4.grid.util.AtLocation;
import ml.project4.grid.util.GoalPainter;
import ml.project4.grid.util.MapPrinter;
import ml.project4.grid.util.Movement;
import ml.project4.grid.util.TrapPainter;
import ml.project4.grid.util.WallPainter;

import static ml.project4.OutputUtils.EOL;

/**
 * Domain generator for our little grid world.
 */
public class BasicGridWorld implements DomainGenerator {

    private static final double DEFAULT_SUCCESS_PROB = 0.8;

    public static final String ATTX = "x";
    public static final String ATTY = "y";

    private static final String ZERO = "0";

    public static final String CLASS_AGENT = "agent";
    public static final String CLASS_GOAL = "location";
    private static final String CLASS_TRAP = "trap";

    static final String ACTION_NORTH = "north";
    static final String ACTION_SOUTH = "south";
    static final String ACTION_EAST = "east";
    static final String ACTION_WEST = "west";

    public static final String PFAT = "at";

    private static final int AGENT_START_X = 1;
    private static final int AGENT_START_Y = 0;

    static final int TRAP_X = 2;
    static final int TRAP_Y = 1;

    static final int GOAL_X = 3;
    static final int GOAL_Y = 3;



    // ordered so first dimension is x
    protected int[][] map;
    private int xMax;
    private int yMax;
    private double successProb;


    /**
     * Initializes the grid world.
     *
     * @param map map of world
     */
    BasicGridWorld(int[][] map) {
        this.map = map;
        this.xMax = map.length - 1;
        this.yMax = map[0].length - 1;
        successProb = DEFAULT_SUCCESS_PROB;
    }

    /**
     * Sets the movement success probability to use when generating domains.
     * (Default is {@value #DEFAULT_SUCCESS_PROB} if this method is never
     * called.)
     *
     * @param successProb movement success probability
     */
    void setProbSuccess(double successProb) {
        this.successProb = successProb;
    }

    /**
     * Returns a reference to our map.
     *
     * @return map
     */
    public int[][] getMap() {
        return map;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BasicGridWorld:").append(EOL)
                .append("Pr(succ) = ").append(successProb).append(EOL)
                .append(MapPrinter.mapAsString(MapPrinter.matrixToMap(map)));
        return sb.toString();
    }

    @Override
    public Domain generateDomain() {

        SADomain domain = new SADomain();

        Attribute xatt = new Attribute(domain, ATTX, AttributeType.INT);
        xatt.setLims(0, xMax);

        Attribute yatt = new Attribute(domain, ATTY, AttributeType.INT);
        yatt.setLims(0, yMax);

        addObjectClass(domain, CLASS_AGENT, xatt, yatt);
        addObjectClass(domain, CLASS_GOAL, xatt, yatt);
        addObjectClass(domain, CLASS_TRAP, xatt, yatt);

        new Movement(ACTION_NORTH, domain, Movement.NORTH, map, successProb);
        new Movement(ACTION_SOUTH, domain, Movement.SOUTH, map, successProb);
        new Movement(ACTION_EAST, domain, Movement.EAST, map, successProb);
        new Movement(ACTION_WEST, domain, Movement.WEST, map, successProb);

        new AtLocation(domain);

        return domain;
    }

    private void addObjectClass(Domain domain, String className, Attribute... attrs) {
        ObjectClass objClass = new ObjectClass(domain, className);
        for (Attribute attr: attrs) {
            objClass.addAttribute(attr);
        }
    }

    /**
     * Returns our initial state.
     *
     * @param domain the domain to attach the state to
     * @return the initial state
     */
    static State getInitialState(Domain domain) {
        State s = new MutableState();
        addObjectToState(domain, s, CLASS_AGENT, AGENT_START_X, AGENT_START_Y);
        addObjectToState(domain, s, CLASS_GOAL, GOAL_X, GOAL_Y);
        addObjectToState(domain, s, CLASS_TRAP, TRAP_X, TRAP_Y);
        return s;
    }

    private static void addObjectToState(Domain domain, State s, String oClass,
                                         int x, int y) {
        ObjectClass oc = domain.getObjectClass(oClass);
        ObjectInstance oi = new MutableObjectInstance(oc, oClass + ZERO);
        oi.setValue(ATTX, x);
        oi.setValue(ATTY, y);
        s.addObject(oi);
    }

    /**
     * Returns a state render layer that knows how to paint walls and objects.
     *
     * @return renderer
     */
    private StateRenderLayer getStateRenderLayer() {
        StateRenderLayer rl = new StateRenderLayer();
        rl.addStaticPainter(new WallPainter(map));
        rl.addObjectClassPainter(CLASS_GOAL, new GoalPainter(map));
        rl.addObjectClassPainter(CLASS_TRAP, new TrapPainter(map));
        rl.addObjectClassPainter(CLASS_AGENT, new AgentPainter(map));
        return rl;
    }

    /**
     * Returns a visualizer for a grid world.
     *
     * @return visualizer
     */
    public Visualizer getVisualizer() {
        return new Visualizer(this.getStateRenderLayer());
    }

}
