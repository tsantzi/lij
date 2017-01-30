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

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import lij.runtime.Letter;



/**
 * A monitor panel for observing the state (and contents) of the (global)
 * message queue.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class PanelLetterBox extends JPanel
{
	private static final int JSP_LOCATION = 200;
	private DefaultListModel listModel = new LetterBoxListModel();
	private JTextArea taLog = new JTextArea();
	private JLabel lCount = new JLabel("0");
	


	/**
	 * Constructor.
	 */
	public PanelLetterBox()
	{
		super(new BorderLayout());
		JSplitPane jsp = new JSplitPane();
		jsp.setLeftComponent(new JScrollPane(new JList(listModel)));
		jsp.setRightComponent(new JScrollPane(taLog));
		jsp.setDividerLocation(JSP_LOCATION);
		add(jsp, BorderLayout.CENTER);
		add(lCount, BorderLayout.SOUTH);
	}
	


	/**
	 * Notifies that a new Letter has been added in the message queue.
	 * @param letter The newly arrived letter.
	 */
	public synchronized void letterAdded(Letter letter)
	{
		taLog.append("> " + letter.toString() + "\n");
		taLog.setCaretPosition(taLog.getDocument().getLength());
		listModel.addElement(letter);
		lCount.setText(String.valueOf(listModel.size()));
	}
	


	/**
	 * Notifies that a Letter has been removed from the message queue.
	 * @param letter The newly removed letter.
	 */
	public synchronized void letterRemoved(Letter letter)
	{
		taLog.append("X  " + letter.toString() + "\n");
		taLog.setCaretPosition(taLog.getDocument().getLength());
		listModel.removeElement(letter);
		lCount.setText(String.valueOf(listModel.size()));
	}
	


	/**
	 * A default ListModel implementation.
	 */
	private class LetterBoxListModel extends DefaultListModel
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.DefaultListModel#getElementAt(int)
		 */
		public Object getElementAt(int index)
		{
			try
			{
				return super.elementAt(index);
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				// This is a bug workaround.
				// The bug is caused by the fact that the messages go in and out of the list model very quickly
				// The view calls getSize(), but by the time it begins rendering, the elements in the model have changed.
				// This caused ArrayIndexOutOfBoundsExceptions to be thrown occasionally.
				return null;
			}
		}
	}
}
