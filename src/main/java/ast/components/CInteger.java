package ast.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;
import fields.PlaceholderIntegerField;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CInteger implements Component, Placeholder, BasicLayout {
	private final String name;
	private final String prompt;
	private final BigInteger defVal;
	private final List<Constraint> constraints;
	
	public CInteger(String name, String prompt, BigInteger defVal, List<Constraint> constraints) {
		this.name = name;
		this.prompt = prompt;
		this.defVal = defVal;
		this.constraints = constraints;
	}
	
	public CInteger(ComponentContext ctx) {
		name = extractCompName(ctx);
		prompt = extractCompTitle(ctx);
		defVal = initDefVal(ctx);
		constraints = extractConstraints(ctx);
	}
	
	private BigInteger initDefVal(ComponentContext ctx) {
		String value = extractCompDefVal(ctx);
		try {
			return value.isEmpty()? null :new BigInteger(value);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(value + " is not a valid integer");
		}
	}
	
	public JPanelWithValue make() {
		PlaceholderIntegerField textField = new PlaceholderIntegerField();
		Map<ConstraintId, Constraint> constraintMap = getMapConstraint(constraints);
		JPanelWithValue panel = new JPanelWithValue(Id.Integer, this, name, prompt){
			@Override
			public boolean checkForError() {
				String errorMsg = validateConstraints(Id.Integer, textField.getText(), constraintMap);
				boolean haveError = setErrorLabel(errorMsg);
				setComponentErrorIndicator(textField, errorMsg, true);
				return haveError;
			}
			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					textField.setText(defVal == null? "": String.valueOf(defVal));
					setValue(defVal == null? "": String.valueOf(defVal));
				} else {
					try {
						textField.setText(value == null? "": String.valueOf(value));
						setValue(value == null? "": String.valueOf(value));
					} catch (Exception e) {
						throw new NumberFormatException(value + " is not a valid integer");
					}
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
		setPlaceHolder(textField, constraintMap);
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				String errorMsg = validateConstraints(Id.Integer, textField.getText(), constraintMap);
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
				 String error = validateConstraints(Id.Integer, textField.getText(), constraintMap);
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

	public BigInteger getDefVal() {
		return defVal;
	}

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}

	@Override
	public Id getType() { return Id.Integer; }

	@Override
	public String toString() {
		return "Integer [name=" + name + ", prompt=" + prompt + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}

	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitInteger(this);
	}
}