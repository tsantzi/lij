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



import java.util.ArrayList;



/**
 * An LCC message Def.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class DefMessage extends Def
{
	private Term content;
	private DefAgent agent;
	private boolean outgoing;
	


	/**
	 * Constructor.
	 * @param _content The content Term of the message.
	 * @param _agent The Term specifying the participant agent.
	 * @param _outgoing A flag to denote outgoing (true) or incoming (false)
	 *            messages.
	 */
	public DefMessage(Term _content, DefAgent _agent, boolean _outgoing)
	{
		content = _content;
		agent = _agent;
		outgoing = _outgoing;
	}
	


	/**
	 * Accessor.
	 * @return The content Term of the message.
	 */
	public Term getContent()
	{
		return content;
	}
	


	/**
	 * Accessor.
	 * @return The Term specifying the participant agent.
	 */
	public DefAgent getAgent()
	{
		return agent;
	}
	


	/**
	 * Accessor.
	 * @return The flag that denotes whether this message is outgoing (true) or
	 *         incoming (false).
	 */
	public boolean isOutgoing()
	{
		return outgoing;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see interpreter.model.Def#clone()
	 */
	public Object clone()
	{
		Term cloneContent = (content == null ? null : (Term)content.clone());
		DefAgent cloneAgent = (agent == null ? null : (DefAgent)agent.clone());
		ArrayList<Constraint> cloneConstraints = new ArrayList<Constraint>();
		for (Constraint constraint : constraints)
			cloneConstraints.add((Constraint)constraint.clone());
		
		DefMessage clone = new DefMessage(cloneContent, cloneAgent, outgoing);
		clone.constraints = cloneConstraints;
		
		return clone;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "MESSAGE: " + content + (outgoing ? " => " : " <= ") + agent;
	}
}
