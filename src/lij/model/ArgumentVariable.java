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
 * An implementation of an Argument (for use in Terms), for storing a variable
 * in a SymbolTable. Note that objects of this class only specify a name for the
 * variable, and do not contain the value itself (this is stored in the
 * SymbolTable instead).
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class ArgumentVariable implements Argument
{
	private String name = "";
	


	/**
	 * Constructor.
	 * @param _name The name of the ArgumentVariable.
	 */
	public ArgumentVariable(String _name)
	{
		name = _name;
	}
	


	/**
	 * Accessor.
	 * @return The name of the ArgumentVariable.
	 */
	public String getName()
	{
		return name;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone()
	{
		return new ArgumentVariable(name);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return name;
	}
}
