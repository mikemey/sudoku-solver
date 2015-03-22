
package sudoku.ui;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;

public class Test {

	private Shell sShell = null;
	private Menu menuBar = null;
	private Menu submenu = null;
	private Menu submenu1 = null;

	/**
	 * This method initializes sShell
	 */
	private void createSShell() {
		sShell = new Shell();
		sShell.setText("Shell");
		sShell.setSize(new Point(300, 200));
		sShell.setLayout(new GridLayout());
		menuBar = new Menu(sShell, SWT.BAR);
		MenuItem submenuItem = new MenuItem(menuBar, SWT.CASCADE);
		submenuItem.setText("Datei");
		submenu = new Menu(submenuItem);
		MenuItem push1 = new MenuItem(submenu, SWT.PUSH);
		push1.setText("Beenden");
		MenuItem push = new MenuItem(submenu, SWT.PUSH);
		push.setText("push");
		MenuItem separator = new MenuItem(submenu, SWT.SEPARATOR);
		MenuItem check = new MenuItem(submenu, SWT.CHECK);
		check.setText("check");
		MenuItem radio = new MenuItem(submenu, SWT.RADIO);
		radio.setText("radio");
		MenuItem submenuItem1 = new MenuItem(submenu, SWT.CASCADE);
		submenuItem1.setText("asdfadsv");
		submenu1 = new Menu(submenuItem1);
		MenuItem push2 = new MenuItem(submenu1, SWT.PUSH);
		push2.setText("dflksaöjsk");
		MenuItem push3 = new MenuItem(submenu1, SWT.PUSH);
		push3.setText("asadf");
		submenuItem1.setMenu(submenu1);
		submenuItem.setMenu(submenu);
		sShell.setMenuBar(menuBar);
	}

}
