package ypa.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;

/**
 * A rectangular grid of cells for a Kakuro puzzle,
 * with the list of hints. A hint consists of a number
 * that is the desired sum of the numbers appearing in
 * a group of horizontally or vertically adjacent cells.
 * Such a group is called an <em>entry</em>.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class HGrid extends AbstractGroup implements Iterable<HCell> {

    /** The grid of cells. */
    private final List<List<HCell>> grid;

    /** Number of rows. */
    private final int nRows;

    /** Number of columns. */
    private final int nColumns;

    private int maxNumber;

    // TODO: Consider merging nRows, nColumns into EnumMap<Direction, Integer>

    /**
     * Constructs a grid for a Hidato puzzle from a given scanner.
     * The input format is expected to define the grid layout.
     *
     * @param scanner the given scanner
     * @throws NullPointerException     if {@code scanner == null}
     * @throws IllegalArgumentException if {@code scanner} does not yield a valid
     *                                  Hidato puzzle grid
     */
    public HGrid(final Scanner scanner) {
        if (scanner == null) {
            throw new NullPointerException("Scanner cannot be null");
        }

        // Read the dimensions of the grid
        nRows = scanner.nextInt();
        nColumns = scanner.nextInt();
        scanner.nextLine(); // Move to the next line after reading integers

        maxNumber = calculateMaxNumber();

        grid = new ArrayList<>(nRows);

        for (int rowIndex = 0; rowIndex < nRows; rowIndex++) {
            List<HCell> row = new ArrayList<>(nColumns);
            String[] cellStates = scanner.nextLine().trim().split(" ");
            for (String state : cellStates) {
                HCell cell;
                if (state.equals("X")) { // Assuming 'X' represents a blocked cell
                    cell = new HCell(HCell.BLOCKED);
                } else if (state.equals(".")) { // Assuming '.' represents an empty cell
                    cell = new HCell(HCell.EMPTY);
                } else {
                    cell = new HCell(Integer.parseInt(state)); // Assuming other values are numbers
                }
                row.add(cell);
            }
            grid.add(row);
        }
    }

    /**
     * Gets the number of columns in this grid.
     * 
     * @return number of columns
     */
    public int getColumnCount() {
        return nColumns;
    }

    /**
     * Gets the number of rows in this grid.
     * 
     * @return number of rows
     */
    public int getRowCount() {
        return nRows;
    }

    /**
     * Gets the cell at given coordinates.
     * 
     * @param rowIndex    the row coordinate to get from
     * @param columnIndex the column coordinate to get from
     * @return cell at {@code rowIndex, columnIndex}
     * @pre {@code 0 <= rowIndex < getRowCount() && 0 <= columnIndex < getColumnCount()}
     */
    public HCell getCell(final int rowIndex, final int columnIndex) {
        if (rowIndex < 0 || rowIndex >= nRows || columnIndex < 0 || columnIndex >= nColumns) {
            throw new IndexOutOfBoundsException("Invalid cell coordinates.");
        }
        return grid.get(rowIndex).get(columnIndex);
    }

    /**
     * Checks whether this grid is full (no more empty cells).
     * 
     * @return whether this grid is full
     */
    public boolean isFull() {
        for (List<HCell> row : grid) {
            for (HCell cell : row) {
                if (cell.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether this grid is valid for a Hidato puzzle.
     * 
     * @return whether this grid is a valid Hidato configuration
     */
    @Override
    public boolean isValid() {
        // Step 1: Find the starting cell (cell with number 1)
        HCell startCell = findStartCell();
        if (startCell == null) {
            return false; // No starting cell, puzzle is invalid
        }

        // Step 2: Traverse the grid from the starting cell, checking for a contiguous
        // sequence
        return isContiguousSequence(startCell, 1);
    }

    /**
     * Finds the starting cell of the Hidato puzzle (cell with number 1).
     * 
     * @return the starting cell, or null if not found
     */
    private HCell findStartCell() {
        for (List<HCell> row : grid) {
            for (HCell cell : row) {
                if (cell.getState() == 1) {
                    return cell;
                }
            }
        }
        return null;
    }

    /**
     * Checks if the numbers form a contiguous sequence from a given cell.
     * 
     * @param cell           the current cell
     * @param expectedNumber the expected number for this cell
     * @return true if the sequence is contiguous, false otherwise
     */
    private boolean isContiguousSequence(HCell cell, int expectedNumber) {
        if (cell == null || cell.isBlocked() || cell.isEmpty()) {
            return false;
        }

        if (cell.getState() != expectedNumber) {
            return false;
        }

        if (expectedNumber == calculateMaxNumber()) {
            return true; // Reached the end of the sequence
        }

        // Check adjacent cells for the next number in the sequence
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0)
                    continue; // Skip the current cell
                int nextRow = cell.getLocation().getRow() + dRow;
                int nextCol = cell.getLocation().getColumn() + dCol;
                if (isValidCoordinate(nextRow, nextCol)) {
                    HCell nextCell = getCell(nextRow, nextCol);
                    if (isContiguousSequence(nextCell, expectedNumber + 1)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checks if the given row and column coordinates are within the grid
     * boundaries.
     * 
     * @param row the row index
     * @param col the column index
     * @return true if the coordinates are valid, false otherwise
     */
    private boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < nRows && col >= 0 && col < nColumns;
    }

    /**
     * Clears the non-blocked cells.
     */
    public void clear() {
        for (List<HCell> row : grid) {
            for (HCell cell : row) {
                if (!cell.isBlocked()) {
                    cell.setState(HCell.EMPTY);
                }
            }
        }
    }

    /**
     * Converts the grid of cell states to a string in 2D layout.
     * 
     * @return string representation of the grid
     */
    public String gridAsString() {
        StringBuilder result = new StringBuilder();
        for (List<HCell> row : grid) {
            for (HCell cell : row) {
                result.append(cell).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public String toString() {
        // Provides a string representation of the entire grid
        return gridAsString();
    }

    /**
     * Calculates the maximum number present in the Hidato grid.
     * This number represents the highest number in the sequence that needs to be
     * achieved to solve the puzzle.
     * 
     * @return The maximum number in the grid.
     */
    private int calculateMaxNumber() {
        int maxNum = 0;
        for (List<HCell> row : grid) {
            for (HCell cell : row) {
                if (!cell.isBlocked() && cell.getState() > maxNum) {
                    maxNum = cell.getState();
                }
            }
        }
        return maxNum;
    }

}
