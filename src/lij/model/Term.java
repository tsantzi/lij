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



import java.util.ArrayList;



/**
 * A Term is a definition of the form a(x, y, z, ...) a is the name of the term,
 * and x, y, z, ... are the arguments. A way to envision this is a Java method
 * call, such as add(a, b)
 * 
 * @author Nikolaos Chatzinikolaou
 */
public class Term implements Argument
{
	private String name;
	private ArrayList<Argument> arguments = new ArrayList<Argument>();
	


	/**
	 * Constructor.
	 * @param _name The name constant of the term.
	 */
	public Term(String _name)
	{
		this(_name, new ArrayList<Argument>());
	}
	


	/**
	 * Constructor.
	 * @param _name The name constant of the term.
	 * @param _terms The arguments of the term.
	 */
	public Term(String _name, ArrayList<Argument> _terms)
	{
		name = _name;
		arguments = _terms;
	}
	


	/**
	 * Accessor.
	 * @return The name of the term.
	 */
	public String getName()
	{
		return name;
	}
	


	/**
	 * Accessor.
	 * @return The list of arguments of the term.
	 */
	public ArrayList<Argument> getArguments()
	{
		return arguments;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other)
	{
		// In theory, we shouldn't be using equals() and hashCode() to do Term matching,
		// but for the time being this will have to do
		
		if (this == other)
			return true;
		
		if (!(other instanceof Term))
			return false;
		
		Term otherTerm = (Term)other;
		
		if (!(this.name.equals(otherTerm.name)))
			return false;
		
		if (this.arguments.size() != otherTerm.arguments.size())
			return false;
		
		for (int i = 0; i < arguments.size(); i++)
			if (!equalsForArgument(this.arguments.get(i), otherTerm.arguments.get(i)))
				return false;
		
		return true;
	}
	


	/**
	 * This method performs an equality check between two terms' arguments. Note
	 * that this method will NOT check whether the actual contents (i.e. value)
	 * of the arguments being compared are equal.
	 * @param arg1 The first Argument to compare.
	 * @param arg2 The second Argument to compare.
	 * @return True, if both Arguments are NOT of class Term. If they are, this
	 *         method will call the .equals() method on the Term objects.
	 */
	private boolean equalsForArgument(Argument arg1, Argument arg2)
	{
		if (arg1 instanceof Term && arg2 instanceof Term)
			return arg1.equals(arg2); // Recursive call back to Term.equals()
			
		else
			return true;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		int hashCode = name.hashCode();
		
		for (Argument argument : arguments)
			hashCode += hashCodeForArgument(argument);
		
		return hashCode;
	}
	


	/**
	 * Returns a hashcode for the specified Argument.
	 * @param argument The Argument object.
	 * @return The Argument's hashcode.
	 */
	private int hashCodeForArgument(Argument argument)
	{
		if (argument instanceof Term)
			return argument.hashCode(); // Recursive call back to Term.hashCode()
		else
			return 1000;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone()
	{
		ArrayList<Argument> cloneArguments = new ArrayList<Argument>();
		for (Argument argument : arguments)
			cloneArguments.add((Argument)argument.clone());
		
		return new Term(name, cloneArguments);
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		
		s.append(name.toString());
		
		if (arguments != null && arguments.size() > 0)
		{
			s.append("(");
			for (int i = 0; i < arguments.size(); i++)
				s.append(arguments.get(i).toString() + (i < arguments.size() - 1 ? ", " : ""));
			s.append(")");
		}
		
		return s.toString();
	}
}
