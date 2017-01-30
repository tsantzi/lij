/*
 * LiJ Copyright 2009 Nikolaos Chatzinikolaou nchatzi@gmail.com
 * 
 * This file is part of LiJ.
 * 
 * LiJ is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * LiJ is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with LiJ. If not, see <http://www.gnu.org/licenses/>.
 */

package lij.monitor;



import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import lij.runtime.Interpreter;
import lij.runtime.Letter;



/**
 * A GUI for monitoring the internal state of the interpreter.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class Monitor
{
	private static final int WIDTH = 540, HEIGHT = 240;
	private JFrame frame = null;
	private PanelAgents panelAgents = null;
	private PanelLetterBox panelLetterBox = null;
	private PanelLog panelLog = null;
	private Interpreter interpreter;
	private boolean active = false;
	


	/**
	 * Constructor.
	 * @param _interpreter A pointer to the Interpreter object.
	 */
	public Monitor(Interpreter _interpreter)
	{
		interpreter = _interpreter;
	}
	


	private void setupFrame()
	{
		// Setup panels
		panelAgents = new PanelAgents(interpreter);
		panelLetterBox = new PanelLetterBox();
		panelLog = new PanelLog();
		
		// Setup frame
		frame = new JFrame("LiJ Monitor");
		int maxWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int maxHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setBounds(maxWidth - WIDTH, maxHeight - HEIGHT, WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("Agents", panelAgents);
		jtp.addTab("Messages", panelLetterBox);
		jtp.addTab("Log", panelLog);
		frame.getContentPane().add(jtp);
	}
	


	/**
	 * Activates or deactivates the monitor.
	 * @param _active The new active state.
	 */
	public void setActive(boolean _active)
	{
		active = _active;
		
		if (frame == null && active)
			setupFrame();
		
		if (frame != null)
		{
			panelAgents.setActive(_active);
			frame.setVisible(active);
		}
	}
	


	/**
	 * Notifies that a new Letter has been added in the message queue.
	 * @param letter The newly arrived letter.
	 */
	public void letterAdded(Letter letter)
	{
		if (!active)
			return;
		
		panelLetterBox.letterAdded(letter);
	}
	


	/**
	 * Notifies that a Letter has been removed from the message queue.
	 * @param letter The newly removed letter.
	 */
	public void letterRemoved(Letter letter)
	{
		if (!active)
			return;
		
		panelLetterBox.letterRemoved(letter);
	}
	


	/**
	 * Writes a new log message.
	 * @param source The source of the message.
	 * @param text The message String.
	 */
	public void log(String source, String text)
	{
		if (!active)
			return;
		
		panelLog.log(source, text);
	}
}
