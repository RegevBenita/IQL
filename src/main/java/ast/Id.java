package ast;

public enum Id{String,Integer,Decimal,Boolean,
	SingleOpt,MultiOpt,Password,Slider,TextArea, Single, Tabular, Pages, Group, Tab, Query, TabsContainer;
	
	public static Id from(String input) {
		return switch(input){
		case "String" -> String;
		case "Integer" -> Integer;
		case "Decimal" -> Decimal;
		case "Boolean" -> Boolean;
		case "SingleOpt" -> SingleOpt;
		case "MultiOpt" -> MultiOpt;
		case "Password" -> Password;
		case "Slider" -> Slider;
		case "TextArea" -> TextArea;
		case "Single" -> Single;
		case "Tabular" -> Tabular;
		case "Pages" -> Pages;
		case "Group" -> Group;
		case "Tab" -> Tab;
		case "Query" -> Query;
		default -> throw new IllegalArgumentException(input + " is not a valide id");
		};
	}

}