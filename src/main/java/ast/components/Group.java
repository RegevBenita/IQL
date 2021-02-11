package ast.components;

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.Ast;
import ast.Id;
import fields.JPanelContainer;
import ui.Visitor;

public class Group implements Ast.Tabable {
	private String title;
	private List<Component> components;
	
	public Group(TerminalNode titleNode, List<Component> components) {
		this.components = components;
		this.title = extractTitle(titleNode);
	}

	@Override
	public Id getType() { return Id.Group; }
	
	public List<Component> getComponents() {
		return Collections.unmodifiableList(components);
	}

	public String getTitle() { return title; }

	@Override
	public String toString() {
		return "Group [title=" + title + ", components=" + components + "]";
	}

	@Override
	public JPanelContainer accept(Visitor v) {
		return v.visitGroup(this);
	}

}
