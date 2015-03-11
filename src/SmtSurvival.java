import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.layout.FillLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridData;
//import java.awt.FlowLayout;
//import org.eclipse.swt.layout.FormLayout;
//import org.eclipse.swt.layout.FormData;
//import org.eclipse.swt.layout.FormAttachment;
//import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

//import org.eclipse.wb.swt.SWTResourceManager;

public class SmtSurvival extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text text;
	private Text cmdTextBox;
	private TabFolder displayTaskFolder;
	private TabItem tbtmToday;
	private TabItem tbtmSchedule;
	private TabItem tbtmPending;
	private TabItem tbtmTodo;
	private TabItem tbtmMisc;
	private Label lblToday;

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

		Group grpOutput = new Group(this, SWT.NONE);
		GridData gd_grpOutput = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_grpOutput.heightHint = 381;
		gd_grpOutput.widthHint = 424;
		grpOutput.setLayoutData(gd_grpOutput);
		grpOutput.setText("Output");
		toolkit.adapt(grpOutput);
		toolkit.paintBordersFor(grpOutput);

		Composite composite = new Composite(grpOutput, SWT.NONE);
		composite.setBounds(10, 22, 410, 372);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);

		text = new Text(composite, SWT.BORDER);
		text.setBounds(0, 0, 410, 372);
		toolkit.adapt(text, true, true);

		Group group = new Group(this, SWT.NONE);
		GridData gd_group = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1,
				2);
		gd_group.heightHint = 473;
		gd_group.widthHint = 424;
		group.setLayoutData(gd_group);
		toolkit.adapt(group);
		toolkit.paintBordersFor(group);

		displayTaskFolder = new TabFolder(group, SWT.NONE);
		displayTaskFolder.setBounds(0, 10, 430, 476);
		toolkit.adapt(displayTaskFolder);
		toolkit.paintBordersFor(displayTaskFolder);

		tbtmToday = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmToday.setText("Today");
		lblToday = new Label(displayTaskFolder, SWT.NONE);
		tbtmToday.setControl(lblToday);
		lblToday.setText("You are now in the today tab");

		tbtmSchedule = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmSchedule.setText("Schedule");

		tbtmPending = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmPending.setText("Pending");

		tbtmTodo = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmTodo.setText("To-Do");

		tbtmMisc = new TabItem(displayTaskFolder, SWT.NONE);
		tbtmMisc.setText("Misc");

		Composite composite_1 = new Composite(this, SWT.NONE);
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_composite_1.heightHint = 83;
		gd_composite_1.widthHint = 429;
		composite_1.setLayoutData(gd_composite_1);
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);

		Label lblCommand = new Label(composite_1, SWT.NONE);
		lblCommand.setBounds(10, 10, 84, 20);
		toolkit.adapt(lblCommand, true, true);
		lblCommand.setText("Command :");

		cmdTextBox = new Text(composite_1, SWT.BORDER);
		cmdTextBox.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					displayTaskFolder.setSelection(tbtmToday);
					lblToday.setText(cmdTextBox.getText());
				}
			}
		});
		cmdTextBox.setBounds(100, 10, 319, 63);
		toolkit.adapt(cmdTextBox, true, true);
	}

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Smart Management Tool");

		shell.open();

		SmtSurvival Smt = new SmtSurvival(shell, SWT.NONE);

		Smt.pack();
		shell.pack();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
}
