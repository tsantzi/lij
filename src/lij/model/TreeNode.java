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

package lij.model;



import lij.exceptions.InterpreterException;
import lij.interfaces.Result;
import lij.runtime.AgentInstance;
import lij.runtime.DefInstance;
import lij.runtime.Interpreter;



/**
 * This class is used to construct clause trees. Each node in the tree contains
 * a Token (the "contents" of the node), as well as two children nodes (left and
 * right).
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class TreeNode
{
	private TreeNodeToken token = null;
	private TreeNode left = null;
	private TreeNode right = null;
	private Result.State evaluationResult = Result.State.MAYBE;
	


	/**
	 * Constructor.
	 * @param _token The token object contained in this node.
	 */
	public TreeNode(TreeNodeToken _token)
	{
		token = _token;
	}
	


	/**
	 * Accessor.
	 * @return The node's token object.
	 */
	public TreeNodeToken getToken()
	{
		return token;
	}
	


	/**
	 * Accessor.
	 * @param _left The node at the left branch of the tree.
	 */
	public void setLeft(TreeNode _left)
	{
		left = _left;
	}
	


	/**
	 * Accessor.
	 * @return The node at the left branch of the tree.
	 */
	public TreeNode getLeft()
	{
		return left;
	}
	


	/**
	 * Accessor.
	 * @param _right The node at the right branch of the tree.
	 */
	public void setRight(TreeNode _right)
	{
		right = _right;
	}
	


	/**
	 * Accessor.
	 * @return The node at the right branch of the tree.
	 */
	public TreeNode getRight()
	{
		return right;
	}
	


	/**
	 * Resets the state of the evaluation result of this node and all of its
	 * children to MAYBE.
	 */
	public void resetEvaluationResult()
	{
		evaluationResult = Result.State.MAYBE;
		
		if (left != null)
			left.resetEvaluationResult();
		
		if (right != null)
			right.resetEvaluationResult();
	}
	


	/**
	 * Returns the evaluation result of this node. If the state of this result
	 * is not yet determined (i.e. it is MAYBE), the node (and its children)
	 * will first get (re-)evaluated.
	 * @param interpreter A reference to the current Interpreter instance.
	 * @param agentInstance A reference to the current agent instance.
	 * @return The evaluation result of this node.
	 * @throws InterpreterException
	 */
	public Result.State getEvaluationResult(Interpreter interpreter, AgentInstance agentInstance) throws InterpreterException
	{
		if (evaluationResult == Result.State.MAYBE)
			evaluate(interpreter, agentInstance);
		
		return evaluationResult;
	}
	


	/**
	 * Performs the evaluation of this node (and its children, recursively)
	 * @param interpreter A reference to the current Interpreter instance.
	 * @param agentInstance A reference to the current agent instance.
	 * @throws InterpreterException
	 */
	private void evaluate(Interpreter interpreter, AgentInstance agentInstance) throws InterpreterException
	{
		if (token instanceof Def)
		{
			Def def = (Def)token;
			DefInstance defInstance = DefInstance.createDefInstance(def, interpreter, agentInstance);
			evaluationResult = defInstance.execute();
		}
		
		else if (token instanceof Operator)
		{
			if (token instanceof Operator.Then)
			{
				Result.State resultLeft = left.getEvaluationResult(interpreter, agentInstance);
				if (resultLeft == Result.State.TRUE)
					evaluationResult = right.getEvaluationResult(interpreter, agentInstance);
				else if (resultLeft == Result.State.FALSE)
					evaluationResult = Result.State.FALSE;
				else if (resultLeft == Result.State.MAYBE)
					evaluationResult = Result.State.MAYBE;
			}
			
			else if (token instanceof Operator.Or)
			{
				Result.State resultLeft = left.getEvaluationResult(interpreter, agentInstance);
				if (resultLeft == Result.State.TRUE)
					evaluationResult = Result.State.TRUE;
				else if (resultLeft == Result.State.FALSE)
					evaluationResult = right.getEvaluationResult(interpreter, agentInstance);
				else if (resultLeft == Result.State.MAYBE)
				{
					Result.State resultRight = right.getEvaluationResult(interpreter, agentInstance);
					if (resultRight == Result.State.TRUE)
						evaluationResult = Result.State.TRUE;
					else
						evaluationResult = Result.State.MAYBE;
				}
			}
		}
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone()
	{
		TreeNodeToken cloneToken = (token == null ? null : (TreeNodeToken)token.clone());
		TreeNode cloneLeft = (left == null ? null : (TreeNode)left.clone());
		TreeNode cloneRight = (right == null ? null : (TreeNode)right.clone());
		Result.State cloneEvaluationResult = evaluationResult;
		
		TreeNode newTreeNode = new TreeNode(cloneToken);
		newTreeNode.left = cloneLeft;
		newTreeNode.right = cloneRight;
		newTreeNode.evaluationResult = cloneEvaluationResult;
		
		return newTreeNode;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		if (token instanceof Def)
			return token.toString();
		else
		{
			String opString = "";
			if (token instanceof Operator.Then)
				opString = " > ";
			else if (token instanceof Operator.Or)
				opString = " | ";
			else
				opString = " ? ";
			return "(" + left.toString() + opString + right.toString() + ")";
		}
	}
}
