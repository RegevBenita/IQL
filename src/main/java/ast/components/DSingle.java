package ast.components;

import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import ast.Ast.Dialog;
import ast.constraints.Constraint;
import ast.Id;
import generated.GuiInputParser.DialogContext;
import ui.Visitor;

public class DSingle implements Dialog {
	private String title;
	private String description;
	private List<Constraint> constraints;
	public DSingle(DialogContext ctx) {
		title = extractDialogTitle(ctx);
		description = extractDialogDesc(ctx);
		constraints = extractConstraints(ctx);
	}
	
	@Override public String getTitle() { return title; }

	@Override public String getDescription() { return description; }

	@Override public List<Constraint> getConstraints() {
		return Collections.unmodifiableList(constraints);
	}

	@Override
	public Id getType() { return Id.Single;	}

	@Override
	public String toString() {
		return "Single[title=" + title + ", description=" + description + ", constraints=" + constraints + "]";
	}

	// Support future operations
	@Override
	public JFrame accept(Visitor v) {
		return null;
	}
}


