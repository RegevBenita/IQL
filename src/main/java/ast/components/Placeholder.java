package ast.components;

import java.util.Map;

import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.constraints.Constraint.HolderCon;
import fields.PlaceholderTextField;

public interface Placeholder {
	default void setPlaceHolder(PlaceholderTextField textField, Map<ConstraintId, Constraint> constraints) {
		if(textField.getText().isEmpty() && constraints.containsKey(ConstraintId.HOLDER)) {
			textField.setPlaceholder(((HolderCon)constraints.get(ConstraintId.HOLDER)).value());
		}
	}
}
