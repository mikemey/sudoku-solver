/**
 * 
 */

package sudoku.core.internal;

public enum GroupPosition {
	TOP_LEFT(0, 0),
	TOP_MIDDLE(0, 3),
	TOP_RIGHT(0, 6),
	CENTER_LEFT(3, 0),
	CENTER_MIDDLE(3, 3),
	CENTER_RIGHT(3, 6),
	BOTTOM_LEFT(6, 0),
	BOTTOM_MIDDLE(6, 3),
	BOTTOM_RIGHT(6, 6);

	private int _lineStart;
	private int _columnStart;

	private GroupPosition(int lineStart, int columnStart) {
		_lineStart = lineStart;
		_columnStart = columnStart;
	}

	public static GroupPosition getGroupPosition(int index) {
		GroupPosition gp;
		switch (index) {
			case 0:
				gp = TOP_LEFT;
				break;
			case 1:
				gp = TOP_MIDDLE;
				break;
			case 2:
				gp = TOP_RIGHT;
				break;
			case 3:
				gp = CENTER_LEFT;
				break;
			case 4:
				gp = CENTER_MIDDLE;
				break;
			case 5:
				gp = CENTER_RIGHT;
				break;
			case 6:
				gp = BOTTOM_LEFT;
				break;
			case 7:
				gp = BOTTOM_MIDDLE;
				break;
			case 8:
				gp = BOTTOM_RIGHT;
				break;
			default:
				throw new IllegalArgumentException("Ungültiger Index: " + index + ". Bereich zw. 0-8.");
		}

		return gp;
	}

	public int getLineStart() {
		return _lineStart;
	}

	public int getColumnStart() {
		return _columnStart;
	}
}