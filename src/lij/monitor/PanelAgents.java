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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import lij.runtime.Interpreter;



/**
 * A monitor panel for observing subscribed agents.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class PanelAgents extends JPanel
{
	private Interpreter interpreter;
	private JTable table;
	private PanelAgentsTableModel tableModel;
	


	/**
	 * Constructor.
	 * @param _interpreter A pointer to the Interpreter object.
	 */
	public PanelAgents(Interpreter _interpreter)
	{
		super(new BorderLayout());
		
		interpreter = _interpreter;
		tableModel = new PanelAgentsTableModel(interpreter);
		table = new JTable(tableModel);
		
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	


	/**
	 * Activates or deactivates the monitor component.
	 * @param _active The new active state.
	 */
	public void setActive(boolean _active)
	{
		tableModel.setActive(_active);
	}
}
