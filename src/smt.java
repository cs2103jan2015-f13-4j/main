import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;

import logic.Menu;

public class smt extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Composite composite = null;
	private CTabFolder tabFolder;
	private CTabItem tabMain;
	private CTabItem tabSchedule;
	private CTabItem tabToday;
	private CTabItem tabCompleted;
	private CTabItem tabPending;
	private CTabItem tabBlocked;
	private Label lblMain;
	private Label lblSchedule;
	private Label lblToday;
	private Label lblCompleted;
	private Label lblPending;
	private Label lblBlocked;
	private ScrolledComposite scMain;
	private ScrolledComposite scSchedule;
	private ScrolledComposite scToday;
	private ScrolledComposite scCompleted;
	private ScrolledComposite scPending;
	private ScrolledComposite scBlocked;
	private Composite composite_1;
	private Label lblCommand;
	private Text cmdTextBox;
	private static Menu controller;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public smt(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(173, 216, 230));

		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		
		tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setBounds(10, 10, 435, 452);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		toolkit.adapt(tabFolder);
		toolkit.paintBordersFor(tabFolder);

		/* Main Tab */
		tabMain = new CTabItem(tabFolder, SWT.NONE);
		tabMain.setText("Main");
		tabMain.setToolTipText("Click this tab to show the Main page");

		scMain = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		tabMain.setControl(scMain);
		composite = new Composite(scMain, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblMain = new Label(composite, SWT.NONE);
		lblMain.setAlignment(SWT.CENTER);
		lblMain.setText("Welcome to Smart Management Tool");

		scMain.setContent(composite);
		scMain.setExpandVertical(true);
		scMain.setFocus();
		scMain.setMinSize(composite.computeSize(1000, 1000));

		/* Schedule Tab */
		tabSchedule = new CTabItem(tabFolder, SWT.NONE);
		tabSchedule.setText("Schedule");

		scSchedule = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		//tabSchedule.setControl(scSchedule);
		composite = new Composite(scSchedule, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);
		
		lblSchedule = new Label(composite, SWT.NONE);
		lblSchedule.setText("This page is for Schedule Tasks");

		scSchedule.setContent(composite);
		scSchedule.setExpandVertical(true);
		scSchedule.setMinSize(composite.computeSize(1000, 1000));

		/* Today Tab */
		tabToday = new CTabItem(tabFolder, SWT.NONE);
		tabToday.setText("Today");

		scToday = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		//tabToday.setControl(scToday);
		composite = new Composite(scToday, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblToday = new Label(composite, SWT.NONE);
		lblToday.setText("This page is for Today's Tasks");

		scToday.setContent(composite);
		scToday.setExpandVertical(true);
		scToday.setMinSize(composite.computeSize(1000, 1000));

		/* Completed Tab */
		tabCompleted = new CTabItem(tabFolder, SWT.NONE);
		tabCompleted.setText("Completed");

		scCompleted = new ScrolledComposite(tabFolder, SWT.BORDER
				| SWT.V_SCROLL);
		//tabCompleted.setControl(scCompleted);
		composite = new Composite(scCompleted, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblCompleted = new Label(composite, SWT.NONE);
		lblCompleted.setText("This page is for Completed Tasks");

		scCompleted.setContent(composite);
		scCompleted.setExpandVertical(true);
		scCompleted.setMinSize(composite.computeSize(1000, 1000));

		/* Pending Tab */
		tabPending = new CTabItem(tabFolder, SWT.NONE);
		tabPending.setText("Pending");

		scPending = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		//tabPending.setControl(scPending);
		composite = new Composite(scPending, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblPending = new Label(composite, SWT.NONE);
		lblPending.setText("This page is for Pending Tasks");

		scPending.setContent(composite);
		scPending.setExpandVertical(true);
		scPending.setMinSize(composite.computeSize(1000, 1000));
		
		/* Blocked Tab */
		tabBlocked = new CTabItem(tabFolder, SWT.NONE);
		tabBlocked.setText("Blocked");

		scBlocked = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		//tabBlocked.setControl(scBlocked);
		composite = new Composite(scBlocked, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblBlocked = new Label(composite, SWT.NONE);
		lblBlocked.setText("This page is for Blocked Tasks");

		scBlocked.setContent(composite);
		scBlocked.setExpandVertical(true);
		scBlocked.setMinSize(composite.computeSize(1000, 1000));
		
		tabFolder.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				tabControl(event);
			}
		});
		
		composite_1 = new Composite(this, SWT.NONE);
		composite_1.setBounds(10, 468, 435, 78);

		lblCommand = new Label(composite_1, SWT.NONE);
		lblCommand.setBounds(10, 10, 85, 20);
		toolkit.adapt(lblCommand, true, true);
		lblCommand.setText("Command :");

		cmdTextBox = new Text(composite_1, SWT.BORDER);
		cmdTextBox.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				passControl(e);
			}
		});
		cmdTextBox.setToolTipText("Please enter a command here");
		cmdTextBox.setFocus();
		cmdTextBox.setBounds(101, 10, 324, 58);
		toolkit.adapt(composite_1, true, true);
	}

	private void passControl(KeyEvent e) {
		String output = new String();
		lblMain = new Label(tabFolder, SWT.NONE);
		if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
			output = controller.commandExecution(cmdTextBox.getText());
			tabFolder.setSelection(tabMain);
			tabMain.setControl(lblMain);
			lblMain.setText(output);

			cmdTextBox.setText("");
		}
	}
	
	private void tabControl(SelectionEvent event){
		if(tabFolder.getSelectionIndex() == 0){
			tabMain.setControl(scMain);
			lblMain.setText("");
		}
		
		if(tabFolder.getSelection() == tabMain){
			
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
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
