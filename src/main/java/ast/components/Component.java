package ast.components;

import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import ast.Ast;
import ast.Id;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.DisplayId;
import ast.constraints.Constraint.OptionalCon;
import fields.JPanelContainer;
import generated.GuiInputParser.ComponentContext;

public interface Component extends Attributable<JPanelContainer>, Ast.Tabable {
	String getPrompt();
	List<Constraint> getConstraints();
	
	default List<Constraint> extractConstraints(ComponentContext ctx) {
		return ctx.compCon() == null ? List.of() : extractConstraints(getType(), ctx.compCon().children);
	}
	
	default String extractCompTitle(ComponentContext ctx) {
		return extractTitle(ctx.QuotedCharText());
	}
	
	default String extractCompDefVal(ComponentContext ctx) {
		return extractDefVal(ctx.DefaultValue());
	}

	default String extractCompName(ComponentContext ctx) {
		if(ctx.NameWord() == null)
			throw new IllegalArgumentException("Component name is not valid");
		return ctx.NameWord().getText();
	}
	
	default Map<ConstraintId, Constraint> getMapConstraint(List<Constraint> constraints) {
		Map<ConstraintId, Constraint> constraintMap = new HashMap<>();
		if(constraints == null)
			return constraintMap ;
		constraints.forEach(con -> {
			ConstraintId id = con.getID();
			switch (id) {
				case MIN -> constraintMap.put(id,(Constraint.MinCon)con);
				case MAX -> constraintMap.put(id,(Constraint.MaxCon)con);
				case REGEX -> constraintMap.put(id,(Constraint.RegexCon)con);
				case HOLDER -> constraintMap.put(id,(Constraint.HolderCon)con);
				case OPTIONAL -> constraintMap.put(id,(Constraint.OptionalCon)con);
				case DISPLAY -> constraintMap.put(id,(DisplayId)con);
				case MINORTICKS -> constraintMap.put(id,(Constraint.MinorTicksCon)con);
				case MAJORTICKS -> constraintMap.put(id,(Constraint.MajorTicksCon)con);
				case SELECTED -> constraintMap.put(id,(Constraint.SelectedCon)con);
				default ->
					throw new IllegalArgumentException("Unexpected String constraint: " + id);
			}
		});
		// If not exist then apply default setting
		if (!constraintMap.containsKey(ConstraintId.DISPLAY))
			constraintMap.put(ConstraintId.DISPLAY, DisplayId.Non);
		if (!constraintMap.containsKey(ConstraintId.OPTIONAL))
			constraintMap.put(ConstraintId.OPTIONAL, OptionalCon.Required);
		return constraintMap;	
	}
	
	default JLabel generateTitle(String title, Map<ConstraintId, Constraint> constraints) {
		JLabel label = new JLabel();
		label.setBorder(new EmptyBorder(0, 0, 0, 10));
		Font font = label.getFont();
		label.setFont(font.deriveFont(font.getStyle() | Font.ITALIC));
		label.setText(title);
		label.setToolTipText(title);
		addRequiredToLabel(label, constraints);
		return label;
	}
	
	default JLabel addRequiredToLabel(JLabel label, Map<ConstraintId, Constraint> constraints) {
		if(constraints.get(ConstraintId.OPTIONAL) == OptionalCon.Required) {
			label.setText("<html>" + label.getText() + "<span  color='red'> *</span ></html>" );
		} else {
			label.setText(label.getText() + "  ");
		}
		return label;
	}
	
	default String validateConstraints(Id id, String text, Map<ConstraintId, Constraint> constraints) {
		if(constraints.get(ConstraintId.OPTIONAL) == OptionalCon.Required) {
			String requiredError = ((Constraint.OptionalCon)constraints.get(ConstraintId.OPTIONAL)).validate(text);
			if(!" ".equals(requiredError))
				return requiredError;
		}
		if(text!=null && !text.isEmpty() && constraints.containsKey(ConstraintId.MIN)) {
			String minError = switch (id) {
				case Integer ->
					((Constraint.MinCon)constraints.get(ConstraintId.MIN)).validateInt(text);
				case Decimal ->
					((Constraint.MinCon)constraints.get(ConstraintId.MIN)).validateDec(text);
				default ->
					((Constraint.MinCon)constraints.get(ConstraintId.MIN)).validateLength(text);
			};
			if(!" ".equals(minError))
				return minError;
		}
		if(text!=null && !text.isEmpty() && constraints.containsKey(ConstraintId.MAX)) {
			String maxError = switch (id) {
				case Integer ->
					((Constraint.MaxCon)constraints.get(ConstraintId.MAX)).validateInt(text);
				case Decimal ->
					((Constraint.MaxCon)constraints.get(ConstraintId.MAX)).validateDec(text);
				default ->
					((Constraint.MaxCon)constraints.get(ConstraintId.MAX)).validateLength(text);
			};
			if(!" ".equals(maxError))
				return maxError;
		}
		if(text!=null && !text.isEmpty() && constraints.containsKey(ConstraintId.REGEX)) {
			String regexError = ((Constraint.RegexCon)constraints.get(ConstraintId.REGEX)).validate(text);
			if(!" ".equals(regexError))
				return regexError;
			}
		return " ";
	}

}
