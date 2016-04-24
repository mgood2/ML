package ml.project4.grid;

/**
 * Exploring a small "Grid World".
 */
public class GridWorldProblem {

    /**
     * Main entry point.
     *
     * @param args (ignored)
     */
    public static void main(String[] args) {

        GridWorldMDP mdp = new GridWorldMDP();
        mdp.visualize();

        // action success probability
        mdp.runExperiments(0.8);
    }
}
