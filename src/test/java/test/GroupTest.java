package test;

import org.junit.jupiter.api.Test;

public class GroupTest {
	
	@Test public void group01() {
		TestHelper.checkAst("""
				'Dialog Title' Single('dialog description')
				'First Group:' Group{
					name 'Name:' String('John')
				}
				""",
				"""
				Query[dialog=Single[title=Dialog Title, \
				description=dialog description, constraints=[]], \
				containers=[Group [title=First Group:, \
				components=[String [name=name, prompt=Name:, \
				defVal=John, constraints=[]]]]]]\
				""");
	}
	
	@Test public void group02() {
		TestHelper.checkParseError("""
				'Dialog Title' Single('dialog description')
				'First Group:' Group{}
				}
				""");
	}
	
	@Test public void group03() {
		TestHelper.checkParseError("""
				'Dialog Title' Single('dialog description')
				'First Group:' GroupY{
					name 'Name:' String('John')
				}
				""");
	}
}
