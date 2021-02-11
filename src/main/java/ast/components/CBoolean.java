package ast.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CBoolean implements Component {
	private final String name;
	private final String prompt;
	private final String defVal;
	private final List<Constraint> constraints;

	public CBoolean(String name, String prompt, String defVal, List<Constraint> constraints) {
		this.name = name;
		this.prompt = prompt;
		this.defVal = defVal;
		this.constraints = constraints;
	}

	public CBoolean(ComponentContext ctx) {
		name = extractCompName(ctx);
		prompt = extractCompTitle(ctx);
		defVal = initDefaultVal(ctx);
		constraints = extractConstraints(ctx);
	}

	private String initDefaultVal(ComponentContext ctx) {
		String value = extractCompDefVal(ctx);
		if ("".equals(value))
			return "";
		if ("true".equals(value) || "false".equals(value))
			return value;
		else
			throw new IllegalArgumentException("Boolean default value must be only 'true' or 'false'");
	}

	public JPanelWithValue make() {
		Map<ConstraintId, Constraint> constraintMap = getMapConstraint(constraints);
		JLabel jTitle = generateTitle(prompt, constraintMap);
		DisplayId display = (DisplayId) constraintMap.get(ConstraintId.DISPLAY);
		if(display == DisplayId.Non) {
			display = DisplayId.Block;
		}
		return setBooleanLayout(display, jTitle, constraintMap);
	}
	

	private JPanelWithValue setBooleanLayout(DisplayId display, JLabel title ,Map<ConstraintId, Constraint> constraintMap) {
		return switch (display) {
		case Block -> setBooleanBlockDisplay(title, constraintMap);
		case Inline -> setBooleanInlineDisplay(title, constraintMap);
		case InlineList -> setBooleanInlineListDisplay(title, constraintMap);
		case BlockList -> setBooleanBlockListDisplay(title, constraintMap);
		default -> throw new IllegalArgumentException("Unexpected value: " + display);
		};
	}
	
	private JPanelWithValue setBooleanBlockListDisplay(JLabel title, Map<ConstraintId, Constraint> constraintMap) {
		String[] optionsArray = new String[]{"", "true", "false"};
		JComboBox<String> combo = new JComboBox<String>(optionsArray);
		combo.setPreferredSize(new Dimension(326, 22));
		combo.setBackground(Color.white);
		JPanelWithValue panel = getListDisplayPanel(constraintMap, combo);
		panel.setValueOrDefault("", true);
		panel.setLayout(new GridBagLayout());
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(combo.getSelectedItem() == null)
					panel.setValue("");
				else
					panel.setValue(combo.getSelectedItem().toString());
				String errorMsg = validateConstraints(Id.Boolean, panel.getValue(), constraintMap);
				panel.setComponentErrorIndicator(combo, errorMsg, true);
				panel.setErrorLabel(errorMsg);
			}
		});
		return setBlockListDisplay(title, combo, panel);
	}

	private JPanelWithValue setBlockListDisplay(JLabel title, JComboBox<String> combo, JPanelWithValue panel) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(combo, gbc);
		gbc.gridy = 2;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setBooleanInlineListDisplay(JLabel title, Map<ConstraintId, Constraint> constraintMap) {
		String[] optionsArray = new String[]{"", "true", "false"};
		JComboBox<String> combo = new JComboBox<String>(optionsArray);
		combo.setPreferredSize(new Dimension(326, 22));
		combo.setBackground(Color.white);
		JPanelWithValue panel = getListDisplayPanel(constraintMap, combo);
		panel.setValueOrDefault("", true);
		panel.setLayout(new GridBagLayout());
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(combo.getSelectedItem() == null)
					panel.setValue("");
				else
					panel.setValue(combo.getSelectedItem().toString());
				String errorMsg = validateConstraints(Id.Boolean, panel.getValue(), constraintMap);
				panel.setComponentErrorIndicator(combo, errorMsg, true);
				panel.setErrorLabel(errorMsg);
			}
		});
		return setInlineListDisplay(title, combo, panel);
	}

	private JPanelWithValue setInlineListDisplay(JLabel title, JComboBox<String> combo, JPanelWithValue panel) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(combo, gbc);
		gbc.gridy = 1;
		gbc.gridx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setBooleanInlineDisplay(JLabel title, Map<ConstraintId, Constraint> constraintMap) {
		JRadioButton yesButton = new JRadioButton("Yes");
		JRadioButton noButton = new JRadioButton("No");
		JPanelWithValue panel = getRadioDisplayPanel(constraintMap, yesButton, noButton);
		panel.setValueOrDefault("", true);
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noButton.setSelected(false);
				yesButton.setSelected(yesButton.isSelected());
				if (yesButton.isSelected())
					panel.setValue("true");
				else
					panel.setValue("");
				String errorMsg = validateConstraints(Id.Boolean, panel.getValue(), constraintMap);
				panel.setErrorLabel(errorMsg);
			}
		});
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				yesButton.setSelected(false);
				noButton.setSelected(noButton.isSelected());
				if (noButton.isSelected())
					panel.setValue("false");
				else
					panel.setValue("");
				String errorMsg = validateConstraints(Id.Boolean, panel.getValue(), constraintMap);
				panel.setErrorLabel(errorMsg);
			}
		});
		return setInlineDisplay(title, yesButton, noButton, panel);
	}

	private JPanelWithValue setInlineDisplay(JLabel title, JRadioButton yesButton, JRadioButton noButton,
			JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.anchor = GridBagConstraints.WEST;
		
		JPanel buttonsPanel = new JPanel(new GridBagLayout());
		buttonsPanel.add(yesButton, gbc);
		gbc.weightx = 1;
		gbc.gridx = 1;
		buttonsPanel.add(noButton, gbc);
		buttonsPanel.setPreferredSize(new Dimension(326, (int) panel.getPreferredSize().getHeight()));
		
		panel.add(buttonsPanel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(panel.getErrorLabel(), gbc);
		return panel;
	}

	private JPanelWithValue setBooleanBlockDisplay(JLabel title, Map<ConstraintId, Constraint> constraintMap) {
		JRadioButton yesButton = new JRadioButton("Yes");
		JRadioButton noButton = new JRadioButton("No");
		JPanelWithValue panel = getRadioDisplayPanel(constraintMap, yesButton, noButton);
		panel.setValueOrDefault("", true);
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noButton.setSelected(false);
				yesButton.setSelected(yesButton.isSelected());
				if (yesButton.isSelected())
					panel.setValue("true");
				else
					panel.setValue("");
				String errorMsg = validateConstraints(Id.Boolean, panel.getValue(), constraintMap);
				panel.setErrorLabel(errorMsg);
			}
		});
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				yesButton.setSelected(false);
				noButton.setSelected(noButton.isSelected());
				if (noButton.isSelected())
					panel.setValue("false");
				else
					panel.setValue("");
				String errorMsg = validateConstraints(Id.Boolean, panel.getValue(), constraintMap);
				panel.setErrorLabel(errorMsg);
			}
		});
		return setBlockDisplay(title, yesButton, noButton, panel);
	}

	private JPanelWithValue setBlockDisplay(JLabel title, JRadioButton yesButton, JRadioButton noButton,
			JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(yesButton, gbc);
		gbc.gridy = 2;
		panel.add(noButton, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(panel.getErrorLabel(), gbc);
		panel.setPreferredSize(new Dimension(326, (int) panel.getPreferredSize().getHeight()));
		return panel;
	}

	private JPanelWithValue getRadioDisplayPanel(Map<ConstraintId, Constraint> constraintMap, JRadioButton yesButton,
			JRadioButton noButton) {
		return new JPanelWithValue(Id.Boolean, this, name, prompt) {
			@Override
			public boolean checkForError() {
				return setErrorLabel(validateConstraints(Id.Boolean, getValue(), constraintMap));
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if (setDefault)
					value = defVal;
				if ("".equals(value)) {
					yesButton.setSelected(false);
					noButton.setSelected(false);
					setValue("");
				}
				if ("true".equals(value)) {
					yesButton.setSelected(true);
					noButton.setSelected(false);
					setValue("true");
				}
				if ("false".equals(value)) {
					noButton.setSelected(true);
					yesButton.setSelected(false);
					setValue("false");
				}
			}
		};
	}
	

	private JPanelWithValue getListDisplayPanel(Map<ConstraintId, Constraint> constraintMap, JComboBox<String> combo) {
		return new JPanelWithValue(Id.Boolean, this, name, prompt) {
			@Override
			public boolean checkForError() {
				String errorMsg = validateConstraints(Id.Boolean, getValue(), constraintMap);
				boolean haveError = setErrorLabel(errorMsg);
				setComponentErrorIndicator(combo, errorMsg, true);
				return haveError;
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					combo.setSelectedItem(defVal);
					setValue(defVal);
				} else if ("".equals(value)) {
					combo.setSelectedIndex(-1);
					setValue(value);
					} else {
						combo.setSelectedItem(value);
						setValue(value);
					}
			}
		};
	}

	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitBoolean(this);
	}

	public String getName() {
		return name;
	}

	public String getPrompt() {
		return prompt;
	}

	public String getDefVal() {
		return defVal;
	}

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}

	@Override
	public Id getType() {
		return Id.Boolean;
	}

	@Override
	public String toString() {
		return "Boolean [name=" + name + ", prompt=" + prompt + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}
}
