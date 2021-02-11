package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ast.Id;
import ast.components.CBoolean;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class BooleanTest {
	
	@Test public void boolean01() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Boolean('true')
			dogs 'Have dogs?' Boolean('false')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Boolean [name=cats, prompt=Have cats?, \
			defVal=true, constraints=[]], Boolean [name=dogs, \
			prompt=Have dogs?, defVal=false, constraints=[]]]]\
			""");
	}
	
	@Test public void boolean02() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Boolean
			dogs 'Have dogs?' Boolean
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Boolean [name=cats, prompt=Have cats?, \
			defVal=, constraints=[]], Boolean [name=dogs, \
			prompt=Have dogs?, defVal=, constraints=[]]]]\
			""");
	}
	
	@Test public void boolean03() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			dogs 'Have dogs?' Boolean('yes')
			""",
			"Boolean default value must be only 'true' or 'false'");
	}
	
	@Test public void boolean04() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Boolean{optional inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Boolean [name=cats, prompt=Have cats?, \
			defVal=, constraints=[Optional, Inline]]]]\
			""");
	}
	
	@Test public void boolean5() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Boolean{block}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Boolean [name=cats, prompt=Have cats?, \
			defVal=, constraints=[Block]]]]\
			""");
	}
	
	@Test public void boolean6() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Boolean{blockList}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Boolean [name=cats, prompt=Have cats?, \
			defVal=, constraints=[BlockList]]]]\
			""");
	}
	
	@Test public void boolean7() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' Boolean{inlineList}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Boolean [name=cats, prompt=Have cats?, \
			defVal=, constraints=[InlineList]]]]\
			""");
	}
	
	@Test
	public void testBooleanBlockDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Boolean, "block");
		List<Constraint> constraints = List.of(constraint);
		var cb = new CBoolean("name", "title", "true", constraints);
		JPanelWithValue booleanPanel = cb.make();
		assertEquals("true", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", false);
		assertEquals("false", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("", false);
		assertEquals("", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", true);
		assertEquals("true", booleanPanel.getValue());
		TestHelper.withGui(frame, booleanPanel, false);
	}
	
	@Test
	public void testBooleanInlineDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Boolean, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cb = new CBoolean("name", "title", "true", constraints);
		JPanelWithValue booleanPanel = cb.make();
		assertEquals("true", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", false);
		assertEquals("false", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("", false);
		assertEquals("", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", true);
		assertEquals("true", booleanPanel.getValue());
		TestHelper.withGui(frame, booleanPanel, false);
	}
	
	@Test
	public void testBooleanBlockListDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Boolean, "blockList");
		List<Constraint> constraints = List.of(constraint);
		var cb = new CBoolean("name", "title", "true", constraints);
		JPanelWithValue booleanPanel = cb.make();
		assertEquals("true", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", false);
		assertEquals("false", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("", false);
		assertEquals("", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", true);
		assertEquals("true", booleanPanel.getValue());
		TestHelper.withGui(frame, booleanPanel, false);
	}
	
	@Test
	public void testBooleanInlineListDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Boolean, "inlineList");
		List<Constraint> constraints = List.of(constraint);
		var cb = new CBoolean("name", "title", "true", constraints);
		JPanelWithValue booleanPanel = cb.make();
		assertEquals("true", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", false);
		assertEquals("false", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("", false);
		assertEquals("", booleanPanel.getValue());
		booleanPanel.setValueOrDefault("false", true);
		assertEquals("true", booleanPanel.getValue());
		TestHelper.withGui(frame, booleanPanel, false);
	}

}
