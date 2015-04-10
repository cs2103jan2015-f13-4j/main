//@A0112502A
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.KeyAdapter;

import logic.CommandEnteredHistoryHandler;
import logic.LockApp;
import logic.Menu;
import utility.IndicatorMessagePair;
import utility.MessageList;

/**
 * This class is for the UI
 * 
 */

public class SmtSurvival extends Composite {

	/**
	 * Declare the constant variables
	 */
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Composite composite = null;
	private Composite compositeBackground;
	private CTabFolder displayTaskFolder;
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
	 * @param parent of the composite created
	 * @param style value describing its behavior and appearance
	 */
	public SmtSurvival(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));

		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});

		// this is to create the background by calling the backgroundComposite
		// method
		backgroundComposite();
		
		// this is to create the tab folder by calling the tabFolder method
		tabFolder();

		// this will create the tab items by calling the corresponding method
		// for the tab
		createTabMain();
		createTabAll();
		createTabToday();
		createTabCompleted();
		createTabPending();
		createTabBlocked();

		displayTaskFolder.setSelection(tabMain);
		tabMain.setControl(scMain);

		// this is to create the combo box by calling the comboBox method
		createComboBox();

		displayTaskFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				tabControl(event);
			}
		});
	}

	/**
	 * This method is to create the combo box
	 */
	private void createComboBox() {
		combo = new Combo(compositeBackground, SWT.NONE);
		combo.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		combo.setLocation(10, 10);
		combo.setSize(435, 28);
		combo.setText("Enter command here");
		combo.setToolTipText("Enter command to manage your tasks");
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
	}

	/**
	 * This method is to create the tab "blocked"
	 */
	private void createTabBlocked() {
		tabBlocked = new CTabItem(displayTaskFolder, SWT.NONE);
		tabBlocked.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		tabBlocked.setText("  Blocked  ");
		tabBlocked.setToolTipText("Select this tab to show the Blocked tasks");

		scBlocked = new ScrolledComposite(displayTaskFolder, SWT.BORDER | SWT.V_SCROLL);
		tabBlocked.setControl(scBlocked);
		composite = new Composite(scBlocked, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblBlocked = new Label(composite, SWT.NONE);
		lblBlocked.setForeground(SWTResourceManager.getColor(255, 0, 0));
		lblBlocked.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.BOLD));
		lblBlocked.setText("This page is for Blocked Tasks");

		scBlocked.setContent(composite);
		scMouseWheel(scBlocked);
		scBlocked.setExpandVertical(true);
		scBlocked.setMinSize(composite.computeSize(2000, 2000));
	}

	/**
	 * This method is to create the tab "pending"
	 */
	private void createTabPending() {
		tabPending = new CTabItem(displayTaskFolder, SWT.NONE);
		tabPending.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		tabPending.setText("  Pending  ");
		tabPending.setToolTipText("Select this tab to show the Pending tasks");

		scPending = new ScrolledComposite(displayTaskFolder, SWT.BORDER | SWT.V_SCROLL);
		tabPending.setControl(scPending);
		composite = new Composite(scPending, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblPending = new Label(composite, SWT.NONE);
		lblPending.setForeground(SWTResourceManager.getColor(255, 102, 0));
		lblPending.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.BOLD));
		lblPending.setText("This page is for Pending Tasks");

		scPending.setContent(composite);
		scMouseWheel(scPending);
		scPending.setExpandVertical(true);
		scPending.setMinSize(composite.computeSize(2000, 2000));
	}

	/**
	 * This method is to create the tab "completed"
	 */
	private void createTabCompleted() {
		tabCompleted = new CTabItem(displayTaskFolder, SWT.NONE);
		tabCompleted.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		tabCompleted.setText("  Completed  ");
		tabCompleted.setToolTipText("Select this tab to show the Completed tasks");

		scCompleted = new ScrolledComposite(displayTaskFolder, SWT.BORDER
				| SWT.V_SCROLL);
		tabCompleted.setControl(scCompleted);
		composite = new Composite(scCompleted, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblCompleted = new Label(composite, SWT.NONE);
		lblCompleted.setForeground(SWTResourceManager.getColor(34, 139, 34));
		lblCompleted.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.BOLD));
		lblCompleted.setText("This page is for Completed Tasks");

		scCompleted.setContent(composite);
		scMouseWheel(scCompleted);
		scCompleted.setExpandVertical(true);
		scCompleted.setMinSize(composite.computeSize(2000, 2000));
	}

	/**
	 * This method is to create the tab "today"
	 */
	private void createTabToday() {
		tabToday = new CTabItem(displayTaskFolder, SWT.NONE);
		tabToday.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		tabToday.setText("  Today  ");
		tabToday.setToolTipText("Select this tab to show the Today's tasks");

		scToday = new ScrolledComposite(displayTaskFolder, SWT.BORDER | SWT.V_SCROLL);
		tabToday.setControl(scToday);
		composite = new Composite(scToday, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblToday = new Label(composite, SWT.NONE);
		lblToday.setForeground(SWTResourceManager.getColor(128, 0, 128));
		lblToday.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.BOLD));
		lblToday.setText("This page is for Today's Tasks");

		scToday.setContent(composite);
		scMouseWheel(scToday);
		scToday.setExpandVertical(true);
		scToday.setMinSize(composite.computeSize(2000, 2000));
	}

	/**
	 * This method is to create the tab "all"
	 */
	private void createTabAll() {
		tabAll = new CTabItem(displayTaskFolder, SWT.NONE);
		tabAll.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		tabAll.setText("  All  ");
		tabAll.setToolTipText("Select this tab to show all tasks");

		scAll = new ScrolledComposite(displayTaskFolder, SWT.BORDER | SWT.V_SCROLL);
		tabAll.setControl(scAll);
		composite = new Composite(scAll, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblAll = new Label(composite, SWT.NONE);
		lblAll.setForeground(SWTResourceManager.getColor(0, 128, 128));
		lblAll.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.BOLD));
		lblAll.setText("This page is for All Tasks");

		scAll.setContent(composite);
		scMouseWheel(scAll);
		scAll.setExpandVertical(true);
		scAll.setMinSize(composite.computeSize(2000, 2000));
	}

	/**
	 * This method is to create the tab "main"
	 */
	private void createTabMain() {
		tabMain = new CTabItem(displayTaskFolder, SWT.NONE);
		tabMain.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		tabMain.setText("  Main  ");
		tabMain.setToolTipText("Select this tab to show the Main page");

		scMain = new ScrolledComposite(displayTaskFolder, SWT.BORDER | SWT.V_SCROLL);
		scMain.setShowFocusedControl(true);
		tabMain.setControl(scMain);
		composite = new Composite(scMain, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblMain = new Label(composite, SWT.NONE);
		lblMain.setForeground(SWTResourceManager.getColor(30, 144, 255));
		lblMain.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.BOLD));
		lblMain.setAlignment(SWT.CENTER);
		lblMain.setText("Welcome to Smart Management Tool");

		scMain.setContent(composite);
		scMouseWheel(scMain);
		scMain.setExpandVertical(true);
		scMain.setFocus();
		scMain.setMinSize(composite.computeSize(2000, 2000));
	}

	/**
	 * This method is use to create tab folder
	 */
	private void tabFolder() {
		displayTaskFolder = new CTabFolder(compositeBackground, SWT.BORDER);
		displayTaskFolder.setLocation(10, 44);
		displayTaskFolder.setSize(435, 401);
		displayTaskFolder.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		toolkit.adapt(displayTaskFolder);
		toolkit.paintBordersFor(displayTaskFolder);
	}

	/**
	 * This method is use to create the background using composite
	 */
	private void backgroundComposite() {
		compositeBackground = new Composite(this, SWT.NONE);
		compositeBackground.setBackground(SWTResourceManager.getColor(102, 153, 204));
		compositeBackground.setBounds(0, 0, 454, 455);
	}

	/**
	 * This method is to activate the Mouse Wheel for the scroll composite
	 * @param scReceived received the corresponding scrolled composite
	 */
	private void scMouseWheel(final ScrolledComposite scReceived) {
		scReceived.addListener(SWT.Activate, new Listener() {
			public void handleEvent(Event e) {
				scReceived.setFocus();
			}
		});
	}

	/**
	 * This method is to get the selection of the corresponding tab and set the tab control by calling setTabControl method
	 * @param event of the selection event
	 */
	private void tabControl(SelectionEvent event) {

		lblMain = new Label(displayTaskFolder, SWT.NONE);

		if (displayTaskFolder.getSelection() == tabMain) {
			setTabControl(tabMain, lblMain, scMain, savedExistingContents);
		} else if (displayTaskFolder.getSelection() == tabAll) {
			setTabControl(tabAll, lblAll, scAll, "Display All");
		} else if (displayTaskFolder.getSelection() == tabToday) {
			setTabControl(tabToday, lblToday, scToday, "Display Today");
		} else if (displayTaskFolder.getSelection() == tabCompleted) {
			setTabControl(tabCompleted, lblCompleted, scCompleted,
					"Display Completed");
		} else if (displayTaskFolder.getSelection() == tabPending) {
			setTabControl(tabPending, lblPending, scPending, "Display Pending");
		} else if (displayTaskFolder.getSelection() == tabBlocked) {
			setTabControl(tabBlocked, lblBlocked, scBlocked, "Display Block");
		}
	}

	/**
	 * This method is to pass control to the logic component's menu class to
	 * read the output from the command text box
	 * @param e represent the keyboard event
	 */
	private void passControl(KeyEvent e) {

		String output = new String();

		if ((e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR)
				&& combo.getSelectionIndex() == -1) {
			CommandEnteredHistoryHandler.newCommandEntered(saveCurrentCommand);
			setTabControl(tabMain, lblMain, scMain, saveCurrentCommand);
			combo.removeAll();
			lblMain.setAlignment(SWT.LEFT);
			savedExistingContents = lblMain.getText();
		} else if (combo.getSelectionIndex() > -1) {
			// do nth
		} else if (!flagForSwitchTab && (e.keyCode != SWT.ARROW_UP && e.keyCode != SWT.ARROW_DOWN)) {

			output = controller.getHint(combo.getText());
			displayTaskFolder.setSelection(tabMain);

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

	/**
	 * This method will help to switch the tab control using short cut key
	 * @param e represent the keyboard event
	 */
	private void switchTabControl(KeyEvent e) {
		flagForSwitchTab = true;
		
		if ((((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '1'))) {
			displayTaskFolder.setSelection(tabMain);
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

	/**
	 * This method is to set the control of the tab
	 * @param tabReceived receive the corresponding tab item
	 * @param lblReceive receive the corresponding lbl
	 * @param scReceived receive the corresponding sc composite
	 * @param command
	 */
	private void setTabControl(CTabItem tabReceived, Label lblReceive,
			ScrolledComposite scReceived, String command) {
		displayTaskFolder.setSelection(tabReceived);
		tabReceived.setControl(scReceived);
		lblReceive.setText(controller.commandExecution(command));
	}

	/**
	 * This method will load the command history so that user can use the up
	 * down button for execution
	 * @param e represent the keyboard event
	 */
	private void loadCommandHistory(KeyEvent e) {

		if (((e.stateMask & SWT.SHIFT) == SWT.SHIFT) && e.keyCode == SWT.ARROW_LEFT) {
			combo.select(-1);
			combo.setText(CommandEnteredHistoryHandler.retrieveCommand(CommandEnteredHistoryHandler.getPrevCmd()));
			saveCurrentCommand = combo.getText();
		} else if (((e.stateMask & SWT.SHIFT) == SWT.SHIFT) && e.keyCode == SWT.ARROW_RIGHT) {
			combo.select(-1);
			combo.setText(CommandEnteredHistoryHandler.retrieveCommand(CommandEnteredHistoryHandler.getAfterCmd()));
			saveCurrentCommand = combo.getText();
		}
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
	
	/**
	 * This method is use to open the shell
	 * @param shell
	 */
	private static void shellOpen(Shell shell) {
		shell.open();
		SmtSurvival Smt = new SmtSurvival(shell, SWT.NONE);
		Smt.pack();
		shell.pack();
	}

	/**
	 * This is the main method
	 * @param args is an array of String objects
	 */
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE));
		shell.setText("Smart Management Tool");
		
		// this will prevent user from launching the program when one is running
		LockApp.checkExistingApp();
		
		// this will open the shell and start up the program
		shellOpen(shell);

		// this will call to set up files by
		setUpFiles();

		// run the event loop as long as the window is open
		while (!shell.isDisposed()) {
			
			// read the next OS event queue and transfer it to a SWT event
			if (!display.readAndDispatch())
				// if there are currently no other OS event to process
				// sleep until the next OS event is available
				display.sleep();
		}
		
		// this will release the program
		LockApp.unLockApp();
		
		// disposes all associated windows and their components
		display.dispose();
	}
}