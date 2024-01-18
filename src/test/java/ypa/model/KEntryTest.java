package ypa.model;

import org.junit.jupiter.api.Test;

import ypa.model.Direction;
import ypa.model.HCell;
import ypa.model.HEntry;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@code KEntry}.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class KEntryTest {

    /**
     * Test of HEntry constructor and basic methods.
     */
    @Test
    public void testConstructor() {
        System.out.println("HEntry");
        Location location = new Location(1, 2);
        HEntry instance = new HEntry(location);
        assertEquals(location, instance.getLocation(), "Location should match");

        // Assuming HEntry uses a scanner to read location in a specific format
        Scanner scanner = new Scanner("1 2");
        HEntry instanceFromScanner = new HEntry(scanner);
        assertEquals(1, instanceFromScanner.getLocation().getRow(), "Row should be 1");
        assertEquals(2, instanceFromScanner.getLocation().getColumn(), "Column should be 2");
    }

    /**
     * Test of isValid method, of class HEntry.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        HEntry entry = new HEntry(new Location(0, 0));
        HCell cell1 = new HCell(1); // Assume 1 is the starting number
        HCell cell2 = new HCell(2); // Next number in the sequence
        entry.add(cell1);
        entry.add(cell2);
        assertTrue(entry.isValid(), "Entry should be valid with a correct sequence");
    }
}
