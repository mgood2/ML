package ml.project4.grid.util;

import java.util.ArrayList;
import java.util.List;

import static ml.project4.grid.OutputUtils.print;

/**
 * Aggregates results from analysis runner.
 */
public final class AnalysisAggregator {
    private static List<Integer> numIterations = new ArrayList<>();

    private static List<Integer> convergenceValueIteration = new ArrayList<>();
    private static List<Integer> convergencePolicyIteration = new ArrayList<>();
    private static List<Integer> convergenceQLearning = new ArrayList<>();

    private static List<Integer> stepsToFinishValueIteration = new ArrayList<>();
    private static List<Integer> stepsToFinishPolicyIteration = new ArrayList<>();
    private static List<Integer> stepsToFinishQLearning = new ArrayList<>();

    private static List<Integer> millisecondsToFinishValueIteration = new ArrayList<>();
    private static List<Integer> millisecondsToFinishPolicyIteration = new ArrayList<>();
    private static List<Integer> millisecondsToFinishQLearning = new ArrayList<>();

    private static List<Double> rewardsForValueIteration = new ArrayList<>();
    private static List<Double> rewardsForPolicyIteration = new ArrayList<>();
    private static List<Double> rewardsForQLearning = new ArrayList<>();

    static void addNumberOfIterations(int nIter) {
        numIterations.add(nIter);
    }

    static void addConvergenceValueIteration(int passes) {
        convergenceValueIteration.add(passes);
    }

    static void addConvergencePolicyIteration(int passes) {
        convergencePolicyIteration.add(passes);
    }

    static void addConvergenceQLearning(int passes) {
        convergenceQLearning.add(passes);
    }

    static void addStepsToFinishValueIteration(int steps) {
        stepsToFinishValueIteration.add(steps);
    }

    static void addStepsToFinishPolicyIteration(int steps) {
        stepsToFinishPolicyIteration.add(steps);
    }

    static void addStepsToFinishQLearning(int steps) {
        stepsToFinishQLearning.add(steps);
    }

    static void printValueIterationPasses() {
        System.out.print("Value Iteration,");
        printList(convergenceValueIteration);
    }

    static void printPolicyIterationPasses() {
        System.out.print("Policy Iteration,");
        printList(convergencePolicyIteration);
    }

    static void printQLearningPasses() {
        System.out.print("Q Learning,");
        printList(convergenceQLearning);
    }

    static void printValueIterationResults() {
        System.out.print("Value Iteration,");
        printList(stepsToFinishValueIteration);
    }

    static void printPolicyIterationResults() {
        System.out.print("Policy Iteration,");
        printList(stepsToFinishPolicyIteration);
    }

    static void printQLearningResults() {
        System.out.print("Q Learning,");
        printList(stepsToFinishQLearning);
    }


    static void addMillisecondsToFinishValueIteration(Integer millisecondsToFinishValueIteration1) {
        millisecondsToFinishValueIteration.add(millisecondsToFinishValueIteration1);
    }

    static void addMillisecondsToFinishPolicyIteration(Integer millisecondsToFinishPolicyIteration1) {
        millisecondsToFinishPolicyIteration.add(millisecondsToFinishPolicyIteration1);
    }

    static void addMillisecondsToFinishQLearning(Integer millisecondsToFinishQLearning1) {
        millisecondsToFinishQLearning.add(millisecondsToFinishQLearning1);
    }

    static void addValueIterationReward(double reward) {
        rewardsForValueIteration.add(reward);
    }

    static void addPolicyIterationReward(double reward) {
        rewardsForPolicyIteration.add(reward);
    }

    static void addQLearningReward(double reward) {
        rewardsForQLearning.add(reward);
    }

    private static void printValueIterationTimeResults() {
        System.out.print("Value Iteration,");
        printList(millisecondsToFinishValueIteration);
    }

    private static void printPolicyIterationTimeResults() {
        System.out.print("Policy Iteration,");
        printList(millisecondsToFinishPolicyIteration);
    }

    private static void printQLearningTimeResults() {
        System.out.print("Q Learning,");
        printList(millisecondsToFinishQLearning);
    }

    private static void printValueIterationRewards() {
        System.out.print("Value Iteration Rewards,");
        printDoubleList(rewardsForValueIteration);
    }

    private static void printPolicyIterationRewards() {
        System.out.print("Policy Iteration Rewards,");
        printDoubleList(rewardsForPolicyIteration);
    }

    private static void printQLearningRewards() {
        System.out.print("Q Learning Rewards,");
        printDoubleList(rewardsForQLearning);
    }


    private static void printNumIterations() {
        System.out.print("Iterations,");
        printList(numIterations);
    }

    private static void printList(List<Integer> valueList) {
        int counter = 0;
        for (int value : valueList) {
            System.out.print(String.valueOf(value));
            if (counter != valueList.size() - 1) {
                System.out.print(",");
            }
            counter++;
        }
        print("");
    }

    private static void printDoubleList(List<Double> valueList) {
        int counter = 0;
        for (double value : valueList) {
            System.out.print(String.valueOf(value));
            if (counter != valueList.size() - 1) {
                System.out.print(",");
            }
            counter++;
        }
        print("");
    }

    public static void printAggregateAnalysis() {
        print("//Aggregate Analysis//\n");

        print("The data below shows the number of passes the agent required to converge \n"
                + "on the optimal policy");
        printNumIterations();
        printValueIterationPasses();
        printPolicyIterationPasses();
        printQLearningPasses();
        print("");

        print("The data below shows the number of steps/actions the agent required to reach \n"
                + "the terminal state given the number of iterations the algorithm was run.");
        printNumIterations();
        printValueIterationResults();
        printPolicyIterationResults();
        printQLearningResults();
        print("");

        print("The data below shows the number of milliseconds the algorithm required to generate \n"
                + "the optimal policy given the number of iterations the algorithm was run.");
        printNumIterations();
        printValueIterationTimeResults();
        printPolicyIterationTimeResults();
        printQLearningTimeResults();
        print("");

        print("The data below shows the total reward gained for \n"
                + "the optimal policy given the number of iterations the algorithm was run.");
        printNumIterations();
        printValueIterationRewards();
        printPolicyIterationRewards();
        printQLearningRewards();
        print("");
    }
}
