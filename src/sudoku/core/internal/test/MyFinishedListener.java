/**
 * 
 */

package sudoku.core.internal.test;

import sudoku.core.IFinishedListener;

class MyFinishedListener
		implements IFinishedListener {

	private int _methodHitCount;
	private boolean _correct;
	private boolean _methodInvocated = false;

	public void finished(boolean correct) {
		_methodHitCount++;
		_correct = correct;
	}

	public void reset() {
		_methodHitCount = 0;
		_methodInvocated = false;
		_correct = false;
	}

	public boolean getCorrectFlag() {
		if (_methodInvocated == false) { throw new RuntimeException("Call wasMethodFinishedCalled() first!"); }
		_methodInvocated = false;
		return _correct;
	}

	public boolean wasMethodFinishedCalled() {
		_methodInvocated = _methodHitCount != 0;
		return _methodInvocated;
	}
}