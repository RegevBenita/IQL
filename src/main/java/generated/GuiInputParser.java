// Generated from src/main/resources/GuiInput.g4 by ANTLR 4.9.1
package generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GuiInputParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, Whitespace=3, DialogId=4, GroupId=5, TabId=6, CompId=7, 
		ApproveCon=8, CancelCon=9, MinCon=10, MaxCon=11, RegexCon=12, OptionalCon=13, 
		DisplayCon=14, HolderCon=15, SelectedCon=16, MajorTicksCon=17, MinorTicksCon=18, 
		NameWord=19, DefaultValue=20, QuotedCharText=21;
	public static final int
		RULE_compCon = 0, RULE_component = 1, RULE_group = 2, RULE_groupOrcomp = 3, 
		RULE_tab = 4, RULE_dialogCon = 5, RULE_dialog = 6, RULE_query = 7;
	private static String[] makeRuleNames() {
		return new String[] {
			"compCon", "component", "group", "groupOrcomp", "tab", "dialogCon", "dialog", 
			"query"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", null, null, "'Group'", "'Tab'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "Whitespace", "DialogId", "GroupId", "TabId", "CompId", 
			"ApproveCon", "CancelCon", "MinCon", "MaxCon", "RegexCon", "OptionalCon", 
			"DisplayCon", "HolderCon", "SelectedCon", "MajorTicksCon", "MinorTicksCon", 
			"NameWord", "DefaultValue", "QuotedCharText"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "GuiInput.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GuiInputParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class CompConContext extends ParserRuleContext {
		public List<TerminalNode> MaxCon() { return getTokens(GuiInputParser.MaxCon); }
		public TerminalNode MaxCon(int i) {
			return getToken(GuiInputParser.MaxCon, i);
		}
		public List<TerminalNode> MinCon() { return getTokens(GuiInputParser.MinCon); }
		public TerminalNode MinCon(int i) {
			return getToken(GuiInputParser.MinCon, i);
		}
		public List<TerminalNode> RegexCon() { return getTokens(GuiInputParser.RegexCon); }
		public TerminalNode RegexCon(int i) {
			return getToken(GuiInputParser.RegexCon, i);
		}
		public List<TerminalNode> OptionalCon() { return getTokens(GuiInputParser.OptionalCon); }
		public TerminalNode OptionalCon(int i) {
			return getToken(GuiInputParser.OptionalCon, i);
		}
		public List<TerminalNode> HolderCon() { return getTokens(GuiInputParser.HolderCon); }
		public TerminalNode HolderCon(int i) {
			return getToken(GuiInputParser.HolderCon, i);
		}
		public List<TerminalNode> SelectedCon() { return getTokens(GuiInputParser.SelectedCon); }
		public TerminalNode SelectedCon(int i) {
			return getToken(GuiInputParser.SelectedCon, i);
		}
		public List<TerminalNode> MajorTicksCon() { return getTokens(GuiInputParser.MajorTicksCon); }
		public TerminalNode MajorTicksCon(int i) {
			return getToken(GuiInputParser.MajorTicksCon, i);
		}
		public List<TerminalNode> MinorTicksCon() { return getTokens(GuiInputParser.MinorTicksCon); }
		public TerminalNode MinorTicksCon(int i) {
			return getToken(GuiInputParser.MinorTicksCon, i);
		}
		public List<TerminalNode> DisplayCon() { return getTokens(GuiInputParser.DisplayCon); }
		public TerminalNode DisplayCon(int i) {
			return getToken(GuiInputParser.DisplayCon, i);
		}
		public CompConContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compCon; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).enterCompCon(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).exitCompCon(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GuiInputVisitor ) return ((GuiInputVisitor<? extends T>)visitor).visitCompCon(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompConContext compCon() throws RecognitionException {
		CompConContext _localctx = new CompConContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compCon);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			match(T__0);
			setState(20);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MinCon) | (1L << MaxCon) | (1L << RegexCon) | (1L << OptionalCon) | (1L << DisplayCon) | (1L << HolderCon) | (1L << SelectedCon) | (1L << MajorTicksCon) | (1L << MinorTicksCon))) != 0)) {
				{
				{
				setState(17);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MinCon) | (1L << MaxCon) | (1L << RegexCon) | (1L << OptionalCon) | (1L << DisplayCon) | (1L << HolderCon) | (1L << SelectedCon) | (1L << MajorTicksCon) | (1L << MinorTicksCon))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(22);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(23);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComponentContext extends ParserRuleContext {
		public TerminalNode NameWord() { return getToken(GuiInputParser.NameWord, 0); }
		public TerminalNode QuotedCharText() { return getToken(GuiInputParser.QuotedCharText, 0); }
		public TerminalNode CompId() { return getToken(GuiInputParser.CompId, 0); }
		public TerminalNode DefaultValue() { return getToken(GuiInputParser.DefaultValue, 0); }
		public CompConContext compCon() {
			return getRuleContext(CompConContext.class,0);
		}
		public ComponentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_component; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).enterComponent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).exitComponent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GuiInputVisitor ) return ((GuiInputVisitor<? extends T>)visitor).visitComponent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComponentContext component() throws RecognitionException {
		ComponentContext _localctx = new ComponentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_component);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			match(NameWord);
			setState(26);
			match(QuotedCharText);
			setState(27);
			match(CompId);
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DefaultValue) {
				{
				setState(28);
				match(DefaultValue);
				}
			}

			setState(32);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(31);
				compCon();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupContext extends ParserRuleContext {
		public TerminalNode QuotedCharText() { return getToken(GuiInputParser.QuotedCharText, 0); }
		public TerminalNode GroupId() { return getToken(GuiInputParser.GroupId, 0); }
		public List<ComponentContext> component() {
			return getRuleContexts(ComponentContext.class);
		}
		public ComponentContext component(int i) {
			return getRuleContext(ComponentContext.class,i);
		}
		public GroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).enterGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).exitGroup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GuiInputVisitor ) return ((GuiInputVisitor<? extends T>)visitor).visitGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupContext group() throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_group);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(QuotedCharText);
			setState(35);
			match(GroupId);
			setState(36);
			match(T__0);
			setState(38); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(37);
				component();
				}
				}
				setState(40); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==NameWord );
			setState(42);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupOrcompContext extends ParserRuleContext {
		public GroupContext group() {
			return getRuleContext(GroupContext.class,0);
		}
		public ComponentContext component() {
			return getRuleContext(ComponentContext.class,0);
		}
		public GroupOrcompContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupOrcomp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).enterGroupOrcomp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).exitGroupOrcomp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GuiInputVisitor ) return ((GuiInputVisitor<? extends T>)visitor).visitGroupOrcomp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GroupOrcompContext groupOrcomp() throws RecognitionException {
		GroupOrcompContext _localctx = new GroupOrcompContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_groupOrcomp);
		try {
			setState(46);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QuotedCharText:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				group();
				}
				break;
			case NameWord:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				component();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TabContext extends ParserRuleContext {
		public TerminalNode QuotedCharText() { return getToken(GuiInputParser.QuotedCharText, 0); }
		public TerminalNode TabId() { return getToken(GuiInputParser.TabId, 0); }
		public List<GroupOrcompContext> groupOrcomp() {
			return getRuleContexts(GroupOrcompContext.class);
		}
		public GroupOrcompContext groupOrcomp(int i) {
			return getRuleContext(GroupOrcompContext.class,i);
		}
		public TabContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tab; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).enterTab(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).exitTab(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GuiInputVisitor ) return ((GuiInputVisitor<? extends T>)visitor).visitTab(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TabContext tab() throws RecognitionException {
		TabContext _localctx = new TabContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_tab);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(QuotedCharText);
			setState(49);
			match(TabId);
			setState(50);
			match(T__0);
			setState(52); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(51);
				groupOrcomp();
				}
				}
				setState(54); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==NameWord || _la==QuotedCharText );
			setState(56);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DialogConContext extends ParserRuleContext {
		public List<TerminalNode> MaxCon() { return getTokens(GuiInputParser.MaxCon); }
		public TerminalNode MaxCon(int i) {
			return getToken(GuiInputParser.MaxCon, i);
		}
		public List<TerminalNode> MinCon() { return getTokens(GuiInputParser.MinCon); }
		public TerminalNode MinCon(int i) {
			return getToken(GuiInputParser.MinCon, i);
		}
		public List<TerminalNode> ApproveCon() { return getTokens(GuiInputParser.ApproveCon); }
		public TerminalNode ApproveCon(int i) {
			return getToken(GuiInputParser.ApproveCon, i);
		}
		public List<TerminalNode> CancelCon() { return getTokens(GuiInputParser.CancelCon); }
		public TerminalNode CancelCon(int i) {
			return getToken(GuiInputParser.CancelCon, i);
		}
		public DialogConContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dialogCon; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).enterDialogCon(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).exitDialogCon(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GuiInputVisitor ) return ((GuiInputVisitor<? extends T>)visitor).visitDialogCon(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DialogConContext dialogCon() throws RecognitionException {
		DialogConContext _localctx = new DialogConContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_dialogCon);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(T__0);
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ApproveCon) | (1L << CancelCon) | (1L << MinCon) | (1L << MaxCon))) != 0)) {
				{
				{
				setState(59);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ApproveCon) | (1L << CancelCon) | (1L << MinCon) | (1L << MaxCon))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(65);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DialogContext extends ParserRuleContext {
		public TerminalNode QuotedCharText() { return getToken(GuiInputParser.QuotedCharText, 0); }
		public TerminalNode DialogId() { return getToken(GuiInputParser.DialogId, 0); }
		public TerminalNode DefaultValue() { return getToken(GuiInputParser.DefaultValue, 0); }
		public DialogConContext dialogCon() {
			return getRuleContext(DialogConContext.class,0);
		}
		public DialogContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dialog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).enterDialog(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).exitDialog(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GuiInputVisitor ) return ((GuiInputVisitor<? extends T>)visitor).visitDialog(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DialogContext dialog() throws RecognitionException {
		DialogContext _localctx = new DialogContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_dialog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(QuotedCharText);
			setState(68);
			match(DialogId);
			setState(69);
			match(DefaultValue);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(70);
				dialogCon();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryContext extends ParserRuleContext {
		public DialogContext dialog() {
			return getRuleContext(DialogContext.class,0);
		}
		public TerminalNode EOF() { return getToken(GuiInputParser.EOF, 0); }
		public List<GroupOrcompContext> groupOrcomp() {
			return getRuleContexts(GroupOrcompContext.class);
		}
		public GroupOrcompContext groupOrcomp(int i) {
			return getRuleContext(GroupOrcompContext.class,i);
		}
		public List<TabContext> tab() {
			return getRuleContexts(TabContext.class);
		}
		public TabContext tab(int i) {
			return getRuleContext(TabContext.class,i);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GuiInputListener ) ((GuiInputListener)listener).exitQuery(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GuiInputVisitor ) return ((GuiInputVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_query);
		int _la;
		try {
			setState(89);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				dialog();
				setState(75); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(74);
					groupOrcomp();
					}
					}
					setState(77); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NameWord || _la==QuotedCharText );
				setState(79);
				match(EOF);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				dialog();
				setState(83); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(82);
					tab();
					}
					}
					setState(85); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==QuotedCharText );
				setState(87);
				match(EOF);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\27^\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\7\2\25\n\2"+
		"\f\2\16\2\30\13\2\3\2\3\2\3\3\3\3\3\3\3\3\5\3 \n\3\3\3\5\3#\n\3\3\4\3"+
		"\4\3\4\3\4\6\4)\n\4\r\4\16\4*\3\4\3\4\3\5\3\5\5\5\61\n\5\3\6\3\6\3\6\3"+
		"\6\6\6\67\n\6\r\6\16\68\3\6\3\6\3\7\3\7\7\7?\n\7\f\7\16\7B\13\7\3\7\3"+
		"\7\3\b\3\b\3\b\3\b\5\bJ\n\b\3\t\3\t\6\tN\n\t\r\t\16\tO\3\t\3\t\3\t\3\t"+
		"\6\tV\n\t\r\t\16\tW\3\t\3\t\5\t\\\n\t\3\t\2\2\n\2\4\6\b\n\f\16\20\2\4"+
		"\3\2\f\24\3\2\n\r\2`\2\22\3\2\2\2\4\33\3\2\2\2\6$\3\2\2\2\b\60\3\2\2\2"+
		"\n\62\3\2\2\2\f<\3\2\2\2\16E\3\2\2\2\20[\3\2\2\2\22\26\7\3\2\2\23\25\t"+
		"\2\2\2\24\23\3\2\2\2\25\30\3\2\2\2\26\24\3\2\2\2\26\27\3\2\2\2\27\31\3"+
		"\2\2\2\30\26\3\2\2\2\31\32\7\4\2\2\32\3\3\2\2\2\33\34\7\25\2\2\34\35\7"+
		"\27\2\2\35\37\7\t\2\2\36 \7\26\2\2\37\36\3\2\2\2\37 \3\2\2\2 \"\3\2\2"+
		"\2!#\5\2\2\2\"!\3\2\2\2\"#\3\2\2\2#\5\3\2\2\2$%\7\27\2\2%&\7\7\2\2&(\7"+
		"\3\2\2\')\5\4\3\2(\'\3\2\2\2)*\3\2\2\2*(\3\2\2\2*+\3\2\2\2+,\3\2\2\2,"+
		"-\7\4\2\2-\7\3\2\2\2.\61\5\6\4\2/\61\5\4\3\2\60.\3\2\2\2\60/\3\2\2\2\61"+
		"\t\3\2\2\2\62\63\7\27\2\2\63\64\7\b\2\2\64\66\7\3\2\2\65\67\5\b\5\2\66"+
		"\65\3\2\2\2\678\3\2\2\28\66\3\2\2\289\3\2\2\29:\3\2\2\2:;\7\4\2\2;\13"+
		"\3\2\2\2<@\7\3\2\2=?\t\3\2\2>=\3\2\2\2?B\3\2\2\2@>\3\2\2\2@A\3\2\2\2A"+
		"C\3\2\2\2B@\3\2\2\2CD\7\4\2\2D\r\3\2\2\2EF\7\27\2\2FG\7\6\2\2GI\7\26\2"+
		"\2HJ\5\f\7\2IH\3\2\2\2IJ\3\2\2\2J\17\3\2\2\2KM\5\16\b\2LN\5\b\5\2ML\3"+
		"\2\2\2NO\3\2\2\2OM\3\2\2\2OP\3\2\2\2PQ\3\2\2\2QR\7\2\2\3R\\\3\2\2\2SU"+
		"\5\16\b\2TV\5\n\6\2UT\3\2\2\2VW\3\2\2\2WU\3\2\2\2WX\3\2\2\2XY\3\2\2\2"+
		"YZ\7\2\2\3Z\\\3\2\2\2[K\3\2\2\2[S\3\2\2\2\\\21\3\2\2\2\r\26\37\"*\608"+
		"@IOW[";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}