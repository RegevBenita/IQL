// Generated from src/main/resources/GuiInput.g4 by ANTLR 4.9.1
package generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GuiInputParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GuiInputVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GuiInputParser#compCon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompCon(GuiInputParser.CompConContext ctx);
	/**
	 * Visit a parse tree produced by {@link GuiInputParser#component}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComponent(GuiInputParser.ComponentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GuiInputParser#group}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroup(GuiInputParser.GroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link GuiInputParser#groupOrcomp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroupOrcomp(GuiInputParser.GroupOrcompContext ctx);
	/**
	 * Visit a parse tree produced by {@link GuiInputParser#tab}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTab(GuiInputParser.TabContext ctx);
	/**
	 * Visit a parse tree produced by {@link GuiInputParser#dialogCon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDialogCon(GuiInputParser.DialogConContext ctx);
	/**
	 * Visit a parse tree produced by {@link GuiInputParser#dialog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDialog(GuiInputParser.DialogContext ctx);
	/**
	 * Visit a parse tree produced by {@link GuiInputParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(GuiInputParser.QueryContext ctx);
}