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



import java.io.File;
import java.io.FileInputStream;

import lij.runtime.Interpreter;



public class Main
{
	private static final File INPUT_FILE = new File("./src/helloworld/helloworld.lcc");
	


	public static void main(String[] args)
	{
		try
		{
			// Create interpreter
			Interpreter interpreter = new Interpreter(new FileInputStream(INPUT_FILE), false);
			
			// Subscribe agents
			interpreter.subscribe("greeter", new AgentGreeter());
			interpreter.subscribe("responder", new AgentResponder());
			
			// Run IM
			interpreter.run();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
