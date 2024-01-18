package ypa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A grid cell for a Kakuro puzzle.
 * Every group that contains this cell is treated as a listener for state
 * changes of this cell.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class HCell {

    /** The blocked state. */
    public static final int BLOCKED = -1;

    /** The empty state. */
    public static final int EMPTY = 0;

    /** String for blocked state. */
    public static final String BLOCKED_STR = "\\";

    /** String for empty state. */
    public static final String EMPTY_STR = ".";

    /** The cell's state. */
    private int state;

    /** Location in the grid, if any. */
    private Location location;

    /** The grid to which this cell belongs, if any. */
    private HGrid grid;

    /**
     * Constructs a cell with a given state.
     *
     * @param state state
     * @pre {@code state} is a valid state
     */
    public HCell(final int state) {
        if (state < BLOCKED) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + "(" + state + ").pre failed");
        }
        this.state = state;
        this.grid = null;
        this.location = null;
    }

    /**
     * Constructs a cell with a state given by a string.
     *
     * @param state state
     */
    public HCell(final String state) {
        this(fromString(state));
    }

    /**
     * Constructs a cell from a given scanner.
     *
     * @param scanner the given scanner
     */
    public HCell(final Scanner scanner) {
        this(scanner.next());
    }

    public int getState() {
        return state;
    }

    /**
     * Sets a new cell state.
     *
     * @param state the new state
     * @pre {@code state} is valid
     */
    public void setState(int state) {
        if (state < BLOCKED) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + ".setState().pre failed: Invalid state " + state);
        }
        this.state = state;
    }

    /**
     * Returns whether cell is blocked.
     *
     * @return whether {@code this} is blocked
     */
    public boolean isBlocked() {
        return state == BLOCKED;
    }

    /**
     * Returns whether cell is empty.
     *
     * @return whether {@code this} is empty
     */
    public boolean isEmpty() {
        return state == EMPTY;
    }

    /**
     * Returns whether cell is filled.
     *
     * @return whether {@code this} is filled
     */
    public boolean isFilled() {
        return state > EMPTY;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public HGrid getGrid() {
        return grid;
    }

    public void setGrid(HGrid grid) {
        this.grid = grid;
    }

    /**
     * Converts string to cell state.
     *
     * @param s string to convert
     * @return cell state corresponding to s
     * @throws IllegalArgumentException if invalid string
     */
    public static int fromString(final String s) {
        switch (s) {
            case BLOCKED_STR -> {
                return BLOCKED;
            }
            case EMPTY_STR -> {
                return EMPTY;
            }
            default -> {
                try {
                    int result = Integer.parseInt(s);
                    if (result <= EMPTY) {
                        throw new IllegalArgumentException(HCell.class.getSimpleName()
                                + ".fromString(" + s + ").pre failed");
                    }
                    return result;
                } catch (Exception e) {
                    throw new IllegalArgumentException(HCell.class.getSimpleName()
                            + ".fromString(" + s + ").pre failed");
                }
            }
        }
    }

    @Override
    public String toString() {
        switch (state) {
            case BLOCKED:
                return BLOCKED_STR;
            case EMPTY:
                return EMPTY_STR;
            default:
                return String.valueOf(state);
        }
    }
}
