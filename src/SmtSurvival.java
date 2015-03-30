import java.awt.event.KeyListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import utility.IndicatorMessagePair;
import utility.MessageList;
import logic.Menu;

public class SmtSurvival extends Composite {

	/**
	 * Constant variables declared
	 */
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text cmdTxtBox;
	private TabItem tbtmMain;
	private TabItem tbtmSchedule;
	private TabItem tbtmToday;
	private TabItem tbtmCompleted;
	private TabItem tbtmPending;
	private TabFolder displayTaskFolder;
	private static Label lblDisplay;
	private static Menu controller;

	/**
	 * This method will be first executed when program runs
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE));
		shell.setText("Smart Management Tool");

		open(shell);
		setUpFiles();
		checkShellDisposed(display, shell);
	}

	/**
	 * This method is to open the shell for SmtSurvival
	 * 
	 * @param shell
	 */
	private static void open(Shell shell) {

		shell.open();
		SmtSurvival Smt = new SmtSurvival(shell, SWT.NONE);
		Smt.pack();
		shell.pack();
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
	 * This method will check for shell disposed
	 * 
	 * @param display
	 * @param shell
	 */
	private static void checkShellDisposed(Display display, Shell shell) {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SmtSurvival(Composite parent, int style) {
		super(parent, SWT.BORDER | SWT.NO_BACKGROUND);
		// setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});

		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new GridLayout(2, false));
		new Label(this, SWT.NONE);

		// Created a method that create a group for the tab region for the UI
		Group group = createGroupForTabRegion();

		// will call this method to display the tab folder created
		tabFolder(group);

		// will call this method to show the tab item display
		tabItem();

		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		// add a listener to listen to the tab behavior
		displayTaskFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				tabControl(event);
			}
		});

		// creating composite
		Composite composite_1 = createComposite();

		// show command label at UI
		commandLabel(composite_1);

		// setting command text box next beside the command label at UI, for
		// user to key in
		setCommandTextBox(composite_1);
	}

	/**
	 * This method creates a group at the tab region
	 * 
	 * @return
	 */
	private Group createGroupForTabRegion() {
		Group group = new Group(this, SWT.NONE);
		GridData gd_group = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1,
				2);
		gd_group.heightHint = 358;
		gd_group.widthHint = 424;
		group.setLayoutData(gd_group);
		toolkit.adapt(group);
		toolkit.paintBordersFor(group);
		return group;
	}

	/**
	 * This method display the tab folder created
	 * 
	 * @param group
	 */
	private void tabFolder(Group group) {

		displayTaskFolder = new TabFolder(group, SWT.NONE);
		displayTaskFolder.setBounds(0, 10, 430, 371);
		toolkit.adapt(displayTaskFolder);
		toolkit.paintBordersFor(displayTaskFolder);
		
		displayTaskFolder.forceFocus();
	}

	/**
	 * This method is to display the tab item along with the tool tip text for
	 * each tab item
	 */
	private void tabItem() {

		// This is for Main Tab
		tbtmMain = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmMain.setText("Main");
		tbtmMain.setToolTipText("This tab will show the all the tasks");
		lblDisplay = new Label(displayTaskFolder, SWT.NONE);
		lblDisplay.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		tbtmMain.setControl(lblDisplay);
		lblDisplay.setText("Welcome to Smart Management Tool");

		// This is for Schedule Tab
		tbtmSchedule = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmSchedule.setToolTipText("This tab will show all scheduled tasks");
		tbtmSchedule.setText("Schedule");

		// This is for Today Tab
		tbtmToday = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmToday.setToolTipText("This tab will show today's tasks");
		tbtmToday.setText("Today");

		// This is for Completed Tab
		tbtmCompleted = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmCompleted.setToolTipText("This tab will show the tasks completed");
		tbtmCompleted.setText("Completed");

		// This is for Pending Tab
		tbtmPending = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmPending.setToolTipText("This tab will show all pending tasks");
		tbtmPending.setText("Pending");
	}

	/**
	 * This method creates a composite for the UI
	 * 
	 * @return
	 */
	private Composite createComposite() {
		Composite composite_1 = new Composite(this, SWT.NONE);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite_1.heightHint = 83;
		gd_composite_1.widthHint = 429;
		composite_1.setLayoutData(gd_composite_1);
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);
		return composite_1;
	}

	/**
	 * This method is to create a label named "Command:" which indicate where
	 * should the user key in their commands
	 * 
	 * @param composite_1
	 */
	private void commandLabel(Composite composite_1) {
		Label lblCommand = new Label(composite_1, SWT.NONE);
		lblCommand.setBounds(10, 10, 84, 20);
		toolkit.adapt(lblCommand, true, true);
		lblCommand.setText("Command :");
	}

	/**
	 * This method set the command text box for user to enter their commands
	 * 
	 * @param composite_1
	 */
	private void setCommandTextBox(Composite composite_1) {
		cmdTxtBox = new Text(composite_1, SWT.BORDER);
		cmdTxtBox.setToolTipText("Please enter a command here");
		cmdTxtBox.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		cmdTxtBox.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		cmdTxtBox.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				passControl(e);
			}
		});

		cmdTxtBox.setBounds(100, 10, 319, 63);
		toolkit.adapt(cmdTxtBox, true, true);
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
		lblDisplay = new Label(displayTaskFolder, SWT.NONE);
		if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
			output = controller.commandExecution(cmdTxtBox.getText());
			displayTaskFolder.setSelection(tbtmMain);
			tbtmMain.setControl(lblDisplay);
			lblDisplay.setText(output);

			cmdTxtBox.setText("");
		}
	}

	/**
	 * This method will toggle the respective tab
	 * 
	 * @param event
	 */
	private void tabControl(SelectionEvent event) {

		lblDisplay = new Label(displayTaskFolder, SWT.NONE);

		if (displayTaskFolder.getSelection()[0].equals(tbtmMain)) {
			tbtmMain.setControl(lblDisplay);
			lblDisplay.setText("");
		} else if (displayTaskFolder.getSelection()[0].equals(tbtmSchedule)) {
			tbtmSchedule.setControl(lblDisplay);
			lblDisplay.setText(controller.commandExecution("display schedule"));
		} else if (displayTaskFolder.getSelection()[0].equals(tbtmToday)) {
			tbtmToday.setControl(lblDisplay);
			lblDisplay.setText(controller.commandExecution("display today"));
		} else if (displayTaskFolder.getSelection()[0].equals(tbtmCompleted)) {
			tbtmCompleted.setControl(lblDisplay);
			lblDisplay.setText(controller.commandExecution("display todo"));
		} else if (displayTaskFolder.getSelection()[0].equals(tbtmPending)) {
			tbtmPending.setControl(lblDisplay);
			lblDisplay.setText(controller.commandExecution("display pending"));
		}
	}
}
