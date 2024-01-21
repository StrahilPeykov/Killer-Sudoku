package ypa.model;

import org.junit.jupiter.api.Test;
import ypa.model.Direction;
import ypa.model.KCell;
import ypa.model.KEntry;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Javadoc describing the method below.
 */
public class HNeighbourTest {

    /**
     * Test of isValid method for HNeighbour, ensuring proper neighbor cell
     * calculation.
     */
    @Test
    public void testGetNeighboringCells() {
        System.out.println("isValid, empty cells");
        // Initialize a KGrid instance using a scanner with specified input
        String expResult = """
                           a 0 3
                           b 0 3
                           c 0 3
                           d 0 3
                           """;
        Scanner scanner = new Scanner(expResult);
        KGrid grid = new KGrid(scanner);

        // Test 1: With an empty grid, isValid should return true
        assertEquals(true, grid.isValid(), "Expected true for an empty grid");

        // Test 2: Assign a cell state and test validity. It should
        // still be valid as only one cell is set
        grid.getCell(new Location(1, 0)).setState(2);
        assertEquals(true, grid.isValid(), "Expected true with only one cell set");

        // Test 2b: Assign a same cell state and test validity. It should
        // be false as all the states have to be original
        grid.getCell(new Location(2, 0)).setState(2);
        assertEquals(false, grid.isValid(), "Expected false with non-unique cell states");
        grid.getCell(new Location(2, 0)).setState(0); // reset the state

        // Test 3: Assign a non-neighboring cell, breaking the
        // validity rule (non-neighboring sequential cells)
        grid.getCell(new Location(1, 2)).setState(1);
        assertEquals(false, grid.isValid(), 
                "Expected false due to non-neighboring sequential cells");

        // Test 4: Reset the grid and assign sequential cells correctly,
        // checking for valid configurations
        grid.getCell(new Location(1, 2)).setState(0); // Resetting the grid
        grid.getCell(new Location(1, 0)).setState(1); // Assigning sequential cells
        grid.getCell(new Location(1, 1)).setState(2);
        grid.getCell(new Location(1, 2)).setState(3);
        grid.getCell(new Location(2, 1)).setState(4);
        assertEquals(true, grid.isValid(), 
                "Expected true for a valid sequential cell configuration");

        // Test 5: Break the sequential cell neighborhood by moving '4' away from '3'
        // Unsetting the previous position of '4'
        grid.getCell(new Location(2, 1)).setState(0);
        // Assigning '4' to a non-neighboring location
        grid.getCell(new Location(3, 1)).setState(4);
        assertEquals(false, grid.isValid(), 
                "Expected false due to '3' and '4' not being neighbors");
    }
}
