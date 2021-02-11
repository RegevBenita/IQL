package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;

import fields.JPanelWithValue;
import parser.Parser;

public class TestHelper {
	public static void arrgumentException(String input, String expected) {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Parser.parse(input);
	    });
		assertEquals(expected, exception.getMessage());
	}
	
	public static void numberException(String input, String expected) {
		Exception exception = assertThrows(NumberFormatException.class, () -> {
			Parser.parse(input);
	    });
		assertEquals(expected, exception.getMessage());
	}
	
	public static void checkParseError(String input) {
		Error error = assertThrows(Error.class, () -> {
			Parser.parse(input);
	    });
		assertTrue(error.getMessage().contains("Parsing error"));
	}
	
	public static void checkAst(String input, String expected) {
		var ast = Parser.parse(input);
		assertEquals(expected, ast.toString());
	}
	
	public static void withGui(JFrame frame, JPanelWithValue panel, boolean enabled) {
		if(enabled == false)
			return;
		frame.add(panel);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true); 
		try{System.in.read();}catch(IOException e){}
	}
}
