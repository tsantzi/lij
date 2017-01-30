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
 * This implementation of a Constraint is used to extract the head from a list
 * into another variable.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class ConstraintList implements Constraint
{
	private ArgumentVariable list;
	private ArgumentVariable head;
	private ArgumentVariable tail;
	


	/**
	 * Constructor.
	 * @param _list The LHS ArgumentVariable.
	 * @param _head The head RHS ArgumentVariable.
	 * @param _tail The tail RHS ArgumentVariable.
	 */
	public ConstraintList(ArgumentVariable _list, ArgumentVariable _head, ArgumentVariable _tail)
	{
		list = _list;
		head = _head;
		tail = _tail;
	}
	


	/**
	 * Accessor.
	 * @return The LHS ArgumentVariable.
	 */
	public ArgumentVariable getList()
	{
		return list;
	}
	


	/**
	 * Accessor.
	 * @return The head RHS ArgumentVariable.
	 */
	public ArgumentVariable getHead()
	{
		return head;
	}
	


	/**
	 * Accessor.
	 * @return The tail RHS ArgumentVariable.
	 */
	public ArgumentVariable getTail()
	{
		return tail;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone()
	{
		ArgumentVariable cloneList = (ArgumentVariable)list.clone();
		ArgumentVariable cloneHead = (ArgumentVariable)head.clone();
		ArgumentVariable cloneTail = (ArgumentVariable)tail.clone();
		
		return new ConstraintList(cloneList, cloneHead, cloneTail);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return list + " = [" + head + " | " + tail + "]";
	}
}
