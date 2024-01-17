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
public class KGridTest {

    /**
     * Tests constructor.
     */
    @Test
    public void testKGrid() {
        System.out.println("KGrid constructor, plain");
        String expResult = """
                a 2 -  9 3
                b 1 | 17 2
                """;
        String expMatrix = """
                 \\ \\ \\ \\ \\
                 \\ \\ . . .
                 \\ . \\ \\ \\
                 \\ . \\ \\ \\
                """;
        final HGrid instance;
        instance = new HGrid(new Scanner(expResult));
        System.out.println(instance);
        System.out.println(instance.gridAsString());
        assertAll(
                () -> assertEquals(4, instance.getRowCount(), "getRowCount"),
                () -> assertEquals(5, instance.getColumnCount(), "getColumnCount"),
                //() -> assertEquals(expResult, instance.entriesAsString(), "entriesAsString"),
                () -> assertEquals(expMatrix, instance.gridAsString(), "matrixAsString"),
                () -> assertEquals(15, instance.getStateCount(HCell.BLOCKED), "# blocked cells"),
                () -> assertEquals(5, instance.getStateCount(HCell.EMPTY), "# empty cells"),
                () -> assertFalse(instance.isFull(), "isFull"),
                () -> assertTrue(instance.isValid(), "isValid")
        );
    }

    /**
     * Tests constructor with initialization of non-blocked cells.
     */
    @Test
    public void testKGrid2() {
        System.out.println("KGrid constructor, with extra initialized cell");
        String expResult = """
                a 2 -  9 3
                b 1 | 17 2
                =
                a 3 = 1
                c 1 = 9
                """;
        String expMatrix = """
                 \\ \\ \\ \\ \\
                 \\ \\ . 1 .
                 \\ . \\ \\ \\
                 \\ 9 \\ \\ \\
                """;
        final HGrid instance;
        instance = new HGrid(new Scanner(expResult));
        System.out.println(instance);
        assertAll(
                () -> assertEquals(4, instance.getRowCount(), "getRowCount"),
                () -> assertEquals(5, instance.getColumnCount(), "getColumnCount"),
                () -> assertEquals(expResult, instance.toString(), "toString"),
                () -> assertEquals(expMatrix, instance.gridAsString(), "matrixAsString"),
                () -> assertEquals(15, instance.getStateCount(HCell.BLOCKED), "# blocked cells"),
                () -> assertEquals(3, instance.getStateCount(HCell.EMPTY), "# empty cells"),
                () -> assertEquals(1, instance.getStateCount(9), "# 9 cells"),
                () -> assertFalse(instance.isFull(), "isFull"),
                () -> assertTrue(instance.isValid(), "isValid")
        );
    }

}
