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
 * A Def instance can be a DefAgent (role switch), DefMessage, or DefNullOp -
 * and an associated constraint list.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public abstract class Def implements TreeNodeToken
{
	protected ArrayList<Constraint> constraints = new ArrayList<Constraint>();
	


	/**
	 * Accessor.
	 * @return The list of Constraints that are associated with this Def.
	 */
	public ArrayList<Constraint> getConstraints()
	{
		return constraints;
	}
	


	/**
	 * Accessor.
	 * @param _constraints The list of Constraints that are associated with this
	 *            Def.
	 */
	public void setConstraints(ArrayList<Constraint> _constraints)
	{
		constraints = _constraints;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public abstract Object clone();
}
