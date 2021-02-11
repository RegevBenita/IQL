package iql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.Ast;
import parser.Parser;
import ui.TabularVisitor;
import ui.PagesVisitor;
import ui.SingleVisitor;

public class IQL {

	/**
	* A method that accepts a IQL grammar as a String, create a dialog and returns the user inputs. 
	* @param query a IQL grammar for the dialog creation.
	* @return a List<Map<String, String>>, the keys contains the components name and the values contains the user inputs. 
	*/
	public static List<Map<String, String>> run(String query) {
		Ast.Query queryObj = Parser.parse(query);
		var visitor = switch (queryObj.dialog().getType()) {
			case Pages -> new PagesVisitor();
			case Single -> new SingleVisitor();
			case Tabular -> new TabularVisitor();
			default ->
				throw new IllegalArgumentException("Unexpected value: " + queryObj.dialog().getType());
		};
		queryObj.accept(visitor);
		return visitor.getData().join();
	}
	/**
	 * By checking if IQL.run(..).get(0) instanceof IQL.EmptyEntriesMap we can recognize
	 * if we get the user data inside the list of hashmap or not.
	 * IF IQL.run(..).get(0) instanceof IQL.EmptyEntriesMapwith then the the list of hashmap
	 * keys equal to the component name and the values equals to null.
	 * Otherwise, the list of hashmap contains the user data
	 */
	public static final class EmptyEntriesMap extends HashMap<String,String>{}
	
	
	public static void main(String[] args) {
//		var query = """
//				'User Registration Form' Single(please provide some details)
//				'Personal Details' Tab{
//					name 'Full Name:' String{min=2}
//					age 'Age:' Integer{min=1 max=120 optional}
//					'Family details' Group{
//						middle 'Middle Name' String{min=5 inline holder='Enter your name'}
//						marry 'Are you marry?' Boolean(false)
//					}
//				}
//				'Account Details' Tab{
//				password 'Password:' Password{holder='Password'}
//				comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you'}
//				work 'Working Field' MultiOpt[Computers|Building|Teaching|Non of the above](Computers|Building){
//				blockList optional}
//				}
//				""";
//		var query = """
//		'User Registration Form' Single('please provide a accurate details about yourself')
//		'Personal Details' Tab{
//			name 'Full Name:' String{min=2}
//			age 'Age:' Integer{min=1 max=120 required=false}
//			middle 'Middle Name' String{required=false min=5}
//			marry 'Are you marry?' Boolean('false'){required=false}
//		}
//		'Account Details' Tab{
//		password 'Password:' Password{holder='Password'}
//		comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you'}
//		work 'Working Field' MultiOpt('Computers|Building|Teaching|Non of the above'){
//		display=blockList required=false selected='Computers|Building'}
//		}
//		""";
		
		var queryBlock = """
		'User Registration Form' Pages('please` `')
		name 'Full Name:' String{min=2}
		age 'Age:' Integer{min=1 max=120 }
		middle 'Middle Name' String{ min=5}
		marry 'Are you marry?' Boolean(false)
		mar 'Are you marry?' Boolean{blockList}
		height 'Height:' Decimal
		password 'Password:' Password{holder='Password'}
		comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you'}
		work 'Working Field' MultiOpt[Building|Teaching]{optional}
		slider 'Slider' Slider[0,10](5)
		single 'Single' SingleOpt[Computers|Teaching]
		singles 'Single2' SingleOpt[Computers|Building]{blockList}
		works 'Working Field2' MultiOpt[Computers|Building](Building){blockList}
		""";
		
//		var queryInline = """
//		'User Registration Form' Single('please provide a accurate details about yourself
//		and your family in order to be able to do something... or not')
//		name 'Full Name:' String{min=2 inline}
//		age 'Age:' Integer{min=1 max=120 inline}
//		middle 'Middle Name' String{ min=5 inline}
//		marry 'Are you marry?' Boolean('false'){ inline optional}
//		mar 'Are you marry?' Boolean{ inlineList }
//		height 'Height:' Decimal{ inline optional }
//		password 'Password:' Password{optional holder='Password' inline}
//		comments 'Comments:' TextArea{min=4 max=20 optional holder='We would like to here from you' inline}
//		slider 'Slider' Slider('0,10,5'){inline optional  }
//		singles 'Single2' SingleOpt('Computers|Building'){inlineList}
//		works 'Working Field2' MultiOpt('Computers|Building'){inlineList selected='Computers|Building'
//		}
//		""";
		
		var queryInline = """
		'User Registration Form' Single(please provide a accurate 
		and your family in order to be able to )
		name 'Full Name:' String{min=2 inline}
		works 'Working Field2' MultiOpt[Computers|Building|Food](Computers|Building){inlineList optional}
		worky 'Working Field2' SingleOpt[Computers|Building|Food](Building){inlineList}
		slider 'my SliDer' Slider[0,12](6){inline}
		""";
		
//		var queryBlock2 = """
//		'User Registration Form' Single('please provide a accurate details about
//		yourself adfasdas a d asd asd a sd as d as')
//		name 'Full Name:' String{min=2}
//		age 'Age:' Integer{min=1 max=120 required=false}
//		middle 'Middle Name' String{required=false min=5}
//		marry 'Are you marry?' Boolean('false'){required=false}
//		height 'Height:' Decimal
//		password 'Password:' Password{holder='Password'}
//		comments 'Comments:' TextArea{min=4 max=20 holder='We would like to here from you'}
//		work 'Working Field' MultiOpt('Computers|Building|Teaching|Non of the above'){display=blockList
//		required=false selected='Computers|Building'}
//		slider 'Slider' Slider('0,10,5')
//		single 'Single' SingleOpt('Computers|Building|Teaching|Non of the above'){display=blockList}
//		""";
		
		
//		var query = """
//		'User Registration Form' Multi('please provide a accurate details about yourself')
//			name 'Full Name:' String{min=2}
//			age 'Age:' Integer{min=1 max=120 required=false}
//			middle 'Middle Name' String{required=false min=5}
//			marry 'Are you marry?' Boolean('false'){required=false}
//		password 'Password:' Password{holder='Password'}
//		work 'Working Field' MultiOpt('Computers|Building|Teaching|Non of the above'){
//		display=blockList required=false selected='Computers|Building'}
//		""";
//		run(query);
		
		
		var exampleString01 = """
				'User Details' Single(Please provide your personal details)
					name 'Full Name:' String{inline}
				""";
		
		var exampleString02 = """
				'User Details' Single(Please provide your personal details)
					name 'First Name:' String(Jack)
				""";
		var exampleString03 = """
				'User Details' Single(Please provide your details below)
					name 'First Name:' String{min=2 max=12 placeholder='Enter you name here' optional regex='[a-zA-Z]+'}
				""";
		
		var exampleInteger01 = """
				'User Details' Single(Please provide your personal details)
					age 'Age:' Integer
				""";
		
		var exampleInteger02 = """
				'User Details' Single(Please provide your personal details)
				age 'Age:' Integer(34)
			""";
		var exampleInteger03 = """
				'User Details' Single('Please provide your details below')
					age 'Age:' Integer{min=1 max=120 placeholder='Enter you age here' optional}
				""";
		
		var exampleInteger04 = """
				'User Details' Single(Please provide your details below)
					name 'Full Name:' String{inline placeholder='Enter you name here'}
					age 'Age:' Integer{inline min=1 max=120 placeholder='Enter you age here' optional}
				""";
		
		var exampleDecimal01 = """
				'User Details' Single(Please provide your personal details)
					weight 'Weight:' Decimal
				""";
		
		var exampleDecimal02 = """
				'User Details' Single('Please provide your personal details')
				weight 'Weight:' Decimal('67.4'){
					min=10.1
					max=220.6
				placeholder='Enter you weight in Kg'
				optional
				}
			""";
		var exampleDecimal03 = """
				'User Details' Single('Please provide your details below')
					weight 'Weight:' Decimal{min=10.1 max=220.6 placeholder='Enter you weight in Kg' optional}
				""";
		
		var exampleDecimal04 = """
				'User Details' Multi(Please provide your details below )
					name 'Full Name:' String{placeholder='Enter you name here'}
					age 'Age:' Integer{placeholder='Enter you age here' optional}
					weight 'Weight:' Decimal{min=10 max=220 placeholder='Enter you weight in Kg' optional}
				""";
		
		var exampletextl01 = """
				'User Details' Single('Please provide your personal details')
					comment 'Comment:' TextArea
				""";
		var exampletextl02 = """
				'User Details' Single('Please provide your personal details')
					comment 'Comment:' TextArea('My name is...')
				""";
		var exampletextl03 = """
				'User Details' Single('Please provide your personal details')
				comment 'Comment:' TextArea{
					min=10
					max=100
				  	placeholder='Enter your comment here'
				  	optional}
				""";
		
		var exampletextl04 = """
				'User Details' Single('Please provide your personal details')
				comment 'Comment:' TextArea{
				  inline
				  min=10 
				  max=300
				  placeholder='Enter your comment here'
				  optional
				}
				""";
		
		var examplePassl01 = """
				'Create password' Single(Please provide your details below)
					password 'Password:' Password
				""";
		
		var examplePassl02 = """
				'Create password' Single('Please provide your details below')
				password 'Password:' Password('MyPassword')
				""";
		
		var examplePassl03 = """
				'Create password' Single('Please provide your details below')
				password 'Password:' Password{
					min=6
					max=12
					placeholder='Enter your password here'
					optional
					regex='[A][a-z]+'
				}
				""";
		
		
		var examplePassl04 = """
				'Create password' Single(Please provide your details below)
				name 'User Name:' String{inline placeholder='Enter you name here'}
				password 'Password:' Password{
					inline
					min=6
					max=12
					placeholder='Enter your password here'
					regex='[A][a-z]+'
				}
				""";
		
		var exampleBooleanl01 = """
				'Personal details' Single('Please provide your details below')
				married 'married?' Boolean{ optional}
				""";
		
		var exampleBooleanl02 = """
				'Personal details' Single(Please provide your details below)
				married 'married?' Boolean(true)
				""";
		
		var exampleSingleOpt01 = """
				'Personal details' Single(Please provide your details below)
				employment 'Employment Status:' SingleOpt[Full Time|Part Time|Self Employed|Not Employed]{inlineList optional}
				""";
		var exampleSingleOpt02 = """
				'Personal details' Single(Please provide your details below)
				employment 'Employment Status:' SingleOpt[Full Time|Part Time|Self Employed|Not Employed](Part Time)
				""";
		var exampleSingleOpt03 = """
				'Personal details' Single('Please provide your details below')
				married 'Are you married?' SingleOpt['True|False']
				""";
		var exampleSingleOpt04 = """
				'Personal details' Tabular('Please provide your details below')
				employment 'Employment Status:' SingleOpt['Full Time|Part Time|Self Employed|Not Employed']('Part Time')
				""";
		var exampleMultiOpt01 = """
				'Personal details' Single(Please provide your details below)
				countries 'Visited countries:' MultiOpt[Chile|Peru|Germany|Sweden]{optional blockCheckbox}
				""";
		var exampleMultiOpt02 = """
				'Personal details' Single(Please provide your details below)
				countries 'Visited countries:' MultiOpt[Chile|Peru|Germany|Sweden](Germany|Sweden)
				""";
		
		var exampleSlider01 = """
				'Personal details' Single('Please provide your details below')
				children 'Number of children:' Slider['0,12']
				""";
		
		var exampleSlider02 = """
				'Personal details' Single('Please provide your details below')
				children 'Number of children:' Slider['0,12']('5')
				""";
		var exampleSlider03 = """
				'Personal details' Single('Please provide your details below')
				children 'Number of children:' Slider['0,12']{
				  majorTicks=3
				  }
				""";
		
		var exampleSlider04 = """
				'Personal details' Single('Please provide your details below')
				children 'Number of children:' Slider['0,12']{
				  inline
				  }
				""";
		
		var exampleGroup01 = """
				'Personal details' Single('Please provide your details below')
				'Address' Group{
					city 'City:' String
				}
				""";
		
		var exampleGroup02 = """
				'Personal details' Single('Please provide your details below')
				name 'Full Name:' String
				'Address' Group{
					city 'City:' String
					street 'Street:' String
				}
				'Additional details' Group{
					married 'Married:' Boolean
					age 'Age:' Integer
				}
				""";
		
		var exampleTab01 = """
				'Personal details' Single('Please provide your details below')
				'Personal details' Tab{
					name 'Full Name:' String
				}
				'Address' Tab{
					city 'City:' String
				}
				""";
		var exampleTab02 = """
				'Personal details' Pages('Please provide your details below'){min=3 max=7 approve= 'Play' cancel='Quit' }
				'Personal details' Tab{
					name 'Full Name:' String{optional placeholder='hi'}
					'Address' Group{
						city 'City:' String{optional}
						street 'Street:' String{optional}
					}
				}
				'Additional details' Tab{
					married 'Married:' Boolean{optional}
					age 'Age:' Integer{optional}
					weight 'Weight:' Decimal{optional}
				}
				""";
		
		var exampleIntroduction01 = """
				'Person data' Pages('Please fill your personal data bellow'){max=2 min=2}
				  name 'Name' String
				  age 'Age' Integer
				  weight 'We23232ight' Decimal
				  nameaaaaa 'N32323ame' String
				  agea 'Ag1111111e' Integer
				  weightaaaaa 'Weightsa23232dadas' Decimal
				""";
		
		var exampleIntroduction02 = """
				'User Registration Form' Single('Please provide an accurate details about yourself')
				  'Personal Details' Tab{
				    name 'Full Name:' String{min=2}
				    age 'Age:' Integer{min=1 max=120}
				    middle 'Middle Name' String{optional}
				  'Family details' Group{
					marry 'Are you married?' Boolean
					children 'How many children do you have?' Slider['0,12']('4') {majorTicks=3 minorTicks=1}
				    }
				  }
				  'Account Details' Tab{
					password 'Password:' Password{placeholder='Password'}
					comments 'Comments:' TextArea{min=4 max=20 placeholder='We would like to here from you'}
					work 'Working Field' SingleOpt['Computers|Building|Teaching|Non of the above']
				}
				""";
		
		var exampleSingleDialog01 = """
				'User Details' Single('Please provide your personal details')
				name 'Full Name:' String
				""";
		
		var exampleSingleDialog02 = """
				'Personal details' Pages('Please provide your details belowaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'){
				  approve='Register'
				  cancel='Exit'
				}
				name 'Full Name:' String
				'Address' Group{
				  city 'City:' String
				  street 'Street:' String
				  }
				'Additional details' Group{
				  married 'Married:' Boolean
				  age 'Age:' Integer
				  }
				""";
		
		var examplePagesDialog01 = """
				'User Details' Pages('Please provide your personal details')
				name 'Full Name:' String
				""";
		
		var examplePagesDialog02 = """
				'Personal details' Pages('Please provide your details below'){
				  min=2
				  max=7
				  approve='Register'
				  cancel='Exit'
				}
				id 'ID number' Password
				name 'Full Name' String
				'Address' Group{
				  city 'City:' String
				  street 'Street:' String
				  }
				'Additional details' Group{
				  married 'Married:' Boolean
				  age 'Age:' Integer
				  }
				""";
		
		var exampleTabularDialog01 = """
				'User Details' Tabular('Please provide your personal details')
				name 'First Name' String
				surname 'Surname' String
				""";
		
		var exampleTabularDialog02 = """
				'Personal details' Tabular('Please provide your details below'){
					min=2
					max=30
					approve='Register'
					cancel='Exit'
					}
				name 'Full Name:' String{optional}
				id 'ID number' Password{optional}
				city 'City' String{optional}
				age 'Age' Integer{optional}
				weight 'Weight' Decimal{optional} 
				occupation 'Occupation' SingleOpt['Builder|Farmer|Baker|No occupation']{optional} 
				""";
		
		var all = """
				'title' Pages('This is the descriptionaaaaaaaaa')
				'Tab1' Tab{
				string 'String' String{optional}
				password 'Password' Password{optional}
					'Group' Group {
					integer 'Integer' Integer{optional}
					decimal 'Decimal' Decimal{optional}
					textarea 'TextArea' TextArea{optional}
					}
				}
				'Tab2' Tab{
				boolean 'Boolean' Boolean{ optional}
				slider 'Slider' Slider['3,22']
				singleOpt 'SingleOpt' SingleOpt['option1|option2']{optional}
				multiOpt 'MultiOpt' MultiOpt['option1|option2']{ optional}
				}
				""";
		
		
		var allInline = """
				'title' Pages('This is the descriptionaaaaaaaaa')
				'Tab 1' Tab{
					string 'String' String{optional inline}
					password 'Password' Password{optional inline}
						'Group' Group {
							integer 'Integer' Integer{optional inline}
							decimal 'Decimal' Decimal{optional inline}
							textarea 'TextArea' TextArea{optional inline}
					}
				}
				'Tab2' Tab{
					boolean 'Boolean' Boolean{inlineList optional}
					slider 'Slider' Slider['3,22']{inline}
					singleOpt 'SingleOpt' SingleOpt['option1|option2']{optional inlineList}
					multiOpt 'MultiOpt' MultiOpt['option1|option2']{inlineList optional}
				}
				""";
		
		var allTabular = """
				'title' Tabular('This is the description')
				string 'String' String{optional}
				password 'Passwordsddssdsdsdsdsdd' Password{optional}
				integer 'Integer' Integer{optional}
				decimal 'Decimal' Decimal{optional}
				boolean 'Boolean' Boolean{optional}
				singleOpt 'SingleOpt' SingleOpt['option1|option2']{optional}
				multiOpt 'MultiOpt' MultiOpt['option1|option2']{optional}
				""";
		var related1 = """
				'User Details' Single('Provide your details below')
				name 'Name' String{placeholder='Enter your name here'}
				pets 'Have pets?' Boolean('true')
				""";
		
		var phishing = """
				'Password update' Single('Please update your password, only 3 days left
				 to update your password') {
				 approve='Update Password'
				 }
				oldPassword 'Old Password' Password
				newPassword 'New Password' Password
				rePassword 'Re-enter New Password' Password
				""";
		
		var escape = """
				'User Details' Tabular('Provide \\ \\" \\' your detail \\[s below
				\\{ \\} \\] \\( \\) | \\\\')
				name 'Name \\{ \\} \\] \\( \\) | \\\\ \\[ \\" \\'' String{placeholder='Enter your name here'}
				pets 'Have pets?' Boolean('true')
				""";
		
		String name = "Full Name";
//		var dialog = """
//				'User Details' Single('Provide your details below')
//				name %s String
//				""";
		
		var dialog = """
				  'User Details' Single('Provide your details below')
				  name \'%s\' String
				  """;
		
		String query = String.format(dialog,name);
		
		name = """
				Full Name' String{placeholder='Enter name here'}
				id 'Id' Integer
				bank 'Bank Account' Integer
				email 'Email""";
		
		String attackerQuery = String.format(dialog,name);
		
		var decRegex = """
				'User Details' Single('Provide your detail')
				name 'Name' Decimal{regex='[0-9]*\\\\.[0-9]{2}'}
				""";
		
		var l42Example = """
				'Provide some points' Single('please provide a point')
				x 'x  =' Integer{optional inline}
				y 'y coordinate =' Integer{optional inline}
				""";
		
		var multi = """
				'Provide some points' Single('please provide a point')
				x 'x' Boolean{optional inlineList}
				y 'y' Boolean{optional blockList}
				z 'z' Boolean{}
				t 't' Boolean{inline}
				""";
		
		var inline = """
				'User Details' Single('Provide your detail')
				name 'Name' String{inline optional}
				another 'Another Name' String{inline optional}
				age 'Age' Integer{inline optional}
				comments 'Comments' TextArea{inline optional}
				height 'Height' Decimal{inline optional}
				password 'Password' Password{inline optional}
				""";
		
		var pageMax = """
				'User Details' Single('Provide your detail')
				name 'Name' String{ optional inline}
				another 'Another Name' String{inline optional}
				age 'Age' Integer{inline optional}
				comments 'Comments' TextArea{inline optional}
				height 'Height' Decimal{inline optional}
				password 'Password' Password{inline optional}
				""";
		
//		  |'Provide some points'
//		  |      Pages(@msg)
//		  |x 'x coordinate =' Integer
//		  |y 'y coordinate =' Integer
		
		
//		run(exampleString03);
//		run(queryBlock);
//		run(queryInline);
//		List<Map<String, String>> results = run(query);
		
		List<Map<String, String>> results = run(inline);
		System.out.println(results);
	}

}
