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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import lij.exceptions.InterpreterException;
import lij.interfaces.Accessor;
import lij.interfaces.Result;
import lij.model.Argument;
import lij.model.ArgumentVariable;
import lij.model.Constraint;
import lij.model.ConstraintAssignment;
import lij.model.ConstraintComparison;
import lij.model.ConstraintList;
import lij.model.ConstraintMethod;
import lij.model.Term;
import lij.model.ConstraintComparison.Type;



/**
 * This class performs checking of Constraints. It's main purpose is to decouple
 * the execution code from the model.Def class
 * 
 * @author Nikolaos Chatzinikolaou
 */
public abstract class ConstraintInstance
{
	protected Constraint constraint;
	protected Interpreter interpreter;
	protected AgentInstance agentInstance;
	


	/**
	 * Factory method for creating an instance of a ConstraintInstance subclass.
	 * @param _constraint The Constraint encapsulated in this
	 *            ConstraintInstance.
	 * @param _agentInstance The AgentInstance performing the instantiation.
	 * @return The instantiated ConstraintInstance.
	 */
	public static ConstraintInstance createConstraintInstance(Constraint _constraint, Interpreter _interpreter, AgentInstance _agentInstance)
	{
		ConstraintInstance constraintInstance = null;
		
		if (_constraint instanceof ConstraintComparison)
			constraintInstance = new ConstraintComparisonInstance();
		else if (_constraint instanceof ConstraintAssignment)
			constraintInstance = new ConstraintAssignmentInstance();
		else if (_constraint instanceof ConstraintList)
			constraintInstance = new ConstraintListInstance();
		else if (_constraint instanceof ConstraintMethod)
			constraintInstance = new ConstraintMethodInstance();
		
		constraintInstance.constraint = _constraint;
		constraintInstance.interpreter = _interpreter;
		constraintInstance.agentInstance = _agentInstance;
		
		return constraintInstance;
	}
	


	/**
	 * This method needs to be implemented by any subclasses. It performs the
	 * evaluation (checking) of the constraint.
	 * @return The result of the evaluation (TRUE, FALSE or MAYBE).
	 * @throws InterpreterException
	 */
	public abstract Result.State check() throws InterpreterException;
	


	/**
	 * A ConstraintInstance subclass for comparison constraints.
	 */
	private static class ConstraintComparisonInstance extends ConstraintInstance
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.runtime.ConstraintInstance#check()
		 */
		public Result.State check() throws InterpreterException
		{
			Argument argument1 = ((ConstraintComparison)constraint).getArgument1();
			Argument argument2 = ((ConstraintComparison)constraint).getArgument2();
			Serializable value1 = agentInstance.getCurrentClauseInstance().getValueForArgument(argument1);
			Serializable value2 = agentInstance.getCurrentClauseInstance().getValueForArgument(argument2);
			
			return checkValues(value1, value2, ((ConstraintComparison)constraint).getType());
		}
		


		/**
		 * Compares the two specified values. This method can be used
		 * recursively, so that lists can be compared as well as singleton
		 * values.
		 * @param value1 The LHS value.
		 * @param value2 The RHS value.
		 * @param type The type of the comparison.
		 * @return The result of the comparison (true or false).
		 * @throws InterpreterException
		 */
		private Result.State checkValues(Serializable value1, Serializable value2, Type type) throws InterpreterException
		{
			// One or both of the values are null
			if (value1 == null || value2 == null)
				throw new InterpreterException("Attempting to perform a comparison between null values");
			
			// Values are of different classes
			if (!value1.getClass().equals(value2.getClass()))
				throw new InterpreterException("Attempting to perform a comparison between values of different class");
			
			if (value1 instanceof ArrayList /* && value2 instanceof ArrayList */)
			{
				ArrayList<Serializable> list1 = (ArrayList<Serializable>)value1;
				ArrayList<Serializable> list2 = (ArrayList<Serializable>)value2;
				
				if (list1.size() != list2.size())
					return Result.State.FALSE;
				
				for (int i = 0; i < list1.size(); i++)
					if (checkValues(list1.get(i), list2.get(i), type) == Result.State.FALSE)
						return Result.State.FALSE;
				
				return Result.State.TRUE;
			}
			
			if (type == Type.EQUAL_TO)
				return (((Comparable)value1).compareTo((Comparable)value2) == 0) ? Result.State.TRUE : Result.State.FALSE;
			else if (type == Type.LESS_THAN)
				return (((Comparable)value1).compareTo((Comparable)value2) < 0) ? Result.State.TRUE : Result.State.FALSE;
			else if (type == Type.GREATER_THAN)
				return (((Comparable)value1).compareTo((Comparable)value2) > 0) ? Result.State.TRUE : Result.State.FALSE;
			
			return Result.State.FALSE;
		}
	}
	


	/**
	 * A ConstraintInstance subclass for assignment constraints.
	 */
	private static class ConstraintAssignmentInstance extends ConstraintInstance
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.runtime.ConstraintInstance#check()
		 */
		public Result.State check() throws InterpreterException
		{
			Argument argument1 = ((ConstraintAssignment)constraint).getArgument1();
			Argument argument2 = ((ConstraintAssignment)constraint).getArgument2();
			
			if (!(argument1 instanceof ArgumentVariable))
				throw new InterpreterException("Attempting to assign a value to a non-variable argument");
			else
			{
				ArgumentVariable key = (ArgumentVariable)argument1;
				Serializable value = agentInstance.getCurrentClauseInstance().getValueForArgument(argument2);
				agentInstance.getCurrentClauseInstance().storeVariable(key, value);
			}
			
			return Result.State.TRUE;
		}
	}
	


	/**
	 * A ConstraintInstance subclass for list constraints.
	 */
	private static class ConstraintListInstance extends ConstraintInstance
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.runtime.ConstraintInstance#check()
		 */
		public Result.State check() throws InterpreterException
		{
			ArgumentVariable list = ((ConstraintList)constraint).getList();
			ArgumentVariable head = ((ConstraintList)constraint).getHead();
			ArgumentVariable tail = ((ConstraintList)constraint).getTail();
			
			// List append
			if (agentInstance.getCurrentClauseInstance().getValueForArgument(list) == null)
			{
				ArrayList<Serializable> resultList = new ArrayList<Serializable>();
				resultList.addAll(createListForArgument(head));
				resultList.addAll(createListForArgument(tail));
				agentInstance.getCurrentClauseInstance().storeVariable(list, resultList);
			}
			
			// List extract
			else
			{
				ArrayList<Serializable> sourceList = createListForArgument(list);
				if (sourceList.isEmpty())
					return Result.State.FALSE;
				
				Serializable valueHead = sourceList.get(0);
				ArrayList<Serializable> valueTail = new ArrayList<Serializable>();
				for (int i = 1; i < sourceList.size(); i++)
					valueTail.add(sourceList.get(i));
				agentInstance.getCurrentClauseInstance().storeVariable(head, valueHead);
				agentInstance.getCurrentClauseInstance().storeVariable(tail, valueTail);
			}
			
			return Result.State.TRUE;
		}
		


		/**
		 * Accepts an Argument, which can be either an ArrayList or a singleton
		 * value, creates a new ArrayList containing this Argument, and returns
		 * it. The effect is that the returned object is always an ArrayList,
		 * regardless of whether the Argument specified originally was a list or
		 * a singleton.
		 * @param argument The Argument to encapsulate into an ArrayList.
		 * @return The ArrayList.
		 * @throws InterpreterException
		 */
		private ArrayList<Serializable> createListForArgument(Argument argument) throws InterpreterException
		{
			ArrayList<Serializable> result = new ArrayList<Serializable>();
			
			Serializable value = agentInstance.getCurrentClauseInstance().getValueForArgument(argument);
			
			if (value instanceof ArrayList)
				result.addAll((ArrayList)value);
			else if (value != null)
				result.add(value);
			
			return result;
		}
	}
	


	/**
	 * A ConstraintInstance subclass for method constraints.
	 */
	private static class ConstraintMethodInstance extends ConstraintInstance
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see interpreter.runtime.ConstraintInstance#check()
		 */
		public Result.State check() throws InterpreterException
		{
			// Determine required method
			Term constraintMethodTerm = ((ConstraintMethod)constraint).getConstraintMethodTerm();
			Accessor[] argumentAccessors = new Accessor[constraintMethodTerm.getArguments().size()];
			for (int i = 0; i < constraintMethodTerm.getArguments().size(); i++)
				argumentAccessors[i] = new ArgumentAccessor(constraintMethodTerm.getArguments().get(i), agentInstance.getCurrentClauseInstance().getSymbolTable());
			Class[] parameterTypes = new Class[constraintMethodTerm.getArguments().size()];
			for (int i = 0; i < parameterTypes.length; i++)
				parameterTypes[i] = Accessor.class;
			
			// Attempt to execute method in special constraints interface
			try
			{
				SpecialConstraints specialConstraints = new SpecialConstraints(interpreter, agentInstance);
				String methodName = constraintMethodTerm.getName();
				return executeConstraintMethod(specialConstraints, methodName, parameterTypes, argumentAccessors);
			}
			catch (NoSuchMethodException nsme)
			{
				// Ignore; simply means that the method is not a special constraint method, so go ahead and try the ConstraintImplementor instead
			}
			catch (Throwable t)
			{
				t.printStackTrace();
				throw new InterpreterException("Constraint method '" + constraintMethodTerm + "' invocation failed");
			}
			
			// Attempt to execute method in constraint implementor of agent
			try
			{
				Object constraintImplementor = agentInstance.getConstraintImplementor();
				String methodName = constraintMethodTerm.getName();
				return executeConstraintMethod(constraintImplementor, methodName, parameterTypes, argumentAccessors);
			}
			catch (NoSuchMethodException nsme)
			{
				throw new InterpreterException("Constraint method '" + constraintMethodTerm + "' was not found");
			}
			catch (Throwable t)
			{
				t.printStackTrace();
				throw new InterpreterException("Constraint method '" + constraintMethodTerm + "' invocation failed");
			}
		}
		


		Result.State executeConstraintMethod(Object object, String methodName, Class[] parameterTypes, Accessor[] argumentAccessors) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
		{
			Method method = object.getClass().getMethod(methodName, parameterTypes);
			Object result = method.invoke(object, (Object[])argumentAccessors);
			
			// Can handle both old-style boolean constraints (true/false), or new-style tri-state ones (TRUE/FALSE/MAYBE)
			if (result instanceof Boolean)
				return ((Boolean)result) ? Result.State.TRUE : Result.State.FALSE;
			else
				return (Result.State)result;
		}
	}
}
