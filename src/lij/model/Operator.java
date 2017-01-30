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
 * A structure operator to be used in the clause tree. Can be a parenthesis, an
 * "or" or a "then". Each sub-class of this class has a distinct precedence,
 * which is used during RPN processing.
 * 
 * @author Nikolaos Chatzinikolaou
 */
public abstract class Operator implements TreeNodeToken
{
	protected int precedence = -1;
	


	/**
	 * Accessor.
	 * @return The operator's precedence.
	 */
	public int getPrecedence()
	{
		return precedence;
	}
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public abstract Object clone();
	


	/**
	 * Operator subclass.
	 */
	public static class OpenParen extends Operator
	{
		/**
		 * Constructor.
		 */
		public OpenParen()
		{
			precedence = 2;
		}
		


		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.model.Operator#clone()
		 */
		public Object clone()
		{
			return new OpenParen();
		}
		


		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			return " ( ";
		}
	}
	


	/**
	 * Operator subclass.
	 */
	public static class CloseParen extends Operator
	{
		/**
		 * Constructor.
		 */
		public CloseParen()
		{
			precedence = 2;
		}
		


		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.model.Operator#clone()
		 */
		public Object clone()
		{
			return new CloseParen();
		}
		


		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			return " ) ";
		}
	}
	


	/**
	 * Operator subclass.
	 */
	public static class Or extends Operator
	{
		/**
		 * Constructor.
		 */
		public Or()
		{
			precedence = 3;
		}
		


		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.model.Operator#clone()
		 */
		public Object clone()
		{
			return new Or();
		}
		


		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			return " | ";
		}
	}
	


	/**
	 * Operator subclass.
	 */
	public static class Then extends Operator
	{
		/**
		 * Constructor.
		 */
		public Then()
		{
			precedence = 4;
		}
		


		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.model.Operator#clone()
		 */
		public Object clone()
		{
			return new Then();
		}
		


		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			return " > ";
		}
	}
}
