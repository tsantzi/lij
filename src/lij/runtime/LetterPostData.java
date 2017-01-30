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



import java.io.Serializable;

import lij.model.Term;



/**
 * Encapsulates the post data of a letter described by an LCC message, i.e.
 * message type, as well as sender and recepient type and ID.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class LetterPostData
{
	private Term messageType;
	private Term senderType;
	private Serializable senderID;
	private Term recepientType;
	private Serializable recepientID;
	


	/**
	 * Constructor.
	 * @param _messageType The type Term of the message.
	 * @param _senderType The type Term of the sender of the message.
	 * @param _senderID The ID of the sender of the message.
	 * @param _recepientType The type Term of the recepient of the message.
	 * @param _recepientID The ID of the recepient of the message.
	 */
	public LetterPostData(Term _messageType, Term _senderType, Serializable _senderID, Term _recepientType, Serializable _recepientID)
	{
		messageType = _messageType;
		senderType = _senderType;
		senderID = _senderID;
		recepientType = _recepientType;
		recepientID = _recepientID;
	}
	


	/**
	 * Accessor.
	 * @return The type Term of the message.
	 */
	public Term getMessageType()
	{
		return messageType;
	}
	


	/**
	 * Accessor.
	 * @return The type Term of the sender of the message.
	 */
	public Term getSenderType()
	{
		return senderType;
	}
	


	/**
	 * Accessor.
	 * @return The ID of the sender of the message.
	 */
	public Serializable getSenderID()
	{
		return senderID;
	}
	


	/**
	 * Accessor.
	 * @return The type Term of the recepient of the message.
	 */
	public Term getRecepientType()
	{
		return recepientType;
	}
	


	/**
	 * Accessor.
	 * @return The ID of the recepient of the message.
	 */
	public Serializable getRecepientID()
	{
		return recepientID;
	}
	


	/**
	 * Compares this LetterPostData object with another for a match.
	 * @param other The LetterPostData object to compare against.
	 * @return True, if this LetterPostData object matches the other
	 *         LetterPostData object; false otherwise.
	 */
	public boolean matches(LetterPostData other)
	{
		if (this.messageType.equals(other.messageType))
			if (this.senderType == null || other.senderType == null || this.senderType.getName().equals(other.senderType.getName())) // Note: Only using role name, not full role type
				if (this.senderID == null || other.senderID == null || this.senderID.equals(other.senderID))
					if (this.recepientType == null || other.recepientType == null || this.recepientType.getName().equals(other.recepientType.getName())) // Note: Only using role name, not full role type
						if (this.recepientID == null || other.recepientID == null || this.recepientID.equals(other.recepientID))
							return true;
		return false;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "TYPE: " + messageType + " FROM: " + senderType + " ID: " + senderID + " TO: " + recepientType + " ID: " + recepientID;
	}
}
