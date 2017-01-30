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
 * This implementation of a Constraint is used to perform a comparison between
 * two arguments (values (including lists) or variables).
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class ConstraintComparison implements Constraint
{
	public enum Type
	{
		LESS_THAN, GREATER_THAN, EQUAL_TO, NOT_EQUAL_TO
	};
	


	private Type type;
	private Argument argument1;
	private Argument argument2;
	


	/**
	 * Constructor.
	 * @param _type The type of the comparison (one of EQUAL_TO, LESS_THAN or
	 *            GREATER_THAN).
	 * @param _argument1 The LHS Argument.
	 * @param _argument2 The RHS Argument.
	 */
	public ConstraintComparison(Type _type, Argument _argument1, Argument _argument2)
	{
		type = _type;
		argument1 = _argument1;
		argument2 = _argument2;
		
	}
	


	/**
	 * Accessor.
	 * @return The type of the comparison (one of EQUAL_TO, LESS_THAN or
	 *         GREATER_THAN).
	 */
	public Type getType()
	{
		return type;
	}
	


	/**
	 * Accessor.
	 * @return The LHS Argument.
	 */
	public Argument getArgument1()
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
		Argument cloneArgument1 = (Argument)argument1.clone();
		Argument cloneArgument2 = (Argument)argument2.clone();
		
		return new ConstraintComparison(type, cloneArgument1, cloneArgument2);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String symbol = "";
		if (type == Type.LESS_THAN)
			symbol = "<";
		else if (type == Type.GREATER_THAN)
			symbol = ">";
		else if (type == Type.EQUAL_TO)
			symbol = "==";
		else if (type == Type.NOT_EQUAL_TO)
			symbol = "!=";

		return argument1 + " " + symbol + " " + argument2;
	}
}
