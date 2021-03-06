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
 * An LCC role switch Def.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class DefAgent extends Def
{
	private Term type;
	private Argument id;
	


	/**
	 * Constructor.
	 * @param _type The type Term of the agent.
	 * @param _id The ID of the agent.
	 */
	public DefAgent(Term _type, Argument _id)
	{
		type = _type;
		id = _id;
	}
	


	/**
	 * Accessor.
	 * @return The type Term of the agent.
	 */
	public Term getType()
	{
		return type;
	}
	


	/**
	 * Accessor.
	 * @return The ID of the agent.
	 */
	public Argument getID()
	{
		return id;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see interpreter.model.Def#clone()
	 */
	public Object clone()
	{
		Term cloneType = (type == null ? null : (Term)type.clone());
		Argument cloneID = (id == null ? null : (Argument)id.clone());
		ArrayList<Constraint> cloneConstraints = new ArrayList<Constraint>();
		for (Constraint constraint : constraints)
			cloneConstraints.add((Constraint)constraint.clone());
		
		DefAgent clone = new DefAgent(cloneType, cloneID);
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
		return "a(" + type + ", " + id + ")";
	}
}
