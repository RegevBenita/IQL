package test;

import org.junit.jupiter.api.Test;

public class EscapeCharTest {

	@Test public void eascape01() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single \\') my description')
			name 'Name:' String('John') {min=1 max=6 inline}
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single ') my description, constraints=[]], \
			containers=[String [name=name, prompt=Name:, defVal=John, \
			constraints=[MinCon[value=1.0], MaxCon[value=6.0], Inline]]]]\
			""");
	}
	
	@Test public void eascape02() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single \\') my description')
			options 'Options' MultiOpt['yes \\|no | Yes | no ]']('yes \\|no | no ]')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single ') my description, constraints=[]], \
			containers=[MultiOpt [name=options, prompt=Options, \
			options=[yes |no, Yes, no ]], defValues=[yes |no, no ]], constraints=[]]]]\
			""");
	}
	
	@Test public void eascape03() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single \\\\[ []{}()"\n\\\'\\| my description')
			options 'Options' MultiOpt['yes \\|no | Yes | no ]']('yes \\|no | no ]')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single \\[ []{}()"
			'| my description, constraints=[]], \
			containers=[MultiOpt [name=options, prompt=Options, \
			options=[yes |no, Yes, no ]], defValues=[yes |no, no ]], constraints=[]]]]\
			""");
	}
	
	@Test public void eascape04() {
		TestHelper.checkAst("""
			'Single Dialog' Single('single \\\\[ []{}()"\n\\\'\\| my description')
			options 'Options' MultiOpt['(yes) \\|no | {\\\'no\\\'}']('(yes) \\|no | {\\\'no\\\'}')
			""",
			"""
			Query[dialog=Single[title=Single Dialog, \
			description=single \\[ []{}()"
			'| my description, constraints=[]], \
			containers=[MultiOpt [name=options, prompt=Options, \
			options=[(yes) |no, {'no'}], defValues=[(yes) |no, {'no'}], constraints=[]]]]\
			""");
	}
	
	@Test public void eascape05() {
		TestHelper.checkAst("""
			'User Details' Single('Provide your detail')
			name 'Name' Decimal{regex='[0-9]*\\\\.[0-9]{2}'}
			""",
			"""
			Query[dialog=Single[title=User Details, \
			description=Provide your detail, constraints=[]], \
			containers=[Decimal [name=name, prompt=Name, defVal=null, \
			constraints=[RegexCon[value=[0-9]*\\.[0-9]{2}]]]]]\
			""");
	}
	
	@Test public void eascape06() {
		TestHelper.checkAst("""
			'User Details' Single('Provide your detail')
			name 'Name' String('\\\\[ []{}()"\\\'\\|'){placeholder='\\\\[ []{}()"\\\'\\|'}
			""",
			"""
			Query[dialog=Single[title=User Details, description=Provide your detail, \
			constraints=[]], containers=[String [name=name, prompt=Name, \
			defVal=\\\\[ []{}()"\\'\\|, constraints=[HolderCon[value=\\\\[ []{}()"\\'\\|]]]]]\
			""");
	}
	
	@Test public void eascape07() {
		TestHelper.checkAst("""
			'User Details' Single('Provide your detail')
			name 'Name' Password('\\\\[ []{}()"\\\'\\|'){placeholder='\\\\[ []{}()"\\\'\\|'}
			""",
			"""
			Query[dialog=Single[title=User Details, description=Provide your detail, \
			constraints=[]], containers=[Password [name=name, prompt=Name, \
			defVal=\\\\[ []{}()"\\'\\|, constraints=[HolderCon[value=\\\\[ []{}()"\\'\\|]]]]]\
			""");
	}
	
	@Test public void eascape08() {
		TestHelper.checkAst("""
			'User Details' Single('Provide your detail')
			comments 'Comments' TextArea('\\\\[ []{}()"\\\'\\|'){placeholder='\\\\[ []{}()"\\\'\\|'}
			""",
			"""
			Query[dialog=Single[title=User Details, description=Provide your detail, \
			constraints=[]], containers=[TextArea [name=comments, prompt=Comments, \
			defVal=\\\\[ []{}()"\\'\\|, constraints=[HolderCon[value=\\\\[ []{}()"\\'\\|]]]]]\
			""");
	}
	
}
