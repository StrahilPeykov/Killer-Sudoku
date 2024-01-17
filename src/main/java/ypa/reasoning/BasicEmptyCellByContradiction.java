package ypa.reasoning;

import ypa.command.Command;
import ypa.command.CompoundCommand;
import ypa.command.SetCommand;
import ypa.model.HCell;
import ypa.model.HPuzzle;

/**
 * When only one way of filling an empty cell does not lead to an invalid state,
 * then that one way of filling is forced.
 *
 * This reasoner checks each empty cell and tries to fill it with every possible
 * number.
 * If only one number leads to a valid state, that number is chosen.
 */
public class BasicEmptyCellByContradiction extends EmptyCellReasoner {

    public BasicEmptyCellByContradiction(HPuzzle puzzle) {
        super(puzzle);
    }

    @Override
    CompoundCommand applyToCell(final HCell cell) throws NullPointerException {
        CompoundCommand result = super.applyToCell(cell);
        Command candidateForcedCommand = null; // command that worked, if any

        int minNumber = 1; // Implicit minimum number for Hidato is always 1
        for (int state = minNumber; state <= puzzle.getMaxNumber(); ++state) {
            Command command = new SetCommand(cell, state);
            command.execute();
            boolean valid = puzzle.isValid();
            command.revert();
            if (valid) {
                // no contradiction; command is a candidate
                if (candidateForcedCommand == null) {
                    // first command that is valid; memorize it
                    candidateForcedCommand = command;
                } else {
                    // multiple valid ways of filling cell; no forced command
                    return result;
                }
            }
        }
        // at most one command worked

        if (candidateForcedCommand == null) {
            // all commands failed: puzzle not solvable
            return null;
        } else {
            // exactly one command worked
            result.add(candidateForcedCommand);
            return result;
        }
    }
}