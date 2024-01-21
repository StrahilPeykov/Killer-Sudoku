package ypa.model;

/**
 * This class represents a group of neighboring cells around a specific cell.
 * It extends from AbstractGroup and is used to manage and interact with
 * neighboring cells.
 */
public class HNeighbour extends AbstractGroup {

    private final KCell cell; // The central cell around which neighbors are determined
    private final Location location; // Location of the central cell
    final int maxNum; // A parameter used for validation purposes

    /**
     * Constructs an HNeighbour instance with a specified cell, location, and
     * maximum number.
     * 
     * @param cell   The central cell for which neighbors are to be determined.
     * @param loc    The location of the cell.
     * @param maxNum The maximum number used in validation checks.
     */
    public HNeighbour(final KCell cell, final Location loc, final int maxNum,
            final int maxRow, final int maxCol) {
        this.cell = cell;
        this.location = cell.getLocation();
        this.maxNum = maxNum;

        int row = location.getRow();
        int column = location.getColumn();

        setNeighbours(row, column, maxRow + 1, maxCol);
        cell.add(this);

    }

    /**
     * Sets the neighboring cells of the central cell.
     * 
     * @param row      The row index of the central cell.
     * @param column   The column index of the central cell.
     * @param nRows    The total number of rows in the grid.
     * @param nColumns The total number of columns in the grid.
     */
    private void setNeighbours(int row, int column, int nRows, int nColumns) {
        // Define offsets in a two-dimensional array
        int[][] offsets = {
                { -1, -1 }, { -1, 0 }, { -1, 1 },
                { 0, -1 }, { 0, 1 },
                { 1, -1 }, { 1, 0 }, { 1, 1 }
        };

        for (int[] offset : offsets) {
            int newRow = row + offset[0];
            int newColumn = column + offset[1];

            // System.out.println(newRow + " -- " + newColumn);
            // Check bounds and, if applicable, whether the cell is blocked
            if (newRow >= 0 && newRow < nRows && newColumn >= 0 && newColumn < nColumns) {
                Location newLocation = new Location(newRow, newColumn);
                KCell newCell = cell.getGrid().getCell(newLocation);
                if (!newCell.isBlocked()) {
                    // System.out.println(row + " / " + column + " --- " + newCell.getLocation());
                    this.add(newCell);
                }
                // System.out.println(newCell.getLocation() + "*");
            }
        }
        // System.out.println("--------------------");
    }

    /**
     * Returns the central cell around which this group of neighbours is defined.
     */
    public KCell getCell() {
        return cell;
    }

    /**
     * Returns a string representation of the HNeighbour object.
     */
    @Override
    public String toString() {
        return "HNeighbour{" + "cell=" + cell + ", maxNum=" + maxNum + "}";
    }

    /**
     * Checks if the group of cells around the central cell satisfies all
     * constraints.
     */
    @Override
    public boolean isValid() {
        KGrid grid = this.cell.getGrid();

        if (isEmptyGrid(grid)) {
            return true;
        }

        if (isDuplicateInGrid(grid)) {
            return false;
        }

        return isNeighbouringCellsValid(grid);
    }

    /**
     * Validates whether neighboring cells comply with the puzzle rules.
     */
    private boolean isNeighbouringCellsValid(KGrid grid) {
        boolean hasSmaller = hasNeighboringCellWithState(this.cell.getState() - 1);
        boolean hasBigger = hasNeighboringCellWithState(this.cell.getState() + 1);

        if (!isCellValid(hasSmaller, this.cell.getState() - 1, grid)) {
            return false;
        }

        if (!isCellValid(hasBigger, this.cell.getState() + 1, grid) && this.cell.getState() != 0) {
            return false;
        }

        return true;
    }

    /**
     * Determines if a cell's state is valid based on neighboring cells and the
     * overall grid.
     */
    private boolean isCellValid(boolean hasNeighbor, int state, KGrid grid) {
        if (!hasNeighbor) {
            for (KCell cell : grid) {
                if (cell.getState() == state) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if there's a duplicate of the current cell's state in the grid.
     * 
     * @param grid The grid to check in.
     * @return true if a duplicate is found, false otherwise.
     */
    private boolean isDuplicateInGrid(KGrid grid) {
        for (KCell cell : grid) {
            if (cell.getState() > 0 && cell.getState() < 1000) {
                if (cell.getState() == this.cell.getState()
                        && !cell.getLocation().equals(this.cell.getLocation())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there's a neighboring cell with the specified state.
     * 
     * @param state The state to check for in neighboring cells.
     * @return true if such a neighboring cell is found, false otherwise.
     */
    private boolean hasNeighboringCellWithState(int state) {
        if (state > 0) {
            for (KCell cell : this) {
                if (cell.getState() > 0 && cell.getState() < 1000) {
                    if (cell.getState() == state) {
                        return true;
                    }
                }
            }
        } else {
            return true;
        }
        return false;
    }

    /**
     * Returns the info about the state of the grid.
     * 
     * @param grid The state to check for in neighboring cells.
     * @return true if empty, otherwise false.
     */
    private boolean isEmptyGrid(KGrid grid) {
        for (KCell cell : grid) {
            if (cell.getState() > 0) {
                // System.out.println(cell.getState() + " lok " + cell.getLocation());
                return false;
            }
        }
        return true;
    }

    /**
     * Prints grid.
     */
    private void printGrid(KGrid grid) {
        for (KCell cell : grid) {
            if (cell.getState() >= 0 && cell.getState() < 1000) {
                System.out.println(cell.getLocation() + " : " + cell.getState());
            }
        }
    }
}