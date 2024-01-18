package ypa.model;

import java.util.HashMap;

/**
 * A histogram of cell states, counting how often each state occurs in a group.
 *
 * @inv For each possible cell state, a count is maintained
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class Histogram extends HashMap<Integer, Integer> {

    /** Constructs an empty histogram. */
    public Histogram() {
        super();
        // Initialize counts for blocked and empty cells
        put(HCell.BLOCKED, 0);
        put(HCell.EMPTY, 0);
        // Initialize counts for numbered cells if needed
        // For example, for numbers 1 to N, where N is the maximum number in the puzzle

    }

    /**
     * Returns the occurrence count for a given cell state.
     *
     * @param key the given cell state
     * @return how often {@code key} occurs
     */
    @Override
    public Integer get(Object key) {
        return super.getOrDefault(key, 0);
    }

    /**
     * Adjusts the count for a given state.
     *
     * @param state state whose count changes
     * @param delta the amount of change
     */
    public void adjust(final int state, final int delta) {
        this.put(state, get(state) + delta);
    }

}
