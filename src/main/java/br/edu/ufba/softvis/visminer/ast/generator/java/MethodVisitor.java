package br.edu.ufba.softvis.visminer.ast.generator.java;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import br.edu.ufba.softvis.visminer.ast.Statement;
import br.edu.ufba.softvis.visminer.constant.NodeType;

public class MethodVisitor extends ASTVisitor {

	private List<Statement> statements = new ArrayList<Statement>();
	private int maxNesting = 0;

	public MethodVisitor() {
		this.maxNesting = 0;
	}

	@Override
	public boolean visit(BreakStatement node) {
		return addStatement(NodeType.BREAK, null);
	}

	@Override
	public boolean visit(CatchClause node) {
		return addStatement(NodeType.CATCH, node.getException().getName().getFullyQualifiedName());
	}

	@Override
	public boolean visit(ContinueStatement node) {
		return addStatement(NodeType.CONTINUE, null);
	}

	@Override
	public boolean visit(DoStatement node) {
		calculateMaxNesting(node);
		return addStatement(NodeType.DO_WHILE, node.getExpression().toString());
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		calculateMaxNesting(node);
		return addStatement(NodeType.FOR, node.getExpression().toString());
	}

	@Override
	public boolean visit(ForStatement node) {
		calculateMaxNesting(node);
		return addStatement(NodeType.FOR, node.getExpression().toString());
	}

	@Override
	public boolean visit(IfStatement node) {
		calculateMaxNesting(node);
		addStatement(NodeType.IF, node.getExpression().toString());
		if (node.getElseStatement() != null) {
			calculateMaxNesting(node.getElseStatement());
			addStatement(NodeType.ELSE, null);
		}
		return true;
	}

	@Override
	public boolean visit(ReturnStatement node) {
		return addStatement(NodeType.RETURN, null);
	}

	@Override
	public boolean visit(SwitchCase node) {
		calculateMaxNesting(node);
		if (node.isDefault())
			return addStatement(NodeType.SWITCH_DEFAULT, null);
		else
			return addStatement(NodeType.SWITCH_CASE, node.getExpression().toString());

	}

	@Override
	public boolean visit(ThrowStatement node) {
		return addStatement(NodeType.THROW, node.getExpression().toString());
	}

	@Override
	public boolean visit(TryStatement node) {
		calculateMaxNesting(node);
		addStatement(NodeType.TRY, null);
		if (node.getFinally() != null) {
			calculateMaxNesting(node.getFinally());
			addStatement(NodeType.FINALLY, null);
		}
		return true;
	}

	@Override
	public boolean visit(WhileStatement node) {
		calculateMaxNesting(node);
		return addStatement(NodeType.WHILE, node.getExpression().toString());
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		if (node.getNodeType() == ASTNode.CONDITIONAL_EXPRESSION)
			return addStatement(NodeType.CONDITIONAL_EXPRESSION, node.getExpression().toString());
		return true;
	}

	@Override
	public boolean visit(MethodInvocation node) {
		if (node.resolveMethodBinding() != null) {
			ITypeBinding type = node.resolveMethodBinding().getDeclaringClass();
			if (type.isFromSource()) {
				String expression = type.getQualifiedName() + "." + node.resolveMethodBinding().getName();
				return addStatement(NodeType.METHOD_INVOCATION, expression);
			}
		}
		return true;
	}

	@Override
	public boolean visit(SimpleName node) {
		boolean addStatement = true;
		if (node.resolveBinding() != null) {
			if (node.resolveBinding().getKind() == IBinding.VARIABLE) {
				IVariableBinding variable = (IVariableBinding) node.resolveBinding();
				if (variable.getDeclaringClass() != null) {
					if (variable.isField() && variable.getDeclaringClass().isFromSource()) {
						String expression = variable.getDeclaringClass().getQualifiedName() + "." + variable.getName();
						addStatement = addStatement(NodeType.FIELD_ACCESS, expression);
					}
				} else if (!variable.isField() && !variable.isEnumConstant() && !variable.isParameter()) {
					addStatement = addStatement(NodeType.VARIABLE, variable.getName());
				}
				addStatement(NodeType.VARIABLE_ACCESS, variable.getName());
			}
		}
		return addStatement;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public int getMaxNesting() {
		return maxNesting;
	}

	private boolean addStatement(NodeType type, String expression) {
		Statement stmt = new Statement();
		stmt.setNodeType(type);
		stmt.setExpression(expression);
		statements.add(stmt);
		return true;
	}

	private boolean okToNextNestingLevel(ASTNode node) {
		return ((node.getNodeType() == ASTNode.DO_STATEMENT) || (node.getNodeType() == ASTNode.FOR_STATEMENT)
				|| (node.getNodeType() == ASTNode.ENHANCED_FOR_STATEMENT)
				|| (node.getNodeType() == ASTNode.IF_STATEMENT) || (node.getNodeType() == ASTNode.SWITCH_CASE)
				|| (node.getNodeType() == ASTNode.SWITCH_STATEMENT) || (node.getNodeType() == ASTNode.TRY_STATEMENT)
				|| (node.getNodeType() == ASTNode.WHILE_STATEMENT) || (node.getNodeType() == ASTNode.CATCH_CLAUSE));
	}

	private void calculateMaxNesting(ASTNode node) {
		int nesting = 1;
		while ((node.getParent() != null) && (okToNextNestingLevel(node.getParent()))) {
			nesting++;

			node = node.getParent();
		}

		if (nesting > maxNesting) {
			maxNesting = nesting;
		}
	}

}