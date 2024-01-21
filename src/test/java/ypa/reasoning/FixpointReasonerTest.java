package ypa.reasoning;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ypa.command.CompoundCommand;
import ypa.model.KCell;
import ypa.model.KPuzzle;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

/**
 * Test cases for {@link FixpointReasoner}.
 * These tests validate the behavior of the FixpointReasoner with various puzzle
 * states,
 * ensuring correct application of the reasoning strategy.
 */
public class FixpointReasonerTest {

    private KPuzzle initialPuzzleState;

    /**
     * Sets up the test puzzle.
     */
    @BeforeEach
    void setUp() {
        initialPuzzleState = new KPuzzle(new Scanner("""
                        a 0 3
                        b 0 3
                        c 0 3
                        =
                        a 0 = 1
                        a 2 = 3
                        b 0 = 6
                        b 1 = 5
                        b 2 = 4
                        """), "Test");
    }

    /**
     * Tests the apply method with an initial empty state.
     * Verifies the result size, execution status, and puzzle solution state.
     */
    @Test
    public void testApplyEmpty() {
        Reasoner reasoner = new BasicEmptyCellByContradiction(initialPuzzleState);
        FixpointReasoner instance = new FixpointReasoner(initialPuzzleState, reasoner);
        CompoundCommand result = instance.apply();
        assertAll(
            () -> assertEquals(1, result.size(), "Result size should be 1"),
            () -> assertTrue(result.isExecuted(), "Result should be executed"),
            () -> assertFalse(initialPuzzleState.isSolved(), "Puzzle should not be solved")
        );
    }

    /**
     * Tests the apply method on a puzzle that is already solved.
     * Checks for the result size and execution status.
     */
    @Test
    public void testApplySolved() {
        KCell cell = initialPuzzleState.getCell(0, 1);
        cell.setState(2);
        Reasoner reasoner = new BasicEmptyCellByContradiction(initialPuzzleState);
        FixpointReasoner instance = new FixpointReasoner(initialPuzzleState, reasoner);
        CompoundCommand result = instance.apply();
        assertAll(
            () -> assertEquals(0, result.size(), "Result size should be 0"),
            () -> assertTrue(result.isExecuted(), "Result should be executed"),
            () -> assertFalse(initialPuzzleState.isSolved(), "Puzzle should not be solved")
        );
    }

    /**
     * Tests the apply method on a puzzle that becomes unsolvable.
     * Verifies the result size, puzzle solution state, and state count.
     */
    @Test
    public void testApplyUnsolvable1() {
        KCell cell = initialPuzzleState.getCell(2, 1);
        cell.setState(8);
        Reasoner reasoner = new BasicEmptyCellByContradiction(initialPuzzleState);
        FixpointReasoner instance = new FixpointReasoner(initialPuzzleState, reasoner);
        CompoundCommand result = instance.apply();
        assertAll(
            () -> assertEquals(3, result.size(), "Result size should be 3"),
            () -> assertTrue(initialPuzzleState.isSolved(), "Puzzle should be solved"),
            () -> assertEquals(0, initialPuzzleState.getStateCount(
                    KCell.EMPTY), "No empty cells should remain")
        );
    }

    /**
     * Tests the apply method on a puzzle that is unsolvable from the start.
     * Expects a null result and verifies that the puzzle is not solved.
     */
    @Test
    public void testApplyUnsolvable2() {
        KCell cell = initialPuzzleState.getCell(0, 1);
        cell.setState(1);
        Reasoner reasoner = new BasicEmptyCellByContradiction(initialPuzzleState);
        FixpointReasoner instance = new FixpointReasoner(initialPuzzleState, reasoner);
        CompoundCommand result = instance.apply();
        assertAll(
            () -> assertNull(result, "Result should be null"),
            () -> assertFalse(initialPuzzleState.isSolved(), "Puzzle should not be solved")
        );
    }
}
