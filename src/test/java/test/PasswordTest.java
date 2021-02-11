package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ast.Id;
import ast.components.CPassword;
import ast.constraints.Constraint;
import ast.constraints.DisplayId;
import fields.JPanelWithValue;

public class PasswordTest {
	
	@Test public void password1() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			password 'Password:' Password('My default value')
			{max=8 min=2 placeholder='my holder' inline regex='[1-9]+'}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Password [name=password, prompt=Password:, defVal=My default value, \
			constraints=[MaxCon[value=8.0], MinCon[value=2.0], \
			HolderCon[value=my holder], Inline, RegexCon[value=[1-9]+]]]]]\
			""");
	}
	
	@Test public void password2() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			password 'Password:' Password('My default value')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Password [name=password, prompt=Password:, defVal=My default value, \
			constraints=[]]]]\
			""");
	}
	
	@Test public void password3() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			password 'Password:' Password
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Password [name=password, prompt=Password:, defVal=, \
			constraints=[]]]]\
			""");
	}
	
	@Test public void password4() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single my description')
			password 'Password:' Password{block}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single my description, constraints=[]], \
			containers=[Password [name=password, prompt=Password:, defVal=, \
			constraints=[Block]]]]\
			""");
	}
	
	@Test
	public void testPasswordBlockDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Password, "block");
		List<Constraint> constraints = List.of(constraint);
		var cp = new CPassword("name", "title", "Pass1", constraints);
		JPanelWithValue password = cp.make();
		assertEquals("Pass1", password.getValue());
		password.setValueOrDefault("Pass2", false);
		assertEquals("Pass2", password.getValue());
		password.setValueOrDefault("", false);
		assertEquals("", password.getValue());
		password.setValueOrDefault("Pass2", true);
		assertEquals("Pass1", password.getValue());
		TestHelper.withGui(frame, password, false);
	}
	
	@Test
	public void testPasswordInlineDisplay() {
		var frame = new JFrame();
		Constraint constraint = DisplayId.from(Id.Password, "inline");
		List<Constraint> constraints = List.of(constraint);
		var cp = new CPassword("name", "title", "Pass1", constraints);
		JPanelWithValue password = cp.make();
		assertEquals("Pass1", password.getValue());
		password.setValueOrDefault("Pass2", false);
		assertEquals("Pass2", password.getValue());
		password.setValueOrDefault("", false);
		assertEquals("", password.getValue());
		password.setValueOrDefault("Pass2", true);
		assertEquals("Pass1", password.getValue());
		TestHelper.withGui(frame, password, false);
	}
}
