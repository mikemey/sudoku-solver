
package sudoku.core.internal.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import sudoku.core.internal.DataCont;
import sudoku.core.internal.Field;
import sudoku.core.internal.GroupPosition;
import sudoku.core.internal.SudokuModel;

public class DataContTest {

	private static DataCont _cont;

	@BeforeClass
	public static void setupClass() {
		setupDataContainer();
	}

	private static void setupDataContainer() {
		_cont = new DataCont(SudokuModel.getSolutionFinder());

		boolean solutionFound = false;

		while (!solutionFound) {
			try {
				_cont.shuffleData();
				solutionFound = true;
			}
			catch (StackOverflowError soe) {
				solutionFound = false;
			}
		}
	}

	@Test
	public void testContainerSetup() {
		boolean initializationError = false;
		String data;
		int iData;
		for (int i = 0; i < 9 && !initializationError; i++) {
			for (int j = 0; j < 9 && !initializationError; j++) {
				data = _cont.getOriginalData(i, j);
				iData = 0;
				try {
					iData = Integer.parseInt(data);
					if (iData < 1 || iData > 9) {
						initializationError = true;
					}
				}
				catch (NumberFormatException nfe) {
					initializationError = true;
				}
			}
		}
		if (initializationError) {
			Assert.fail("Container wird nicht richtig initialisiert!");
		}
	}

	@Test
	public void testGetOriginalData() {
		assertDataRange(_cont.getOriginalData(0, 0));
		assertDataRange(_cont.getOriginalData(8, 8));
		assertDataRange(_cont.getOriginalData(1, 1));
		assertDataRange(_cont.getOriginalData(7, 7));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetOriginalData2() {
		_cont.getOriginalData(-1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetOriginalData3() {
		_cont.getOriginalData(9, 9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetOriginalData4() {
		_cont.getOriginalData(2, 9);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetOriginalData5() {
		_cont.getOriginalData(0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetOriginalData6() {
		_cont.getOriginalData(10, 5);
	}

	@Test
	public void testSetData() {
		_cont.setData(0, 0, "1");
		_cont.setData(8, 8, "9");
		_cont.setData(1, 8, "2");
		_cont.setData(7, 1, "8");
	}

	@Test
	public void testSetDataFinishCorrect() {
		setupDataContainer();
		MyFinishedListener fListener = new MyFinishedListener();
		_cont.setFinishedListener(fListener);
		fListener.reset();
		Field[][] _fields = _cont.getOriginData();
		Field _lastField = _fields[8][8];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (_fields[i][j] == _lastField) {
					break;
				}
				_cont.setData(i, j, _fields[i][j].getNum() + "");
			}
		}
		if (fListener.wasMethodFinishedCalled() == true) {
			Assert.fail("Finished zu früh aufgerufen!");
		}
		fListener.reset();
		_cont.setData(8, 8, _fields[8][8].getNum() + "");
		if (fListener.wasMethodFinishedCalled() == false) {
			Assert.fail("Finished nicht aufgerufen!");
		}
		if (fListener.getCorrectFlag() == false) {
			Assert.fail("Finished mit dem falschen Ergebnis aufgerufen!");
		}
	}

	@Test
	public void testSetDataFinishWrong() {
		setupDataContainer();
		MyFinishedListener fListener = new MyFinishedListener();
		_cont.setFinishedListener(fListener);

		fListener.reset();
		Field[][] _fields = _cont.getOriginData();
		Field _lastField = _fields[8][8];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (_fields[i][j] == _lastField) {
					break;
				}
				_cont.setData(i, j, _fields[i][j].getNum() + "");
				_cont.setData(i, j, _fields[i][j].getNum() + "");
			}
		}
		if (fListener.wasMethodFinishedCalled() == true) {
			Assert.fail("Finished zu früh aufgerufen!");
		}
		fListener.reset();
		int wrongNumber = -1;
		if (_fields[8][8].getNum() == 8) {
			wrongNumber = 3;
		}
		else {
			wrongNumber = 8;
		}
		_cont.setData(8, 8, wrongNumber + "");
		if (fListener.wasMethodFinishedCalled() == false) {
			Assert.fail("Finished nicht aufgerufen!");
		}
		if (fListener.getCorrectFlag() == true) {
			Assert.fail("Finished mit dem falschen Ergebnis aufgerufen!");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetData1() {
		_cont.setData(0, 0, "0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetData2() {
		_cont.setData(0, 0, "10");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetData3() {
		_cont.setData(-1, 0, "0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetData4() {
		_cont.setData(10, 9, "0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetData5() {
		_cont.setData(9, 0, "ad");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetData6() {
		_cont.setData(0, 9, "5");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetData7() {
		_cont.setData(0, 0, "-1");
	}

	@Test
	public void testGeneratedNumbers() {
		Field[][] genNums = _cont.getOriginData();
		int[][] indexArr = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				indexArr[i][j] = genNums[i][j].getNum();
			}
		}
		for (int i = 0; i < 9; i++) {
			if (!isValidLine(indexArr[i])) {
				Assert.fail("Zeile " + i + " nicht korrekt initialisiert!");
			}
			if (!isValidColumn(indexArr, i)) {
				Assert.fail("Spalte " + i + " nicht korrekt initialisiert!");
			}
			if (!isValidGroup(indexArr, GroupPosition.getGroupPosition(i))) {
				Assert.fail("Gruppe " + GroupPosition.getGroupPosition(i) + " nicht korrekt initialisiert!");
			}
		}
	}

	private boolean isValidGroup(int[][] area, GroupPosition position) {
		int[] equalLine = new int[9];
		int lineIndex = 0;
		int lineStart = position.getLineStart();
		int lineEnd = lineStart + 3;
		int colStart = position.getColumnStart();
		int colEnd = colStart + 3;
		for (int i = lineStart; i < lineEnd; i++) {
			for (int j = colStart; j < colEnd; j++) {
				equalLine[lineIndex] = area[i][j];
				lineIndex++;
			}
		}
		return isValidLine(equalLine);
	}


	private boolean isValidLine(int[] indexLine) {
		boolean[] occurence = new boolean[9];
		for (int i = 0; i < 9; i++) {
			if (!isValidNumber(indexLine[i])) { return false; }
			if (occurence[indexLine[i] - 1] == false) {
				occurence[indexLine[i] - 1] = true;
			}
			else {
				return false;
			}
		}
		return true;
	}

	private boolean isValidColumn(int[][] area, int columnIndex) {
		int[] line = new int[9];
		for (int i = 0; i < 9; i++) {
			line[i] = area[i][columnIndex];
		}
		return isValidLine(line);
	}

	private boolean isValidNumber(int num) {
		return !(num < 1 || num > 9);
	}

	private void assertDataRange(String data) {
		try {
			int iData = Integer.parseInt(data);
			if (iData < 1 || iData > 9) {
				Assert.fail("Zahl '" + iData + "' nicht im gültigen Bereich (1-9)");
			}
		}
		catch (NumberFormatException nfe) {
			Assert.fail("Ungültiges Datenobjekt: " + data);
		}
	}
}