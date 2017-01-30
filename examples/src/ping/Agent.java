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



import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import lij.interfaces.Accessor;
import lij.interfaces.ConstraintImplementor;
import lij.interfaces.Result;



public class Agent implements ConstraintImplementor, ActionListener
{
	public static final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int SCREEN_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int FRAME_WIDTH = 240;
	public static final int FRAME_HEIGHT = 120;
	private static int lastX = 0;
	private static int lastY = 120;
	private int id;
	private JButton b = new JButton("Ping");
	private JLabel l = new JLabel(" ");
	private boolean buttonPressed = false;
	


	public Agent(int _id)
	{
		id = _id;
		
		b.addActionListener(this);
		
		JFrame f = new JFrame("Agent " + id);
		f.setBounds(lastX, lastY, FRAME_WIDTH, FRAME_HEIGHT);
		lastX += FRAME_WIDTH;
		if (lastX + FRAME_WIDTH > SCREEN_WIDTH)
		{
			lastX = 0;
			lastY += FRAME_HEIGHT;
		}
		f.getContentPane().add(b, BorderLayout.CENTER);
		f.getContentPane().add(l, BorderLayout.SOUTH);
		f.setVisible(true);
	}
	


	public void actionPerformed(ActionEvent ae)
	{
		buttonPressed = true;
	}
	


	public Result.State waitForClick()
	{
		if (!buttonPressed)
		{
			return Result.State.MAYBE;
		}
		else
		{
			buttonPressed = false;
			return Result.State.TRUE;
		}
	}
	


	public Result.State gotPing(Accessor ID)
	{
		l.setText("Got ping from: " + ID.getValue().toString());
		
		return Result.State.TRUE;
	}
}
