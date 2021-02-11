package ast.components;

import ast.Ast.Tabable;
import generated.GuiInputParser.ComponentContext;
import generated.GuiInputParser.GroupOrcompContext;

public class TabableFactory {

	public static Tabable generate(GroupOrcompContext ctx) {
		if(ctx.component() != null && ctx.component().CompId() != null) {
			ComponentContext cCtx = ctx.component();
			return ComponentFactory.from(cCtx);
		}
		throw new IllegalArgumentException("Must provide at least one group or component");
	}
}
