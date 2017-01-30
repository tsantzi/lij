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



/**
 * Encapsulates the data described by an LCC message.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class Letter
{
	private LetterPostData postData;
	private Serializable[] payload = new Serializable[0];
	


	/**
	 * Constructor.
	 * @param _postData The post data.
	 * @param _payload The payload data in the message.
	 */
	public Letter(LetterPostData _postData, Serializable[] _payload)
	{
		postData = _postData;
		payload = _payload;
	}
	


	/**
	 * Accessor.
	 * @return The post data in the message.
	 */
	public LetterPostData getPostData()
	{
		return postData;
	}
	


	/**
	 * Accessor.
	 * @return The payload data in the message.
	 */
	public Serializable[] getPayload()
	{
		return payload;
	}
	


	/**
	 * Compares the LetterPostData object of this Letter with another for a
	 * match.
	 * @param other The LetterPostData object to compare against.
	 * @return True, if this Letter's LetterPostData object matches the other
	 *         LetterPostData object; false otherwise.
	 */
	public boolean matches(LetterPostData other)
	{
		return postData.matches(other);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return postData.toString() + " PAYLOAD: " + payload.toString();
	}
}
