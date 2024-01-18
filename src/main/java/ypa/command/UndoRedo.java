package ypa.command; // <<<<< TODO: Comment this line out when submitting to Momotor!

import java.util.Stack;

/**
 * Facilities for an undo-redo mechanism, on the basis of commands.
 *
 * <!--//# BEGIN TODO: Names, student IDs, group name, and date-->
 * Ole Wouters 1413996, Mila van Bokhoven 1754238,
 * Tunay Ata Gök XXXXXXX, Borna Simic XXXXXXX,
 * Group 58, 16/01/2024
 * <!--//# END TODO-->
 */
public class UndoRedo {

    // # BEGIN TODO: Representation in terms of instance variables, incl. rep. inv.
    /**
     * The undo stack for all commands that can be undone.
     * 
     * @invariant for all the commands on this stack it holds that they
     *            are executed and that they can be undone.
     */
    private Stack<Command> undoStack = new Stack<>();

    /**
     * The redo stack for all undone commands that can be redone.
     * 
     * @invariant for all the commands on this stack it holds that they
     *            are unexecuted and can be executed again.
     */
    private Stack<Command> redoStack = new Stack<>();
    // # END TODO

    /**
     * Returns whether an {@code undo} is possible.
     *
     * @return whether {@code undo} is possible
     */
    public boolean canUndo() {
        // # BEGIN TODO: Implementation of canUndo
        return !undoStack.isEmpty();
        // # END TODO
    }

    /**
     * Returns whether a {@code redo} is possible.
     *
     * @return {@code redo().pre}
     */
    public boolean canRedo() {
        // # BEGIN TODO: Implementation of canRedo
        return !redoStack.isEmpty();
        // # END TODO
    }

    /**
     * Returns command most recently done.
     *
     * @return command at top of undo stack
     * @throws IllegalStateException if precondition failed
     * @pre {@code canUndo()}
     */
    public Command lastDone() throws IllegalStateException {
        // # BEGIN TODO: Implementation of lastDone
        // Check if there is something to be undone. If not, throw exception.
        if (!canUndo()) {
            throw new IllegalStateException("The undoStack is empty so there is nothing that"
                    + "can be undone.");
        }

        // Return the top of the undoStack using peek().
        return undoStack.peek();
        // # END TODO
    }

    /**
     * Returns command most recently undone.
     *
     * @return command at top of redo stack
     * @throws IllegalStateException if precondition failed
     * @pre {@code canRedo()}
     */
    public Command lastUndone() throws IllegalStateException {
        // # BEGIN TODO: Implementation of lastUndone
        // Check if there is something to be redone. If not, throw exception.
        if (!canRedo()) {
            throw new IllegalStateException("The redoStack is empty so there is nothing that"
                    + "can be redone.");
        }

        // Return the top of the redoStack using peek().
        return redoStack.peek();
        // # END TODO
    }

    /**
     * Clears all undo-redo history.
     *
     * @modifies {@code this}
     */
    public void clear() {
        // # BEGIN TODO: Implementation of clear
        // Clear both the redoStack and the undoStack.
        redoStack.clear();
        undoStack.clear();
        // # END TODO
    }

    /**
     * Adds given command to the do-history.
     * If the command was not yet executed, then it is first executed.
     *
     * @param command the command to incorporate
     * @modifies {@code this}
     */
    public void did(final Command command) {
        // # BEGIN TODO: Implementation of did
        // Check if command is executed.
        if (!command.isExecuted()) {
            // If not executed, execute the command.
            command.execute();
        }

        // Clear the redoStack and push the just executed command to undoStack.
        redoStack.clear();
        undoStack.push(command);
        // # END TODO
    }

    /**
     * Undo the most recently done command, optionally allowing it to be redone.
     *
     * @param redoable whether to allow redo
     * @throws IllegalStateException if precondition violated
     * @pre {@code canUndo()}
     * @modifies {@code this}
     */
    public void undo(final boolean redoable) throws IllegalStateException {
        // # BEGIN TODO: Implementation of undo
        // Check if there is something to be undone. If not, throw exception.
        if (!canUndo()) {
            throw new IllegalStateException("The undoStack is empty so there is nothing that"
                    + "can be undone.");
        }

        // Remove the command from the undostack and then revert it.
        Command command = undoStack.pop();
        command.revert();

        // If redoable is true, push the command to the redoStack.
        if (redoable) {
            redoStack.push(command);
        }
        // # END TODO
    }

    /**
     * Redo the most recently undone command.
     *
     * @throws IllegalStateException if precondition violated
     * @pre {@code canRedo()}
     * @modifies {@code this}
     */
    public void redo() throws IllegalStateException {
        // # BEGIN TODO: Implementation of redo
        if (!canRedo()) {
            throw new IllegalStateException("The redoStack is empty so there is nothing that"
                    + "can be redone.");
        }

        // Remove the command from the redostack and then execute it again.
        Command command = redoStack.pop();
        command.execute();

        // Push the command to the undoStack.
        undoStack.push(command);
        // # END TODO
    }

    /**
     * Undo all done commands.
     *
     * @param redoable whether to allow redo
     * @modifies {@code this}
     */
    public void undoAll(final boolean redoable) {
        // # BEGIN TODO: Implementation of undoAll

        // Go through whole stack and undo all
        while (!undoStack.isEmpty()) {
            undo(redoable);
        }
        // # END TODO
    }

    /**
     * Redo all undone commands.
     *
     * @modifies {@code this}
     */
    public void redoAll() {
        // # BEGIN TODO: Implementation of redoAll

        // Go through whole stack and redo all
        while (!redoStack.isEmpty()) {
            redo();
        }
        // # END TODO
    }

}
