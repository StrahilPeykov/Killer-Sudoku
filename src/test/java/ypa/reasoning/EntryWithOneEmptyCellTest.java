package ypa.reasoning;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ypa.command.CompoundCommand;
import ypa.model.KCell;
import ypa.model.KPuzzle;
import ypa.reasoning.EntryWithOneEmptyCell;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link EntryWithOneEmptyCell}.
 *
 * @author wstomv
 */
public class EntryWithOneEmptyCellTest {

    private KPuzzle puzzle;

    // /**
    // * Prepares each test case.
    // */
    // private final KPuzzle initialPuzzleState = new KPuzzle(new Scanner("""
    // a 0 3
    // b 0 3
    // c 0 3
    // d 0 3
    // =
    // a 0 = 1
    // c 0 = 2
    // """), "Test");
    //
    // /**
    // * Test of applyToCell method, of class EntryWithOneEmptyCell.
    // */
    // @Test
    // public void testApplyToCell() {
    // System.out.println("applyToCell");
    // KCell cell11 = initialPuzzleState.getCell(1, 1);
    // cell11.setState(1);
    // KCell cell12 = initialPuzzleState.getCell(1, 2);
    // EntryWithOneEmptyCell instance = new
    // EntryWithOneEmptyCell(initialPuzzleState);
    // System.out.println(initialPuzzleState.gridAsString());
    // CompoundCommand result = instance.applyToCell(cell12);
    // System.out.println(initialPuzzleState.gridAsString());
    // assertAll(
    // () -> assertEquals(1, result.size(), "result.size()"),
    // () -> assertFalse(result.isExecuted(), "result.executed"),
    // () -> assertEquals(1, cell11.getState(), "cell 1, 1 state"),
    // () -> assertEquals(KCell.EMPTY, cell12.getState(), "cell 1, 2 state")
    // );
    // }
    //
    // /**
    // * Test of apply method, of class EntryWithOneEmptyCell.
    // */
    // @Test
    // public void testApply() {
    // System.out.println("apply");
    // KCell cell11 = initialPuzzleState.getCell(1, 1);
    // cell11.setState(1);
    // KCell cell12 = initialPuzzleState.getCell(1, 2);
    // EntryWithOneEmptyCell instance = new
    // EntryWithOneEmptyCell(initialPuzzleState);
    // System.out.println(initialPuzzleState.gridAsString());
    // CompoundCommand result = instance.apply();
    // System.out.println(initialPuzzleState.gridAsString());
    // assertAll(
    // () -> assertEquals(1, result.size(), "result.size()"),
    // () -> assertTrue(result.isExecuted(), "result.executed"),
    // () -> assertEquals(1, cell11.getState(), "cell 1, 1 state"),
    // () -> assertEquals(2, cell12.getState(), "new cell 1, 2 state")
    // );
    // }

}
