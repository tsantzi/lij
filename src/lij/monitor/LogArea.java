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
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;



/**
 * This class defines a component containing a JTextArea, and provides
 * convenience methods for logging text into it.
 * 
 * @author Nikolaos Chatzinikolaou
 * @version 2005.12.16
 */
public class LogArea extends JComponent
{
	// Constants
	private static final long serialVersionUID = 997112669721328356L;
	
	// Member variables
	private ArrayList<String> buffer = new ArrayList<String>();
	private JTextArea logArea = new JTextArea();
	private int lineLimit = 0;
	private boolean hold = false;
	private boolean autoScroll = true;
	private boolean showTimestamp = true;
	


	/**
	 * Constructs a new LogArea.
	 */
	public LogArea()
	{
		setLayout(new BorderLayout());
		logArea.setEditable(false);
		JScrollPane jsp = new JScrollPane(logArea);
		add(jsp, BorderLayout.CENTER);
	}
	


	/**
	 * Constructs a new LogArea with the specified line limit.
	 */
	public LogArea(int _lineLimit)
	{
		this();
		lineLimit = _lineLimit;
	}
	


	/**
	 * Sets automatic scrolling.
	 * @param _autoScroll If true, the text area will scroll automatically upon
	 *            appending text to it.
	 */
	public void setAutoScroll(boolean _autoScroll)
	{
		autoScroll = _autoScroll;
	}
	


	/**
	 * Sets the hold state.
	 * @param _hold If true, the text area will not be updated.
	 */
	public synchronized void setHold(boolean _hold)
	{
		hold = _hold;
		
		if (!hold)
		{
			StringBuffer bufferedText = new StringBuffer();
			while (!buffer.isEmpty())
				bufferedText.append(buffer.remove(0));
			
			doAppend(bufferedText.toString());
		}
	}
	


	/**
	 * Sets the line limit of this LogArea.
	 * @param _lineLimit The new line limit.
	 */
	public synchronized void setLineLimit(int _lineLimit)
	{
		lineLimit = _lineLimit;
	}
	


	/**
	 * Enables or disables the timestamp.
	 * @param _showTimestamp If true, a timestamp will be appended to the
	 *            beginning of each message.
	 */
	public void setShowTimestamp(boolean _showTimestamp)
	{
		showTimestamp = _showTimestamp;
	}
	


	/**
	 * Appends the specified text into the LogArea, buffering if hold is on.
	 * 
	 * @param text The text to append to the log.
	 */
	public synchronized void append(String text)
	{
		// Construct line
		StringBuffer line = new StringBuffer();
		if (showTimestamp)
			line.append("<" + new Date().toString() + "> ");
		line.append(text + "\n");
		
		if (hold)
		{
			// Crop buffer
			if (lineLimit > 0 && buffer.size() > lineLimit)
				buffer.remove(0);
			
			// Buffer line
			buffer.add(line.toString());
		}
		else
			doAppend(line.toString());
	}
	


	/**
	 * Appends the specified text into the LogArea.
	 * 
	 * @param text The text to append to the log.
	 */
	private void doAppend(String text)
	{
		// Append line to text area
		logArea.append(text);
		
		// Crop text area text
		if (lineLimit > 0)
		{
			int currentLines = logArea.getLineCount() - 1;
			if (currentLines > lineLimit)
			{
				try
				{
					int offset = logArea.getLineStartOffset(currentLines - lineLimit);
					logArea.setText(logArea.getText().substring(offset));
				}
				catch (BadLocationException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		// Scroll down
		if (autoScroll)
			logArea.setCaretPosition(logArea.getDocument().getLength());
	}
	


	/**
	 * Returns the number of chatacters in the LogArea.
	 * 
	 * @return The number of chatacters in the LogArea.
	 */
	public int getTextSize()
	{
		return logArea.getDocument().getLength();
	}
	


	/**
	 * Copies the contents of the LogArea into the system clipboard.
	 */
	public void copy()
	{
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(logArea.getText()), null);
	}
	


	/**
	 * Clears the contents of the LogArea.
	 */
	public void clear()
	{
		buffer.clear();
		logArea.setText("");
	}
}
