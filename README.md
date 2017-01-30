#LiJ - LCC interpreter for Java

Version 1.3
Copyright © 2009-2011, Nikolaos Chatzinikolaou



##Note:

This software is provided without any warranties whatsoever. It is experimental work, so use at your own risk.

This documentation is provisional. More complete information will (probably) appear in the future.



##What?

LiJ is an interpreter capable of executing Interaction Models (IMs) described in the Lightweight Coordination Calculus (LCC). It is written in Java, and supports external constraint method providers using a (very small!) number of Java interfaces.

This is an early, research release, so the bundled documentation on LCC is severely lacking (read: non-existent). I assume that, if you're using this, you are familiar with LCC. If not, then for the time being you can get an idea by visiting the OpenKnowledge site and reading the page at "The OK System" -> "Being an OK User" -> "Using LCC".



##Why?

This interpreter constitutes part of my PhD research. When work on this interpreter started, the only readily available Java-based LCC interpreter was the one integrated into the OpenKnowledge project. However, the OpenKnowledge system is much more than a simple LCC interpreter. The downside of this is that it carries with it considerable overhead that is not necessary for the execution of simple, single-machine (i.e. not networked) LCC IMs. Hence the birth of LiJ.



##How?

Probably the fastest way to get started with LiJ is by having a look at the examples provided.

In general, using LiJ works like this:

1. You write an LCC protocol, and save it in a text file.

2. You write the following Java classes:
  i)  A class containing a main method
  ii) One or more classes implementing the ConstraintImplementor interface, and providing implementations for the constraint methods used in your LCC protocol.

3. In your main method, you instantiate an Interpreter object, load the LCC protocol file into it, subscribe your ConstraintImplementors to it, and start it.



##Where?

The latest release version of LiJ can be found at its home, at https://github.com/tsantzi/lij



##Who?

LiJ is developed by Nikolaos Chatzinikolaou.

The javacc syntax definition file is based on the one developed by Paolo Besana and David Dupplaw, as part of the OpenKnowledge project.

