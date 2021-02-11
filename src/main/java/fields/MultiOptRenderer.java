package fields;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class MultiOptRenderer extends JPanel implements ListCellRenderer<String> {
    private JLabel item = new JLabel();
    private JCheckBox status = new JCheckBox();
    private Color background = new Color(238, 238, 238);
    private Color foreground = new Color(51, 51, 51);
    
    public MultiOptRenderer() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        status.setOpaque(true);
        add(status);
        gbc.gridx = 1;
        item.setOpaque(true);
        item.setHorizontalAlignment(JLabel.LEFT);
        item.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        add(item, gbc);    
    }

	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
			boolean isSelected, boolean cellHasFocus) {
		list.setFocusable(true);
	      item.setText(value);
	      if (isSelected) {
	      	item.setBackground(Color.BLUE);
	      	item.setForeground(Color.WHITE);
	      } else {
	      	item.setForeground(foreground);
	      	item.setBackground(background);
	      }
	      return this;
	}

}
