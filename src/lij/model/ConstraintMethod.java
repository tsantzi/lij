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
 * This implementation of a Constraint is used to call a constraint method.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class ConstraintMethod implements Constraint
{
	private Term constraintMethodTerm;
	


	/**
	 * Constructor.
	 * @param _constraintMethodTerm The Term denoting the method associated with
	 *            this constraint.
	 */
	public ConstraintMethod(Term _constraintMethodTerm)
	{
		constraintMethodTerm = _constraintMethodTerm;
	}
	


	/**
	 * Accessor.
	 * @return The Term denoting the method associated with this constraint.
	 */
	public Term getConstraintMethodTerm()
	{
		return constraintMethodTerm;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone()
	{
		Term cloneConstraintMethodTerm = (Term)constraintMethodTerm.clone();
		
		return new ConstraintMethod(cloneConstraintMethodTerm);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return constraintMethodTerm.toString();
	}
}
