package ml.project4.augment;

import burlap.behavior.policy.GreedyQPolicy;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.environment.SimulatedEnvironment;
import burlap.oomdp.statehashing.HashableStateFactory;

/**
 * Augments the BURLAP QLearning class to make the convergence count
 * (number of planning iterations) accessible.
 */
public class AugmentedQLearning extends QLearning {

    private int iterations;

    public AugmentedQLearning(Domain domain, double gamma,
                              HashableStateFactory hashingFactory,
                              double qInit, double learningRate) {
        super(domain, gamma, hashingFactory, qInit, learningRate);
    }

    @Override
    public GreedyQPolicy planFromState(State initialState) {
        if (this.rf != null && this.tf != null) {
            SimulatedEnvironment env =
                    new SimulatedEnvironment(this.domain, this.rf, this.tf, initialState);
            int eCount = 0;

            do {
                this.runLearningEpisode(env, this.maxEpisodeSize);
                ++eCount;
            } while (eCount < this.numEpisodesForPlanning &&
                    this.maxQChangeInLastEpisode > this.maxQChangeForPlanningTermination);

            iterations = eCount;

            return new GreedyQPolicy(this);
        } else {
            throw new RuntimeException("QLearning (and its subclasses) cannot " +
                    "execute planFromState because the reward function and/or " +
                    "terminal function for planning have not been set. " +
                    "Use the initializeForPlanning method to set them.");
        }
    }

    /**
     * Returns number of iterations to convergence in the planning stage of
     * the learner.
     *
     * @return number of iterations
     */
    public int getIterations() {
        return iterations;
    }
}
