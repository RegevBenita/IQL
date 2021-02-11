package ast.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.DisplayId;
import ast.constraints.Constraint.HolderCon;
import fields.JPanelWithValue;
import fields.PlaceholderTextAreaField;
import generated.GuiInputParser.ComponentContext;
import ui.Visitor;

public class CTextArea implements Component, BasicLayout {
	private final String name;
	private final String prompt;
	private final String defVal;
	private final List<Constraint> constraints;
	
	public CTextArea(String name, String prompt, String defVal, List<Constraint> constraints) {
		this.name = name;
		this.prompt = prompt;
		this.defVal = defVal;
		this.constraints = constraints;
	}
	
	public CTextArea(ComponentContext ctx) {
		name = extractCompName(ctx);
		prompt = extractCompTitle(ctx);
		defVal = extractCompDefVal(ctx);
		constraints = extractConstraints(ctx);
	}
	
	public JPanelWithValue make() {
		PlaceholderTextAreaField textArea = new PlaceholderTextAreaField(5, 28);
		Map<ConstraintId, Constraint> constraintMap = getMapConstraint(constraints);
		JPanelWithValue panel = new JPanelWithValue(Id.TextArea, this, name, prompt){
			@Override
			public boolean checkForError() {
				String errorMsg = validateConstraints(Id.TextArea, String.valueOf(textArea.getText()), constraintMap);
				boolean haveError = setErrorLabel(errorMsg);
				setComponentErrorIndicator(textArea, errorMsg, true);
				return haveError;
			}
			@Override
			public void setValueOrDefault(String value, boolean setDefault) {
				if(setDefault) {
					textArea.setText(defVal);
					setValue(defVal);
				} else {
					textArea.setText(value);
					setValue(value);
				}
			}
		};
		
		panel.setValueOrDefault("", true);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JLabel jTitle = generateTitle(prompt, constraintMap);
		JLabel errorMsg = panel.getErrorLabel();
		DisplayId display = (DisplayId)constraintMap.get(ConstraintId.DISPLAY);
		if(display == DisplayId.Non) {
			display = DisplayId.Block;
		}
		setTextAreaPlaceHolder(textArea, constraintMap);
		textArea.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				setTextAreaPlaceHolder(textArea, constraintMap);
				String text = String.valueOf(textArea.getText());
				String errorMsg = validateConstraints(Id.TextArea, text, constraintMap);
				boolean haveError = panel.setErrorLabel(errorMsg);
				panel.setComponentErrorIndicator(textArea, errorMsg, true);
				if(!haveError)
					panel.setValue(text);
			}
			
			@Override
			public void focusGained(FocusEvent e) {}
		});
		textArea.addKeyListener(new KeyAdapter() {
			 public void keyReleased(KeyEvent e) {
				 String text = String.valueOf(textArea.getText());
				 String error = validateConstraints(Id.TextArea, text, constraintMap);
				 panel.setComponentErrorIndicator(textArea, error, false);
				 if(" ".equals(error)) {
					errorMsg.setText(" "); 
					panel.setValue(text);
				 }
			 }
		});
		return setLayout(display, jTitle, scroll, errorMsg, panel);
	}
	
	private void setTextAreaPlaceHolder(PlaceholderTextAreaField textField, Map<ConstraintId, Constraint> constraints) {
		if(textField.getText().isEmpty() && constraints.containsKey(ConstraintId.HOLDER)) {
			textField.setPlaceholder(((HolderCon)constraints.get(ConstraintId.HOLDER)).value());
		}
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
	public Id getType() { return Id.TextArea; }

	@Override
	public String toString() {
		return "TextArea [name=" + name + ", prompt=" + prompt + ", defVal=" + defVal + ", constraints=" + constraints
				+ "]";
	}
	
	@Override
	public JPanelWithValue accept(Visitor v) {
		return v.visitTextArea(this);
	}
}

