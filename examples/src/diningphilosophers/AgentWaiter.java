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

package diningphilosophers;



import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import lij.exceptions.InterpreterException;
import lij.interfaces.Accessor;
import lij.interfaces.ConstraintImplementor;



public class AgentWaiter implements ConstraintImplementor
{
	private static final int WIDTH = 320;
	private static final int HEIGHT = 340;
	private static final int SPAGHETTI_RADIUS = 100;
	private static final int FORK_RADIUS = 100;
	private static final ImageIcon ICON_SPAGHETTI = new ImageIcon("res/spaghetti.png");
	private static final int ICON_WIDTH = ICON_SPAGHETTI.getIconWidth();
	private static final int ICON_HEIGHT = ICON_SPAGHETTI.getIconHeight();
	private JFrame frame = null;
	private JLayeredPane lPane = new JLayeredPane();
	private JLabel lTable = new JLabel(new ImageIcon("res/table.png"));
	private JLabel[] lSpaghetti = new JLabel[5];
	private JLabel[] lForks = new JLabel[5];
	public boolean[] forks = new boolean[] { true, true, true, true, true };
	


	public AgentWaiter()
	{
		// Set (up) the table...
		lPane.add(lTable, 1);
		for (int i = 0; i < 5; i++)
		{
			lSpaghetti[i] = new JLabel(ICON_SPAGHETTI);
			lSpaghetti[i].setOpaque(false);
			lPane.add(lSpaghetti[i], 0);
			lForks[i] = new JLabel(new ImageIcon("res/fork" + i + ".png"));
			lForks[i].setOpaque(false);
			lPane.add(lForks[i], 0);
		}
		
		// Frame location
		int centreX = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2.0);
		int centreY = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2.0);
		int x = centreX - WIDTH / 2;
		int y = centreY - HEIGHT / 2;
		
		// Setup GUI
		frame = new JFrame("Waiter");
		frame.setResizable(false);
		frame.setBounds(x, y, WIDTH, HEIGHT);
		frame.setContentPane(lPane);
		frame.addComponentListener(new ComponentAdapter()
		{
			public void componentShown(ComponentEvent ce)
			{
				int width = frame.getContentPane().getWidth();
				int height = frame.getContentPane().getHeight();
				lTable.setBounds(0, 0, width, height);
				for (int i = 0; i < 5; i++)
				{
					double spaghettiAngle = i * 2.0 * Math.PI / 5.0 - Math.PI / 2.0;
					int tableCentreX = width / 2;
					int tableCentreY = height / 2;
					
					// Spaghetti
					int spaghettiOffsetX = (int)(Math.cos(spaghettiAngle) * SPAGHETTI_RADIUS);
					int spaghettiOffsetY = (int)(Math.sin(spaghettiAngle) * SPAGHETTI_RADIUS);
					int spaghettiX = tableCentreX + spaghettiOffsetX - ICON_WIDTH / 2;
					int spaghettiY = tableCentreY + spaghettiOffsetY - ICON_HEIGHT / 2;
					lSpaghetti[i].setBounds(spaghettiX, spaghettiY, ICON_WIDTH, ICON_HEIGHT);
					
					// Forks
					double forkAngle = i * 2.0 * Math.PI / 5.0 - Math.PI / 2.0 + Math.PI / 5.0;
					int forkOffsetX = (int)(Math.cos(forkAngle) * FORK_RADIUS);
					int forkOffsetY = (int)(Math.sin(forkAngle) * FORK_RADIUS);
					int forkX = tableCentreX + forkOffsetX - ICON_WIDTH / 2;
					int forkY = tableCentreY + forkOffsetY - ICON_HEIGHT / 2;
					lForks[i].setBounds(forkX, forkY, ICON_WIDTH, ICON_HEIGHT);
				}
				
				updateGUI();
			}
		});
		
		frame.setVisible(true);
	}
	


	public boolean giveFork(Accessor ForkIndex) throws InterpreterException
	{
		int forkIndex = (Integer)ForkIndex.getValue();
		
		boolean result = false;
		if (forks[forkIndex])
		{
			forks[forkIndex] = false;
			result = true;
		}
		
		updateGUI();
		
		return result;
	}
	


	public boolean forkReturned(Accessor ForkIndex) throws InterpreterException
	{
		int forkIndex = (Integer)ForkIndex.getValue();
		
		forks[forkIndex] = true;
		
		updateGUI();
		
		return true;
	}
	


	private void updateGUI()
	{
		for (int i = 0; i < 5; i++)
		{
			if (forks[i])
				lPane.add(lForks[i], 0);
			else
				lPane.remove(lForks[i]);
		}
		
		lPane.revalidate();
		lPane.repaint();
	}
}
