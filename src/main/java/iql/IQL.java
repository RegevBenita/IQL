package iql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.Ast;
import parser.Parser;
import ui.TabularVisitor;
import ui.PagesVisitor;
import ui.SingleVisitor;

public class IQL {

	/**
	* A method that accepts a IQL grammar as a String, create a dialog and returns the user inputs. 
	* @param query a IQL grammar for the dialog creation.
	* @return a List<Map<String, String>>, the keys contains the components name and the values contains the user inputs. 
	*/
	public static List<Map<String, String>> run(String query) {
		Ast.Query queryObj = Parser.parse(query);
		var visitor = switch (queryObj.dialog().getType()) {
			case Pages -> new PagesVisitor();
			case Single -> new SingleVisitor();
			case Tabular -> new TabularVisitor();
			default ->
				throw new IllegalArgumentException("Unexpected value: " + queryObj.dialog().getType());
		};
		queryObj.accept(visitor);
		return visitor.getData().join();
	}
	/**
	 * By checking if IQL.run(..).get(0) instanceof IQL.EmptyEntriesMap we can recognize
	 * if we get the user data inside the list of hashmap or not.
	 * IF IQL.run(..).get(0) instanceof IQL.EmptyEntriesMapwith then the the list of hashmap
	 * keys equal to the component name and the values equals to null.
	 * Otherwise, the list of hashmap contains the user data
	 */
	public static final class EmptyEntriesMap extends HashMap<String,String>{}

}
