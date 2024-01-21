package ypa.model;

import org.junit.jupiter.api.Test;

import ypa.model.Direction;
import ypa.model.KCell;
import ypa.model.KEntry;

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
     * Tests scanEntries.
     */
    @Test
    public void testScanEntries() {
        System.out.println("scanEntries, plain");
        String entry0 = "a 2 3";
        String entry1 = "b 1 2";
        String expResult = entry0 + "\n" + entry1 + "\n";
        List<KEntry> result = KEntry.scanEntries(new Scanner(expResult));
        assertAll(
                () -> assertEquals(2, result.size(), "size"),
                () -> assertEquals(entry0, result.get(0).toString(), "get(0)"),
                () -> assertEquals(entry1, result.get(1).toString(), "get(1)"));
    }

    /**
     * Tests scanEntries.
     */
    @Test
    public void testScanEntries2() {
        System.out.println("scanEntries, with extra line");
        String entry0 = "a 2 3";
        String entry1 = "b 1 2";
        String expResult = entry0 + "\n" + entry1 + "\n=\n";
        Scanner scanner = new Scanner(expResult);
        List<KEntry> result = KEntry.scanEntries(scanner);
        assertAll(
                () -> assertEquals(2, result.size(), "size"),
                () -> assertEquals(entry0, result.get(0).toString(), "get(0)"),
                () -> assertEquals(entry1, result.get(1).toString(), "get(1)"),
                () -> assertTrue(scanner.hasNext("="), "next"));
    }

    /**
     * Test isValid(). more test in HNeighbourTest s.
     */
    @Test
    public void testIsValidAllEmpty() {
        System.out.println("isValid, empty cells");
        String entry = "a 2 3";
        Scanner scanner = new Scanner(entry);
        KEntry instance = new KEntry(scanner);
        KCell[] cells = new KCell[] {
                new KCell(KCell.EMPTY),
                new KCell(KCell.EMPTY),
                new KCell(KCell.EMPTY)
        };
        for (KCell cell : cells) {
            instance.add(cell);
            cell.add(instance);
        }

        System.out.println("asdasd");

        assertTrue(instance.isValid(), "isValid, all 3 empty");
        cells[0].setState(1);
        assertTrue(instance.isValid(), "isValid, 1 can is valid");
        cells[1].setState(2);
        assertTrue(instance.isValid(), "isValid, 2 can be beside the one");
    }

}
