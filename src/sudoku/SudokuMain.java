package sudoku;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import sudoku.ui.SudokuDialogWatchSolution;

public class SudokuMain {

	public static void main(String[] args) {
		try {
			Display disp = new Display();
			Shell shell = new Shell(Display.getCurrent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			//			SudokuDialog sd = new SudokuDialog(shell);
			SudokuDialogWatchSolution sd = new SudokuDialogWatchSolution(shell);
			shell.open();
			sd.start();
			while (!shell.isDisposed()) {
				if (!disp.readAndDispatch())
					disp.sleep();
			}
		}
		catch (Error e) {
			e.printStackTrace(System.out);
		}
	}
}
