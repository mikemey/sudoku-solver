/**
 * 
 */

package sudoku.core.internal;

import java.util.ArrayList;

import sudoku.core.IFieldListener;


public class Field {

	private int _x, _y;
	private int _num;
	private int _data;
	private static IFieldListener _listener;
	private ArrayList<IBlockListener> _blockListener;

	public Field(int x, int y) {
		_x = x;
		_y = y;
		_blockListener = new ArrayList<IBlockListener>();
	}

	void setListener(IFieldListener listener) {
		_listener = listener;
	}

	IBlockListener getRandomBlockListener() {
		int rand = (int) Math.floor(Math.random() * _blockListener.size());
		return _blockListener.get(rand);
	}

	void addBlockListener(IBlockListener listener) {
		_blockListener.add(listener);
	}

	/**
	 * Checks if all Blocklistener are allowing the new Number,
	 * and sets it into this field if so. Returns false and doesn't
	 * set the number if not.
	 * @param newNum
	 * @return
	 */
	public boolean isSetNumPossible(int newNum) {
		assertInput(newNum);
		if (newNum == _num)
			return true;
		for (IBlockListener listener : _blockListener) {
			if (listener.isFieldNumPossible(this, newNum) == false) { return false; }
		}
		_num = newNum;
		if (_listener != null) {
			_listener.fieldChanged(_x, _y, _num);
		}
		return true;
	}

	public int getNum() {
		return _num;
	}

	public int getErrorData() {
		return _data;
	}

	public void raiseErrorData() {
		_data++;
	}

	public void resetData() {
		_num = 0;
		_data = 0;
		if (_listener != null) {
			_listener.fieldChanged(_x, _y, _num);
		}
	}

	private void assertInput(int i) {
		if (i < 1 || i > 9) { throw new IllegalArgumentException("Eingabe ist keine Zahl!"); }
	}

	public boolean equals(Object obj) {
		if (obj instanceof Field) {
			Field f = (Field) obj;
			if (f._x == this._x && f._y == this._y)
				return true;
		}
		return false;
	}

	public String toString() {
		return "(" + _x + ", " + _y + "):" + _num;
	}
}