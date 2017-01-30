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

package lij.runtime;



import java.io.Serializable;
import java.util.ArrayList;

import lij.exceptions.InterpreterException;
import lij.interfaces.ConstraintImplementor;
import lij.interfaces.Result;
import lij.model.Argument;
import lij.model.Role;
import lij.model.Term;
import lij.util.Utilities;



/**
 * A runtime instance of an Agent definition. Each Agent runs in its own thread.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class AgentInstance extends Thread
{
	private Term roleType;
	private Serializable id;
	private ConstraintImplementor constraintImplementor;
	private Interpreter interpreter;
	private ClauseInstance currentClauseInstance = null;
	


	/**
	 * Constructor.
	 * @param _roleType The agent's role type.
	 * @param _id The agent's ID.
	 * @param _constraintImplementor The associated ConstraintImplementor
	 *            instance.
	 * @param _interpreter A reference to the currently running Interpreter
	 *            instance.
	 */
	public AgentInstance(Term _roleType, Serializable _id, ConstraintImplementor _constraintImplementor, Interpreter _interpreter)
	{
		roleType = _roleType;
		id = _id;
		constraintImplementor = _constraintImplementor;
		interpreter = _interpreter;
		
		try
		{
			currentClauseInstance = interpreter.instantiateClause(roleType, id, null);
		}
		catch (InterpreterException e)
		{
			e.printStackTrace();
		}
	}
	


	/**
	 * Accessor.
	 * @return The associated ConstraintImplementor object.
	 */
	public ConstraintImplementor getConstraintImplementor()
	{
		return constraintImplementor;
	}
	


	/**
	 * Returns the ID of the agent encapsulated in this AgentInstance. This is
	 * the ID specified during subscription, if no ClauseInstance is currently
	 * executing; or the agent ID specified in the ClauseInstance, if one
	 * exists. This stems from the fact that, in the current implementation of
	 * the interpreter, it is possible for an agent to change its ID when
	 * performing a role switch.
	 * @return The current ID of the agent.
	 */
	public Serializable getCurrentID()
	{
		return currentClauseInstance.getID();
	}
	


	/**
	 * Returns the role Term of the agent encapsulated in this AgentInstance.
	 * This is the role specified during subscription, if no ClauseInstance is
	 * currently executing; or the role specified in the ClauseInstance, if one
	 * exists.
	 * @return The current role of the agent.
	 */
	public Term getCurrentType()
	{
		return currentClauseInstance.getType();
	}
	


	/**
	 * Accessor.
	 * @return The currently executing ClauseInstance.
	 */
	public ClauseInstance getCurrentClauseInstance()
	{
		return currentClauseInstance;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run()
	{
		try
		{
			interpreter.getMonitor().log("System", "Starting agent '" + this.toString() + "'");
			ClauseInstanceReturns returns = executeClauseInstance(currentClauseInstance);
			interpreter.getMonitor().log("System", "Agent '" + this.toString() + "' terminated with result: " + returns.getResult());
		}
		catch (InterpreterException e)
		{
			e.printStackTrace();
		}
	}
	


	/**
	 * This method will cause this AgentInstance to execute the specified
	 * ClauseInstance. This method is recursive, in that it can also be called
	 * as part of the execution of a DefAgent (i.e. role switch) in the LCC
	 * protocol. The method will keep looping while evaluating the root tree
	 * node of the ClauseInstance, until a definitive result (i.e. TRUE or
	 * FALSE, but not MAYBE) is achieved. This behaviour is part of the parallel
	 * processing mechanism of the interpreter.
	 * @param clauseInstance The ClauseInstance to execute.
	 * @return The result returned by evaluating the root tree node (and
	 *         recursively its children) of the clause, as well as any values
	 *         returned via the clause's input arguments.
	 * @throws InterpreterException
	 */
	public ClauseInstanceReturns executeClauseInstance(ClauseInstance clauseInstance) throws InterpreterException
	{
		// Store current clause state (this is equivalent to pushing local data on the stack during a conventional function call) 
		ClauseInstance savedClauseInstance = currentClauseInstance;
		
		// Switch to the specified clause
		currentClauseInstance = clauseInstance;
		
		Result.State result;
		
		do // Loop cyclic clauses
		{
			// Unless uncommitted (i.e. if committed), execute clause until it returns a definitive result (TRUE or FALSE - not MAYBE)
			clauseInstance.getRoot().resetEvaluationResult();
			do
			{
				result = clauseInstance.getRoot().getEvaluationResult(interpreter, AgentInstance.this);
				Utilities.yieldThread(Constants.THREAD_YIELD_DELAY);
			} while (!clauseInstance.getKind().equals(Role.KIND_UNCOMMITTED) && result == Result.State.MAYBE);
		} while (clauseInstance.getKind().equals(Role.KIND_CYCLIC) && result != Result.State.FALSE);
		
		// Store return values
		ArrayList<Serializable> values = new ArrayList<Serializable>();
		for (Argument argument : clauseInstance.getType().getArguments())
			values.add(clauseInstance.getValueForArgument(argument));
		
		// Restore previous clause state
		currentClauseInstance = savedClauseInstance;
		
		return new ClauseInstanceReturns(result, values);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#toString()
	 */
	public String toString()
	{
		return getCurrentType() + "<" + getCurrentID() + ">";
	}
}
