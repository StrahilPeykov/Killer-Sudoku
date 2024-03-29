package ypa.reasoning;

import ypa.command.Command;
import ypa.command.CompoundCommand;
import ypa.command.SetCommand;
import ypa.model.AbstractGroup;
import ypa.model.KCell;
import ypa.model.KEntry;
import ypa.model.KPuzzle;

/**
 * When all cells but one of an entry have been filled,
 * then the last empty cell remaining can be calculated.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class EntryWithOneEmptyCell extends EmptyCellReasoner {

    public EntryWithOneEmptyCell(KPuzzle puzzle) {
        super(puzzle);
    }

    @Override
    CompoundCommand applyToCell(KCell cell) throws NullPointerException {
        if (!cell.isEmpty()) {
            throw new IllegalArgumentException(this.getClass().getSimpleName()
                    + "applyToCell.pre failed: cell is not empty");
        }
        CompoundCommand result = super.applyToCell(cell);

        return result;
    }

}
