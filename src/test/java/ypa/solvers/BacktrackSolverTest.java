package ypa.solvers;

import ypa.model.HPuzzle;
import ypa.reasoning.EntryWithOneEmptyCell;
import ypa.reasoning.FixpointReasoner;
import ypa.reasoning.Reasoner;
import ypa.reasoning.ReasonerTest;
import ypa.solvers.BacktrackSolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link BacktrackSolver}.
 *
 * @author wstomv
 */
public class BacktrackSolverTest {

    private HPuzzle puzzle;

    @BeforeEach
    public void setUp() {
        puzzle = new HPuzzle(new Scanner(ReasonerTest.PUZZLE), "Test");
    }

    /**
     * Test of solve method, of class BacktrackSolver.
     */
    @Test
    public void testSolveWithoutReasoner() {
        System.out.println("solve w/o reasoner");
        BacktrackSolver instance = new BacktrackSolver(puzzle, null);
        boolean expResult = true;
        System.out.println(puzzle.gridAsString());
        boolean result = instance.solve();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(expResult, result, "return value"),
                () -> assertTrue(puzzle.isSolved(), "puzzle solved"),
                () -> assertEquals(4, instance.getCommands().size(), "commands size"));
    }

    /**
     * Test of solve method, of class BacktrackSolver.
     */
    @Test
    public void testSolveWithSimpleReasoner() {
        System.out.println("solve with simple reasoner");
        Reasoner reasoner = new EntryWithOneEmptyCell(puzzle);
        BacktrackSolver instance = new BacktrackSolver(puzzle, reasoner);
        boolean expResult = true;
        System.out.println(puzzle.gridAsString());
        boolean result = instance.solve();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(expResult, result, "return value"),
                () -> assertTrue(puzzle.isSolved(), "puzzle solved"),
                () -> assertEquals(5, instance.getCommands().size(), "commands size"));
    }

    /**
     * Test of solve method, of class BacktrackSolver.
     */
    @Test
    public void testSolveWithFixpointReasoner() {
        System.out.println("solve with fixpoint");
        Reasoner reasoner = new EntryWithOneEmptyCell(puzzle);
        reasoner = new FixpointReasoner(puzzle, reasoner);
        BacktrackSolver instance = new BacktrackSolver(puzzle, reasoner);
        boolean expResult = true;
        System.out.println(puzzle.gridAsString());
        boolean result = instance.solve();
        System.out.println(puzzle.gridAsString());
        assertAll(
                () -> assertEquals(expResult, result, "return value"),
                () -> assertTrue(puzzle.isSolved(), "puzzle solved"),
                () -> assertEquals(3, instance.getCommands().size(), "commands size"));
    }

}
