package ypa.reasoning;

import ypa.command.Command;
import ypa.command.CompoundCommand;
import ypa.command.SetCommand;
import ypa.model.KCell;
import ypa.model.KPuzzle;

/**
 * Reasoner that fills an empty cell if only one filling option doesn't lead to
 * an invalid state.
 */
public class BasicEmptyCellByContradiction extends EmptyCellReasoner {

    public BasicEmptyCellByContradiction(KPuzzle puzzle) {
        super(puzzle);
    }

    @Override
    CompoundCommand applyToCell(final KCell cell) throws NullPointerException {
        CompoundCommand result = super.applyToCell(cell);
        // Holds the command that successfully avoids contradictions
        Command candidateForcedCommand = null;
        // Flag to indicate if the reasoner can help in this situation
        boolean cantHelp = false;

        // Iterate over all possible states for the cell
        for (int state = puzzle.getMinNumber(); state <= puzzle.getMaxNumber(); ++state) {
            Command command = new SetCommand(cell, state);
            command.execute(); // Temporarily set the cell to this state
            boolean valid = puzzle.isValid(); // Check if the puzzle remains valid
            command.revert(); // Revert the cell to its original state

            if (valid) {
                cantHelp = true;
                boolean candidate = isUniqueCandidate(cell, state);

                // If this state is a unique valid candidate, store the command
                if (candidate) {
                    if (candidateForcedCommand == null) {
                        candidateForcedCommand = command;
                    } else {
                        return result; // If more than one valid state, return current result
                    }
                }
            }
        }

        // Determine the final command to return
        if (candidateForcedCommand == null) {
            // Return null if no solution found and reasoner can't help
            return cantHelp ? result : null;
        } else {
            result.add(candidateForcedCommand);
            return result; // Return result with the forced command added
        }
    }

    /**
     * Determines if the given state for the cell is a unique valid candidate.
     */
    private boolean isUniqueCandidate(final KCell cell, int state) {
        for (KCell emptyCell : puzzle.getCells()) {
            if (emptyCell.isEmpty() && !emptyCell.isBlocked()
                    && !emptyCell.getLocation().equals(cell.getLocation())) {
                Command tempCommand = new SetCommand(emptyCell, state);
                tempCommand.execute();
                boolean tempValid = puzzle.isValid();
                tempCommand.revert();

                if (tempValid) {
                    return false; // Another valid cell found, so not a unique candidate
                }
            }
        }
        return true; // No other valid cell found, so it is a unique candidate
    }

}
