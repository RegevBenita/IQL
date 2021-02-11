package test;

import org.junit.jupiter.api.Test;

public class TabTest {
	
	@Test public void tab01() {
		TestHelper.arrgumentException("""
					'Dialog Title' Single('dialog description')
					'First Tab:' Tab{
					name 'Name:' String('John')
					}
					""",
					"There must be at least 2 tabs");
	}
	
	@Test public void tab02() {
		TestHelper.checkParseError("""
				'Dialog Title' Single('dialog description')
				'First Tab:' Tab{}
				""");
	}
	
	@Test public void tab03() {
		TestHelper.checkParseError("""
			'Dialog Title' Single('dialog description')
			'First Tab:' Taab{
			name 'Name:' String('John')
			}
			""");
	}
	
	@Test public void tab04() {
		TestHelper.arrgumentException("""
				'Dialog Title' Single('dialog description')
				'First Tab:' Tab{
				name 'Name:' String('John')
				}
				""",
				"""
				There must be at least 2 tabs\
				""");
	}
	
	@Test public void tab05() {
		TestHelper.checkAst("""
				'Dialog Title' Single('dialog description')
				'First Tab:' Tab{
				name 'Name:' String('John')
				}
				'Second Tab:' Tab{
					'new group' Group {
						age 'Age' Integer
				}
				surname 'Surname:' String('Doe')
				}
				""",
				"""
				Query[dialog=Single[title=Dialog Title, \
				description=dialog description, constraints=[]], \
				containers=[Tab [title=First Tab:, \
				containers=[String [name=name, prompt=Name:, defVal=John, constraints=[]]]], \
				Tab [title=Second Tab:, containers=[Group [title=new group, \
				components=[Integer [name=age, prompt=Age, defVal=null, \
				constraints=[]]]], String [name=surname, prompt=Surname:, \
				defVal=Doe, constraints=[]]]]]]\
				""");
	}
}
