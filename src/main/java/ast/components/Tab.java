package ast.components;

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.Ast;
import ast.Id;
import fields.JPanelContainer;
import ui.Visitor;

public class Tab implements Ast.Containable {
	private String title;
	private List<Tabable> containers;
	
	public Tab(TerminalNode titleNode, List<Tabable> containers) {
		this.title = extractTitle(titleNode);
		this.containers = containers;
	}

	@Override
	public Id getType() { return Id.Tab; }

	public String getTitle() { return title; }

	public List<Tabable> getContainers() {
		return Collections.unmodifiableList(containers);
	}

	@Override
	public String toString() {
		return "Tab [title=" + title + ", containers=" + containers + "]";
	}

	@Override
	public JPanelContainer accept(Visitor v) {
		return v.visitTab(this);
	}

}