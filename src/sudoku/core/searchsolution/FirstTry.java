
package sudoku.core.searchsolution;

import sudoku.core.internal.Field;
import sudoku.core.internal.ASolutionFinder;


public class FirstTry extends ASolutionFinder {


	public void findSolution(Field[][] fields) {
		int x = 0;
		int y = 0;
		int num = getRandomNumber();
		while (true) {
			if (shouldStop()) { return; }
			Field field = fields[x][y];
			num = getRandomNumber();
			if (field.isSetNumPossible(num) == true) {
				if (x == 8) {
					x = -1;
					if (y == 8) { return; }
					y++;
				}
				x++;
			}
			else {
				field.raiseErrorData();
				if (field.getErrorData() > 8) {
					field.resetData();
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

}
