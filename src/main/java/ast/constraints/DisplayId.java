package ast.constraints;

import ast.Id;

public enum DisplayId implements Constraint{
	Inline, Block, InlineRadio,	InlineList, 
	BlockRadio, BlockList, InlineCheckbox, BlockCheckbox, Non;
	
	public static DisplayId from(Id id, String value) {
		return switch (id) {
		case String, Integer, Decimal, Password, TextArea, Boolean, Slider -> getDisplay(value);
		case MultiOpt -> getMultiDisplay(value);
		case SingleOpt -> getSingleDisplay(value);
		default ->
			throw new IllegalArgumentException(id + " is not support display constraint");
		};
	}
	
	private static DisplayId getDisplay(String value) {
		return switch (value) {
		case "inline" -> Inline;
		case "block" -> Block;
		case "inlineList" -> InlineList;
		case "blockList" -> BlockList;
		default -> throw new IllegalArgumentException(value + " is not a valide component display");
		};
	}
	
	private static DisplayId getSingleDisplay(String value) {
		return switch (value) {
		case "inlineList" -> InlineList;
		case "blockRadio" -> BlockRadio;
		case "blockList" -> BlockList;
		default -> throw new IllegalArgumentException(value + " is not a valide component display");
		};
	}
	
	private static DisplayId getMultiDisplay(String value) {
		return switch (value) {
		case "blockList" -> BlockList;
		case "inlineList" -> InlineList;
		case "blockCheckbox" -> BlockCheckbox;
		default -> throw new IllegalArgumentException(value + " is not a valide component display");
		};
	}

	@Override
	public ConstraintId getID() {
		return ConstraintId.DISPLAY;
	}
}
