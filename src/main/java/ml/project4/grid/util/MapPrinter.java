package ml.project4.grid.util;

import burlap.behavior.policy.GreedyQPolicy;
import burlap.behavior.policy.Policy;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.core.values.Value;

import java.util.List;

import static ml.project4.grid.OutputUtils.EOL;
import static ml.project4.grid.OutputUtils.print;

/**
 * Utility class for printing grid world maps.
 */
public class MapPrinter {

    private static final String OPEN_B = "[";
    private static final String CLOSE_B = "]";
    private static final String COMMA = ",";

    public static String mapAsString(int[][] map) {
        StringBuilder sb = new StringBuilder();
        final int nCols = map[0].length;
        for (int[] mapRow : map) {
            sb.append(OPEN_B);
            for (int col = 0; col < nCols; col++) {
                sb.append(mapRow[col]).append(COMMA);
            }
            int len = sb.length();
            sb.replace(len - 1, len, CLOSE_B);
            sb.append(EOL);
        }
        return sb.toString();
    }

    public static void printMap(int[][] map) {
        print(mapAsString(map));
        print("\n\n");
    }

    public static void printPolicyMap(List<State> states, Policy p, int[][] matrix) {

        print("");
        print("This is your optimal policy:");
        String[][] policy = new String[matrix.length][matrix[0].length];
//        print("num of rows in policy is " + policy.length);


        for (State state : states) {
            ObjectInstance agent = state.getObject("agent0");
            List<Value> values = agent.getValues();
            Value xValue = values.get(0);
            int x = xValue.getDiscVal();
            Value yValue = values.get(1);
            int y = yValue.getDiscVal();

            String action;
            if (p instanceof GreedyQPolicy) {
                action = p.getAction(state).toString();
            } else {
                action = p.getAction(state).toString();
            }
            switch (action) {
                case "east":
                    action = ">";
                    break;
                case "north":
                    action = "^";
                    break;
                case "west":
                    action = "<";
                    break;
                case "south":
                    action = "v";
                    break;
                default:
                    action = "*";
            }
            policy[x][y] = action;

        }

        String[][] policySymbols = matrixToMap(policy);

        int numCols = policySymbols[0].length;

        StringBuffer sb = new StringBuffer();
        for (String[] policyRow : policySymbols) {
            sb.append(OPEN_B);
            for (int col = 0; col < numCols; col++) {
                String action = String.valueOf(policyRow[col]);
                if (action.equals("null")) {
                    action = "*";
                }
                sb.append(action).append(COMMA);
            }
            final int len = sb.length();
            sb.replace(len - 1, len, CLOSE_B);
            sb.append(EOL);
        }

        print(sb);

//        for (State state : states) {
//            print(state + ":" + p.getAction(state));
//        }

    }

    public static String[][] mapToMatrix(String[][] map) {
        // its rotated and inverted
        int numMapRows = map.length;
        int numMapCols = map[0].length;
        String[][] matrix = new String[numMapCols][numMapRows];
        for (int matrixRow = 0; matrixRow < numMapCols; matrixRow++) {
            for (int matrixCol = 0; matrixCol < numMapRows; matrixCol++) {
                matrix[matrixRow][matrixCol] = map[numMapRows - 1 - matrixCol][matrixRow];
            }
        }
        return matrix;
    }

    public static String[][] matrixToMap(String[][] matrix) {
        // its rotated and inverted
        final int numMatrixRows = matrix.length;
        final int numMatrixCols = matrix[0].length;
        final String[][] map = new String[numMatrixCols][numMatrixRows];
        for (int mapRow = 0; mapRow < numMatrixCols; mapRow++) {
            for (int mapCol = 0; mapCol < numMatrixRows; mapCol++) {
                map[mapRow][mapCol] = matrix[mapCol][numMatrixCols - 1 - mapRow];
            }
        }
        return map;
    }

    public static int[][] matrixToMap(int[][] matrix) {
        // its rotated and inverted
        final int numMatrixRows = matrix.length;
        final int numMatrixCols = matrix[0].length;
        final int[][] map = new int[numMatrixCols][numMatrixRows];
        for (int mapRow = 0; mapRow < numMatrixCols; mapRow++) {
            for (int mapCol = 0; mapCol < numMatrixRows; mapCol++) {
                map[mapRow][mapCol] = matrix[mapCol][numMatrixCols - 1 - mapRow];
            }
        }
        return map;
    }

    public static int[][] mapToMatrix(int[][] map) {
        // its rotated and inverted
        final int numMapRows = map.length;
        final int numMapCols = map[0].length;
        final int[][] matrix = new int[numMapCols][numMapRows];
        for (int matrixRow = 0; matrixRow < numMapCols; matrixRow++) {
            for (int matrixCol = 0; matrixCol < numMapRows; matrixCol++) {
                matrix[matrixRow][matrixCol] = map[numMapRows - 1 - matrixCol][matrixRow];
            }
        }
        return matrix;
    }

}
