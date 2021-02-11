package ast.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.DisplayId;
import ast.constraints.Constraint.HolderCon;
import fields.JPanelWithValue;
import fields.PlaceholderTextField;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CString implements Component, Placeholder, BasicLayout {
	private final String name;
	private final String prompt;
	private final String defVal;
	private final List<Constraint> constraints;
	
	public CString(String name, String prompt, String defVal, List<Constraint> constraints) {
		this.name = name;
		this.prompt = prompt;
		this.defVal = defVal;
		this.constraints = constraints;
	}
	
	public CString(ComponentContext ctx) {
		name = extractCompName(ctx);
		prompt = extractCompTitle(ctx);
		defVal = extractCompDefVal(ctx);
		constraints = extractConstraints(ctx);
	}
	
	public JPanelWithValue make() {
		PlaceholderTextField textField = new PlaceholderTextField();
		Map<ConstraintId, Constraint> constraintMap = getMapConstraint(constraints);
		JPanelWithValue panel = new JPanelWithValue(Id.String, this, name, prompt) {
			@Override
			public boolean checkForError() {
				String errorMsg = validateConstraints(Id.String, textField.getText(), constraintMap);
				boolean haveError = setErrorLabel(errorMsg);
				setComponentErrorIndicator(textField, errorMsg, true);
				return haveError;
			}

			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					textField.setText(defVal == null? "": defVal);
					setValue(defVal);
				} else {
					textField.setText(value == null? "": value);
					setValue(value);
				}
			}
		};
		JLabel jTitle = generateTitle(prompt, constraintMap);
		JLabel errorMsg = panel.getErrorLabel();
		panel.setValueOrDefault("", true);
		DisplayId display = (DisplayId)constraintMap.get(ConstraintId.DISPLAY);
		if(display == DisplayId.Non) {
			display = DisplayId.Block;
		}
		if(textField.getText().isEmpty() && constraintMap.containsKey(ConstraintId.HOLDER)) {
			textField.setPlaceholder(((HolderCon)constraintMap.get(ConstraintId.HOLDER)).value());
		}
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				String errorMsg = validateConstraints(Id.String, textField.getText(), constraintMap);
				setPlaceHolder(textField, constraintMap);
				boolean haveError = panel.setErrorLabel(errorMsg);
				panel.setComponentErrorIndicator(textField, errorMsg, true);
				if(!haveError)
					panel.setValue(textField.getText());
			}	
			@Override
			public void focusGained(FocusEvent e) {}
		});
		textField.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String error = validateConstraints(Id.String, textField.getText(), constraintMap);
				 panel.setComponentErrorIndicator(textField, error, false);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(textField.getText());
				 }
			 }
		});
		return setLayout(display, jTitle, textField, errorMsg, panel);
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
	public Id getType() { return Id.String; }

	@Override
	public String toString() {
		return "String [name=" + name + ", prompt=" + prompt + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}
	
	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitString(this);
	}
}