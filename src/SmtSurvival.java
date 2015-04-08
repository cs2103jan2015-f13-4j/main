import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.KeyAdapter;

import logic.CommandEnteredHistoryHandler;
import logic.Menu;
import utility.IndicatorMessagePair;
import utility.MessageList;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.TraverseEvent;

public class SmtSurvival extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Composite composite = null;
	private Composite compositeBackground;
	private CTabFolder tabFolder;
	private CTabItem tabMain;
	private CTabItem tabAll;
	private CTabItem tabToday;
	private CTabItem tabCompleted;
	private CTabItem tabPending;
	private CTabItem tabBlocked;
	private Label lblMain;
	private Label lblAll;
	private Label lblToday;
	private Label lblCompleted;
	private Label lblPending;
	private Label lblBlocked;
	private ScrolledComposite scMain;
	private ScrolledComposite scAll;
	private ScrolledComposite scToday;
	private ScrolledComposite scCompleted;
	private ScrolledComposite scPending;
	private ScrolledComposite scBlocked;
	private static Menu controller;
	private Combo combo;
	private static String savedExistingContents = new String();
	private static boolean flagForSwitchTab = false;
	private static String saveCurrentCommand = new String();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SmtSurvival(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});

		// Background composite
		compositeBackground = new Composite(this, SWT.NONE);
		compositeBackground.setBackground(SWTResourceManager.getColor(102, 153,
				204));
		compositeBackground.setBounds(0, 0, 463, 465);

		tabFolder = new CTabFolder(compositeBackground, SWT.BORDER);
		tabFolder.setLocation(10, 44);
		tabFolder.setSize(435, 401);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		toolkit.adapt(tabFolder);
		toolkit.paintBordersFor(tabFolder);

		/* Main Tab */
		tabMain = new CTabItem(tabFolder, SWT.NONE);
		tabMain.setFont(SWTResourceManager.getFont("Segoe UI Black", 9,
				SWT.NORMAL));
		tabMain.setText("Main");
		tabMain.setToolTipText("Select this tab to show the Main page");

		scMain = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		scMain.setShowFocusedControl(true);
		tabMain.setControl(scMain);
		composite = new Composite(scMain, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblMain = new Label(composite, SWT.NONE);
		lblMain.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.NORMAL));
		lblMain.setAlignment(SWT.CENTER);
		lblMain.setText("Welcome to Smart Management Tool");

		scMain.setContent(composite);
		scMouseWheel(scMain);
		scMain.setExpandVertical(true);
		scMain.setFocus();
		scMain.setMinSize(composite.computeSize(2000, 2000));

		/* All Tab */
		tabAll = new CTabItem(tabFolder, SWT.NONE);
		tabAll.setFont(SWTResourceManager.getFont("Segoe UI Black", 9,
				SWT.NORMAL));
		tabAll.setText("All");
		tabAll.setToolTipText("Select this tab to show the all tasks");

		scAll = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		tabAll.setControl(scAll);
		composite = new Composite(scAll, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblAll = new Label(composite, SWT.NONE);
		lblAll.setFont(SWTResourceManager.getFont("Century Gothic", 11,
				SWT.NORMAL));
		lblAll.setText("This page is for Schedule Tasks");

		scAll.setContent(composite);
		scMouseWheel(scAll);
		scAll.setExpandVertical(true);
		scAll.setMinSize(composite.computeSize(2000, 2000));

		/* Today Tab */
		tabToday = new CTabItem(tabFolder, SWT.NONE);
		tabToday.setFont(SWTResourceManager.getFont("Segoe UI Black", 9,
				SWT.NORMAL));
		tabToday.setText("Today");

		scToday = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		tabToday.setControl(scToday);
		composite = new Composite(scToday, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblToday = new Label(composite, SWT.NONE);
		lblToday.setFont(SWTResourceManager.getFont("Century Gothic", 11,
				SWT.NORMAL));
		lblToday.setText("This page is for Today's Tasks");

		scToday.setContent(composite);
		scMouseWheel(scToday);
		scToday.setExpandVertical(true);
		scToday.setMinSize(composite.computeSize(2000, 2000));

		/* Completed Tab */
		tabCompleted = new CTabItem(tabFolder, SWT.NONE);
		tabCompleted.setFont(SWTResourceManager.getFont("Segoe UI Black", 9,
				SWT.NORMAL));
		tabCompleted.setText("Completed");

		scCompleted = new ScrolledComposite(tabFolder, SWT.BORDER
				| SWT.V_SCROLL);
		tabCompleted.setControl(scCompleted);
		composite = new Composite(scCompleted, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblCompleted = new Label(composite, SWT.NONE);
		lblCompleted.setFont(SWTResourceManager.getFont("Century Gothic", 11,
				SWT.NORMAL));
		lblCompleted.setText("This page is for Completed Tasks");

		scCompleted.setContent(composite);
		scMouseWheel(scCompleted);
		scCompleted.setExpandVertical(true);
		scCompleted.setMinSize(composite.computeSize(2000, 2000));

		/* Pending Tab */
		tabPending = new CTabItem(tabFolder, SWT.NONE);
		tabPending.setFont(SWTResourceManager.getFont("Segoe UI Black", 9,
				SWT.NORMAL));
		tabPending.setText("Pending");

		scPending = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		tabPending.setControl(scPending);
		composite = new Composite(scPending, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblPending = new Label(composite, SWT.NONE);
		lblPending.setFont(SWTResourceManager.getFont("Century Gothic", 11,
				SWT.NORMAL));
		lblPending.setText("This page is for Pending Tasks");

		scPending.setContent(composite);
		scMouseWheel(scPending);
		scPending.setExpandVertical(true);
		scPending.setMinSize(composite.computeSize(2000, 2000));

		/* Blocked Tab */
		tabBlocked = new CTabItem(tabFolder, SWT.NONE);
		tabBlocked.setFont(SWTResourceManager.getFont("Segoe UI Black", 9,
				SWT.NORMAL));
		tabBlocked.setText("Blocked");

		scBlocked = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		tabBlocked.setControl(scBlocked);
		composite = new Composite(scBlocked, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblBlocked = new Label(composite, SWT.NONE);
		lblBlocked.setFont(SWTResourceManager.getFont("Century Gothic", 11,
				SWT.NORMAL));
		lblBlocked.setText("This page is for Blocked Tasks");

		scBlocked.setContent(composite);
		scMouseWheel(scBlocked);
		scBlocked.setExpandVertical(true);
		scBlocked.setMinSize(composite.computeSize(2000, 2000));

		tabFolder.setSelection(tabMain);
		tabMain.setControl(scMain);

		combo = new Combo(compositeBackground, SWT.NONE);
		combo.setLocation(10, 10);
		combo.setSize(435, 28);
		combo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switchTabControl(e);
				loadCommandHistory(e);

			}

			@Override
			public void keyReleased(KeyEvent e) {
				passControl(e);
			}
		});
		toolkit.adapt(combo);
		toolkit.paintBordersFor(combo);
		combo.setFocus();

		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				tabControl(event);
			}
		});
	}

	/**
	 * This method is to activate the mousewheel for the scroll composite
	 * 
	 * @param scReceived
	 */
	private void scMouseWheel(final ScrolledComposite scReceived) {
		scReceived.addListener(SWT.Activate, new Listener() {
			public void handleEvent(Event e) {
				scReceived.setFocus();
			}
		});
	}

	private void tabControl(SelectionEvent event) {

		lblMain = new Label(tabFolder, SWT.NONE);

		if (tabFolder.getSelection() == tabMain) {
			setTabControl(tabMain, lblMain, scMain, savedExistingContents);
		} else if (tabFolder.getSelection() == tabAll) {
			setTabControl(tabAll, lblMain, scAll, "Display All");
		} else if (tabFolder.getSelection() == tabToday) {
			setTabControl(tabToday, lblMain, scToday, "Display Today");
		} else if (tabFolder.getSelection() == tabCompleted) {
			setTabControl(tabCompleted, lblMain, scCompleted,
					"Display Completed");
		} else if (tabFolder.getSelection() == tabPending) {
			setTabControl(tabPending, lblMain, scPending, "Display Pending");
		} else if (tabFolder.getSelection() == tabBlocked) {
			setTabControl(tabBlocked, lblMain, scBlocked, "Display Block");
		}
	}

	/**
	 * This method is to pass control to the logic component's menu class to
	 * read the output from the command text box
	 * 
	 * @param e
	 *            keyEvent variable
	 */
	private void passControl(KeyEvent e) {

		String output = new String();
		lblMain = new Label(tabFolder, SWT.NONE);

		if ((e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
				&& combo.getSelectionIndex() == -1) {
			CommandEnteredHistoryHandler.newCommandEntered(saveCurrentCommand);
			output = controller.commandExecution(saveCurrentCommand);
			tabFolder.setSelection(tabMain);
			tabMain.setControl(lblMain);
			lblMain.setText(output);
			combo.removeAll();
			savedExistingContents = lblMain.getText();
		} else if (combo.getSelectionIndex() > -1) {
			// do nth
		} else if (!flagForSwitchTab
				&& (e.keyCode != SWT.ARROW_UP && e.keyCode != SWT.ARROW_DOWN)) {

			output = controller.getHint(combo.getText());
			tabFolder.setSelection(tabMain);

			if (combo.getItemCount() > 0) {
				combo.remove(0, combo.getItemCount() - 1);
			}

			if (output.contains(MessageList.MESSAGE_HINT_INVALID)
					|| output.contains(MessageList.MESSAGE_INVAILD)) {
				combo.setListVisible(false);
				return;
			}

			String[] outputArr = output.split("\n");
			for (String indiString : outputArr) {
				combo.add(indiString);
			}

			if (outputArr.length > 0 && !output.isEmpty()) {
				combo.setListVisible(true);
			} else {
				combo.setListVisible(false);
			}
			saveCurrentCommand = combo.getText();
			savedExistingContents = lblMain.getText();
		}

	}

	private void switchTabControl(KeyEvent e) {
		flagForSwitchTab = true;
		if ((((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '1'))) {
			tabFolder.setSelection(tabMain);
			lblMain.setText(savedExistingContents);
		} else if (((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '2')) {
			setTabControl(tabAll, lblAll, scAll, "Display All");
		} else if (((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '3')) {
			flagForSwitchTab = true;
			setTabControl(tabToday, lblToday, scToday, "Display Today");
		} else if (((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '4')) {
			setTabControl(tabCompleted, lblCompleted, scCompleted,
					"Display Completed");
		} else if (((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '5')) {
			setTabControl(tabPending, lblPending, scPending, "Display Pending");
		} else if (((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '6')) {
			setTabControl(tabBlocked, lblBlocked, scBlocked, "Display Block");
		} else if ((e.stateMask & SWT.ALT) == SWT.ALT) {
			// do nothing
		} else {
			flagForSwitchTab = false;
		}
	}

	private void setTabControl(CTabItem tab, Label lblReceive,
			ScrolledComposite scReceived, String command) {
		tabFolder.setSelection(tab);
		tab.setControl(scReceived);
		lblReceive.setText(controller.commandExecution(command));
	}

	/**
	 * This method will load the command history so that user can use the up
	 * down button for execution
	 * 
	 * @param e
	 */
	private void loadCommandHistory(KeyEvent e) {

		if (((e.stateMask & SWT.SHIFT) == SWT.SHIFT)
				&& e.keyCode == SWT.ARROW_LEFT) {
			combo.select(-1);
			combo.setText(CommandEnteredHistoryHandler
					.retrieveCommand(CommandEnteredHistoryHandler.getPrevCmd()));
			saveCurrentCommand = combo.getText();
		} else if (((e.stateMask & SWT.SHIFT) == SWT.SHIFT)
				&& e.keyCode == SWT.ARROW_RIGHT) {
			combo.select(-1);
			combo.setText(CommandEnteredHistoryHandler
					.retrieveCommand(CommandEnteredHistoryHandler.getAfterCmd()));
			saveCurrentCommand = combo.getText();
		}
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE));

		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;

		shell.setLocation(x, y);
		shell.open();
		SmtSurvival Smt = new SmtSurvival(shell, SWT.NONE);
		Smt.pack();
		shell.pack();

		setUpFiles();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	/**
	 * This method is to set up the files
	 */
	private static void setUpFiles() {
		controller = Menu.getInstance();
		IndicatorMessagePair msgPair = controller.setUp();

		if (!msgPair.isTrue()) {
			MessageList.printErrorMessageAndExit(msgPair.getMessage());
		}
	}

}