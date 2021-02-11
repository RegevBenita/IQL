package test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ast.Id;
import ast.components.CInteger;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class IntegerTest {
	
	@Test public void integer1() { 
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			phone 'Phone:' Integer('988643')
			{max=8 min=2 placeholder='87654' inline optional}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Integer [name=phone, prompt=Phone:, defVal=988643, \
			constraints=[MaxCon[value=8.0], MinCon[value=2.0], \
			HolderCon[value=87654], Inline, Optional]]]]\
			""");
	}
	
	@Test public void integer2() { 
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			phone 'Phone:' Integer('988643')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Integer [name=phone, prompt=Phone:, defVal=988643, \
			constraints=[]]]]\
			""");
	}
	
	@Test public void integer3() { 
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			phone 'Phone:' Integer
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Integer [name=phone, prompt=Phone:, defVal=null, \
			constraints=[]]]]\
			""");
	}
	
	@Test public void integer4() { 
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			phone 'Phone:' Integer{block}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Integer [name=phone, prompt=Phone:, defVal=null, \
			constraints=[Block]]]]\
			""");
	}
	
	@Test public void integer5() {
		TestHelper.numberException("""
			'User Details' Single('Provide your detail')
			age 'Age' Integer('hi there')
			""", 
			"""
			hi there is not a valid integer\
			""");
	}
	
	@Test public void integer6() {
		TestHelper.checkParseError("""
			'User Details' Single('Provide your detail')
			age 'Age' Integer{min='five'}
			""");
	}
	
	@Test
	public void testIntegerBlockDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Integer, "block");
		List<Constraint> constraints = List.of(constraint);
		var ci = new CInteger("name", "title", new BigInteger("3"), constraints);
		JPanelWithValue integerPanel = ci.make();
		assertEquals("3", integerPanel.getValue());
		integerPanel.setValueOrDefault("5", false);
		assertEquals("5", integerPanel.getValue());
		integerPanel.setValueOrDefault("", false);
		assertEquals("", integerPanel.getValue());
		integerPanel.setValueOrDefault("5", true);
		assertEquals("3", integerPanel.getValue());
		TestHelper.withGui(frame, integerPanel, false);
	}
	
	@Test
	public void testIntegerInlineDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Integer, "inline");
		List<Constraint> constraints = List.of(constraint);
		var ci = new CInteger("name", "title", new BigInteger("3"), constraints);
		JPanelWithValue integerPanel = ci.make();
		assertEquals("3", integerPanel.getValue());
		integerPanel.setValueOrDefault("5", false);
		assertEquals("5", integerPanel.getValue());
		integerPanel.setValueOrDefault("", false);
		assertEquals("", integerPanel.getValue());
		integerPanel.setValueOrDefault("5", true);
		assertEquals("3", integerPanel.getValue());
		TestHelper.withGui(frame, integerPanel, false);
	}
}
