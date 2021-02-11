package fields;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CheckListRenderer extends JCheckBox implements ListCellRenderer<Object> {

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		setEnabled(true);
		setSelected(((CheckBoxWithLabel) value).isSelected());
	    setFont(list.getFont());
	    setBackground(list.getBackground());
	    setForeground(list.getForeground());
	    setText(value.toString());
	    return this;
	}

}
