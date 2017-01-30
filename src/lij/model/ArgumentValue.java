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



import java.io.Serializable;



/**
 * An implementation of an Argument (for use in Terms), for representing a
 * constant value.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class ArgumentValue implements Argument
{
	private Serializable value = null;
	


	/**
	 * Constructor.
	 * @param _value The value of the ArgumentValue.
	 */
	public ArgumentValue(Serializable _value)
	{
		value = _value;
	}
	


	/**
	 * Accessor.
	 * @return The value of the ArgumentValue.
	 */
	public Serializable getValue()
	{
		return value;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone()
	{
		return new ArgumentValue(value);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return (value == null ? "null" : value.toString());
	}
}
