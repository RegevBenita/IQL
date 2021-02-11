package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.tree.*;

import ast.Ast;
import ast.Id;
import ast.components.Component;
import ast.components.ComponentFactory;
import ast.Ast.Containable;
import ast.components.DialogFactory;
import ast.components.Group;
import ast.components.Tab;
import ast.constraints.Constraint;
import ast.constraints.ConstraintId;
import ast.Ast.Tabable;
import generated.GuiInputParser.*;

import generated.GuiInputVisitor;

public class FullParser implements GuiInputVisitor<Object>{
	private Set<String> nameSet = new HashSet<String>();

	@Override
	public Object visit(ParseTree arg0) {
		return null;
	}

	@Override
	public Object visitChildren(RuleNode arg0) {
		return null;
	}

	@Override
	public Object visitErrorNode(ErrorNode arg0) {
		return null;
	}

	@Override
	public Object visitTerminal(TerminalNode arg0) {
		return null;
	}

	@Override
	public List<Constraint> visitCompCon(CompConContext ctx) {
		return null;
	}

	@Override
	public Component visitComponent(ComponentContext ctx) {
		var name = ctx.NameWord().getText();
		if(nameSet.contains(name))
			throw new IllegalArgumentException("Components names must be unique. '" + name + "', is appear more then once.");
		nameSet.add(name);
		Component comp = ComponentFactory.from(ctx);
		return comp;
	}
	
	public String extractCompTitle(ComponentContext ctx) {
		if(ctx.QuotedCharText() == null)
			throw new IllegalArgumentException("Component title is not valid");
		return checkNewLine(subString(ctx.QuotedCharText().getText(), 1, 1));
	}
	
	public String extractCompDefVal(ComponentContext ctx) {
		return ctx.DefaultValue() == null? "":checkNewLine(subString(ctx.DefaultValue().getText(), 2, 2));
	}
	
	public List<Constraint> extractCompConstraints(ComponentContext ctx) {
		Id id = Id.from(ctx.CompId().getText());
		return ctx.compCon() == null ? List.of() : extractConstraints(id, ctx.compCon().children);
	}

	public String extractCompName(ComponentContext ctx) {
		if(ctx.NameWord() == null)
			throw new IllegalArgumentException("Component name is not valid");
		return ctx.NameWord().getText();
	}

	@Override
	public Tabable visitGroup(GroupContext ctx) {
		if(ctx.QuotedCharText() == null)
			throw new IllegalArgumentException("Group title is not valid");
		TerminalNode titleNode = ctx.QuotedCharText();
		if(ctx.component() == null)
			throw new IllegalArgumentException("Group must have at least one component");
		List<Component> components = ctx.component().stream()
				.map(c -> visitComponent(c))
				.collect(Collectors.toList());
		return (Tabable) new Group(titleNode, components);
	}
	
	private String checkNewLine(String text) {
		if(text != null && text.contains("\n") || text.contains("\r"))
			throw new IllegalArgumentException(text + " must not contain a new line");
		return text;
	}

	@Override
	public Tabable visitGroupOrcomp(GroupOrcompContext ctx) {
		if(ctx.component() != null) 
			return visitComponent(ctx.component());
		if(ctx.group() != null) 
			return visitGroup(ctx.group());
		throw new IllegalArgumentException("Must provide at least one group or component");
	}

	@Override
	public Tab visitTab(TabContext ctx) {
		if(ctx.QuotedCharText() == null)
			throw new IllegalArgumentException("Tab title is not valid");
		if(ctx.groupOrcomp().isEmpty())
			throw new IllegalArgumentException("Tab must contain at least one component or group");
		TerminalNode titleNode = ctx.QuotedCharText();
		List<Tabable> containers = ctx.groupOrcomp().stream()
			.map(c -> visitGroupOrcomp(c))
			.collect(Collectors.toList());
		return new Tab(titleNode, containers);
	}

	@Override
	public List<Constraint> visitDialogCon(DialogConContext ctx) {
		return null;
	}

	@Override
	public Ast.Dialog visitDialog(DialogContext ctx) {
		return DialogFactory.from(ctx);
	}

	@Override
	public Ast.Query visitQuery(QueryContext ctx) {
		if(ctx == null || ctx.dialog().isEmpty())
			throw new IllegalArgumentException("You must provide the dialog details");
		Ast.Dialog dialog = visitDialog(ctx.dialog());
		List<Containable> containers = getContainers(ctx);				
		return new Ast.Query(dialog, containers);
	}

	private List<Containable> getContainers(QueryContext ctx) {
		if(ctx.groupOrcomp() == null && ctx.tab() == null)
			throw new IllegalArgumentException("You must provide at least one type of Container");
		if(!ctx.groupOrcomp().isEmpty() && !ctx.tab().isEmpty())
			throw new IllegalArgumentException("You cannot provide Tab container with Group or Component containers");
		List<Containable> containers = new ArrayList<>();
		if(!ctx.groupOrcomp().isEmpty()) {
			containers = ctx.groupOrcomp().stream()
					.map(c -> (Containable)visitGroupOrcomp(c))
					.collect(Collectors.toList());
		}
		if(!ctx.tab().isEmpty()) {
			if(ctx.tab().size() < 2)
				throw new IllegalArgumentException("There must be at least 2 tabs");
			containers = ctx.tab().stream()
					.map(c -> visitTab(c))
					.collect(Collectors.toList());
		}
		return containers;
	}

	private String subString(String text, int offsetBegining, int offsetEnd) {
		return text.substring(offsetBegining, text.length()-offsetEnd);
	}
	
	// Return a list of constraint and validate them according to the provided Id
	private List<Constraint> extractConstraints(Id id, List<ParseTree> children) {
		if(children == null) return List.of();
		Set<String> stringconsts = new HashSet<String>();
		List<Constraint> constraints = children.stream()
		.filter(c-> !("{".equals(c.getText()) || "}".equals(c.getText())))
		.map(c -> {
			String constraint = c.getText();
			String constraintName = constraint.substring(0, constraint.indexOf('=')).trim();
			if(stringconsts.contains(constraintName))
				throw new IllegalArgumentException("'"+ constraintName + "' constraint repeat more then once");
			stringconsts.add(constraintName);
			return ConstraintId.from(id, constraint);
			})
		.collect(Collectors.toList());
		return constraints;
	}

}
