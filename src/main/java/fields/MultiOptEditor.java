package fields;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;

public class MultiOptEditor extends BasicComboBoxEditor {
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();
	private String selectedValue;

	public MultiOptEditor() {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		
		label.setOpaque(false);
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setForeground(Color.BLACK);
		Border border = BorderFactory.createCompoundBorder(
		        new LineBorder(Color.GRAY, 1), 
		        BorderFactory.createEmptyBorder(3, 2, 3, 2));
		label.setBorder(border);
		panel.add(label, constraints);
		panel.setBackground(new Color(238, 238, 238));
	}

	@Override
	public Component getEditorComponent() {
		return this.panel;
	}

	@Override
	public Object getItem() {
		return this.selectedValue;
	}

	@Override
	public void setItem(Object item) {
		if (item == null) {
			return;
		}
		selectedValue = (String)item;
		label.setText(selectedValue);
	}
}
