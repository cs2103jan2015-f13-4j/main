import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import javax.swing.JList;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.keys.SWTKeySupport;
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
import logic.CommandEnteredHistoryHandler;
import logic.Menu;

import org.eclipse.swt.widgets.Combo;

/**
 * 
 * @author SHUNA
 *
 */

public class SmtSurvival extends Composite {

	/**
	 * Constant variables declared
	 */
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private TabItem tbtmMain;
	private TabItem tbtmAll;
	private TabItem tbtmToday;
	private TabItem tbtmCompleted;
	private TabItem tbtmPending;
	private TabFolder displayTaskFolder;
	private Composite composite_1;
	private static Label lblDisplay;
	private static Menu controller;
	private TabItem tbtmBlocked;
	private static String savedExistingContents = new String();
	private static boolean flagForSwitchTab = false;
	private static String saveCurrentCommand = new String();
	private Combo combo;

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

		// add a listener to listen to the tab behavior
		displayTaskFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				tabControl(event);
			}
		});
	}

	/**
	 * This method creates a group at the tab region
	 * 
	 * @return
	 */
	private Group createGroupForTabRegion() {
		Group group = new Group(this, SWT.NONE);
		group.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		group.setFont(SWTResourceManager.getFont("Century Gothic", 10, SWT.BOLD));
		GridData gd_group = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 
				2);
		gd_group.heightHint = 358;
		gd_group.widthHint = 424;
		group.setLayoutData(gd_group);
		toolkit.adapt(group);
		toolkit.paintBordersFor(group);
		
		combo = new Combo(group, SWT.NONE);
		combo.addKeyListener(new KeyAdapter() {
			@Override	
			public void keyPressed(KeyEvent e) {
				switchTabControl(e);
				loadCommandHistory(e);
			}
		});
		combo.setBounds(0, 10, 450, 55);
		toolkit.adapt(combo);
		toolkit.paintBordersFor(combo);
		return group;
	}

	/**
	 * This method display the tab folder created
	 * 
	 * @param group
	 */
	private void tabFolder(Group group) {

		displayTaskFolder = new TabFolder(group, SWT.NONE);
		displayTaskFolder.setFont(SWTResourceManager.getFont("Segoe UI Black", 10, SWT.NORMAL));
		displayTaskFolder.setBounds(0, 39, 430, 342);
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
		lblDisplay.setForeground(SWTResourceManager.getColor(0, 0, 0));
		lblDisplay.setAlignment(SWT.CENTER);
		lblDisplay.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		
		tbtmMain.setControl(lblDisplay);
		lblDisplay.setText("Welcome to Smart Management Tool");
		lblDisplay.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));

		// This is for Schedule Tab
		tbtmAll = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmAll.setText("All");
		tbtmAll.setToolTipText("This tab will show all scheduled tasks");

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
		
		tbtmBlocked = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmBlocked.setToolTipText("This tab will show all Blocked tasks");
		tbtmBlocked.setText("Blocked");
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
			CommandEnteredHistoryHandler.newCommandEntered(combo.getText());
			output = controller.commandExecution(combo.getText());
			displayTaskFolder.setSelection(tbtmMain);
			tbtmMain.setControl(lblDisplay);
			lblDisplay.setText(output);
			combo.removeAll();
			//cmdTxtBox.setText("");
			savedExistingContents = lblDisplay.getText();
		} 
		
		else if(!flagForSwitchTab && (e.keyCode != SWT.ARROW_UP && e.keyCode != SWT.ARROW_DOWN)){

			output = controller.getHint(combo.getText());
			displayTaskFolder.setSelection(tbtmMain);
			tbtmMain.setControl(lblDisplay);
			//lblDisplay.setText(output); 
			
			if(output.contains(MessageList.MESSAGE_HINT_INVALID) || output.contains(MessageList.MESSAGE_INVAILD)){
				return;
			}
			
			if(combo.getItemCount() > 0){
				combo.remove(0, combo.getItemCount() -1);
			}
			String[] outputArr = output.split("\n");
			for(String indiString: outputArr){
				combo.add(indiString);
			}
			
			if(outputArr.length > 0 && !output.isEmpty()){
				combo.setListVisible(true);
			}
			else{
				combo.setListVisible(false);
			}
			
			savedExistingContents = lblDisplay.getText();
		}
		saveCurrentCommand = combo.getText();
		
	}
	
	private void switchTabControl(KeyEvent e){
		flagForSwitchTab = true;
		if((((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '1'))) {
			setTabControl(tbtmMain, lblDisplay, savedExistingContents);
		}
		else if(((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '2')) {
			setTabControl(tbtmAll, lblDisplay, "Display All");
		}
		else if(((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '3')) {
			flagForSwitchTab = true;
			setTabControl(tbtmToday, lblDisplay, "Display Today");
		}
		else if(((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '4')) {
			setTabControl(tbtmCompleted, lblDisplay, "Display Completed");
		}
		else if(((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '5')) {
			setTabControl(tbtmPending, lblDisplay, "Display Pending");
		}
		else if(((e.stateMask & SWT.ALT) == SWT.ALT) && (e.keyCode == '6')) {
			setTabControl(tbtmBlocked, lblDisplay, "Display Block");
		}
		else if((e.stateMask & SWT.ALT) == SWT.ALT){
			//do nothing
		}
		else{
			flagForSwitchTab = false;
			passControl(e);
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
			setTabControl(tbtmMain, lblDisplay, savedExistingContents);
		} else if (displayTaskFolder.getSelection()[0].equals(tbtmAll)) {
			setTabControl(tbtmAll, lblDisplay, "Display All");
		} else if (displayTaskFolder.getSelection()[0].equals(tbtmToday)) {
			setTabControl(tbtmToday, lblDisplay, "Display Today");
		} else if (displayTaskFolder.getSelection()[0].equals(tbtmCompleted)) {
			setTabControl(tbtmCompleted, lblDisplay, "Display Completed");
		} else if (displayTaskFolder.getSelection()[0].equals(tbtmPending)) {
			setTabControl(tbtmPending, lblDisplay, "Display Pending");
		} else if (displayTaskFolder.getSelection()[0].equals(tbtmBlocked)) {
			setTabControl(tbtmBlocked, lblDisplay, "Display Block");
		} 
	}
	
	private void setTabControl(TabItem tab, Label lblReceive, String command){
		displayTaskFolder.setSelection(tab);
		tab.setControl(lblReceive);
		lblReceive.setText(controller.commandExecution(command));
	}
	
	/**
	 * This method will load the command history so that user can use the up down button for execution
	 * @param e
	 */
	private void loadCommandHistory(KeyEvent e) {
		
		if(((e.stateMask & SWT.SHIFT) == SWT.SHIFT) && e.keyCode == SWT.ARROW_LEFT) {
			combo.select(-1);
			combo.setText(CommandEnteredHistoryHandler.retrieveCommand(CommandEnteredHistoryHandler.getPrevCmd()));
			saveCurrentCommand = combo.getText();
		}
		else if(((e.stateMask & SWT.SHIFT) == SWT.SHIFT) && e.keyCode == SWT.ARROW_RIGHT) {
			combo.select(-1);
			combo.setText(CommandEnteredHistoryHandler.retrieveCommand(CommandEnteredHistoryHandler.getAfterCmd()));
			saveCurrentCommand = combo.getText();
		}
	}
}