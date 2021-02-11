grammar GuiInput;
@header {package generated;}

compCon: '{' (MaxCon | MinCon | RegexCon | OptionalCon
	| HolderCon | SelectedCon | MajorTicksCon | MinorTicksCon
	| DisplayCon )* '}';
component: NameWord QuotedCharText CompId DefaultValue? compCon?;
group: QuotedCharText GroupId '{' component+ '}';
groupOrcomp: group | component;
tab: QuotedCharText TabId '{'groupOrcomp+ '}';
dialogCon: '{' (MaxCon | MinCon | ApproveCon | CancelCon)* '}';
dialog: QuotedCharText DialogId DefaultValue dialogCon?;
query: dialog groupOrcomp+ EOF | dialog tab+ EOF;

fragment LowerCaseLetter: [a-z];
fragment UpperCaseLetter: [A-Z];
fragment Bool: 'true' | 'false';
fragment Digit: [0-9];
fragment Integer: '-'?Digit+;
fragment Decimal: Integer ('.'Digit+)?;
fragment MultiOpt: 'MultiOpt[' QuotedCharText ']';
fragment SingleOpt: 'SingleOpt[' QuotedCharText ']';
fragment Slider: 'Slider[' QuotedCharText ']';
//fragment CharText: (LowerCaseLetter | UpperCaseLetter | Digit 
//  | '<' | '>' | '&' | '|' | '*' | '+' | '-' | '=' | '!' | '%' | '[' | ']'
//  | '?' | ';' | ':' | ',' | '.' | ' ' | '~' | '@' | '#' | '$' 
//  | '`' | '^' | '_' | '"' | '\n' | ')' | '(' | '{' | '}')* ;
fragment CharText: (LowerCaseLetter | UpperCaseLetter | Digit 
  | '<' | '>' | '&' | '|' | '*' | '+' | '-' | '=' | '!' | '%' | '?' | ';' 
  | ':' | ',' | '.' | ' ' | '~' | '@' | '#' | '$' | '`' | '^' | '_' 
 | '"' | ')' | '(' | ']' | '[' | '{' | '}' 
 | '\\\''  // \'
 | '\\|'   // \|
 | '\n'    // NEW LINE
 | '\\\\'    // \\
)*;       
Whitespace: [ \t\r\n]-> channel(HIDDEN);
DialogId: 'Single' | 'Tabular' | 'Pages';
GroupId: 'Group';
TabId: 'Tab';
CompId: 'String' | 'Integer' | 'Decimal' 
		| 'Boolean' | SingleOpt | MultiOpt 
		| 'Password' | Slider | 'TextArea';
ApproveCon: 'approve' Whitespace* '=' Whitespace* QuotedCharText;
CancelCon: 'cancel' Whitespace* '=' Whitespace* QuotedCharText;
MinCon: 'min' Whitespace* '=' Whitespace* Decimal;
MaxCon: 'max' Whitespace* '=' Whitespace* Decimal;
RegexCon: 'regex' Whitespace* '=' Whitespace* QuotedCharText;
OptionalCon: Whitespace* 'optional' Whitespace*;
DisplayCon: 'inline' | 'block' | 'inlineList' | 'blockList' | 'blockRadio' | 'blockCheckbox';
HolderCon: 'placeholder' Whitespace* '=' Whitespace* QuotedCharText;
SelectedCon: 'selected' Whitespace* '=' Whitespace* QuotedCharText;
MajorTicksCon: 'majorTicks' Whitespace* '=' Whitespace* Integer;
MinorTicksCon: 'minorTicks' Whitespace* '=' Whitespace* Integer;
NameWord: LowerCaseLetter (LowerCaseLetter | UpperCaseLetter)*;
DefaultValue: '(' QuotedCharText ')';
QuotedCharText: '\'' CharText '\'';