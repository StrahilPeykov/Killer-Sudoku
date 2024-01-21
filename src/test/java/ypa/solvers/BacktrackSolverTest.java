package ypa.solvers;

import ypa.model.KPuzzle;
import ypa.reasoning.BasicEmptyCellByContradiction;
import ypa.reasoning.FixpointReasoner;
import ypa.reasoning.Reasoner;
import ypa.solvers.BacktrackSolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BacktrackSolver class.
 */
public class BacktrackSolverTest {

    private KPuzzle puzzle;

    /**
     * Sets up the testing environment before each test.
     * Initializes a puzzle with a predefined state for testing.
     */
    @BeforeEach
    public void setUp() {
        String puzzleState = """
                a 0 3
                b 0 3
                c 0 3
                """;
        puzzle = new KPuzzle(new Scanner(puzzleState), "Test");
    }

    /**
     * Tests solving a puzzle without using any reasoning strategy.
     */
    @Test
    public void testSolveWithoutReasoner() {
        testSolverWithReasoner(null, 9, "solve w/o reasoner");
    }

    /**
     * Tests solving a puzzle using the BasicEmptyCellByContradiction reasoning
     * strategy.
     */
    @Test
    public void testSolveWithBasicEmptyCellByContradiction() {
        Reasoner reasoner = new BasicEmptyCellByContradiction(puzzle);
        testSolverWithReasoner(reasoner, 15, "solve with "
                + "BasicEmptyCellByContradiction reasoner");
    }

    /**
     * Tests solving a puzzle using both BasicEmptyCellByContradiction
     * and FixpointReasoner strategies.
     */
    @Test
    public void testSolveWithBasicEmptyCellByContradictionAndFixpointReasoner() {
        Reasoner reasonerBasic = new BasicEmptyCellByContradiction(puzzle);
        FixpointReasoner reasoner = new FixpointReasoner(puzzle, reasonerBasic);
        testSolverWithReasoner(reasoner, 13, "solve with "
                + "BasicEmptyCellByContradiction and FixpointReasoner");
    }

    private void testSolverWithReasoner(Reasoner reasoner, int expectedCommandSize,
            String message) {
        System.out.println(message);
        BacktrackSolver solver = new BacktrackSolver(puzzle, reasoner);
        boolean expResult = true;
        System.out.println(puzzle.gridAsString());
        boolean result = solver.solve();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(expResult, result, "return value"),
                () -> assertTrue(puzzle.isSolved(), "puzzle solved"),
                () -> assertEquals(expectedCommandSize,
                        solver.getCommands().size(), "commands size"));
    }
}
