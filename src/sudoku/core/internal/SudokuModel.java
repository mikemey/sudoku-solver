
package sudoku.core.internal;

import sudoku.core.IDataContainer;
import sudoku.core.ISudokuModel;
import sudoku.core.searchsolution.Complex;
import sudoku.core.searchsolution.FirstTry;


public class SudokuModel
		implements ISudokuModel {

	private DataCont _cont;
	private static ASolutionFinder _sf;

	public SudokuModel() {
	}

	public IDataContainer getContainer() {
		if (_cont == null) {
			_cont = new DataCont(getSolutionFinder());
		}
		return _cont;
	}

	public static ASolutionFinder getSolutionFinder() {
		if (_sf == null) {
			_sf = new Complex();
		}
		return _sf;
	}
}
