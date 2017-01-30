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
import lij.interfaces.Result;
import lij.model.Argument;
import lij.model.ArgumentValue;
import lij.model.ArgumentVariable;
import lij.model.Constraint;
import lij.model.Def;
import lij.model.DefAgent;
import lij.model.DefMessage;
import lij.model.DefNullOp;
import lij.model.Term;



/**
 * This class executes Def's. It's main purpose is to decouple the execution
 * code from the model.Def class
 * 
 * @author Nikolaos Chatzinikolaou
 */
public abstract class DefInstance
{
	protected Def def;
	protected Interpreter interpreter;
	protected AgentInstance agent;
	


	/**
	 * Factory method for creating an instance of a DefInstance subclass.
	 * @param _def The Def that will be encapsulated by this DefInstance object.
	 * @param _interpreter A reference to the currently running Interpreter
	 *            instance.
	 * @param _agent The AgentInstance performing the instantiation.
	 * @return The instantiated DefInstance.
	 */
	public static DefInstance createDefInstance(Def _def, Interpreter _interpreter, AgentInstance _agent)
	{
		DefInstance defInstance = null;
		
		if (_def instanceof DefNullOp)
			defInstance = new DefNullOpInstance();
		else if (_def instanceof DefAgent)
			defInstance = new DefAgentInstance();
		else if (_def instanceof DefMessage)
			defInstance = new DefMessageInstance();
		
		defInstance.def = _def;
		defInstance.interpreter = _interpreter;
		defInstance.agent = _agent;
		
		return defInstance;
	}
	


	/**
	 * Executes this DefInstance. This includes checking the list of Constraints
	 * associated with this Def. The execution of the actual event will be
	 * delegated to the subclasses, via the executeEvent() method.
	 * @return The result of the execution (TRUE, FALSE or MAYBE).
	 * @throws InterpreterException
	 */
	public Result.State execute() throws InterpreterException
	{
		// Print this Def's constraints
		if (def.getConstraints() != null && def.getConstraints().size() > 0)
			interpreter.getMonitor().log(agent.toString(), "? " + def.getConstraints().toString());
		
		// Check constraints
		Result.State constraintsResult = checkConstraints();
		if (constraintsResult != Result.State.TRUE)
			return constraintsResult;
		
		// Print this Def as executing
		interpreter.getMonitor().log(agent.toString(), "# " + def.toString());
		
		// Execute event and return the result
		return executeEvent();
	}
	


	/**
	 * Checks the constraints of this DefInstance.
	 * @return The result of the check (TRUE, FALSE or MAYBE).
	 * @throws InterpreterException
	 */
	private Result.State checkConstraints() throws InterpreterException
	{
		if (def.getConstraints() == null || def.getConstraints().size() == 0)
			return Result.State.TRUE;
		
		boolean foundMaybe = false;
		
		for (Constraint constraint : def.getConstraints())
		{
			ConstraintInstance constraintInstance = ConstraintInstance.createConstraintInstance(constraint, interpreter, agent);
			Result.State constraintResult = constraintInstance.check();
			
			if (constraintResult == Result.State.FALSE)
				return Result.State.FALSE;
			
			else if (constraintResult == Result.State.MAYBE)
				foundMaybe = true;
		}
		
		return (foundMaybe ? Result.State.MAYBE : Result.State.TRUE);
	}
	


	/**
	 * This method needs to be implemented by any subclasses. It performs the
	 * execution of the actual event associated with this Def.
	 * @return The result of the execution (TRUE, FALSE or MAYBE).
	 * @throws InterpreterException
	 */
	public abstract Result.State executeEvent() throws InterpreterException;
	


	/**
	 * A DefInstance subclass for NULLOP events.
	 */
	private static class DefNullOpInstance extends DefInstance
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.runtime.DefInstance#executeEvent()
		 */
		public Result.State executeEvent() throws InterpreterException
		{
			// A Null operator always returns TRUE
			return Result.State.TRUE;
		}
	}
	


	/**
	 * A DefInstance subclass for AGENT (i.e. role switch) events.
	 */
	private static class DefAgentInstance extends DefInstance
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.runtime.DefInstance#executeEvent()
		 */
		public Result.State executeEvent() throws InterpreterException
		{
			DefAgent roleSwitch = (DefAgent)def;
			
			// Gather arguments to pass to the new clause instance
			Serializable[] clauseArguments = new Serializable[roleSwitch.getType().getArguments().size()];
			for (int i = 0; i < clauseArguments.length; i++)
			{
				Argument argument = roleSwitch.getType().getArguments().get(i);
				clauseArguments[i] = agent.getCurrentClauseInstance().getValueForArgument(argument);
			}
			
			// Gather the new agent ID
			Serializable newID = agent.getCurrentClauseInstance().getValueForArgument(roleSwitch.getID());
			if (newID == null)
				throw new InterpreterException("The ID in a role switch must be non-null");
			
			// Create the new clause instance and execute it
			Term newType = roleSwitch.getType();
			ClauseInstance newClauseInstance = interpreter.instantiateClause(newType, newID, clauseArguments);
			ClauseInstanceReturns returns = agent.executeClauseInstance(newClauseInstance);
			
			// Store the called clause's return values into the calling clause's symbol table
			ArrayList<Argument> callArgs = roleSwitch.getType().getArguments();
			for (int i = 0; i < callArgs.size(); i++)
				if (callArgs.get(i) instanceof ArgumentVariable)
					agent.getCurrentClauseInstance().storeVariable((ArgumentVariable)(callArgs.get(i)), returns.getValues().get(i));
			
			// Return execution result
			return returns.getResult();
		}
	}
	


	/**
	 * A DefInstance subclass for MESSAGE (i.e. role switch) events.
	 */
	private static class DefMessageInstance extends DefInstance
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.runtime.DefInstance#executeEvent()
		 */
		public Result.State executeEvent() throws InterpreterException
		{
			// Message
			DefMessage message = (DefMessage)def;
			Term messageType = message.getContent();
			Term correspondantType = message.getAgent().getType();
			Serializable correspondantID = agent.getCurrentClauseInstance().getValueForArgument(message.getAgent().getID());
			
			// Outgoing message
			if (message.isOutgoing())
			{
				
				Serializable[] payload = new Serializable[message.getContent().getArguments().size()];
				for (int i = 0; i < payload.length; i++)
				{
					Argument argument = message.getContent().getArguments().get(i);
					if (argument instanceof ArgumentVariable)
						payload[i] = agent.getCurrentClauseInstance().getValueForArgument(argument);
					else if (argument instanceof ArgumentValue)
						payload[i] = ((ArgumentValue)argument).getValue();
				}
				LetterPostData postData = new LetterPostData(messageType, agent.getCurrentType(), agent.getCurrentID(), correspondantType, correspondantID);
				Letter letter = new Letter(postData, payload);
				interpreter.sendLetter(letter);
				return Result.State.TRUE;
			}
			
			// Incoming message
			else
			{
				LetterPostData postData = new LetterPostData(messageType, correspondantType, correspondantID, agent.getCurrentType(), agent.getCurrentID());
				Letter incoming = interpreter.receiveLetter(postData);
				
				// Pending message received
				if (incoming != null)
				{
					// If an incoming ID field is named but left unspecified (null contents), copy the actual sender ID in it
					if (message.getAgent().getID() instanceof ArgumentVariable && message.getAgent().getID() != null)
						if (correspondantID == null)
							agent.getCurrentClauseInstance().getSymbolTable().put((ArgumentVariable)message.getAgent().getID(), incoming.getPostData().getSenderID());
					
					// Copy arguments from the incoming message
					// Assumes that local message definition and remote incoming message have the same number of (matching) arguments
					// Also assumes that all arguments specified in an incoming message are variables, never constant values (this is not enforced by javacc parser, although it could be done by using LOOKAHEAD to determine the direction of the message)
					Serializable[] payload = incoming.getPayload();
					for (int i = 0; i < message.getContent().getArguments().size(); i++)
						agent.getCurrentClauseInstance().getSymbolTable().put((ArgumentVariable)message.getContent().getArguments().get(i), payload[i]);
					
					return Result.State.TRUE;
				}
				
				// Pending message not received
				else
					return Result.State.MAYBE;
			}
		}
	}
}
