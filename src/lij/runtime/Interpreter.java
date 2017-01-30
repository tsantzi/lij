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



import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import lij.exceptions.InterpreterException;
import lij.interfaces.ConstraintImplementor;
import lij.model.Argument;
import lij.model.ArgumentVariable;
import lij.model.Clause;
import lij.model.Framework;
import lij.model.Role;
import lij.model.Term;
import lij.monitor.Monitor;
import lij.parser.ParseException;
import lij.parser.Parser;



/**
 * The main interpreter class. This is the starting point for loading and executing an IM.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class Interpreter
{
	private Monitor monitor = new Monitor(this);
	private Framework framework;
	private ArrayList<AgentInstance> agents = new ArrayList<AgentInstance>();
	private LetterBox letterBox = new LetterBox(monitor);
	private boolean isRunning = false;
	


	/**
	 * Constructor.
	 * @param is An InputStream that will provide the LCC protocol file.
	 * @throws FileNotFoundException
	 * @throws ParseException
	 * @throws InterpreterException
	 */
	public Interpreter(InputStream is) throws FileNotFoundException, ParseException, InterpreterException
	{
		this(is, false);
	}
	


	/**
	 * Constructor.
	 * @param is An InputStream that will provide the LCC protocol file.
	 * @param enableMonitor If true, the interpreter monitor GUI frame will be activated and displayed.
	 * @throws FileNotFoundException
	 * @throws ParseException
	 * @throws InterpreterException
	 */
	public Interpreter(InputStream is, boolean enableMonitor) throws FileNotFoundException, ParseException, InterpreterException
	{
		// Setup monitor GUI
		monitor.setActive(enableMonitor);
		
		// Parse protocol and create the framework from it
		Parser parser = new Parser(is);
		parser.start();
		framework = parser.getFramework();
	}
	


	/**
	 * Accessor.
	 * @return The interpreter's monitor.
	 */
	public Monitor getMonitor()
	{
		return monitor;
	}
	


	/**
	 * Accessor.
	 * @return The interpreter's framework model.
	 */
	public Framework getFramework()
	{
		return framework;
	}
	


	/**
	 * Accessor.
	 * @return The interpreter's current list of agents.
	 */
	public ArrayList<AgentInstance> getAgents()
	{
		return agents;
	}
	


	/**
	 * Locates and returns a pointer to the AgentInstance with the specified id.
	 * @param id The ID of the required agent.
	 * @return The required AgentInstance, or null if it was not found.
	 */
	public AgentInstance getAgent(Serializable id)
	{
		for (AgentInstance agent : agents)
			if (agent.getCurrentID().equals(id))
				return agent;
		
		return null;
	}
	


	/**
	 * Creates and adds a new agent subscription, using the default ID.
	 * @param roleName The role name.
	 * @param constraintImplementor A ConstraintImplementor implementation, that will provide implementations for the constraint methods.
	 * @throws InterpreterException
	 */
	public synchronized void subscribe(String roleName, ConstraintImplementor constraintImplementor) throws InterpreterException
	{
		subscribe(roleName, constraintImplementor, Constants.DEFAULT_ID);
	}
	


	/**
	 * Creates and adds a new agent subscription.
	 * @param roleName The role name.
	 * @param constraintImplementor A ConstraintImplementor implementation, that will provide implementations for the constraint methods.
	 * @param id The ID of the agent being subscribed.
	 * @throws InterpreterException
	 */
	public synchronized void subscribe(String roleName, ConstraintImplementor constraintImplementor, Serializable id) throws InterpreterException
	{
		Term roleType = new Term(roleName);
		
		// Check if agent being subscribed is relevant to current IM.
		Role role = framework.getRoles().get(roleType);
		if (role == null)
			throw new InterpreterException("No role '" + roleName + "' is used in the interaction model.");
		
		// Create the new agent
		AgentInstance newAgent = new AgentInstance(roleType, id, constraintImplementor, this);
		agents.add(newAgent);
		
		//If we're waiting for all required agents before starting the interaction, send thread notification 
		if (!isRunning)
			this.notifyAll();
		
		// Else start the newcomer
		else
			newAgent.start();
	}
	


	/**
	 * Starts the execution of the IM. Before doing so, this method will wait until agents for all the required roles specified in the LCC program have been subscribed.
	 * @throws InterpreterException
	 */
	public void run() throws InterpreterException
	{
		waitForRequiredImplementors();
		
		for (AgentInstance agent : agents)
			agent.start();
		
		isRunning = true;
	}
	


	/**
	 * Creates and returns a ClauseInstance object.
	 * @param newType The type of the Clause to instantiate.
	 * @param newID The ID of the agent executing the clause.
	 * @param arguments The arguments to be passed to the ClauseInstance.
	 * @return The instantiated ClauseInstance.
	 * @throws InterpreterException
	 */
	public ClauseInstance instantiateClause(Term newType, Serializable newID, Serializable[] arguments) throws InterpreterException
	{
		Clause originalClause = framework.getClauses().get(newType);
		if (originalClause == null)
			throw new InterpreterException("Clause '" + newType + "' is not defined");
		
		Clause newClause = (Clause)originalClause.clone();
		ClauseInstance newClauseInstance = new ClauseInstance(newID, newClause, this);
		
		// Store call arguments
		// Assumes that the number of arguments provided matches that of the number of arguments expected by the clause (this should always be the case as we are using hashCode-based Term matching)
		// Also assumes that the terms specified in the agent definition of the clause are all ArgumentVariables
		if (arguments != null)
			for (int i = 0; i < arguments.length; i++)
			{
				Argument key = originalClause.getAgent().getType().getArguments().get(i);
				if (!(key instanceof ArgumentVariable))
					throw new InterpreterException("Can only have Variable type arguments in a clause definition");
				newClauseInstance.storeVariable((ArgumentVariable)key, arguments[i]);
			}
		
		// Store ID
		// Assumes ID argument is a non-null ArgumentVariable
		Argument key = originalClause.getAgent().getID();
		if (!(key instanceof ArgumentVariable))
			throw new InterpreterException("Can only have a Variable type ID in a clause definition");
		newClauseInstance.storeVariable((ArgumentVariable)key, newID);
		
		// Reset root node's evaluation result (for recursion, when a clause calls itself, in order for it to re-execute)
		newClauseInstance.getRoot().resetEvaluationResult();
		
		return newClauseInstance;
	}
	


	/**
	 * Searches for the proper recepient agent of the specified letter, and posts it to it.
	 * @param letter The letter to send.
	 * @throws InterpreterException
	 */
	public void sendLetter(Letter letter) throws InterpreterException
	{
		letterBox.putLetter(letter);
	}
	


	/**
	 * Searches for the proper recepient agent of the specified letter, and posts it to it.
	 * @param postData The post data of the message of the expected Letter.
	 * @return The incoming Letter, if found; null otherwise.
	 * @throws InterpreterException
	 */
	public Letter receiveLetter(LetterPostData postData) throws InterpreterException
	{
		return letterBox.getLetter(postData);
	}
	


	/**
	 * This method will block until agents for all of the necessary roles specified in the IM have been subscribed.
	 */
	private synchronized void waitForRequiredImplementors()
	{
		ArrayList<Term> missingRoles = null;
		while ((missingRoles = getMissingRoles()).size() > 0)
		{
			monitor.log("System", "Waiting for required roles: " + missingRoles.toString() + "...");
			
			// Wait for thread notification
			try
			{
				this.wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		monitor.log("System", "All required roles have been subscribed");
	}
	


	/**
	 * Returns a list of all the roles that are necessary for the execution of the IM, that still need a subscriber.
	 * @return The list of the missing required roles.
	 */
	private ArrayList<Term> getMissingRoles()
	{
		// Create list of required roles
		ArrayList<Term> requiredRoleNames = new ArrayList<Term>();
		Role[] rolesArray = framework.getRoles().values().toArray(new Role[0]);
		for (Role role : rolesArray)
			if (role.getKind() == Role.KIND_INITIAL || role.getKind() == Role.KIND_NECESSARY)
				for (int i = 0; i < Math.max(role.getMin(), 1); i++)
					requiredRoleNames.add(role.getType());
		
		// Subtract available agents from the requirements list
		for (AgentInstance agent : agents)
			requiredRoleNames.remove(agent.getCurrentType());
		
		return requiredRoleNames;
	}
}
