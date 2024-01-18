package ypa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ypa.model.Histogram;
import ypa.model.HCell;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@code Histogram}.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class HistogramTest {

    private Histogram instance;
    // private final int maxNumber = 9; // Assuming a max number for the puzzle

    @BeforeEach
    public void setUp() {
        instance = new Histogram();
    }

    private void checkSUT(final int[] expected, int maxState) {
        for (int state = HCell.BLOCKED; state <= maxState; ++state) {
            int result = instance.get(state);
            assertEquals(expected[state], result, "Count for state " + state);
        }
    }

    @Test
    public void testConstructor() {
        System.out.println("Histogram()");
        // Check only for BLOCKED and EMPTY states initially
        int[] expected = { 0, 0 }; // Counts for BLOCKED and EMPTY
        checkSUT(expected, HCell.EMPTY);
    }

    @Test
    public void testAdjust() {
        System.out.println("adjust");
        // Assume adjusting states from BLOCKED to 2 (for example)
        int[] expected = { 1, 2, 1 }; // Adjusted counts for BLOCKED, EMPTY, and 1
        instance.adjust(HCell.BLOCKED, 1);
        instance.adjust(HCell.EMPTY, 2);
        instance.adjust(1, 1); // Adjust state 1
        checkSUT(expected, 1); // Check up to state 1
    }

}
