package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ast.Id;
import ast.components.CString;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class StringTest {
	@Test public void string01() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				name 'Name:' String('John')
				name 'Second Name:' String('Doe')
				""",
				"Components names must be unique. 'name', is appear more then once.");
	}
	
	@Test public void string02() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				'first group' Group{
				name 'Name:' String('John')
				}
				'second group' Group{
				name 'Second Name:' String('Doe')
				}
				""",
				"Components names must be unique. 'name', is appear more then once.");
	}
	
	@Test public void string03() {
		TestHelper.arrgumentException("""
				'Single Dialog' Single('single my description')
				'first Tab' Tab{
				name 'Name:' String('John')
				}
				'second Tab' Tab{
				name 'Second Name:' String('Doe')
				}
				""",
				"Components names must be unique. 'name', is appear more then once.");
	}
	
	@Test public void string04() {
		TestHelper.arrgumentException("""
			'Single Dialog' Single('single my description')
			name 'Name:' String('John') {max=1 max=6}
			""",
			"'MAX' constraint repeat more then once");
	}
	
	@Test public void string05() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			name 'Name:' String('John') {min=1 max=6 inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[String [name=name, prompt=Name:, defVal=John, \
			constraints=[MinCon[value=1.0], MaxCon[value=6.0], Inline]]]]\
			""");
	}
	
	@Test public void string06() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			name 'Name:' String
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[String [name=name, prompt=Name:, defVal=, \
			constraints=[]]]]\
			""");
	}
	
	@Test public void string07() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			name 'Name:' String{regex='[a-f]' placeholder='my holder' optional}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[String [name=name, prompt=Name:, defVal=, \
			constraints=[RegexCon[value=[a-f]], HolderCon[value=my holder], Optional]]]]\
			""");
	}
	

	@Test
	public void testStringBlockDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.String, "block");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CString("name", "title", "defVal1", constraints);
		JPanelWithValue stringPanel = cs.make();
		assertEquals("defVal1", stringPanel.getValue());
		stringPanel.setValueOrDefault("defVal2", false);
		assertEquals("defVal2", stringPanel.getValue());
		stringPanel.setValueOrDefault("", false);
		assertEquals("", stringPanel.getValue());
		stringPanel.setValueOrDefault("defVal2", true);
		assertEquals("defVal1", stringPanel.getValue());
		TestHelper.withGui(frame, stringPanel, false);
	}
	
	@Test
	public void testStringInlineDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.String, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cs = new CString("name", "title", "defVal1", constraints);
		JPanelWithValue stringPanel = cs.make();
		assertEquals("defVal1", stringPanel.getValue());
		stringPanel.setValueOrDefault("defVal2", false);
		assertEquals("defVal2", stringPanel.getValue());
		stringPanel.setValueOrDefault("", false);
		assertEquals("", stringPanel.getValue());
		stringPanel.setValueOrDefault("defVal2", true);
		assertEquals("defVal1", stringPanel.getValue());
		TestHelper.withGui(frame, stringPanel, false);
	}
}