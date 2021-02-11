package ast.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public interface BasicLayout {
	default JPanelWithValue setLayout(DisplayId display, JLabel title, JComponent component,
			JLabel errorMsg, JPanelWithValue panel) {
		return switch (display) {
			case Block -> setBlockDisplay(title, component, errorMsg, panel);
			case Inline -> setInlineDisplay(title, component, errorMsg, panel);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + display);
		};
	}
	
	private JPanelWithValue setInlineDisplay(JLabel title, JComponent component,
			JLabel errorMsg, JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		if(!(component instanceof JScrollPane))
			component.setPreferredSize(new Dimension(326, 22));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(component, gbc);
		gbc.gridy = 1;
		panel.add(errorMsg, gbc);
		return panel;
	}

	private JPanelWithValue setBlockDisplay(JLabel title, JComponent component, JLabel errorMsg, JPanelWithValue panel) {
		if(!(component instanceof JScrollPane))
			component.setPreferredSize(new Dimension(326, 22));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(component, gbc);
		gbc.gridy = 2;
		panel.add(errorMsg, gbc);
		return panel;
	}
}
