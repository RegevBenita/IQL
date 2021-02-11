// Generated from src/main/resources/GuiInput.g4 by ANTLR 4.9.1
package generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GuiInputParser}.
 */
public interface GuiInputListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GuiInputParser#compCon}.
	 * @param ctx the parse tree
	 */
	void enterCompCon(GuiInputParser.CompConContext ctx);
	/**
	 * Exit a parse tree produced by {@link GuiInputParser#compCon}.
	 * @param ctx the parse tree
	 */
	void exitCompCon(GuiInputParser.CompConContext ctx);
	/**
	 * Enter a parse tree produced by {@link GuiInputParser#component}.
	 * @param ctx the parse tree
	 */
	void enterComponent(GuiInputParser.ComponentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GuiInputParser#component}.
	 * @param ctx the parse tree
	 */
	void exitComponent(GuiInputParser.ComponentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GuiInputParser#group}.
	 * @param ctx the parse tree
	 */
	void enterGroup(GuiInputParser.GroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link GuiInputParser#group}.
	 * @param ctx the parse tree
	 */
	void exitGroup(GuiInputParser.GroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link GuiInputParser#groupOrcomp}.
	 * @param ctx the parse tree
	 */
	void enterGroupOrcomp(GuiInputParser.GroupOrcompContext ctx);
	/**
	 * Exit a parse tree produced by {@link GuiInputParser#groupOrcomp}.
	 * @param ctx the parse tree
	 */
	void exitGroupOrcomp(GuiInputParser.GroupOrcompContext ctx);
	/**
	 * Enter a parse tree produced by {@link GuiInputParser#tab}.
	 * @param ctx the parse tree
	 */
	void enterTab(GuiInputParser.TabContext ctx);
	/**
	 * Exit a parse tree produced by {@link GuiInputParser#tab}.
	 * @param ctx the parse tree
	 */
	void exitTab(GuiInputParser.TabContext ctx);
	/**
	 * Enter a parse tree produced by {@link GuiInputParser#dialogCon}.
	 * @param ctx the parse tree
	 */
	void enterDialogCon(GuiInputParser.DialogConContext ctx);
	/**
	 * Exit a parse tree produced by {@link GuiInputParser#dialogCon}.
	 * @param ctx the parse tree
	 */
	void exitDialogCon(GuiInputParser.DialogConContext ctx);
	/**
	 * Enter a parse tree produced by {@link GuiInputParser#dialog}.
	 * @param ctx the parse tree
	 */
	void enterDialog(GuiInputParser.DialogContext ctx);
	/**
	 * Exit a parse tree produced by {@link GuiInputParser#dialog}.
	 * @param ctx the parse tree
	 */
	void exitDialog(GuiInputParser.DialogContext ctx);
	/**
	 * Enter a parse tree produced by {@link GuiInputParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(GuiInputParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GuiInputParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(GuiInputParser.QueryContext ctx);
}