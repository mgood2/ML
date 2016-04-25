package ml.project4.river;

/**
 * Exploring the "River Problem".
 */
public class RiverProblem {

    /**
     * Main entry point.
     *
     * @param args (ignored)
     */
    public static void main(String[] args) {
        RiverMDP mdp = new RiverMDP();
        mdp.runExperiments();
    }
}
