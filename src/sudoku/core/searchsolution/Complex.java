package sudoku.core.searchsolution;

import java.util.ArrayList;

import sudoku.core.internal.ASolutionFinder;
import sudoku.core.internal.Field;

public class Complex extends ASolutionFinder {

	@Override
	public void findSolution(Field[][] fields) {
		int x = 0;
		int y = 0;
		Entry[][] entries = new Entry[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				entries[i][j] = new Entry(fields[i][j]);
			}
		}

		int num = getRandomNumber();
		boolean lastNumCorrectlySet = true;
		while (true) {
			if (shouldStop()) {
				return;
			}
			Entry entry = entries[x][y];
			if (lastNumCorrectlySet) {
				num = getRandomNumber();
			} else {
				num++;
				if (num > 9) {
					num = 1;
				}
			}
			if (!entry._usedNums.contains(num)) {
				entry._usedNums.add(num);
			}
			if (entry._field.isSetNumPossible(num) == true) {
				lastNumCorrectlySet = true;
				if (x == 8) {
					x = -1;
					if (y == 8) {
						return;
					}
					y++;
				}
				x++;
			} else {
				lastNumCorrectlySet = false;
				if (entry._usedNums.size() >= 8) {
					entry._usedNums.clear();
					if (x == 0) {
						x = 9;
						if (y > 0) {
							y--;
						}
					}
					x--;
				}
			}
		}

	}

	class Entry {

		Field _field;
		ArrayList<Integer> _usedNums;

		Entry(Field field) {
			_field = field;
			_usedNums = new ArrayList<Integer>();
		}
	}
}
