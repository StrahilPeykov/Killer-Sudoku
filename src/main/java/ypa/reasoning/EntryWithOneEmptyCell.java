package ypa.reasoning;

import ypa.command.Command;
import ypa.command.CompoundCommand;
import ypa.command.SetCommand;
import ypa.model.AbstractGroup;
import ypa.model.HCell;
import ypa.model.KEntry;
import ypa.model.HPuzzle;

/**
 * When all cells but one of an entry have been filled,
 * then the last empty cell remaining can be calculated.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class EntryWithOneEmptyCell extends EmptyCellReasoner {

    public EntryWithOneEmptyCell(HPuzzle puzzle) {
        super(puzzle);
    }

    @Override
    CompoundCommand applyToCell(HCell cell) throws NullPointerException {
        if (!cell.isEmpty()) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "applyToCell.pre failed: cell is not empty");
        }
        CompoundCommand result = super.applyToCell(cell);
        // Hidato-specific logic: find the next number to place
        int nextNumber = findNextNumberToPlace(cell);

        if (nextNumber != -1) {
            final Command command = new SetCommand(cell, nextNumber);
            command.execute();
            final boolean valid = puzzle.isValid();
            command.revert();
            if (valid) {
                result.add(command);
            }
        }

        return result;

    }

    private int findNextNumberToPlace(HCell cell) {
        // Implement logic to find the next number to place
        // For example, check adjacent cells for the current sequence and deduce the
        // next number
        return -1; // Placeholder return
    }

}
