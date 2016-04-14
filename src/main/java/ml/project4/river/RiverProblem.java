package ml.project4.river;

/**
 * Exploring the "River Problem".
 */
public class RiverProblem {

    /**
     * Runs the problem.
     */
    private RiverProblem() {
        new RiverMDP().solve();
    }

    /**
     * Main entry point.
     *
     * @param args (ignored)
     */
    public static void main(String[] args) {
        new RiverProblem();
    }
}
