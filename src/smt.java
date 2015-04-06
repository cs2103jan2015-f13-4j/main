import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * 
 * @author BAOYI, SHUNA
 * 
 */

public class smt extends Composite {
	
	/**
	 * Declaration for constant variables
	 */
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Composite compositeBackground;
	private Composite composite;
	private Combo comboBox;
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


	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public smt(Composite parent, int style) {
		super(parent, style);
		
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});

		// Background composite
		compositeBackground = new Composite(this, SWT.NONE);
		compositeBackground.setBackground(SWTResourceManager.getColor(102, 153, 204));
		compositeBackground.setBounds(0, 0, 500, 500);
		
		comboBox = new Combo(compositeBackground, SWT.NONE);
		comboBox.setBounds(10, 10, 480, 28);
		comboBox.setFocus();
		toolkit.adapt(comboBox);
		toolkit.paintBordersFor(comboBox);
		
		CTabFolder tabFolder = new CTabFolder(compositeBackground, SWT.BORDER);
		tabFolder.setBounds(10, 44, 480, 446);
		toolkit.adapt(tabFolder);
		toolkit.paintBordersFor(tabFolder);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		/* Main Tab */
		tabMain = new CTabItem(tabFolder, SWT.NONE);
		tabMain.setFont(SWTResourceManager.getFont("Segoe UI Black", 9, SWT.NORMAL));
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
		tabAll = new CTabItem(tabFolder, SWT.NONE);
		tabAll.setFont(SWTResourceManager.getFont("Segoe UI Black", 9, SWT.NORMAL));
		tabAll.setText("All");

		scAll = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		tabAll.setControl(scAll);
		composite = new Composite(scAll, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblAll = new Label(composite, SWT.NONE);
		lblAll.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.NORMAL));
		lblAll.setText("This page is for All Tasks");

		scAll.setContent(composite);
		scAll.setExpandVertical(true);
		scAll.setMinSize(composite.computeSize(1000, 1000));
		
		/* Today Tab */
		tabToday = new CTabItem(tabFolder, SWT.NONE);
		tabToday.setFont(SWTResourceManager.getFont("Segoe UI Black", 9, SWT.NORMAL));
		tabToday.setText("Today");

		scToday = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		tabToday.setControl(scToday);
		composite = new Composite(scToday, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblToday = new Label(composite, SWT.NONE);
		lblToday.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.NORMAL));
		lblToday.setText("This page is for Today's Tasks");

		scToday.setContent(composite);
		scToday.setExpandVertical(true);
		scToday.setMinSize(composite.computeSize(1000, 1000));
		
		/* Completed Tab */
		tabCompleted = new CTabItem(tabFolder, SWT.NONE);
		tabCompleted.setFont(SWTResourceManager.getFont("Segoe UI Black", 9, SWT.NORMAL));
		tabCompleted.setText("Completed");

		scCompleted = new ScrolledComposite(tabFolder, SWT.BORDER
				| SWT.V_SCROLL);
		tabCompleted.setControl(scCompleted);
		composite = new Composite(scCompleted, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblCompleted = new Label(composite, SWT.NONE);
		lblCompleted.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.NORMAL));
		lblCompleted.setText("This page is for Completed Tasks");

		scCompleted.setContent(composite);
		scCompleted.setExpandVertical(true);
		scCompleted.setMinSize(composite.computeSize(1000, 1000));
		
		/* Pending Tab */
		tabPending = new CTabItem(tabFolder, SWT.NONE);
		tabPending.setFont(SWTResourceManager.getFont("Segoe UI Black", 9, SWT.NORMAL));
		tabPending.setText("Pending");

		scPending = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		tabPending.setControl(scPending);
		composite = new Composite(scPending, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblPending = new Label(composite, SWT.NONE);
		lblPending.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.NORMAL));
		lblPending.setText("This page is for Pending Tasks");

		scPending.setContent(composite);
		scPending.setExpandVertical(true);
		scPending.setMinSize(composite.computeSize(1000, 1000));
		
		/* Blocked Tab */
		tabBlocked = new CTabItem(tabFolder, SWT.NONE);
		tabBlocked.setFont(SWTResourceManager.getFont("Segoe UI Black", 9, SWT.NORMAL));
		tabBlocked.setText("Blocked");

		scBlocked = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.V_SCROLL);
		tabBlocked.setControl(scBlocked);
		composite = new Composite(scBlocked, SWT.None);
		composite.setLayout(new FillLayout());
		composite.setSize(435, 452);

		lblBlocked = new Label(composite, SWT.NONE);
		lblBlocked.setFont(SWTResourceManager.getFont("Century Gothic", 11, SWT.NORMAL));
		lblBlocked.setText("This page is for Blocked Tasks");

		scBlocked.setContent(composite);
		scBlocked.setExpandVertical(true);
		scBlocked.setMinSize(composite.computeSize(1000, 1000));
		
		tabFolder.setSelection(tabMain);
		tabMain.setControl(scMain);
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE));
		shell.setText("Smart Management Tool");

		// the layout manager handle the layout
		// of the widgets in the container
		shell.setLayout(new GridLayout());

		// DateTime calendar = new DateTime(parent, SWT.CALENDAR);
		// DateTime date = new DateTime(parent, SWT.DATE);
		// DateTime time = new DateTime(parent, SWT.TIME);
		// // Date Selection as a drop-down
		// DateTime dateD = new DateTime(parent, SWT.DATE | SWT.DROP_DOWN);

		shell.open();
		smt Smt = new smt(shell, SWT.NONE);
		Smt.pack();
		shell.pack();

		// run the event loop as long as the window is open
		while (!shell.isDisposed()) {

			// read the next OS event queue and transfer it to a SWT event
			if (!display.readAndDispatch()) {
				// if there are currently no other OS event to process
				// sleep until the next OS event is available
				display.sleep();
			}
		}

		// disposes all associated windows and their components
		display.dispose();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}