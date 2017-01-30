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



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import lij.exceptions.InterpreterException;
import lij.interfaces.Accessor;
import lij.interfaces.ConstraintImplementor;



public class AgentPhilosopher implements ConstraintImplementor
{
	private static final String[] NAMES = new String[] { "Plato", "Konfuzius", "Socrates", "Voltaire", "Descartes" };
	private static final String DESIRE_EAT = "EAT";
	private static final String DESIRE_THINK = "THINK";
	private static final String STATE_THINKING = "THINKING";
	private static final String STATE_EATING = "EATING";
	private static final String STATE_WAITING_LEFT = "WAITING_LEFT";
	private static final String STATE_WAITING_RIGHT = "WAITING_RIGHT";
	private static final int WIDTH = 90;
	private static final int HEIGHT = 170;
	private static final int RADIUS = 320;
	
	private JFrame frame = null;
	private JLabel lPicture = new JLabel();
	private JLabel lState = new JLabel();
	private String desire = DESIRE_THINK;
	private String state = STATE_THINKING;
	private int id = -1;
	private int forkIndexLeft = -1;
	private int forkIndexRight = -1;
	


	public AgentPhilosopher(int _id, int _forkIndexLeft, int _forkIndexRight)
	{
		id = _id;
		forkIndexLeft = _forkIndexLeft;
		forkIndexRight = _forkIndexRight;
		
		// Frame location
		int centreX = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2.0);
		int centreY = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2.0);
		double angle = (id) * 2.0 * Math.PI / 5.0 - Math.PI / 2.0;
		int offsetX = (int)(Math.cos(angle) * RADIUS);
		int offsetY = (int)(Math.sin(angle) * RADIUS);
		int x = centreX + offsetX - WIDTH / 2;
		int y = centreY + offsetY - HEIGHT / 2;
		
		// Setup GUI
		lPicture.setHorizontalAlignment(SwingConstants.CENTER);
		lPicture.setIcon(new ImageIcon("res/philosopher" + id + ".png"));
		lState.setHorizontalAlignment(SwingConstants.CENTER);
		lState.setOpaque(true);
		lState.setBorder(BorderFactory.createLoweredBevelBorder());
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(lPicture, BorderLayout.CENTER);
		contentPane.add(lState, BorderLayout.SOUTH);
		frame = new JFrame(NAMES[id]);
		frame.setResizable(false);
		frame.setBounds(x, y, WIDTH, HEIGHT);
		frame.setContentPane(contentPane);
		frame.setVisible(true);
		
		updateGUI();
	}
	


	public boolean updateDesire() throws InterpreterException
	{
		if (state == STATE_WAITING_LEFT || state == STATE_WAITING_RIGHT)
			return true;
		
		int input = JOptionPane.showConfirmDialog(frame, "Do you want to eat?", frame.getTitle(), JOptionPane.YES_NO_OPTION);
		if (input == JOptionPane.YES_OPTION)
		{
			desire = DESIRE_EAT;
			
			if (state == STATE_THINKING)
				state = STATE_WAITING_LEFT;
		}
		else
			desire = DESIRE_THINK;
		
		return true;
	}
	


	public boolean wantsFork(Accessor ForkIndex) throws InterpreterException
	{
		boolean result = false;
		
		if (state == STATE_WAITING_LEFT)
		{
			ForkIndex.setValue(forkIndexLeft);
			result = true;
		}
		
		if (state == STATE_WAITING_RIGHT)
		{
			ForkIndex.setValue(forkIndexRight);
			result = true;
		}
		
		updateGUI();
		
		return result;
	}
	


	public boolean wantsStartThinking(Accessor ReturnedForkIndexLeft, Accessor ReturnedForkIndexRight) throws InterpreterException
	{
		boolean result = false;
		
		if (state == STATE_EATING && desire == DESIRE_THINK)
		{
			ReturnedForkIndexLeft.setValue(forkIndexLeft);
			ReturnedForkIndexRight.setValue(forkIndexRight);
			
			state = STATE_THINKING;
			result = true;
		}
		
		updateGUI();
		
		return result;
	}
	


	public boolean wantsNothing() throws InterpreterException
	{
		boolean result = false;
		
		if (state == STATE_EATING && desire == DESIRE_EAT)
			result = true;
		
		if (state == STATE_THINKING && desire == DESIRE_THINK)
			result = true;
		
		updateGUI();
		
		return result;
	}
	


	public boolean gotFork(Accessor ForkIndex) throws InterpreterException
	{
		if (ForkIndex.getValue().equals(forkIndexLeft))
			state = STATE_WAITING_RIGHT;
		
		else if (ForkIndex.getValue().equals(forkIndexRight))
			state = STATE_EATING;
		
		updateGUI();
		
		return true;
	}
	


	public boolean gotWait(Accessor ForkIndex) throws InterpreterException
	{
		if (ForkIndex.getValue().equals(forkIndexLeft))
			state = STATE_WAITING_LEFT;
		
		else if (ForkIndex.getValue().equals(forkIndexRight))
			state = STATE_WAITING_RIGHT;
		
		updateGUI();
		
		return true;
	}
	


	private void updateGUI()
	{
		if (state == STATE_WAITING_LEFT)
		{
			lState.setText("Needs left fork");
			lState.setBackground(Color.ORANGE);
		}
		else if (state == STATE_WAITING_RIGHT)
		{
			lState.setText("Needs right fork");
			lState.setBackground(Color.ORANGE);
		}
		else if (state == STATE_THINKING)
		{
			lState.setText("Is thinking...");
			lState.setBackground(Color.GREEN);
		}
		else if (state == STATE_EATING)
		{
			lState.setText("Is eating...");
			lState.setBackground(Color.RED);
		}
	}
}
