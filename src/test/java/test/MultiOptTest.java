package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ast.Id;
import ast.components.CMultiOpt;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class MultiOptTest {
	
	@Test public void multiOpt1() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' MultiOpt['Red|Blue|Green']
			{ inlineList optional}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[MultiOpt [name=height, prompt=Height:, \
			options=[Red, Blue, Green], defValues=[], constraints=[InlineList, Optional]]]]\
			""");
	}
	
	@Test public void multiOpt2() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' MultiOpt['Red|Blue|Green']('Red|Blue')
			{ blockList}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[MultiOpt [name=height, prompt=Height:, \
			options=[Red, Blue, Green], defValues=[Red, Blue], constraints=[BlockList]]]]\
			""");
	}
	
	@Test public void multiOpt3() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' MultiOpt['Red|Blue|Green']('Red|Blue')
			{ blockCheckbox}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[MultiOpt [name=height, prompt=Height:, \
			options=[Red, Blue, Green], defValues=[Red, Blue], constraints=[BlockCheckbox]]]]\
			""");
	}
	
	@Test public void multiOpt4() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			height 'Height:' MultiOpt['Red|Blue|Green']('Yellow')
			{inlineList}
			""",
			"'Yellow' is not a valid option");
	}
	
	@Test public void multiOpt5() {
		TestHelper.checkAst("""
				'Single Dialog' Single('single my description')
				height 'Height:' MultiOpt['Red|Blue|Green']('')
				{ inlineList}
				""",
				"""
				Query[dialog=Single[title=Single Dialog, \
				description=single my description, constraints=[]], \
				containers=[MultiOpt [name=height, prompt=Height:, \
				options=[Red, Blue, Green], defValues=[], constraints=[InlineList]]]]\
				""");
	}
	
	@Test public void multiOpt6() {
		TestHelper.checkParseError("""
				'Single Dialog' Single('single my description')
				height 'Height:' MultiOpt[Red|Blue|Green]
				{ inlineList}
				""");
	}
	
	@Test
	public void testMultiOptBlockListDisplay() {
		  var frame=new JFrame();
		  List<String> options = List.of("option1", "option2", "option3", "option4");
		  List<String> defValues = List.of("option1", "option2");
		  Constraint constraint = DisplayId.from(Id.MultiOpt, "blockList");
		  List<Constraint> constraints = List.of(constraint);
		  var cmulti=new CMultiOpt("name","title",options,defValues, constraints);
		  JPanelWithValue multi=cmulti.make();
		  assertEquals("option1, option2", multi.getValue());
		  multi.setValueOrDefault("option3, option4", false);
		  assertEquals("option3, option4", multi.getValue());
		  multi.setValueOrDefault("", true);
		  assertEquals("option1, option2", multi.getValue());
		  multi.setValueOrDefault("", false);
		  assertEquals("", multi.getValue());
		  TestHelper.withGui(frame, multi, false);
	  }
	
	@Test 
	public void testMultiOptInlineListDisplay() {
		  var frame=new JFrame();
		  List<String> options = List.of("option1", "option2", "option3", "option4");
		  List<String> defValues = List.of("option1", "option2");
		  Constraint constraint = DisplayId.from(Id.MultiOpt, "inlineList");
		  List<Constraint> constraints = List.of(constraint);
		  var cmulti=new CMultiOpt("name","title",options,defValues, constraints);
		  JPanelWithValue multi=cmulti.make();
		  assertEquals("option1, option2", multi.getValue());
		  multi.setValueOrDefault("option3, option4", false);
		  assertEquals("option3, option4", multi.getValue());
		  multi.setValueOrDefault("", true);
		  assertEquals("option1, option2", multi.getValue());
		  multi.setValueOrDefault("", false);
		  assertEquals("", multi.getValue());
		  TestHelper.withGui(frame, multi, false);
		  
	  }

	@Test
	public void testMultiOptBlockCheckboxDisplay() {
		var frame=new JFrame();
		List<String> options = List.of("option1", "option2", "option3", "option4");
		List<String> defValues = List.of("option1", "option2");
		Constraint constraint = DisplayId.from(Id.MultiOpt, "blockCheckbox");
		List<Constraint> constraints = List.of(constraint);
		var cmulti=new CMultiOpt("name","title",options,defValues, constraints);
		JPanelWithValue multi=cmulti.make();
		assertEquals("option1, option2", multi.getValue());
		multi.setValueOrDefault("option3, option4", false);
		assertEquals("option3, option4", multi.getValue());
		multi.setValueOrDefault("", true);
		assertEquals("option1, option2", multi.getValue());
		multi.setValueOrDefault("", false);
		  assertEquals("", multi.getValue());
		TestHelper.withGui(frame, multi, false);
	}
	
}
