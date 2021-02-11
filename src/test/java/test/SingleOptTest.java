package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ast.Id;
import ast.components.CSingleOpt;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class SingleOptTest {
	
	@Test public void singleOpt1() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' SingleOpt['Red|Blue|Green']
			{inlineList optional}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[SingleOpt [name=height, prompt=Height:, options=[Red, Blue, Green], \
			defValue=, constraints=[InlineList, Optional]]]]\
			""");
	}
	
	@Test public void singleOpt2() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' SingleOpt['Red|Blue|Green']('')
			{blockList}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[SingleOpt [name=height, prompt=Height:, options=[Red, Blue, Green], \
			defValue=, constraints=[BlockList]]]]\
			""");
	}
	
	@Test public void singleOpt3() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' SingleOpt['Red|Blue|Green']('Red')
			{blockRadio}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[SingleOpt [name=height, prompt=Height:, options=[Red, Blue, Green], \
			defValue=Red, constraints=[BlockRadio]]]]\
			""");
	}
	
	@Test public void singleOpt4() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			height 'Height:' SingleOpt['Red|Blue|Green']('Green|Blue')
			{inlineList}
			""",
			"'Green|Blue' is not a valid option");
	}
	
	@Test public void singleOpt5() {
		TestHelper.checkParseError("""
			'Single Dialog' Single('single my description')
			height 'Height:' SingleOpt[Red|Blue|Green]
			{inlineList}
			""");
	}
	
	@Test
	public void testSingleOptBlockListDisplay() {
		var frame = new JFrame();
		List<String> options = List.of("option1", "option2", "option3", "option4");
		Constraint constraint = DisplayId.from(Id.SingleOpt, "blockList");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CSingleOpt("name", "title", options, "option1", constraints);
		JPanelWithValue single = cs.make();
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("option4", false);
		assertEquals("option4", single.getValue());
		single.setValueOrDefault("", true);
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("", false);
		assertEquals("", single.getValue());
		TestHelper.withGui(frame, single, false);
	}

	@Test
	public void testSingleOptInlineListDisplay() {
		var frame = new JFrame();
		List<String> options = List.of("option1", "option2", "option3", "option4");
		Constraint constraint = DisplayId.from(Id.SingleOpt, "inlineList");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CSingleOpt("name", "title", options, "option1", constraints);
		JPanelWithValue single = cs.make();
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("option4", false);
		assertEquals("option4", single.getValue());
		single.setValueOrDefault("", true);
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("", false);
		assertEquals("", single.getValue());
		TestHelper.withGui(frame, single, false);
	}

	@Test
	public void testSingleOptBlockRadioDisplay() {
		var frame = new JFrame();
		List<String> options = List.of("option1", "option2", "option3", "option4");
		Constraint constraint = DisplayId.from(Id.SingleOpt, "blockRadio");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CSingleOpt("name", "title", options, "option1", constraints);
		JPanelWithValue single = cs.make();
		assertEquals("option1", single.getValue());
		single.setValueOrDefault("option4", false);
		assertEquals("option4", single.getValue());
		single.setValueOrDefault("", false);
		assertEquals("", single.getValue());
		TestHelper.withGui(frame, single, false);
	}
}
