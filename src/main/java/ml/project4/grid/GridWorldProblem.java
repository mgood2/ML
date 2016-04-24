package ml.project4.grid;

/**
 * Exploring a small "Grid World".
 */
public class GridWorldProblem {

    private static final double ACTION_PROB_SUCCESS = 0.8;

    /**
     * Main entry point.
     *
     * @param args (ignored)
     */
    public static void main(String[] args) {
        GridWorldMDP mdp = new GridWorldMDP();
        mdp.visualize();
        mdp.runExperiments(ACTION_PROB_SUCCESS);
    }
}
