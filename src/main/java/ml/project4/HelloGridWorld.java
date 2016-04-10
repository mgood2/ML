package ml.project4;

import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.domain.singleagent.gridworld.GridWorldVisualizer;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.explorer.VisualExplorer;
import burlap.oomdp.visualizer.Visualizer;

/**
 * Grid world with Burlap.
 */
public class HelloGridWorld {

    public static void main(String[] args) {

        // create a grid world domain
        GridWorldDomain gw = new GridWorldDomain(11, 11);
        gw.setMapToFourRooms();
        gw.setProbSucceedTransitionDynamics(0.8);
        Domain domain = gw.generateDomain();

        // set up initial state
        State s = GridWorldDomain.getOneAgentOneLocationState(domain);
        GridWorldDomain.setAgent(s, 0, 0);
        GridWorldDomain.setLocation(s, 0, 10, 10);

        // create visualizer and explorer
        Visualizer v = GridWorldVisualizer.getVisualizer(gw.getMap());
        VisualExplorer exp = new VisualExplorer(domain, v, s);

        // set control keys to use w, a, s, d
        exp.addKeyAction("w", GridWorldDomain.ACTIONNORTH);
        exp.addKeyAction("s", GridWorldDomain.ACTIONSOUTH);
        exp.addKeyAction("a", GridWorldDomain.ACTIONWEST);
        exp.addKeyAction("d", GridWorldDomain.ACTIONEAST);

        exp.initGUI();
    }
}
