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




import java.util.HashMap;

import lij.exceptions.InterpreterException;



/**
 * This class contains the IM's Role and Clause definitions.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class Framework
{
	private HashMap<Term, Role> roles = new HashMap<Term, Role>();
	private HashMap<Term, Clause> clauses = new HashMap<Term, Clause>();
	


	/**
	 * Accessor.
	 * @return A map containing the Framework's Role definitions.
	 */
	public HashMap<Term, Role> getRoles()
	{
		return roles;
	}
	


	/**
	 * Accessor.
	 * @return A map containing the Framework's Clause definitions.
	 */
	public HashMap<Term, Clause> getClauses()
	{
		return clauses;
	}
	


	/**
	 * Adds a new Role into the Framework's map of Role definitions.
	 * @param newRole The Role to add.
	 * @throws InterpreterException
	 */
	public void addRole(Role newRole) throws InterpreterException
	{
		// Check if role has already been defined
		if (roles.containsKey(newRole.getType()))
			throw new InterpreterException("Role '" + newRole.getType() + "' has already been defined.");
		
		roles.put(newRole.getType(), newRole);
	}
	


	/**
	 * Adds a new Clause into the Framework's map of Clause definitions.
	 * @param newClause The Clause to add.
	 * @throws InterpreterException
	 */
	public void addClause(Clause newClause) throws InterpreterException
	{
		// Check if clause has already been defined
		if (clauses.containsKey(newClause.getAgent().getType()))
			throw new InterpreterException("Clause '" + newClause.getAgent().getType() + "' has already been defined.");
		
		// Check if clause has a matching role definition
		if (!roles.containsKey(newClause.getAgent().getType()))
			throw new InterpreterException("Clause '" + newClause.getAgent().getType() + "' does not have a role declaration.");
		
		clauses.put(newClause.getAgent().getType(), newClause);
	}
	


	/**
	 * Returns the Role definition that is of the kind "initial".
	 * @return The initial Role definition.
	 * @throws InterpreterException
	 */
	public Role getInitialRole() throws InterpreterException
	{
		Role[] rolesArray = roles.values().toArray(new Role[0]);
		for (Role role : rolesArray)
			if (role.getKind().equals(Role.KIND_INITIAL))
				return role;
		
		throw new InterpreterException("Initial role was not found");
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		s.append("Roles:\n");
		for (String role : roles.keySet().toArray(new String[0]))
			s.append(role + ": " + roles.get(role));
		s.append("\n\n");
		
		s.append("Clauses:\n");
		for (String clause : clauses.keySet().toArray(new String[0]))
			s.append(clause + ": " + clauses.get(clause));
		s.append("\n");
		
		return s.toString();
	}
}
