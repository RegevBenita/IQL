package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ast.Id;
import ast.components.CTextArea;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class TextAreaTest {
	
	@Test public void textArea1() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' TextArea('My default value')
			{max=4 min=2  placeholder='my holder' inline optional}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[TextArea [name=cats, prompt=Have cats?, defVal=My default value, \
			constraints=[MaxCon[value=4.0], MinCon[value=2.0], \
			HolderCon[value=my holder], Inline, Optional]]]]\
			""");
	}
	
	@Test public void textArea2() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' TextArea
			{max=4 min=2  placeholder='my holder' inline optional}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[TextArea [name=cats, prompt=Have cats?, defVal=, \
			constraints=[MaxCon[value=4.0], MinCon[value=2.0], \
			HolderCon[value=my holder], Inline, Optional]]]]\
			""");
	}
	
	@Test public void textArea3() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' TextArea
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[TextArea [name=cats, prompt=Have cats?, defVal=, \
			constraints=[]]]]\
			""");
	}
	
	@Test public void textArea4() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' TextArea{block}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[TextArea [name=cats, prompt=Have cats?, defVal=, \
			constraints=[Block]]]]\
			""");
	}
	
	@Test public void textArea5() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			cats 'Have cats?' TextArea{block regex='[a-z]'}
			""",
			"""
			TextArea is not support Regex constraint\
			""");
	}
	
	@Test
	public void testStringBlockDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.TextArea, "block");
		List<Constraint> constraints = List.of(constraint);
		var cta = new CTextArea("name", "title", "defVal1", constraints);
		JPanelWithValue textArea = cta.make();
		assertEquals("defVal1", textArea.getValue());
		textArea.setValueOrDefault("defVal2", false);
		assertEquals("defVal2", textArea.getValue());
		textArea.setValueOrDefault("", false);
		assertEquals("", textArea.getValue());
		textArea.setValueOrDefault("defVal2", true);
		assertEquals("defVal1", textArea.getValue());
		TestHelper.withGui(frame, textArea, false);
	}
	
	@Test
	public void testStringInlineDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.TextArea, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cta = new CTextArea("name", "title", "defVal1", constraints);
		JPanelWithValue textArea = cta.make();
		assertEquals("defVal1", textArea.getValue());
		textArea.setValueOrDefault("defVal2", false);
		assertEquals("defVal2", textArea.getValue());
		textArea.setValueOrDefault("", false);
		assertEquals("", textArea.getValue());
		textArea.setValueOrDefault("defVal2", true);
		assertEquals("defVal1", textArea.getValue());
		TestHelper.withGui(frame, textArea, false);
	}
}
