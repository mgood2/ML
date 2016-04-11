package ml.project4.river;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A model of the River Problem.
 * <p>
 * A farmer has to cross a (shallow) river. He has with him a fox, a chicken,
 * and a sack of grain. The problem is, he can only carry one item at a time.
 * He cannot leave the fox and chicken alone together because the fox will eat
 * the chicken. He cannot leave the chicken and grain alone together because
 * the chicken will eat the grain. How can the farmer get all three safely
 * across the river?
 */
public class RiverProblem {

    private static final String EOL = String.format("%n");
    private static final String COLON = ": ";


    enum LocationId {LEFT_BANK, RIVER, RIGHT_BANK}

    enum ItemId {FARMER, FOX, CHICKEN, SACK_OF_GRAIN}

    public static final ItemId[] THING_IDS = {
            ItemId.FOX, ItemId.CHICKEN, ItemId.SACK_OF_GRAIN
    };

    /**
     * Represents the locations in the problem.
     */
    public class Location {
        private final LocationId id;
        private final Set<Item> contents = new HashSet<>();

        /**
         * Constructs the location using the given identifier.
         *
         * @param id location identifier
         */
        Location(LocationId id) {
            this.id = id;
        }

        /**
         * Returns the location identifier.
         *
         * @return the location identifier
         */
        public LocationId id() {
            return id;
        }

        @Override
        public String toString() {
            return String.format("%12s: ", id) + contents;
        }

        /**
         * Places the given item in this location, also setting the
         * reverse reference (i.e. telling the item that its new location
         * is here.
         *
         * @param item the item to add to this location
         */
        public void add(Item item) {
            item.location = this;
            contents.add(item);
        }

        /**
         * Adds all the given items to this location.
         *
         * @param items items to add to this location
         */
        public void addAll(Item... items) {
            for (Item item : items) {
                add(item);
            }
        }

        /**
         * Removes the specified item from this location.
         *
         * @param item the item to remove
         */
        public void remove(Item item) {
            contents.remove(item);
        }
    }

    /**
     * Superclass for farmers and things.
     */
    public abstract class Item {
        private final ItemId id;

        Location location;

        Item(ItemId id) {
            this.id = id;
        }

        public ItemId id() {
            return id;
        }

        @Override
        public String toString() {
            return id.toString();
        }

    }

    /**
     * Represents our farmer.
     */
    public class Farmer extends Item {

        private Thing carrying = null;

        Farmer() {
            super(ItemId.FARMER);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("FARMER");
            if (carrying != null) {
                sb.append(" carrying ").append(carrying);
            }
            sb.append(" <").append(location.id()).append(">");
            return sb.toString();
        }

        /**
         * Chaining method to define the thing the farmer is taking with him.
         *
         * @param thing the thing identifier (fox, chicken, sack of grain)
         * @return self, for chaining
         */
        public Farmer take(ItemId thing) {
            carrying = thingMap.get(thing);
            return this;
        }

        public boolean moveTo(LocationId destination) {
            Location dest = locMap.get(destination);
            if (canMoveTo().contains(dest)) {
                Location oldLoc = location;
                oldLoc.remove(this);
                dest.add(this);
                return true;
            }
            return false;
        }

        /**
         * Returns the locations to which the farmer can move from current
         * location.
         *
         * @return set of destinations
         */
        public Set<Location> canMoveTo() {
            Set<Location> destinations = new HashSet<>();
            if (location == leftBank || location == rightBank) {
                destinations.add(river);
            } else {
                destinations.add(leftBank);
                destinations.add(rightBank);
            }
            return destinations;
        }
    }

    /**
     * Represents something that the farmer can carry.
     */
    public class Thing extends Item {
        Thing(ItemId id) {
            super(id);
        }
    }


    // ========

    private final Map<LocationId, Location> locMap = new HashMap<>();
    private final Map<ItemId, Thing> thingMap = new HashMap<>();

    final Location leftBank = new Location(LocationId.LEFT_BANK);
    final Location river = new Location(LocationId.RIVER);
    final Location rightBank = new Location(LocationId.RIGHT_BANK);

    final Farmer farmer = new Farmer();
    final Thing fox = new Thing(ItemId.FOX);
    final Thing chicken = new Thing(ItemId.CHICKEN);
    final Thing sackOfGrain = new Thing(ItemId.SACK_OF_GRAIN);

    RiverProblem() {
        // initialize lookups
        locMap.put(LocationId.LEFT_BANK, leftBank);
        locMap.put(LocationId.RIVER, river);
        locMap.put(LocationId.RIGHT_BANK, rightBank);

        // the farmer is not a thing
        thingMap.put(ItemId.FOX, fox);
        thingMap.put(ItemId.CHICKEN, chicken);
        thingMap.put(ItemId.SACK_OF_GRAIN, sackOfGrain);

        // start everyone on the left bank of the river
        leftBank.addAll(farmer, fox, chicken, sackOfGrain);
    }

    /**
     * Returns the location with the given identifier.
     *
     * @param id item identifier
     * @return the item
     */
    public Location get(LocationId id) {
        return locMap.get(id);
    }

    /**
     * Returns the thing with the given identifier.
     *
     * @param id item identifier
     * @return the item
     */
    public Thing get(ItemId id) {
        return thingMap.get(id);
    }

    /**
     * Returns a reference to the farmer.
     *
     * @return the farmer
     */
    public Farmer farmer() {
        return farmer;
    }

    @Override
    public String toString() {
        return "River Problem" + EOL +
                leftBank + EOL +
                river + EOL +
                rightBank + EOL;
    }
}
