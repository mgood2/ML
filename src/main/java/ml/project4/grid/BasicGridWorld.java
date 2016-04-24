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
import ml.project4.grid.util.LocationPainter;
import ml.project4.grid.util.Movement;
import ml.project4.grid.util.WallPainter;

/**
 * Designates ...
 */
public class BasicGridWorld implements DomainGenerator {

    public static final String ATTX = "x";
    public static final String ATTY = "y";

    public static final String CLASSAGENT = "agent";
    public static final String CLASSLOCATION = "location";
    public static final String AGENT_0 = CLASSAGENT + "0";
    public static final String LOCATION_0 = CLASSLOCATION + "0";

    public static final String ACTIONNORTH = "north";
    public static final String ACTIONSOUTH = "south";
    public static final String ACTIONEAST = "east";
    public static final String ACTIONWEST = "west";

    public static final String PFAT = "at";

    public static final int AGENT_START_X = 1;
    public static final int AGENT_START_Y = 0;

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

        ObjectClass agentClass = new ObjectClass(domain, CLASSAGENT);
        agentClass.addAttribute(xatt);
        agentClass.addAttribute(yatt);

        ObjectClass locationClass = new ObjectClass(domain, CLASSLOCATION);
        locationClass.addAttribute(xatt);
        locationClass.addAttribute(yatt);

        new Movement(ACTIONNORTH, domain, 0, map);
        new Movement(ACTIONSOUTH, domain, 1, map);
        new Movement(ACTIONEAST, domain, 2, map);
        new Movement(ACTIONWEST, domain, 3, map);

        new AtLocation(domain);

        return domain;
    }

    public static State getInitialState(Domain domain) {
        State s = new MutableState();
        ObjectClass oClass = domain.getObjectClass(CLASSAGENT);
        ObjectInstance agent = new MutableObjectInstance(oClass, AGENT_0);
        agent.setValue(ATTX, AGENT_START_X);
        agent.setValue(ATTY, AGENT_START_Y);

        oClass = domain.getObjectClass(CLASSLOCATION);
        ObjectInstance location = new MutableObjectInstance(oClass, LOCATION_0);
        location.setValue(ATTX, GOAL_X);
        location.setValue(ATTY, GOAL_Y);

        s.addObject(agent);
        s.addObject(location);

        return s;
    }

    public StateRenderLayer getStateRenderLayer() {
        StateRenderLayer rl = new StateRenderLayer();
        rl.addStaticPainter(new WallPainter(map));
        rl.addObjectClassPainter(CLASSLOCATION, new LocationPainter(map));
        rl.addObjectClassPainter(CLASSAGENT, new AgentPainter(map));

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
