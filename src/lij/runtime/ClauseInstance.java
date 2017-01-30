/*
 * LiJ Copyright 2009 Nikolaos Chatzinikolaou nchatzi@gmail.com
 * 
 * This file is part of LiJ.
 * 
 * LiJ is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * LiJ is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with LiJ. If not, see <http://www.gnu.org/licenses/>.
 */

package lij.runtime;



import java.io.Serializable;

import lij.exceptions.InterpreterException;
import lij.model.Argument;
import lij.model.ArgumentValue;
import lij.model.ArgumentVariable;
import lij.model.Clause;
import lij.model.Role;
import lij.model.Term;
import lij.model.TreeNode;



/**
 * A ClauseInstance instance is a runtime instance of a Clause definition. It contains the clause definition that is being executed, as well as a SymbolTable instance for storing any variables pertaining to that clause instance. It's main purpose is to decouple the execution code from the model.Clause class
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class ClauseInstance
{
	private Serializable id = null;
	private Clause clause;
	private SymbolTable symbolTable = new SymbolTable();
	private Interpreter interpreter;
	


	/**
	 * Constructor.
	 * @param _id The ID of the agent executing this ClauseInstance.
	 * @param _clause The Clause encapsulated in this ClauseInstance.
	 * @param _interpreter A reference to the currently running Interpreter instance.
	 */
	public ClauseInstance(Serializable _id, Clause _clause, Interpreter _interpreter)
	{
		id = _id;
		clause = _clause;
		interpreter = _interpreter;
	}
	


	/**
	 * Accessor.
	 * @return The ID of the agent executing this ClauseInstance.
	 */
	public Serializable getID()
	{
		return id;
	}
	


	/**
	 * Accessor.
	 * @return The Clause encapsulated in this ClauseInstance.
	 */
	public Term getType()
	{
		return clause.getAgent().getType();
	}
	


	/**
	 * Returns the kind of the current role of this AgentInstance.
	 * @return The current role of the agent.
	 */
	public String getKind()
	{
		Term roleType = getType();
		Role role = interpreter.getFramework().getRoles().get(roleType);
		return role.getKind();
	}
	


	/**
	 * Convenience method for accessing the root node of the Clause encapsulated in this ClauseInstance.
	 * @return The root node of the Clause encapsulated in this ClauseInstance.
	 */
	public TreeNode getRoot()
	{
		return clause.getRoot();
	}
	


	/**
	 * Accessor.
	 * @return The SymbolTable of this ClauseInstance.
	 */
	public SymbolTable getSymbolTable()
	{
		return symbolTable;
	}
	


	/**
	 * Convenience method for storing a variable and its associated value into the SymbolTable of this ClauseInstance.
	 * @param variable The variable to store.
	 * @param value The value associated with the variable.
	 */
	public void storeVariable(ArgumentVariable variable, Serializable value)
	{
		symbolTable.put(variable, value);
	}
	


	/**
	 * Convenience method for retrieving the value associated with a variable or constant.
	 * @param argument The Argument whose value will be returned. This can be an ArgumentVariable (in which case the value will be retrieved from the SymbolTable), or an ArgumentConstant (in which case the value returned will be the constant value represented by the ArgumentConstant).
	 * @return The value represented by the specified Argument.
	 * @throws InterpreterException
	 */
	public Serializable getValueForArgument(Argument argument) throws InterpreterException
	{
		if (argument == null)
			return null;
		else if (argument instanceof ArgumentVariable)
			return symbolTable.get((ArgumentVariable)argument);
		else if (argument instanceof ArgumentValue)
			return ((ArgumentValue)argument).getValue();
		else
			throw new InterpreterException("Invalid Argument subclass: " + argument.getClass());
	}
}
