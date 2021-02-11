package ast.components;

import java.util.function.Function;

import ast.Id;
import generated.GuiInputParser.ComponentContext;

public enum ComponentFactory {
	String(ctx -> new CString(ctx)),
	Integer(ctx -> new CInteger(ctx)),
	Decimal(ctx -> new CDecimal(ctx)),
	Boolean(ctx -> new CBoolean(ctx)),
	SingleOpt(ctx -> new CSingleOpt(ctx)),
	MultiOpt(ctx -> new CMultiOpt(ctx)),
	Password(ctx -> new CPassword(ctx)),
	Slider(ctx -> new CSlider(ctx)),
	TextArea(ctx -> new CTextArea(ctx));

	ComponentFactory(Function<ComponentContext, Component> operation) {
		this.createComp = operation;
	}

	private final Function<ComponentContext, Component> createComp;

	public static Component from(ComponentContext ctx) {
		if(ctx == null || ctx.CompId() == null)
			throw new IllegalArgumentException("ComponentContext and Id cannot be null");
		String idText = ctx.CompId().getText();
		Id id = idText.indexOf("[") == -1? Id.from(idText) : Id.from(idText.substring(0, idText.indexOf("[")));
		return switch(id){
		case String -> String.createComp.apply(ctx);
		case Integer -> Integer.createComp.apply(ctx);
		case Decimal -> Decimal.createComp.apply(ctx);
		case Boolean -> Boolean.createComp.apply(ctx);
		case SingleOpt -> SingleOpt.createComp.apply(ctx);
		case MultiOpt -> MultiOpt.createComp.apply(ctx);
		case Password -> Password.createComp.apply(ctx);
		case Slider -> Slider.createComp.apply(ctx);
		case TextArea -> TextArea.createComp.apply(ctx);
		default -> throw new IllegalArgumentException(id + " is not a valide id");
		};
	}
}