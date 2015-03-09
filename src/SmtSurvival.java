import java.awt.TextField;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;

import swing2swt.layout.BoxLayout;

import org.eclipse.swt.custom.SashForm;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;
import javax.swing.JTextPane;


public class SmtSurvival extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text text;
	private Text text_1;
	private Text txtSmartManagementTool;
	private SashForm sashForm;
	private Label lblCommands;
	private Text txtSmartManagementTool_1;
	private Text text_2;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SmtSurvival(Composite parent, int style) {
		super(parent, style);
	
		Group group = new Group(this, SWT.NONE);
		group.setBounds(10, 10, 597, 417);
		toolkit.adapt(group);
		toolkit.paintBordersFor(group);
		
		Composite composite = new Composite(group, SWT.NONE);
		composite.setBounds(0, 10, 157, 22);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
		txtSmartManagementTool_1 = new Text(composite, SWT.BORDER);
		txtSmartManagementTool_1.setText("Smart Management Tool");
		txtSmartManagementTool_1.setBounds(0, 1, 157, 21);
		toolkit.adapt(txtSmartManagementTool_1, true, true);
		
		Composite composite_1 = new Composite(group, SWT.NONE);
		composite_1.setBounds(10, 363, 313, 32);
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);
		
		Label lblCommands_1 = new Label(composite_1, SWT.NONE);
		lblCommands_1.setBounds(0, 10, 68, 15);
		toolkit.adapt(lblCommands_1, true, true);
		lblCommands_1.setText("Commands :");
		
		text_2 = new Text(composite_1, SWT.BORDER);
		text_2.setBounds(74, 7, 233, 21);
		toolkit.adapt(text_2, true, true);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
	
	
		
		
	
	
		

	}
}
