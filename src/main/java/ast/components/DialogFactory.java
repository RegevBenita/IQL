package ast.components;

import ast.Ast;
import ast.Id;
import generated.GuiInputParser.DialogContext;

public enum DialogFactory {
	Single,
	Pages,
	Multi;
	
	public static Ast.Dialog from(DialogContext ctx) {
		if(ctx == null || ctx.DialogId() == null)
			throw new IllegalArgumentException("DialogContext and Id cannot be null");
		Id id = Id.from(ctx.DialogId().getText());
		return switch (id) {
		case Single -> new DSingle(ctx);
		case Pages -> new DPages(ctx);
		case Tabular -> new DTabular(ctx);
		default ->
		throw new IllegalArgumentException(id + " , is invalid dialog type");
		};
	}
	
}