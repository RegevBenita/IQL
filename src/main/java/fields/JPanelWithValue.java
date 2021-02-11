package fields;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import ast.Id;
import ast.components.Component;

public abstract class JPanelWithValue extends JPanelContainer {
	private boolean hasError = false;
	private String value = "";
	private String name = null;
	private String prompt;
	private JLabel errorLabel;
	private Component component;
	
	public abstract void setValueOrDefault(String value, boolean setDefault);
	
	public JPanelWithValue(Id id) {
		super(id);
		errorLabel = generateErrorLabel(" ");
	}

	public JPanelWithValue(Id id, Component component, String name, String prompt) {
		this(id);
		this.name = name;
		this.prompt = prompt;
		this.component = component;
	}
	
	public void setValue(String value) {
		hasError = false;
		this.value = value;
	}
	public String getValue() {
		return hasError? null:value;
	}
	
	public JLabel getErrorLabel() {
		return errorLabel;
	}

	public boolean setErrorLabel(String errorMsg) {
		errorLabel.setText(errorMsg);
		hasError = !" ".equals(errorMsg);
		return hasError;
	}
	
	public void setComponentErrorIndicator(JComponent component, String errorMsg, boolean setBackground) {
		if(" ".equals(errorMsg)) {
			component.setBackground(Color.WHITE);
			component.setToolTipText(null);
		} else if(setBackground) {
			component.setBackground(Color.pink);
			component.setToolTipText(errorMsg);
		}	
	}

	private JLabel generateErrorLabel(String msg) {
		JLabel label = new JLabel();
		Font font = label.getFont();
		label.setFont(font.deriveFont(6));
		label.setForeground(Color.red);
		label.setText(msg);
		return label;
	}

	public Component getComponent() {
		return component;
	}

	public String getName() {
		return name;
	}

	public String getPrompt() {
		return prompt;
	}
	
}
