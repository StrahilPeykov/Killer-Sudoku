package ypa.model;

import org.junit.jupiter.api.Test;

import ypa.model.HCell;
import ypa.model.HGrid;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Smoke tests for class {@code KGrid}.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class HGridTest {

    /**
     * Tests the HGrid constructor and basic methods.
     */
    @Test
    public void testHGridConstructor() {
        System.out.println("HGrid constructor, Hidato puzzle");
        String input = "3 3\n1 . .\n. X .\n. . 2\n"; // Example Hidato puzzle layout
        HGrid grid = new HGrid(new Scanner(input));

        assertAll(
            () -> assertEquals(3, grid.getRowCount(), "Row count should be 3"),
            () -> assertEquals(3, grid.getColumnCount(), "Column count should be 3"),
            () -> assertEquals(HCell.BLOCKED, grid.getCell(1, 
                    1).getState(), "Middle cell should be blocked"),
            () -> assertEquals(1, grid.getCell(0, 0).getState(), "Top-left cell should contain 1"),
            () -> assertEquals(2, grid.getCell(2, 
                    2).getState(), "Bottom-right cell should contain 2"),
            () -> assertTrue(grid.isValid(), "Grid should be a valid Hidato configuration")
        );
    }
}
