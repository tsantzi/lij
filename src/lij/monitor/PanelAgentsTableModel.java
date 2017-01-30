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



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import lij.runtime.Interpreter;



/**
 * A TableModel implementation for the PanelAgents table. This tablemodel will periodically query the interpreter for a list of active agents, and will populate the table with the agents' details.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class PanelAgentsTableModel extends DefaultTableModel
{
	private static final int UPDATE_DELAY = 100; // ms
	private static final String[] COLUMN_NAMES = new String[] { "ID", "Role" };
	
	private Interpreter interpreter;
	private Timer timer = new Timer(UPDATE_DELAY, new TimerListener());
	


	/**
	 * Constructor.
	 * @param _interpreter A pointer to the Interpreter object.
	 */
	public PanelAgentsTableModel(Interpreter _interpreter)
	{
		interpreter = _interpreter;
	}
	


	/**
	 * Activates or deactivates the monitor component.
	 * @param _active The new active state.
	 */
	public void setActive(boolean _active)
	{
		if (_active)
			timer.start();
		else
			timer.stop();
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	public Class getColumnClass(int columnIndex)
	{
		if (columnIndex == 0)
			return String.class;
		else if (columnIndex == 1)
			return String.class;
		
		return null;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#getColumnName(int)
	 */
	public String getColumnName(int columnIndex)
	{
		return COLUMN_NAMES[columnIndex].toString();
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		return COLUMN_NAMES.length;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#getRowCount()
	 */
	public int getRowCount()
	{
		return (interpreter == null) ? 0 : interpreter.getAgents().size();
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.DefaultTableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		if (interpreter != null)
		{
			if (columnIndex == 0)
				return interpreter.getAgents().get(rowIndex).getCurrentID();
			else if (columnIndex == 1)
				return interpreter.getAgents().get(rowIndex).getCurrentType().toString();
		}
		
		return null;
	}
	


	/**
	 * An ActionListener implementation for the timer.
	 */
	private class TimerListener implements ActionListener
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent )
		 */
		public void actionPerformed(ActionEvent ae)
		{
			fireTableDataChanged();
		}
	}
}
