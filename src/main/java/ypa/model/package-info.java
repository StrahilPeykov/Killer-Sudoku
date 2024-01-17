/**
 * Package containing the model of a Kakuro puzzle.
 * The class {@link KPuzzle} also acts as a <em>façade</em> for this package.
 * However, also class {@link ypa.model.KCell} is important:
 * <ul>
 * <li>To iterate over all cells:
 *      {@code for (KCell cell : puzzle.getCells())}</li>
 * <li>To create a new {@link ypa.command.SetCommand}</li>
 * <li>To check whether a cell is empty:
 *      {@link ypa.model.KCell#isEmpty()}</li>
 * <li>To obtain the empty cell state: {@link ypa.model.KCell#EMPTY}</li>
 * </ul>
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
package ypa.model;
