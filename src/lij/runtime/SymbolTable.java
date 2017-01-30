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
import java.util.HashMap;

import lij.model.ArgumentVariable;



/**
 * A SymbolTable instance is used by objects of the ClauseInstance class, for
 * storing mappings between variable names and variable values.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class SymbolTable
{
	private HashMap<String, Serializable> table = new HashMap<String, Serializable>();
	


	/**
	 * Stores the specified variable and its associated value into the
	 * SymbolTable.
	 * @param variable The variable.
	 * @param value The value.
	 */
	public void put(ArgumentVariable variable, Serializable value)
	{
		table.put(variable.getName(), value);
	}
	


	/**
	 * Retrieves the value of the specified variable from the SymbolTable. If a
	 * variable with the specified name does not exist, it is created with a
	 * null value.
	 * @param variable The variable.
	 * @return The value associated with the variable.
	 */
	public Serializable get(ArgumentVariable variable)
	{
		if (!table.containsKey(variable.getName()))
			table.put(variable.getName(), null);
		
		return table.get(variable.getName());
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return table.toString();
	}
}
