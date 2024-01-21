package ypa.reasoning;

import org.junit.jupiter.api.Test;

import ypa.command.CompoundCommand;
import ypa.model.KPuzzle;
import ypa.reasoning.Reasoner;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link Reasoner}.
 *
 * @author wstomv
 */
public class ReasonerTest {

    public static final String PUZZLE = """
            a 0 3
            b 0 3
            c 0 3
            d 0 3
            """;

    /** Puzzle for testing. */
    private final KPuzzle puzzle;

    public ReasonerTest() {
        puzzle = new KPuzzle(new Scanner(PUZZLE), "Test");
    }

    /**
     * Test of apply method, of class Reasoner.
     */
    @Test
    public void testApply() {
        System.out.println("apply");
        Reasoner instance = new Reasoner(puzzle);
        CompoundCommand result = instance.apply();
        assertAll(
                () -> assertEquals(0, result.size(), "result.size()"),
                () -> assertTrue(result.isExecuted(), "result.executed"));
    }

}
