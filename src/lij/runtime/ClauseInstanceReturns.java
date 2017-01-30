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
import java.util.ArrayList;

import lij.interfaces.Result;



/**
 * Contains the execution result of a ClauseInstance (TRUE, FALSE or MAYBE) as
 * well as any values returned via the clause's input arguments.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class ClauseInstanceReturns
{
	private Result.State result;
	private ArrayList<Serializable> values;
	


	/**
	 * Constructor.
	 * @param _result The clause execution result (TRUE, FALSE or MAYBE)
	 * @param _values The input argument return values.
	 */
	public ClauseInstanceReturns(Result.State _result, ArrayList<Serializable> _values)
	{
		result = _result;
		values = _values;
	}
	


	/**
	 * Accessor
	 * @return The clause execution result (TRUE, FALSE or MAYBE)
	 */
	public Result.State getResult()
	{
		return result;
	}
	


	/**
	 * Accessor
	 * @return The input argument return values.
	 */
	public ArrayList<Serializable> getValues()
	{
		return values;
	}
}
