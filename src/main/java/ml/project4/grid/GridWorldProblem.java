package ml.project4.grid;

/**
 * Exploring a small "Grid World".
 */
public class GridWorldProblem {

    /*
        Experiments were run with P = 0.99, 0.75, 0.50
     */
    private static final double ACTION_PROB_SUCCESS = 0.50;

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
