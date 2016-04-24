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
import ml.project4.grid.util.Movement;
import ml.project4.grid.util.TrapPainter;
import ml.project4.grid.util.WallPainter;

/**
 * Designates ...
 */
public class BasicGridWorld implements DomainGenerator {

    public static final String ATTX = "x";
    public static final String ATTY = "y";

    public static final String ZERO = "0";

    public static final String CLASS_AGENT = "agent";
    public static final String CLASS_GOAL = "location";
    public static final String CLASS_TRAP = "trap";


    public static final String ACTION_NORTH = "north";
    public static final String ACTION_SOUTH = "south";
    public static final String ACTION_EAST = "east";
    public static final String ACTION_WEST = "west";


    public static final String PFAT = "at";

    public static final int AGENT_START_X = 1;
    public static final int AGENT_START_Y = 0;

    public static final int TRAP_X = 2;
    public static final int TRAP_Y = 1;

    public static final int GOAL_X = 3;
    public static final int GOAL_Y = 3;



    // ordered so first dimension is x
    protected int[][] map;
    protected int xMax;
    protected int yMax;

    /**
     * Initializes the grid world.
     *
     * @param map map of world
     */
    public BasicGridWorld(int[][] map) {
        this.map = map;
        this.xMax = map.length - 1;
        this.yMax = map[0].length - 1;
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

        new Movement(ACTION_NORTH, domain, Movement.NORTH, map);
        new Movement(ACTION_SOUTH, domain, Movement.SOUTH, map);
        new Movement(ACTION_EAST, domain, Movement.EAST, map);
        new Movement(ACTION_WEST, domain, Movement.WEST, map);

        new AtLocation(domain);

        return domain;
    }

    private void addObjectClass(Domain domain, String className, Attribute... attrs) {
        ObjectClass objClass = new ObjectClass(domain, className);
        for (Attribute attr: attrs) {
            objClass.addAttribute(attr);
        }
    }

    public static State getInitialState(Domain domain) {
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

    public StateRenderLayer getStateRenderLayer() {
        StateRenderLayer rl = new StateRenderLayer();
        rl.addStaticPainter(new WallPainter(map));
        rl.addObjectClassPainter(CLASS_GOAL, new GoalPainter(map));
        rl.addObjectClassPainter(CLASS_TRAP, new TrapPainter(map));
        rl.addObjectClassPainter(CLASS_AGENT, new AgentPainter(map));
        return rl;
    }

    public Visualizer getVisualizer() {
        return new Visualizer(this.getStateRenderLayer());
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

}
