
package sudoku.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import sudoku.core.IDataContainer;
import sudoku.core.IFieldListener;
import sudoku.core.IFinishedListener;
import sudoku.core.internal.GroupPosition;
import sudoku.core.internal.SudokuModel;

public class SudokuDialogWatchSolution extends Dialog
		implements IFinishedListener, IFieldListener {

	private Shell _shell = null;
	private final Font _textFont = new Font(Display.getDefault(), "Tahoma", 10, SWT.BOLD);

	IDataContainer _container;
	Text[][] _textArea = new Text[9][9];
	private int _lastX;
	private int _lastlastX;
	private int _lastY;
	private int _lastlastY;

	public SudokuDialogWatchSolution(Shell shell) {
		super(shell, 0);
		_shell = shell;
		this.setText("Sudoku");
		shell.setSize(275, 325);
		shell.setLayout(new GridLayout());
		SudokuModel model = new SudokuModel();
		_container = model.getContainer();
		_container.setFinishedListener(this);
		_container.setFieldListener(this);
		createContent();
		TextSetter._redColor = new Color(this.getParent().getDisplay(), 255, 0, 0);
		TextSetter._whiteColor = new Color(this.getParent().getDisplay(), 255, 255, 255);
		TextSetter._rosaColor = new Color(this.getParent().getDisplay(), 255, 127, 127);
		shell.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(@SuppressWarnings("unused")
			DisposeEvent e) {
				if (_container != null) {
					_container.abortWork();
				}
			}
		});
	}

	public void start() {
		new Thread(new Runnable() {

			public void run() {
				_container.shuffleData();
			}
		}, "ContainerThread").start();
	}

	private void createContent() {
		Composite comp = new Composite(_shell, SWT.NONE);
		GridLayout glayout = new GridLayout(3, true);
		glayout.horizontalSpacing = 1;
		glayout.verticalSpacing = 1;
		glayout.marginWidth = 0;
		glayout.marginHeight = 0;
		comp.setLayout(glayout);

		GroupPosition gp;
		for (int i = 0; i < 9; i++) {
			gp = GroupPosition.getGroupPosition(i);
			int[] lines = new int[9];
			int[] cols = new int[9];
			int arrIndex = 0;
			int lineStart = gp.getLineStart();
			int colStart = gp.getColumnStart();
			for (int j = lineStart; j < lineStart + 3; j++) {
				for (int k = colStart; k < colStart + 3; k++) {
					lines[arrIndex] = j;
					cols[arrIndex] = k;
					arrIndex++;
				}
			}
			createTextGroup(comp, lines, cols);
		}
	}

	private Text createNumberText(Composite parent, int x, int y) {
		Text t = new Text(parent, SWT.CENTER);
		t.setFont(_textFont);
		GridData gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		t.setLayoutData(gd);
		t.setTextLimit(1);
		t.addVerifyListener(new VerifyListener() {

			public void verifyText(VerifyEvent e) {
				String c = e.text;
				try {
					Integer.parseInt(c);
				}
				catch (NumberFormatException nfe) {
					e.doit = false;
				}
			}
		});
		t.setText(x + "," + y);
		_textArea[x][y] = t;
		return t;
	}

	private Group createTextGroup(Composite parent, int[] lines, int[] cols) {
		Group g = new Group(parent, 0);
		GridLayout glayout = new GridLayout(3, true);
		glayout.horizontalSpacing = 1;
		glayout.verticalSpacing = 1;
		glayout.marginWidth = 0;
		glayout.marginHeight = 0;
		g.setLayout(glayout);

		GridData gd = new GridData(80, 80);
		g.setLayoutData(gd);

		for (int i = 0; i < 9; i++) {
			createNumberText(g, lines[i], cols[i]);
		}
		return g;
	}

	public void finished(boolean correct) {
		MessageBox box = new MessageBox(this._shell, SWT.OK);
		if (correct) {
			box.setText("Gratulation");
			box.setMessage("Sudoku gelöst!");
		}
		else {
			box.setText("Ohje");
			box.setMessage("Sudoku hat noch Fehler!");
		}
		box.open();
	}

	public void fieldChanged(int x, int y, int value) {
		Display.getDefault().asyncExec(new TextSetter(_textArea[x][y], _textArea[_lastX][_lastY], //
				_textArea[_lastlastX][_lastlastY], value));
		_lastlastX = _lastX;
		_lastlastY = _lastY;
		_lastX = x;
		_lastY = y;

		//		try {
		//			Thread.sleep(500);
		//		}
		//		catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}
	}
}