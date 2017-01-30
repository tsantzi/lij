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

package lij.model;



/**
 * This class provides the definition of a role as specified in an IM.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class Role
{
	public static final String KIND_INITIAL = "initial";
	public static final String KIND_NECESSARY = "necessary";
	public static final String KIND_OPTIONAL = "optional";
	public static final String KIND_AUXILIARY = "auxiliary";
	public static final String KIND_CYCLIC = "cyclic";
	public static final String KIND_UNCOMMITTED = "uncommitted";
	
	private Term type;
	private String kind;
	private int min;
	private int max;
	


	/**
	 * Constructor.
	 * @param _type The type of the Role.
	 * @param _kind The "kind" of the role (i.e. initial, necessary etc).
	 * @param _min The minimum number of agents specified in the role declaration.
	 * @param _max The maximum number of agents specified in the role declaration.
	 */
	public Role(Term _type, String _kind, int _min, int _max)
	{
		type = _type;
		kind = _kind;
		min = _min;
		max = _max;
	}
	


	/**
	 * Accessor.
	 * @return The type of the Role.
	 */
	public Term getType()
	{
		return type;
	}
	


	/**
	 * Accessor.
	 * @return The "kind" of the role (i.e. initial, necessary etc).
	 */
	public String getKind()
	{
		return kind;
	}
	


	/**
	 * Accessor.
	 * @return The minimum number of agents specified in the role declaration.
	 */
	public int getMin()
	{
		return min;
	}
	


	/**
	 * Accessor.
	 * @return The maximum number of agents specified in the role declaration.
	 */
	public int getMax()
	{
		return max;
	}
	


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "r(" + type + ", " + kind + ")";
	}
}
