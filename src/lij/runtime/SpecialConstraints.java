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
import lij.interfaces.Accessor;



/**
 * This class provides a number of special constraint methods that can be used
 * to interface with the interpreter runtime.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class SpecialConstraints
{
	private Interpreter interpreter;
	private AgentInstance agentInstance;
	


	/**
	 * Constructor.
	 * @param _interpreter A reference to the current interpreter runtime.
	 * @param _agentInstance A reference to the current agent instance.
	 */
	public SpecialConstraints(Interpreter _interpreter, AgentInstance _agentInstance)
	{
		interpreter = _interpreter;
		agentInstance = _agentInstance;
	}
	


	/**
	 * Generates and returns (via the provided Accessor) a list of all the IDs
	 * of the agents that match the specified role.
	 * @param role The role to match.
	 * @param list Will contain the generated list of IDs
	 * @return True, if any agents matching the specified role were found.
	 * @throws InterpreterException
	 */
	public synchronized boolean _findPeers(Accessor role, Accessor list) throws InterpreterException
	{
		ArrayList<Serializable> ids = new ArrayList<Serializable>();
		
		ArrayList<AgentInstance> allAgents = interpreter.getAgents();
		for (AgentInstance anAgent : allAgents)
			if (anAgent != agentInstance) // Skip self
				if (role.getValue().equals("") || anAgent.getCurrentType().getName().equals(role.getValue()))
					ids.add(anAgent.getCurrentID());
		
		list.setValue(ids);
		
		return !ids.isEmpty();
	}
}
