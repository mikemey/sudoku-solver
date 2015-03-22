
package sudoku.core.internal;

import sudoku.core.IDataContainer;
import sudoku.core.IFieldListener;
import sudoku.core.IFinishedListener;

public class DataCont
		implements IDataContainer {

	private Field[][] _originData;
	private Field[][] _guessedData;
	private IFinishedListener _finListener;
	private IFieldListener _fieldListener;
	private ASolutionFinder _solFinder;


	public DataCont(ASolutionFinder solFinder) {
		_originData = new Field[9][9];
		_guessedData = new Field[9][9];
		_solFinder = solFinder;
	}

	// just for the visibility for the test...
	public Field[][] getOriginData() {
		return _originData;
	}

	public String getOriginalData(int line, int column) {
		assertPosition(line, column);
		return _originData[line][column].getNum() + "";
	}

	public void setData(int line, int column, String data) {
		assertPosition(line, column);
		parseData(data);
		if (_guessedData[line][column] == null) {
			_guessedData[line][column] = new Field(line, column);
		}
		_guessedData[line][column].isSetNumPossible(parseData(data));
		assertFinished();
	}

	public void setFinishedListener(IFinishedListener listener) {
		_finListener = listener;
	}

	public void setFieldListener(IFieldListener flistener) {
		_fieldListener = flistener;
	}

	private void assertPosition(int line, int column) {
		if (line < 0 || line > 8 || column < 0 || column > 8) { throw new IllegalArgumentException("Ungültige Positionsangabe!"); }
		return;
	}

	private int parseData(String data) {
		try {
			int dataInt = Integer.parseInt(data);
			if (dataInt < 1 || dataInt > 9) { throw new IllegalArgumentException("Ungültiges Datenfeld!"); }
			return dataInt;
		}
		catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Ungültige Zahl!");
		}
	}

	private void assertFinished() {
		if (_finListener == null)
			return;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (_guessedData[i][j] == null)
					return;
				if (_originData[i][j] == null)
					return;
				if (_guessedData[i][j].getNum() != _originData[i][j].getNum()) {
					_finListener.finished(false);
					return;
				}
			}
		}
		_finListener.finished(true);
	}

	public void abortWork() {
		if (_solFinder != null) {
			_solFinder.stopWork();
		}
	}

	public void shuffleData() {
		NumBlock[] lines = new NumBlock[9];
		NumBlock[] columns = new NumBlock[9];
		NumBlock[] groups = new NumBlock[9];

		// phase 1: initialize fields randomly
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				_originData[i][j] = new Field(i, j);
				_originData[i][j].setListener(_fieldListener);
			}
			lines[i] = new NumBlock(_originData[i], "L" + i);
		}
		for (int i = 0; i < 9; i++) {
			Field[] tmpFields = new Field[9];
			for (int j = 0; j < 9; j++) {
				tmpFields[j] = _originData[j][i];
			}
			columns[i] = new NumBlock(tmpFields, "C" + i);
		}
		for (int i = 0; i < 9; i++) {
			GroupPosition gp = GroupPosition.getGroupPosition(i);
			int lineStart = gp.getLineStart();
			int colStart = gp.getColumnStart();
			int groupIndex = 0;
			Field[] tmpFields = new Field[9];
			for (int j = lineStart; j < lineStart + 3; j++) {
				for (int k = colStart; k < colStart + 3; k++) {
					tmpFields[groupIndex] = _originData[j][k];
					groupIndex++;
				}
			}
			groups[i] = new NumBlock(tmpFields, "G" + i);
		}

		// phase 2: find a solution 
		//		findSolutionSimple();
		_solFinder.findSolution(_originData);
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				buff.append(_originData[i][j].getNum() + " ");
				if (((j + 1) % 3) == 0) {
					buff.append("| ");
				}
			}
			if (((i + 1) % 3) == 0) {
				buff.append("\n---------------------");
			}
			buff.append("\n");
		}
		return buff.toString();
	}
}