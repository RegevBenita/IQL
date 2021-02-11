package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ast.Id;
import ast.components.CDecimal;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class DecimalTest {
	
	@Test public void decimal1() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Decimal('1.45')
			{max=8 min=2 placeholder='enter your height' inline optional regex='[0-5]+.[0-9]*'}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Decimal [name=height, prompt=Height:, defVal=1.45, \
			constraints=[MaxCon[value=8.0], MinCon[value=2.0], \
			HolderCon[value=enter your height], Inline, Optional, RegexCon[value=[0-5]+.[0-9]*]]]]]\
			""");
	}
	
	@Test public void decimal2() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Decimal('1.45')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Decimal [name=height, prompt=Height:, defVal=1.45, \
			constraints=[]]]]\
			""");
	}
	
	@Test public void decimal3() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Decimal
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Decimal [name=height, prompt=Height:, defVal=null, \
			constraints=[]]]]\
			""");
	}
	
	@Test public void decimal4() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			height 'Height:' Decimal{block}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Decimal [name=height, prompt=Height:, defVal=null, \
			constraints=[Block]]]]\
			""");
	}
	
	@Test public void decimal5() {
		TestHelper.numberException("""
			'User Details' Single('Provide your detail')
			height 'Height' Decimal('hi there')
			""", 
			"""
			hi there is not a valid decimal\
			""");
	}
	
	@Test public void decimal6() {
		TestHelper.checkParseError("""
			'User Details' Single('Provide your detail')
			height 'Height' Decimal{max='five point four'}
			""");
	}
	
	@Test
	public void testDecimalBlockDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Decimal, "block");
		List<Constraint> constraints = List.of(constraint);
		var cd = new CDecimal("name", "title", new BigDecimal("3.0"), constraints);
		JPanelWithValue decimalPanel = cd.make();
		assertEquals("3.0", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("5.0", false);
		assertEquals("5.0", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("", false);
		assertEquals("", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("5.0", true);
		assertEquals("3.0", decimalPanel.getValue());
		TestHelper.withGui(frame, decimalPanel, false);
	}
	
	@Test
	public void testDecimalInlineDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Decimal, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cd = new CDecimal("name", "title", new BigDecimal("3.0"), constraints);
		JPanelWithValue decimalPanel = cd.make();
		assertEquals("3.0", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("5.0", false);
		assertEquals("5.0", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("", false);
		assertEquals("", decimalPanel.getValue());
		decimalPanel.setValueOrDefault("5.0", true);
		assertEquals("3.0", decimalPanel.getValue());
		TestHelper.withGui(frame, decimalPanel, false);
	}
}
