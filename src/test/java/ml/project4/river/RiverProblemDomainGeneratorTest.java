package ml.project4.river;

import burlap.oomdp.core.TerminalFunction;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.singleagent.RewardFunction;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link RiverProblemDomainGenerator}.
 */
public class RiverProblemDomainGeneratorTest {

    private class FakeState extends StateAdapter {
        final int id;

        FakeState(int id) {
            this.id = id;
        }

        @Override
        public ObjectInstance getFirstObjectOfClass(String s) {
            return new FakeObjectInstance(id);
        }
    }

    private class FakeObjectInstance extends ObjectInstanceAdapter {
        final int id;

        FakeObjectInstance(int id) {
            this.id = id;
        }

        @Override
        public int getIntValForAttribute(String s) {
            return id;
        }
    }

    private RiverProblemDomainGenerator riverDg;

    @Before
    public void before() {
        riverDg = new RiverProblemDomainGenerator();
    }

    private static final int[] EXP_REWARD = {
            0, -100, -1, -100, -1, -1, -1, -1,
            -1, -1, -1, -100, -1, -1, -100, -1,
            -1, -1, -1, -1, -1, -1, -1, 100
    };

    @Test
    public void rewardFunction() {
        RewardFunction rf = riverDg.generateRewardFunction();

        for (int id=0; id<EXP_REWARD.length; id++) {
            State s = new FakeState(id);
            int reward = (int) rf.reward(null, null, s);
            assertEquals("wrong reward", EXP_REWARD[id], reward);
        }
    }

    private static final List<Integer> TERM_IDS = Arrays.asList(1, 3, 11, 14, 23);


    @Test
    public void terminalFunction() {
        TerminalFunction tf = riverDg.generateTerminalFunction();

        for (int id=0; id<EXP_REWARD.length; id++) {
            boolean expectedTerminal = TERM_IDS.contains(id);
            boolean actual = tf.isTerminal(new FakeState(id));
            assertEquals("unexpected terminal result", expectedTerminal, actual);
        }
    }
}
