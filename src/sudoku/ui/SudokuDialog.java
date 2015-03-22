
package sudoku.ui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import sudoku.core.IDataContainer;
import sudoku.core.IFinishedListener;
import sudoku.core.internal.GroupPosition;
import sudoku.core.internal.SudokuModel;

public class SudokuDialog extends Dialog
		implements IFinishedListener {

	private Shell _shell = null;
	private final Font _textFont = new Font(Display.getDefault(), "Tahoma", 10, SWT.BOLD);
	private Difficulty _difficulty = Difficulty.MEDIUM;

	IDataContainer _container;
	Text[][] _textArea = new Text[9][9];

	public SudokuDialog(Shell shell) {
		super(shell, 0);
		_shell = shell;
		this.setText("Sudoku");
		shell.setSize(275, 345);
		shell.setLayout(new GridLayout());
		createContent();
		shell.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(@SuppressWarnings("unused")
			DisposeEvent e) {
				quitAllStuff();
			}
		});
	}

	public void start() {

	}

	// private methods to create the ui-content
	private void createContent() {
		Menu game = new Menu(_shell, SWT.BAR);
		MenuItem gameSubMenu = new MenuItem(game, SWT.CASCADE);
		gameSubMenu.setText("Spiel");
		Menu sub = new Menu(gameSubMenu);
		MenuItem start = new MenuItem(sub, SWT.PUSH);
		start.setText("Starte Sudoku...");
		start.addSelectionListener(new SelectionListener() {

			@SuppressWarnings("unused")
			public void widgetSelected(SelectionEvent e) {
				createNewSudoku();
			}

			@SuppressWarnings("unused")
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		new MenuItem(sub, SWT.SEPARATOR);
		MenuItem hard = new MenuItem(sub, SWT.RADIO);
		hard.setText("Schwer");
		hard.addSelectionListener(new SelectionListener() {

			@SuppressWarnings("unused")
			public void widgetSelected(SelectionEvent e) {
				setDifficultyLevel(Difficulty.HARD);
			}

			@SuppressWarnings("unused")
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		MenuItem medium = new MenuItem(sub, SWT.RADIO);
		medium.setText("Mittel");
		medium.setSelection(true);
		medium.addSelectionListener(new SelectionListener() {

			@SuppressWarnings("unused")
			public void widgetSelected(SelectionEvent e) {
				setDifficultyLevel(Difficulty.MEDIUM);
			}

			@SuppressWarnings("unused")
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		MenuItem easy = new MenuItem(sub, SWT.RADIO);
		easy.setText("Leicht");
		easy.addSelectionListener(new SelectionListener() {

			@SuppressWarnings("unused")
			public void widgetSelected(SelectionEvent e) {
				setDifficultyLevel(Difficulty.EASY);
			}

			@SuppressWarnings("unused")
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		new MenuItem(sub, SWT.SEPARATOR);
		MenuItem quit = new MenuItem(sub, SWT.PUSH);
		quit.addSelectionListener(new SelectionListener() {

			@SuppressWarnings("unused")
			public void widgetSelected(SelectionEvent e) {
				quitAllStuff();
			}

			@SuppressWarnings("unused")
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		quit.setText("Beenden");
		_shell.setMenuBar(game);
		gameSubMenu.setMenu(sub);

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

	// private ui-methods end


	// protected mehtods which are called when an action (for example a menu-
	// item is triggered) is performed.

	/*protected*/void setDifficultyLevel(Difficulty dc) {
		_difficulty = dc;
	}

	/**
	 * Creates a new generated Sudoku-Field.
	 */
	/*protected*/void createNewSudoku() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				_textArea[i][j].setText("");
			}
		}

		SudokuModel model = new SudokuModel();
		_container = model.getContainer();
		_container.setFinishedListener(this);
		_container.shuffleData();

		int loopCount = _difficulty.getNumberCountVisible();

		class Pair {

			int x;
			int y;

			public Pair(int a, int b) {
				x = a;
				y = b;
			}

			public boolean equals(Object other) {
				if (!(other instanceof Pair))
					return false;
				Pair oth = (Pair) other;
				if (oth.x == this.x && oth.y == this.y) { return true; }
				return false;
			}

			public String toString() {
				return "[" + x + "," + y + "]";
			}
		}
		ArrayList<Pair> pairs = new ArrayList<Pair>();
		for (int i = 0; i < loopCount; i++) {
			Pair p = new Pair(get0based1digitNumber(), get0based1digitNumber());
			while (pairs.contains(p)) {
				p = new Pair(get0based1digitNumber(), get0based1digitNumber());
			}
			pairs.add(p);
		}

		for (Pair p : pairs) {
			_textArea[p.x - 1][p.y - 1].setText(_container.getOriginalData(p.x - 1, p.y - 1));
			//			_textArea[p.x-1][p.y-1].setEditable(false);
		}
	}

	private int get0based1digitNumber() {
		return (int) Math.floor(Math.random() * 9) + 1;
	}

	/*protected*/void quitAllStuff() {
		if (_container != null) {
			_container.abortWork();
		}
	}

	// action-methods end

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

	// inner classes and enums
	private enum Difficulty {
		EASY(25),
		MEDIUM(20),
		HARD(15);

		private int _ncv;

		private Difficulty(int ncv) {
			_ncv = ncv;
		}

		public int getNumberCountVisible() {
			return _ncv;
		}
	}
}