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

import lij.exceptions.InterpreterException;
import lij.interfaces.Accessor;
import lij.model.Argument;
import lij.model.ArgumentValue;
import lij.model.ArgumentVariable;



/**
 * For passing arguments to constraint methods, and allowing them to return
 * values (i.e. update values in the symbol table).
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class ArgumentAccessor implements Accessor
{
	private Argument argument = null;
	private SymbolTable symbolTable = null;
	


	/**
	 * Constructor.
	 * @param _argument The Argument object which will be accessed through this
	 *            ArgumentAccessor.
	 * @param _symbolTable A reference to the SymbolTable in the currently
	 *            executing ClauseInstance.
	 */
	public ArgumentAccessor(Argument _argument, SymbolTable _symbolTable)
	{
		argument = _argument;
		symbolTable = _symbolTable;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see interpreter.interfaces.Accessor#setValue(java.io.Serializable)
	 */
	public void setValue(Serializable _value) throws InterpreterException
	{
		if (argument instanceof ArgumentValue)
			throw new InterpreterException("Cannot assign a value to a value argument.");
		
		symbolTable.put((ArgumentVariable)argument, _value);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see interpreter.interfaces.Accessor#getValue()
	 */
	public Serializable getValue()
	{
		if (argument instanceof ArgumentValue)
			return ((ArgumentValue)argument).getValue();
		
		else if (argument instanceof ArgumentVariable)
			return symbolTable.get((ArgumentVariable)argument);
		
		return null;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		Serializable value = getValue();
		return (value == null ? "null" : value.toString());
	}
}
