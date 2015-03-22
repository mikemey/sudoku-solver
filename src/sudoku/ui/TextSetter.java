
package sudoku.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

class TextSetter
		implements Runnable {

	private int _value;
	private Text _currentText;
	private Text _oldText;
	private Text _oldOldText;
	private boolean _labelRun = false;
	private Label _label;

	static Color _redColor;
	static Color _whiteColor;
	static Color _rosaColor;

	public TextSetter(Text currentText, Text oldText, Text oldOldText, int value) {
		_currentText = currentText;
		_oldText = oldText;
		_oldOldText = oldOldText;
		_value = value;
	}

	public TextSetter(Label label, int value) {
		_labelRun = true;
		_label = label;
		_value = value;
	}

	public void run() {
		if (_labelRun) {
			_label.setText(_value + "");
		}
		else {
			_currentText.setText(_value + "");
			_currentText.setBackground(_redColor);
			_oldText.setBackground(_rosaColor);
			_oldOldText.setBackground(_whiteColor);
		}
	}
}