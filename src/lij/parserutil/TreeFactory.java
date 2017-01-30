/*
 * LiJ Copyright 2009 Nikolaos Chatzinikolaou nchatzi@gmail.com
 * 
 * This file is part of LiJ.
 * 
 * LiJ is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * LiJ is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with LiJ. If not, see <http://www.gnu.org/licenses/>.
 */

package lij.parserutil;




import java.util.ArrayList;
import java.util.Stack;

import lij.model.Def;
import lij.model.Operator;
import lij.model.TreeNode;
import lij.model.TreeNodeToken;



/**
 * This class is used by the parser to generate the tree structure that contains
 * the elements of a clause.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class TreeFactory
{
	private Stack<Operator> operatorStack = new Stack<Operator>();
	private ArrayList<TreeNodeToken> rpn = new ArrayList<TreeNodeToken>();
	


	/**
	 * Pushes a new token into the stack, and updtes the RPN (Reverse Polish
	 * Notation) list accordingly.
	 * @param token The token to push.
	 */
	public void pushTreeNodeToken(TreeNodeToken token)
	{
		if (token instanceof Def)
			rpn.add(token);
		else if (token instanceof Operator)
		{
			Operator operator = (Operator)token;
			if (operator instanceof Operator.OpenParen)
				operatorStack.push(operator);
			else if (operator instanceof Operator.CloseParen)
			{
				Operator popped;
				do
				{
					popped = operatorStack.pop();
					if (!(popped instanceof Operator.OpenParen))
						rpn.add(popped);
				} while (!(popped instanceof Operator.OpenParen));
			}
			else
			{
				while (operatorStack.size() > 0 && operator.getPrecedence() < operatorStack.peek().getPrecedence())
					rpn.add(operatorStack.pop());
				operatorStack.push(operator);
			}
		}
	}
	


	/**
	 * Pops all of the remaining tokens from the stack into the RPN. This is equivalent to encountering the EOF operator (lowest precedence) when doing the RPN reordering. It is called AFTER the RPN reordering.
	 */
	public void purgeTreeStack()
	{
		while (operatorStack.size() > 0)
			rpn.add(operatorStack.pop());
	}
	


	/**
	 * Converts the RPN list into a tree and returns the root node.
	 * @return The root node of the tree.
	 */
	public TreeNode createTreeRoot()
	{
		// Purge the tree stack
		purgeTreeStack();
		
		// Convert RPN tokens into tree nodes
		ArrayList<TreeNode> rpnNodes = new ArrayList<TreeNode>();
		for (TreeNodeToken token : rpn)
			rpnNodes.add(new TreeNode(token));
		
		// Assemble tree
		Stack<TreeNode> operandStack = new Stack<TreeNode>();
		for (TreeNode node : rpnNodes)
		{
			if (node.getToken() instanceof Def)
				operandStack.push(node);
			else
			{
				node.setRight(operandStack.pop());
				node.setLeft(operandStack.pop());
				operandStack.push(node);
			}
		}
		
		// Return root of tree
		TreeNode root = operandStack.pop();
		return root;
	}
}
