/**
 * 
 */

package sudoku.core;

public interface IFieldListener {

	public void fieldChanged(int x, int y, int value);
}