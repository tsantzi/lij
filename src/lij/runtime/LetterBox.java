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

package lij.runtime;



import java.util.ArrayList;

import lij.monitor.Monitor;



/**
 * This class provides functionality for sending and receiving messages
 * (encapsulated in Letter instances).
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class LetterBox
{
	private ArrayList<Letter> letters = new ArrayList<Letter>();
	private Monitor monitor;
	


	/**
	 * Constructor.
	 * @param _monitor A pointer to the monitor GUI.
	 */
	public LetterBox(Monitor _monitor)
	{
		monitor = _monitor;
	}
	


	/**
	 * Attempts to retrieve the specified Letter from the letter queue.
	 * @param postData The post data of the Letter being looked for.
	 * @return The Letter, if found; null otherwise.
	 */
	public synchronized Letter getLetter(LetterPostData postData)
	{
		// Search for required message and return it
		for (int i = 0; i < letters.size(); i++)
			if (letters.get(i).matches(postData))
			{
				Letter letter = letters.remove(i);
				
				if (monitor != null)
					monitor.letterRemoved(letter);
				
				return letter;
			}
		
		// Message not found
		return null;
	}
	


	/**
	 * Puts the specified Letter into the letter queue.
	 * @param letter The Letter to put into the queue.
	 */
	public synchronized void putLetter(Letter letter)
	{
		letters.add(letter);
		
		if (monitor != null)
			monitor.letterAdded(letter);
	}
}
