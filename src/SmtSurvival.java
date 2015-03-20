import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
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

public class SmtSurvival extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text cmdTxtBox;
	private TabItem tbtmMain;
	private TabItem tbtmSchedule;
	private TabItem tbtmToday;
	private TabItem tbtmCompleted;
	private TabItem tbtmPending;
	private static TabFolder displayTaskFolder;
	private static Label lblDisplay;
	private static Menu controller = new Menu();

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SmtSurvival(Composite parent, int style) {
		super(parent, SWT.BORDER | SWT.NO_BACKGROUND);
		//setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new GridLayout(2, false));
		new Label(this, SWT.NONE);
		
		Group group = new Group(this, SWT.NONE);
		GridData gd_group = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2);
		gd_group.heightHint = 358;
		gd_group.widthHint = 424;
		group.setLayoutData(gd_group);
		toolkit.adapt(group);
		toolkit.paintBordersFor(group);
		
		displayTaskFolder = new TabFolder(group, SWT.NONE);
		
		displayTaskFolder.setBounds(0, 10, 430, 371);
		toolkit.adapt(displayTaskFolder);
		toolkit.paintBordersFor(displayTaskFolder);
		
		tbtmMain = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmMain.setText("Main");
		tbtmMain.setToolTipText("Click this tab to show the Main page");
		lblDisplay = new Label(displayTaskFolder, SWT.NONE);
		lblDisplay.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		tbtmMain.setControl(lblDisplay);
		lblDisplay.setText("Welcome to Smart Management Tool");
		
		tbtmSchedule = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmSchedule.setText("Schedule");
		tbtmSchedule.setToolTipText("Click this tab to show all the Schedules");
		
		tbtmToday = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmToday.setText("Today");
		tbtmToday.setToolTipText("Click this tab to show Today's tasks");
		
		tbtmCompleted = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmCompleted.setText("Completed");
		tbtmCompleted.setToolTipText("Click this tab to show all the Completed tasks");
		
		tbtmPending = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmPending.setText("Pending");
		tbtmPending.setToolTipText("Click this tab to show all the Pending tasks");
		
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		lblDisplay = new Label(displayTaskFolder, SWT.NONE);
		
		// add a listener to listen to the tab behavior
		displayTaskFolder.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event){
				tabControl(event, lblDisplay);
			}
		});
		
		Composite composite_1 = new Composite(this, SWT.NONE);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.heightHint = 83;
		gd_composite_1.widthHint = 429;
		composite_1.setLayoutData(gd_composite_1);
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);
		
		Label lblCommand = new Label(composite_1, SWT.NONE);
		lblCommand.setBounds(10, 10, 84, 20);
		toolkit.adapt(lblCommand, true, true);
		lblCommand.setText("Command :");
		
		cmdTxtBox = new Text(composite_1, SWT.BORDER);
		cmdTxtBox.setToolTipText("Enter command to manage your tasks");
		cmdTxtBox.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		cmdTxtBox.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		cmdTxtBox.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				passControl(e);
			}
		});
		
		cmdTxtBox.setBounds(100, 10, 319, 63);
		toolkit.adapt(cmdTxtBox, true, true);
	}
	
	private void passControl(KeyEvent e)
	{
		String output = new String();
		lblDisplay = new Label(displayTaskFolder, SWT.NONE);
		if(e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR){
			output = controller.commandExecution(cmdTxtBox.getText());
			
			if(lblDisplay.getText().equals("display schedule")){
				tbtmSchedule.setControl(lblDisplay);
			}
			else {
				tbtmMain.setControl(lblDisplay);
			}
			
			displayTaskFolder.setSelection(tbtmMain);
			tbtmMain.setControl(lblDisplay);
			lblDisplay.setText(output);
			
			cmdTxtBox.setText("");
		}
	}
	
	/**
	 * This method will toggle the respective tab
	 * @param event
	 */
	private void tabControl(SelectionEvent event, Label lblDisplay){
		
		if(displayTaskFolder.getSelection()[0].equals(tbtmMain)){
			tbtmMain.setControl(lblDisplay);
			lblDisplay.setText("");
		}
		else if(displayTaskFolder.getSelection()[0].equals(tbtmSchedule)){
			tbtmSchedule.setControl(lblDisplay);
			lblDisplay.setText(controller.commandExecution("display schedule"));
		}
		else if(displayTaskFolder.getSelection()[0].equals(tbtmToday)){
			tbtmToday.setControl(lblDisplay);
			lblDisplay.setText(controller.commandExecution("display today"));
		}
		else if(displayTaskFolder.getSelection()[0].equals(tbtmCompleted)){
			tbtmCompleted.setControl(lblDisplay);
			lblDisplay.setText(controller.commandExecution("display todo"));
		}
		else if(displayTaskFolder.getSelection()[0].equals(tbtmPending)){
			tbtmPending.setControl(lblDisplay);
			lblDisplay.setText(controller.commandExecution("display pending"));
		}
	}
	
	public static void main(String[] args){
		
	    Display display = new Display();
	    Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE));
	    shell.setText("Smart Management Tool");
	    
	    shell.open();
	    SmtSurvival Smt = new SmtSurvival(shell, SWT.NONE);
	    Smt.pack();
	    shell.pack();
	    
	    // setting up the files
	    IndicatorMessagePair msgPair = new IndicatorMessagePair();
	    controller.retrieveTasksAndLastUsedIndex(msgPair);
	    
	    if(!msgPair.isTrue){
	    	MessageList.printErrorMessageAndExit(msgPair.getMessage());
	    }
	    
	    while(!shell.isDisposed()){
	        if(!display.readAndDispatch()) display.sleep();
	    }
	}
}
