package ml.project4.augment;

import burlap.behavior.policy.GreedyQPolicy;
import burlap.behavior.singleagent.planning.stochastic.policyiteration.PolicyIteration;
import burlap.debugtools.DPrint;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.statehashing.HashableStateFactory;

/**
 * Augments the BURLAP PolicyIteration class to make the convergence count
 * (number of policy iterations) accessible.
 */
public class AugmentedPolicyIteration extends PolicyIteration {

    private int iterations = 0;

    public AugmentedPolicyIteration(Domain domain, RewardFunction rf,
                                    TerminalFunction tf, double gamma,
                                    HashableStateFactory hashingFactory,
                                    double maxDelta, int maxEvaluationIterations,
                                    int maxPolicyIterations) {
        super(domain, rf, tf, gamma, hashingFactory, maxDelta,
                maxEvaluationIterations, maxPolicyIterations);
    }

    @Override
    public GreedyQPolicy planFromState(State initialState) {
        this.initializeOptionsForExpectationComputations();
        if (this.performReachabilityFrom(initialState) || !this.hasRunPlanning) {
            while (true) {
                double delta = this.evaluatePolicy();
                ++iterations;
                this.evaluativePolicy = new GreedyQPolicy(this.getCopyOfValueFunction());
                if (delta <= this.maxPIDelta || iterations >= this.maxPolicyIterations) {
                    this.hasRunPlanning = true;
                    break;
                }
            }
        }

        DPrint.cl(this.debugCode, "Total policy iterations: " + iterations);
        this.totalPolicyIterations += iterations;
        return (GreedyQPolicy) this.evaluativePolicy;
    }

    /**
     * Returns the number of policy iterations to convergence.
     *
     * @return number of iterations
     */
    public int getIterations() {
        return iterations;
    }
}
