package ast.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JToggleButton;
import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.DisplayId;
import ast.constraints.Constraint.HolderCon;
import fields.JPanelWithValue;
import fields.PlaceholderPasswordField;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CPassword implements Component {	
	private final String name;
	private final String prompt;
	private final String defVal;
	private final List<Constraint> constraints;
	
	public CPassword(String name, String prompt, String defVal, List<Constraint> constraints) {
		this.name = name;
		this.prompt = prompt;
		this.defVal = defVal;
		this.constraints = constraints;
	}
	
	public CPassword(ComponentContext ctx) {
		name = extractCompName(ctx);
		prompt = extractCompTitle(ctx);
		defVal = extractCompDefVal(ctx);
		constraints = extractConstraints(ctx);
	}
	
	public JPanelWithValue make() {
		PlaceholderPasswordField passField = new PlaceholderPasswordField();
		Map<ConstraintId, Constraint> constraintMap = getMapConstraint(constraints);
		JPanelWithValue panel = new JPanelWithValue(Id.Password, this, name, prompt){
			@Override
			public boolean checkForError() {
				String errorMsg = validateConstraints(Id.Password, String.valueOf(passField.getPassword()), constraintMap);
				boolean haveError = setErrorLabel(errorMsg);
				setComponentErrorIndicator(passField, errorMsg, true);
				return haveError;
			}
			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					passField.setText(defVal);
					setValue(defVal);
				} else {
					passField.setText(value);
					setValue(value);
				}
			}
		};
		
		panel.setValueOrDefault("", true);
		JLabel jTitle = generateTitle(prompt, constraintMap);
		JLabel errorMsg = panel.getErrorLabel();
		DisplayId display = (DisplayId)constraintMap.get(ConstraintId.DISPLAY);
		if(display == DisplayId.Non) {
			display = DisplayId.Block;
		}
		setPasswordPlaceHolder(passField, constraintMap);
		passField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setPasswordPlaceHolder(passField, constraintMap);
				String passVal = String.valueOf(passField.getPassword());
				String errorMsg = validateConstraints(Id.Password, passVal, constraintMap);
				boolean haveError = panel.setErrorLabel(errorMsg);
				panel.setComponentErrorIndicator(passField, errorMsg, true);
				if(!haveError)
					panel.setValue(passVal);
			}
			
			@Override
			public void focusGained(FocusEvent e) {}
		});
		passField.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String passVal = String.valueOf(passField.getPassword());
				 String error = validateConstraints(Id.Password, passVal, constraintMap);
				 panel.setComponentErrorIndicator(passField, error, false);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(passVal);
				 }
			 }
		});
		return setPasswordLayout(display, jTitle, passField, errorMsg, panel);
	}
	
	private void setPasswordPlaceHolder(PlaceholderPasswordField textField, Map<ConstraintId, Constraint> constraints) {
		if(String.valueOf(textField.getPassword()).isEmpty() && constraints.containsKey(ConstraintId.HOLDER)) {
			textField.setPlaceholder(((HolderCon)constraints.get(ConstraintId.HOLDER)).value());
		}
	}
	
	private JPanelWithValue setPasswordLayout(DisplayId display, JLabel title,
			JPasswordField textField, JLabel errorMsg, JPanelWithValue panel) {
		return switch (display) {
			case Block -> setPasswordBlockDisplay(title, textField, errorMsg, panel);
			case Inline -> setPasswordInlineDisplay(title, textField, errorMsg, panel);
			default ->
			throw new IllegalArgumentException("Unexpected value: " + display);
		};
	}
	
	private JPanelWithValue setPasswordInlineDisplay(JLabel title, JPasswordField passField,
			JLabel errorMsg, JPanelWithValue panel) {
		JToggleButton showButton = new JToggleButton();
		showButton.setPreferredSize(new Dimension(22, 22));
		passField.setEchoChar('\u25CF');
		ImageIcon hideImg = new ImageIcon(this.getClass().getResource("/images/hide.png"));
		Image hideDim = hideImg.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
		ImageIcon hideImg2 = new ImageIcon(hideDim);
		ImageIcon showImg = new ImageIcon(this.getClass().getResource("/images/show.png"));
		Image showDim = showImg.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
		ImageIcon showImg2 = new ImageIcon(showDim);
		showButton.setIcon(showImg2);
		showButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(showButton.isSelected()) {
					passField.setEchoChar((char)0);
					showButton.setIcon(hideImg2);
				}
				else {
					passField.setEchoChar('\u25CF');
					showButton.setIcon(showImg2);
				}
			}
		});
		return setInlineDisplay(title, passField, errorMsg, panel, showButton);
	}

	private JPanelWithValue setInlineDisplay(JLabel title, JPasswordField passField, JLabel errorMsg,
			JPanelWithValue panel, JToggleButton showButton) {
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		passField.setPreferredSize(new Dimension(304, 22));
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridx = 1;
		panel.add(passField, gbc);
		gbc.gridx = 2;
		panel.add(showButton, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(errorMsg, gbc);
		return panel;
	}

	private JPanelWithValue setPasswordBlockDisplay(JLabel title, JPasswordField passField, JLabel errorMsg, JPanelWithValue panel) {
		panel.setLayout(new GridBagLayout());
		JToggleButton showButton = new JToggleButton();
		showButton.setPreferredSize(new Dimension(22, 22));
		passField.setEchoChar('\u25CF');
		ImageIcon hideImg = new ImageIcon(this.getClass().getResource("/images/hide.png"));
		Image hideDim = hideImg.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
		ImageIcon hideImg2 = new ImageIcon(hideDim);
		ImageIcon showImg = new ImageIcon(this.getClass().getResource("/images/show.png"));
		Image showDim = showImg.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
		ImageIcon showImg2 = new ImageIcon(showDim);
		showButton.setIcon(showImg2);
		showButton.addActionListener(e -> {
				if(showButton.isSelected()) {
					passField.setEchoChar((char)0);
					showButton.setIcon(hideImg2);
				}
				else {
					passField.setEchoChar('\u25CF');
					showButton.setIcon(showImg2);
				}
		});
		return setBlockDisplay(title, passField, errorMsg, panel, showButton);
	}

	private JPanelWithValue setBlockDisplay(JLabel title, JPasswordField passField, JLabel errorMsg,
			JPanelWithValue panel, JToggleButton showButton) {
		JPanel passPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		passPanel.setPreferredSize(new Dimension(346, 22));
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		passPanel.add(passField, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.weightx = 0.0;
		passPanel.add(showButton, gbc);
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(title, gbc);
		gbc.gridy = 1;
		panel.add(passPanel, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(errorMsg, gbc);
		return panel;
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
	public Id getType() { return Id.Password; }

	@Override
	public String toString() {
		return "Password [name=" + name + ", prompt=" + prompt + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}
	
	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitPassword(this);
	}	
}
