/**
 * 
 */

package sudoku.core.internal;

import java.util.ArrayList;
import java.util.Collections;


public class NumBlock
		implements IBlockListener {

	private ArrayList<Field> _fields;
	private String _name;

	public NumBlock(final Field[] fields, String name) {
		_fields = new ArrayList<Field>();
		Collections.addAll(_fields, fields);
		_name = name;
		for (int i = 0; i < 9; i++) {
			_fields.get(i).addBlockListener(this);
		}
	}

	//	/**
	//	 * @return a number between 0 and 8 (inclusive)
	//	 */
	//	public int getRandomNumber() {
	//		return (int)Math.floor(Math.random() * 9);
	//	}

	//	/**
	//	 * @return a number between 1 and 9 (inclusive) which 
	//	 * isn't used in this block
	//	 */
	//	public int getUnusedNumber() {
	//		int uun = getRandomNumber() + 1;
	//		for (Field fieldNum : _fields) {
	//			if( uun != fieldNum.getNum()) {
	//				return uun;
	//			}
	//			if( uun == 9 ) {
	//				uun = 0;
	//			}
	//			uun++;
	//		}
	//		throw new IllegalStateException();
	//	}

	public String getName() {
		return _name;
	}

	public String toString() {
		return getName();
	}

	public boolean isFieldNumPossible(Field fieldToTest, int newNum) {
		for (Field f : _fields) {
			if (!f.equals(fieldToTest)) {
				if (f.getNum() == newNum) { return false; }
			}
		}
		return true;
	}
}