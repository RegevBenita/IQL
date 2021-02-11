package parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import ast.Ast.Query;
import generated.GuiInputLexer;
import generated.GuiInputParser;

public class Parser {
	public static class FailConsole extends ConsoleErrorListener {
		public final StringBuilder sb;

		public FailConsole(StringBuilder sb) {
			this.sb = sb;
		}

		@Override
		public void syntaxError(Recognizer<?, ?> r, Object o, int line, int charPos, String msg,
				RecognitionException e) {
			sb.append("Parsing error in line " + line + " position " + charPos + ":\n" + msg);
		}
	}

	public static Query parse(String s) {
		var l = new GuiInputLexer(CharStreams.fromString(s));
		var t = new CommonTokenStream(l);
		var p = new GuiInputParser(t);
		StringBuilder errorst = new StringBuilder();
		StringBuilder errorsp = new StringBuilder();
		l.removeErrorListener(ConsoleErrorListener.INSTANCE);
		l.addErrorListener(new FailConsole(errorst));
		p.removeErrorListener(ConsoleErrorListener.INSTANCE);
		p.addErrorListener(new FailConsole(errorsp));
		var q = p.query();
		if (errorst.length() != 0 || errorsp.length() != 0) {
			throw new Error("" + errorst + errorsp);
		}
		var v = new FullParser();
		Query result = v.visitQuery(q);
		return result;
	}
}