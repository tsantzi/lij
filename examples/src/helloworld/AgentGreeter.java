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

package helloworld;



import javax.swing.JOptionPane;

import lij.exceptions.InterpreterException;
import lij.interfaces.Accessor;
import lij.interfaces.ConstraintImplementor;



public class AgentGreeter implements ConstraintImplementor
{
	public boolean userInitiatesGreeting(Accessor Message) throws InterpreterException
	{
		String input = JOptionPane.showInputDialog("Greeting?");
		if (input == null)
			return false;
		
		Message.setValue(input);
		
		return true;
	}
	


	public boolean displayReply(Accessor Reply)
	{
		JOptionPane.showMessageDialog(null, Reply.getValue());
		
		return true;
	}
}
