
package sudoku.core.internal;

public abstract class ASolutionFinder {

	private boolean _shouldStop = false;

	public abstract void findSolution(Field[][] fields);

	void stopWork() {
		_shouldStop = true;
	}

	protected boolean shouldStop() {
		return _shouldStop;
	}

	protected int getRandomNumber() {
		return (int) Math.floor(Math.random() * 9) + 1;
	}
}
