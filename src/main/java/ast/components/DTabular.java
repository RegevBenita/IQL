package ast.components;

import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import ast.Ast.Dialog;
import ast.constraints.Constraint;
import ast.Id;
import generated.GuiInputParser.DialogContext;
import ui.Visitor;

public class DTabular implements Dialog {
	private String title;
	private String description;
	private List<Constraint> constraints;
	public DTabular(DialogContext ctx) {
		title = extractDialogTitle(ctx);
		description = extractDialogDesc(ctx);
		constraints = extractConstraints(ctx);
	}
	
	public String getTitle() { return title; }

	public String getDescription() { return description; }

	public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}
	
	@Override
	public Id getType() { return Id.Tabular; }

	@Override
	public String toString() {
		return "Tabular[title=" + title + ", description=" + description + ", constraints=" + constraints + "]";
	}

	// Support future operations
	@Override
	public JFrame accept(Visitor v) {
		return null;
	}
}
