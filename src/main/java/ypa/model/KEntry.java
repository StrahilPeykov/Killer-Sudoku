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
public class KEntry extends AbstractGroup {

    /** The location. */
    private final Location location;

    /** The specification. */
    private final KSpec specification;

    /**
     * Constructs a {@code KEntry} from a given location, direction, and
     * specification.
     *
     * @param location      the given location
     * @param specification the given specification
     */
    public KEntry(final Location location, final KSpec specification) {
        this.location = location;
        this.specification = specification;
    }

    /**
     * Constructs a {@code KEntry} from a given scanner.
     *
     * @param scanner the given scanner
     */
    public KEntry(final Scanner scanner) {
        this.location = new Location(scanner);
        this.specification = new KSpec(scanner);
    }

    public Location getLocation() {
        return location;
    }

    public KSpec getSpecification() {
        return specification;
    }

    /**
     * Adds a given empty cell.
     *
     * @param cell the cell to add
     * @pre {@code cell.isEmpty()}
     */
    @Override
    public void add(final KCell cell) {
        if (!cell.isEmpty()) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + "add().pre failed: cell is not empty");
        }
        super.add(cell);
    }

    @Override
    public boolean isValid() {
        int sum = 0;
        for (KCell cell : this) {
            if (!cell.isEmpty()) {
                sum += cell.getState();
            }
            // these cells are not blocked
            if (!cell.isEmpty() && this.getStateCount(cell.getState()) > 1) {
                // digit occurs more than once
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s %s", location, specification);
    }

    /**
     * Returns a verbose string representation.
     *
     * @return verbose string representation of {@code this}
     */
    public String toStringLong() {
        return "{ location: " + location
                + "specification" + specification
                + " }";
    }

    /**
     * Reads a list of entries from a given scanner.
     *
     * @param scanner the given scanner
     * @return the scanned list of entries
     * @post white space has been skipped on scanner
     */
    static List<KEntry> scanEntries(final Scanner scanner) {
        List<KEntry> result = new ArrayList<>();
        Pattern original = scanner.delimiter();
        scanner.skip("\\p{javaWhitespace}*");
        scanner.useDelimiter("");
        while (scanner.hasNext("[a-zA-Z]")) {
            scanner.useDelimiter(original);
            try {
                result.add(new KEntry(scanner));
            } catch (Exception e) {
                throw new IllegalArgumentException(KEntry.class.getSimpleName()
                        + ".scanEntries(Scanner).pre failed: after " + result.size() + " entries");
            }
            scanner.skip("\\p{javaWhitespace}*");
            scanner.useDelimiter("");
        }
        scanner.useDelimiter(original);
        return result;
    }

    /**
     * Determine minimum number of rows and columns of a list of
     * {@code KEntry} items.
     *
     * @param entries the list
     * @return {@code \result.getRow() == number of rows} and
     *         {@code \result.getColumn() == number of columns}
     */
    public static Location dimensions(final List<KEntry> entries) {
        int maxRow = -1; // largest row coordinate encountered
        int maxColumn = -1; // largest column coordinate encountered
        for (KEntry entry : entries) {
            final int row = entry.getLocation().getRow();
            final int column = entry.getLocation().getColumn();
            final int length = entry.getSpecification().getLength();
            final int columnEnd = column + length;
            // Might need to change this to be more accurate
            if (row > maxRow) {
                maxRow = row;
            }
            if (columnEnd > maxColumn) {
                maxColumn = columnEnd;
            }

        }
        return new Location(maxRow, maxColumn);
    }

}
