package ml.project4.augment;

import burlap.behavior.singleagent.planning.stochastic.valueiteration.ValueIteration;
import burlap.debugtools.DPrint;
import burlap.oomdp.core.Domain;
import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.singleagent.RewardFunction;
import burlap.oomdp.statehashing.HashableState;
import burlap.oomdp.statehashing.HashableStateFactory;

import java.util.Iterator;
import java.util.Set;

/**
 * Augments the BURLAP ValueIteration class to make the convergence count
 * (number of iterations) accessible.
 */
public class AugmentedValueIteration extends ValueIteration {

    private int iterations = 0;

    public AugmentedValueIteration(Domain domain, RewardFunction rf,
                                   TerminalFunction tf, double gamma,
                                   HashableStateFactory hashingFactory,
                                   double maxDelta, int maxIterations) {
        super(domain, rf, tf, gamma, hashingFactory, maxDelta, maxIterations);
    }

    @Override
    public void runVI() {
        if (!this.foundReachableStates) {
            throw new RuntimeException(
                    "Cannot run VI until the reachable states have been found. " +
                            "Use the planFromState or performReachabilityFrom " +
                            "method at least once before calling runVI.");
        } else {
            Set states = this.mapToStateIndex.keySet();

            for (iterations = 0; iterations < this.maxIterations; ++iterations) {
                double delta = 0.0D;

                double v;
                double maxQ;
                for (Iterator sIter = states.iterator(); sIter.hasNext();
                                delta = Math.max(Math.abs(maxQ - v), delta)) {
                    HashableState sh = (HashableState) sIter.next();
                    v = this.value(sh);
                    maxQ = this.performBellmanUpdateOn(sh);
                }

                if (delta < this.maxDelta) {
                    break;
                }
            }

            DPrint.cl(this.debugCode, "Passes: " + iterations);
            this.hasRunVI = true;
        }
    }

    /**
     * Returns the number of iterations to convergence.
     *
     * @return number of passes
     */
    public int getIterations() {
        return iterations;
    }
}
