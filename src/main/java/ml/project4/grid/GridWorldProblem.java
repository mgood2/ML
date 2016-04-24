package ml.project4.grid;

/**
 * Exploring a small "Grid World".
 */
public class GridWorldProblem {

    // utility print methods
    private static void print(String s) {
        System.out.println(s);
    }

    private static void print(Object o) {
        print(o.toString());
    }

    private static void print(String fmt, Object... params) {
        print(String.format(fmt, params));
    }


    /**
     * Main entry point.
     *
     * @param args (ignored)
     */
    public static void main(String[] args) {

        GridWorldMDP mdp = new GridWorldMDP();

        print(mdp);

        mdp.visualize();

    }
}
