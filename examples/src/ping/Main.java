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

package ping;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;

import lij.exceptions.InterpreterException;
import lij.runtime.Interpreter;



public class Main
{
	private static int id = 0;
	private static final File INPUT_FILE = new File("./src/ping/ping.lcc");
	


	public static void main(String[] args)
	{
		try
		{
			// Create interpreter
			final Interpreter interpreter = new Interpreter(new FileInputStream(INPUT_FILE), false);
			
			// Setup frame
			JFrame f = new JFrame("Main");
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setBounds(0, 0, 240, 120);
			final JButton b = new JButton("Add Agent " + id);
			f.getContentPane().add(b);
			f.setVisible(true);
			b.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					try
					{
						interpreter.subscribe("agent", new Agent(id), id);
						id++;
						b.setText("Add Agent " + id);
					}
					catch (InterpreterException e)
					{
						e.printStackTrace();
					}
				}
			});
			
			// Run IM
			interpreter.run();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
