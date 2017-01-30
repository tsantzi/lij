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



/**
 * This implementation of a Constraint is used to assign a value to a variable.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class ConstraintAssignment implements Constraint
{
	private ArgumentVariable argument1;
	private Argument argument2;
	


	/**
	 * Constructor.
	 * @param _argument1 The LHS ArgumentVariable.
	 * @param _argument2 The RHS Argument.
	 */
	public ConstraintAssignment(ArgumentVariable _argument1, Argument _argument2)
	{
		argument1 = _argument1;
		argument2 = _argument2;
	}
	


	/**
	 * Accessor.
	 * @return The LHS ArgumentVariable.
	 */
	public ArgumentVariable getArgument1()
	{
		return argument1;
	}
	


	/**
	 * Accessor.
	 * @return The RHS Argument.
	 */
	public Argument getArgument2()
	{
		return argument2;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone()
	{
		ArgumentVariable cloneArgument1 = (ArgumentVariable)argument1.clone();
		Argument cloneArgument2 = (Argument)argument2.clone();
		
		return new ConstraintAssignment(cloneArgument1, cloneArgument2);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return getArgument1() + " = " + getArgument2();
	}
	
}
