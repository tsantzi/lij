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

package lij.monitor;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;



/**
 * A monitor panel for displaying log messages.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class PanelLog extends JPanel
{
	private static final int LOG_LINE_LIMIT = 1024;
	private LogArea logArea = new LogArea(LOG_LINE_LIMIT);
	


	/**
	 * Constructor.
	 */
	public PanelLog()
	{
		super(new BorderLayout());
		
		logArea.setShowTimestamp(false);
		
		final JToggleButton bHold = new JToggleButton("Hold");
		bHold.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				logArea.setHold(bHold.isSelected());
			}
		});
		
		add(logArea, BorderLayout.CENTER);
		add(bHold, BorderLayout.SOUTH);
	}
	


	/**
	 * Writes a new log message.
	 * @param source The source of the message.
	 * @param text The message String.
	 */
	public synchronized void log(String source, String text)
	{
		logArea.append(source + "\t" + text);
	}
}
