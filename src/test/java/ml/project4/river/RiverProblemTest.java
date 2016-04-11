package ml.project4.river;

import ml.project4.river.RiverProblem.ItemId;
import ml.project4.river.RiverProblem.Location;
import ml.project4.river.RiverProblem.LocationId;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static ml.project4.river.RiverProblem.ItemId.CHICKEN;
import static ml.project4.river.RiverProblem.ItemId.FARMER;
import static ml.project4.river.RiverProblem.LocationId.LEFT_BANK;
import static ml.project4.river.RiverProblem.LocationId.RIGHT_BANK;
import static ml.project4.river.RiverProblem.LocationId.RIVER;
import static org.junit.Assert.assertEquals;


/**
 * Unit tests for {@link RiverProblem}.
 */
public class RiverProblemTest extends AbstractTest {

    private RiverProblem rp;

    @Before
    public void setUp() {
        rp = new RiverProblem();
    }

    @Test
    public void basic() {
        title("basic");
        print(rp);
    }

    @Test
    public void lookups() {
        title("lookups");
        for (ItemId id : RiverProblem.THING_IDS) {
            assertEquals("wrong item", id, rp.get(id).id());
        }
        assertEquals("farmer is a thing", null, rp.get(FARMER));

        for (LocationId id : LocationId.values()) {
            assertEquals("wrong location", id, rp.get(id).id());
        }
    }

    private void checkDests(boolean lb, boolean riv, boolean rb) {
        print(rp.farmer());
        Set<Location> dests = rp.farmer().canMoveTo();
        assertEquals("lbank", lb, dests.contains(rp.get(LEFT_BANK)));
        assertEquals("river", riv, dests.contains(rp.get(RIVER)));
        assertEquals("rbank", rb, dests.contains(rp.get(RIGHT_BANK)));

    }

    @Test
    public void farmerMoves() {
        title("farmerMoves");
        checkDests(false, true, false);

        boolean ok = rp.farmer().take(CHICKEN).moveTo(RIVER);
        assertEquals("not ok", true, ok);
        checkDests(true, false, true);

        ok = rp.farmer().moveTo(RIGHT_BANK);
        assertEquals("not ok", true, ok);
        checkDests(false, true, false);
    }
}
