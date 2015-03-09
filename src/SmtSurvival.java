

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;


public class SmtSurvival extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
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
		group.setBounds(10, 10, 643, 417);
		toolkit.adapt(group);
		toolkit.paintBordersFor(group);
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(group, SWT.NONE);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		
		txtSmartManagementTool_1 = new Text(composite, SWT.BORDER);
		txtSmartManagementTool_1.setText("Smart Management Tool");
		txtSmartManagementTool_1.setBounds(0, 1, 157, 21);
		toolkit.adapt(txtSmartManagementTool_1, true, true);
		
		Label lblCommands_1 = new Label(composite, SWT.NONE);
		lblCommands_1.setBounds(10, 371, 275, 15);
		toolkit.adapt(lblCommands_1, true, true);
		lblCommands_1.setText("Commands :");
		
		text_2 = new Text(composite, SWT.BORDER);
		text_2.setBounds(84, 368, 201, 21);
		toolkit.adapt(text_2, true, true);
		
		StyledText styledText = new StyledText(composite, SWT.BORDER);
		styledText.setBounds(10, 28, 298, 334);
		toolkit.adapt(styledText);
		toolkit.paintBordersFor(styledText);
		
		Composite composite_1 = new Composite(group, SWT.NONE);
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
	
	
		
		
	
	
		

	}
}
