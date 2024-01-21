package ypa.reasoning;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ypa.command.CompoundCommand;
import ypa.model.KPuzzle;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import ypa.model.KCell;

/**
 * Test cases for {@link EmptyCellReasoner}.
 *
 * This class tests the functionality of the BasicEmptyCellByContradiction
 * class,
 * which is a specific implementation of EmptyCellReasoner. The tests cover
 * various puzzle states to check the behavior of the apply method.
 */
public class EmptyCellReasonerTest {

    private final KPuzzle initialPuzzleState = new KPuzzle(new Scanner("""
            a 0 3
            b 0 3
            c 0 3
            d 0 3
            =
            a 0 = 1
            c 0 = 2
            """), "Test");

    /**
     * Test of apply method in BasicEmptyCellByContradiction class.
     * Tests different puzzle states to check the behavior of apply method.
     */
    @Test
    public void testApplyMethodInBasicEmptyCellByContradiction() {
        System.out.println("Testing BasicEmptyCellByContradiction.apply() method");

        // Testing apply method with the initial puzzle state
        EmptyCellReasoner instance = new BasicEmptyCellByContradiction(initialPuzzleState);
        CompoundCommand result = instance.apply();
        assertNull(result, "Result should be null, meaning no solution possible in initial state");

        // Modifying puzzle state and testing again
        initialPuzzleState.getCell(3, 0).setState(0);
        initialPuzzleState.getCell(1, 2).setState(3);
        result = instance.apply();
        assertNull(result, "There should be no "
                + "commands, since no unique solution is possible");

        // Further modifying puzzle state and testing
        initialPuzzleState.getCell(2, 2).setState(4);
        initialPuzzleState.getCell(2, 1).setState(5);
        initialPuzzleState.getCell(2, 0).setState(6);
        result = instance.apply();
        assertTrue(result.isExecuted(), "Command should be executed, "
                + "as a unique solution is possible in cell a1");
    }

    /**
     * Test of apply method in BasicEmptyCellByContradiction class with a more
     * complex puzzle state.
     */
    @Test
    public void testApplyWithComplexPuzzleState() {
        KPuzzle complexPuzzleState = new KPuzzle(new Scanner("""
                a 0 3
                b 0 3
                c 0 3
                =
                a 0 = 1
                a 2 = 3
                b 2 = 4
                b 1 = 5
                b 0 = 6
                """), "Test");

        EmptyCellReasoner instance = new BasicEmptyCellByContradiction(complexPuzzleState);
        System.out.println("------------");
        for (KCell cell : complexPuzzleState.getCells()) {
            System.out.println(cell.getLocation() + " " + cell.getState());
        }

        CompoundCommand result1 = instance.applyToCell(complexPuzzleState.getCell(0, 1));
        // Only one unique solution, that being 2
        assertEquals(1, result1.size(), "There should be one "
                + "command, as a unique solution is possible");

        CompoundCommand result2 = instance.applyToCell(complexPuzzleState.getCell(2, 0));
        System.out.println(result2);
        assertEquals(0, result2.size(), "There should be one "
                + "command, as a unique solution is possible");

        complexPuzzleState.getCell(2, 2).setState(7); // making the puzzle invalid
        for (KCell cell : complexPuzzleState.getCells()) {
            if (cell.getState() == 0) {
                CompoundCommand result3 = instance.applyToCell(complexPuzzleState.getCell(
                        cell.getLocation().getRow(), cell.getLocation().getColumn()));
                assertNull(result3, "All empty cells should return a null.");
            }
        }
    }

    private static class EmptyCellReasonerImpl extends EmptyCellReasoner {
        public EmptyCellReasonerImpl(KPuzzle puzzle) {
            super(puzzle);
        }
    }
}
