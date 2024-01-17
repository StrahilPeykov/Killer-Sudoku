package ypa.reasoning;

import ypa.command.CompoundCommand;
import ypa.command.SetCommand;
import ypa.model.HCell;
import ypa.model.HPuzzle;

/**
 * When only one way of filling an empty cell does not lead to an invalid state
 * after applying a given reasoner,
 * then that one way of filling is forced.
 * This generalizes both the {@link EntryWithOneEmptyCell} reasoner,
 * and the {@link BasicEmptyCellByContradiction} reasoner.
 * Note that it is specifically allowed to set the reasoner used
 * for validity checking, to the reasoner itself.
 * That is why the reasoner is not set in the constructor.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class GeneralizedEmptyCellByContradiction extends EmptyCellReasoner {

    /** The reasoner to apply before checking validity. */
    private final Reasoner reasoner;

    /**
     * Constructs a reasoner for the given puzzle and reasoner.
     *
     * @param puzzle   the puzzle to reason about
     * @param reasoner the reasoner to use before validity checking
     * @throws IllegalArgumentException if precondition failed
     * @pre {@code puzzle != null  && reasoner != null && reasoner.puzzle == puzzle}
     */
    public GeneralizedEmptyCellByContradiction(HPuzzle puzzle, final Reasoner reasoner) {
        super(puzzle);
        if (reasoner == null) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + " constructor failed: reasoner is null");
        }
        if (reasoner.puzzle != this.puzzle) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + " constructor failed: reasoner's puzzle is not this puzzle");
        }
        this.reasoner = reasoner;
    }

    /**
     * Constructs self-referencing (recursive) reasoner.
     *
     * @param puzzle the puzzle to reason about
     * @throws IllegalArgumentException if precondition failed
     * @pre {@code puzzle != null}
     */
    public GeneralizedEmptyCellByContradiction(HPuzzle puzzle) {
        super(puzzle);
        this.reasoner = this;
    }

    @Override
    CompoundCommand applyToCell(final HCell cell) throws NullPointerException {
        if (!puzzle.isValid()) {
            return null;
        }
        CompoundCommand result = super.applyToCell(cell);
        CompoundCommand candidateForcedCommand = null;

        int minNumber = 1; // In Hidato, the minimum number is always 1
        for (int state = minNumber; state <= puzzle.getMaxNumber(); ++state) {
            CompoundCommand command = new CompoundCommand();
            command.add(new SetCommand(cell, state));
            command.execute();
            CompoundCommand compound = reasoner.apply();
            if (compound != null) {
                command.add(compound);
            }
            command.revert();
            if (compound != null) {
                if (candidateForcedCommand == null) {
                    candidateForcedCommand = command;
                } else {
                    return result;
                }
            }
        }

        if (candidateForcedCommand == null) {
            return null;
        } else {
            result.add(candidateForcedCommand);
            return result;
        }
    }

}
