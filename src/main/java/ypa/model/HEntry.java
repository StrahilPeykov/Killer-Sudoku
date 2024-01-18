package ypa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Entry in a Kakuro puzzle, consisting of a location, direction,
 * a specification, and a group of cells.
 * The location, direction, and specification are immutable.
 * The group of cells is mutable, and set cell-by-cell during construction.
 *
 * An entry traverses two phases:
 *
 * <ol>
 * <li>Constructor sets location, direction, and specification.
 * <li>During puzzle set-up, cells are associated.
 * <li>During solving, all cells have been associated (see invariant Size),
 * and should not change any more.
 * </ol>
 *
 * @inv NoBlocked: {@code (\forall cell : this; ! cell.isBlocked)}
 *
 * @inv <br>
 *      Size: {@code this.getCount() == this.specification.getLength()}
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class HEntry extends AbstractGroup {

    /** The location. */
    private final Location location;

    /**
     * Constructs a {@code KEntry} from a given location, direction, and
     * specification.
     *
     * @param location      the given location
     */
    public HEntry(final Location location) {
        this.location = location;
    }

    /**
     * Constructs a {@code KEntry} from a given scanner.
     *
     * @param scanner the given scanner
     */
    public HEntry(final Scanner scanner) {
        this.location = new Location(scanner);
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Adds a given empty cell.
     *
     * @param cell the cell to add
     * @pre {@code cell.isEmpty()}
     */
    @Override
    public void add(final HCell cell) {
        if (!cell.isEmpty()) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + "add().pre failed: cell is not empty");
        }
        super.add(cell);
    }

    /*
     * @Override
     * public boolean isValid() {
     * for (HCell cell : this) {
     * // these cells are not blocked
     * if (!cell.isEmpty() && this.getStateCount(cell.getState()) > 1) {
     * // digit occurs more than once
     * return false;
     * }
     * }
     * final int total = this.getTotal();
     * final int emptyCount = this.getStateCount(HCell.EMPTY);
     * final int expectedSum = this.specification.getSum();
     * if (total + emptyCount > expectedSum) {
     * // sum of digits filled in is too high
     * // N.B. empty cells will contain at least a 1
     * return false;
     * }
     * return emptyCount != 0 || total == expectedSum;
     * }
     * 
     * @Override
     * public String toString() {
     * return String.format("%s %s %s", location, direction, specification);
     * }
     */

    @Override
    public boolean isValid() {
        // Assuming each HEntry represents a sequential group of numbers in the puzzle

        Integer previousNumber = null;

        for (HCell cell : this) { // Assuming this iterates over the cells in the entry
            if (!cell.isFilled()) {
                // If any cell in the group is empty, the group is not yet valid
                return false;
            }

            int currentNumber = cell.getState(); // Assuming 
            //the state represents the number in the cell

            if (previousNumber != null) {
                // Check if the current number is consecutive to the previous one
                if (currentNumber != previousNumber + 1) {
                    return false;
                }
            }

            previousNumber = currentNumber;
        }

        // Optionally, check if each cell is adjacent to the next one in the grid
        // This requires access to the grid or additional location logic

        return true;
    }

    /**
     * Returns a verbose string representation.
     *
     * @return verbose string representation of {@code this}
     */
    public String toStringLong() {
        return "{ location: " + location
                + " }";
    }

    /**
     * Reads a list of entries from a given scanner.
     *
     * @param scanner the given scanner
     * @return the scanned list of entries
     * @post white space has been skipped on scanner
     */
    static List<HEntry> scanEntries(final Scanner scanner) {
        List<HEntry> result = new ArrayList<>();
        Pattern original = scanner.delimiter();
        scanner.skip("\\p{javaWhitespace}*");
        scanner.useDelimiter("");
        while (scanner.hasNext("[a-zA-Z]")) {
            scanner.useDelimiter(original);
            try {
                result.add(new HEntry(scanner));
            } catch (Exception e) {
                throw new IllegalArgumentException(HEntry.class.getSimpleName()
                        + ".scanEntries(Scanner).pre failed: after " + result.size() + " entries");
            }
            scanner.skip("\\p{javaWhitespace}*");
            scanner.useDelimiter("");
        }
        scanner.useDelimiter(original);
        return result;
    }

    /**
     * Determine the size of the grid based on a list of {@code HEntry} items.
     * In Hidato, this might be as simple as finding the maximum row and column
     * index.
     *
     * @param entries the list of entries
     * @return the size of the grid as a Location, where row is the height and
     *         column is the width
     */
    public static Location dimensions(final List<HEntry> entries) {
        int maxRow = -1;
        int maxColumn = -1;
        for (HEntry entry : entries) {
            final int row = entry.getLocation().getRow();
            final int column = entry.getLocation().getColumn();

            if (row > maxRow) {
                maxRow = row;
            }
            if (column > maxColumn) {
                maxColumn = column;
            }
        }
        // Adjust for zero-based index if necessary
        return new Location(maxRow + 1, maxColumn + 1); // Assuming zero-based indexing
    }

}
