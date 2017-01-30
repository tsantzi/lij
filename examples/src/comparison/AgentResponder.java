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

package comparison;



import javax.swing.JOptionPane;

import lij.exceptions.InterpreterException;
import lij.interfaces.Accessor;
import lij.interfaces.ConstraintImplementor;



public class AgentResponder implements ConstraintImplementor
{
	private int id;
	


	public AgentResponder(int _id)
	{
		id = _id;
	}
	


	public boolean createReply(Accessor Reply) throws InterpreterException
	{
		String input = null;
		do
		{
			try
			{
				input = JOptionPane.showInputDialog("Enter a value for responder " + id + "?");
				if (input == null)
					return false;
		
				Double dValue = Double.parseDouble(input);
				Reply.setValue(dValue);
			}
			catch (NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Please enter a valid numerical value!");
				input = null;
			}
		} while (input == null);
		
		return true;
	}
}
