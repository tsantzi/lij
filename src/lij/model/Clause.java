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
 * This class provides the definition of an LCC clause as specified in an IM. It
 * contains two things: An Agent definition, and the root of a clause definition
 * tree.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class Clause
{
	private DefAgent agent;
	private TreeNode root = null;
	


	/**
	 * Constructor.
	 * @param _agent The Clause's DefAgent signature.
	 * @param _root The root node of the Clause's tree.
	 */
	public Clause(DefAgent _agent, TreeNode _root)
	{
		agent = _agent;
		root = _root;
	}
	


	/**
	 * Accessor.
	 * @return The Clause's DefAgent signature.
	 */
	public DefAgent getAgent()
	{
		return agent;
	}
	


	/**
	 * Accessor.
	 * @return The root node of the Clause's tree.
	 */
	public TreeNode getRoot()
	{
		return root;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone()
	{
		DefAgent cloneAgent = (DefAgent)agent.clone();
		TreeNode cloneRoot = (TreeNode)root.clone();
		
		return new Clause(cloneAgent, cloneRoot);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		s.append(agent + " :: " + root);
		return s.toString();
	}
}
