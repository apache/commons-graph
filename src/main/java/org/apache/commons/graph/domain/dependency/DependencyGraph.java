package org.apache.commons.graph.domain.dependency;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.commons.graph.*;
import org.apache.commons.graph.contract.*;
import org.apache.commons.graph.exception.*;
import org.apache.commons.graph.factory.GraphFactory;
import org.apache.commons.graph.decorator.DecoratedDirectedGraph;
import org.apache.commons.graph.domain.dependency.exception.CircularDependencyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description of the Class
 */
public class DependencyGraph
     extends DecoratedDirectedGraph
  implements Acyclic
{
    private GraphFactory factory = new GraphFactory();
    private AcyclicContract acyclic = new AcyclicContract();
    private DependencyVisitor visitor = new DependencyVisitor();

    private Map vertices = new HashMap();

    private MutableDirectedGraph DAG = null;

    /**
     * Constructor for the DependencyGraph object
     */
    public DependencyGraph()
    {
        super();
        Contract[] dagContracts = new Contract[1];
        dagContracts[0] = acyclic;
        DAG = factory.makeMutableDirectedGraph(dagContracts,
					       false,
					       null);
        setDirGraph(DAG);
    }

    /**
     * Description of the Method
     */
    private void init()
    {
    }
    
    /**
     * Adds a feature to the Dependencies attribute of the DependencyGraph
     * object
     */
    public void addDependencies(Object head,
                                Collection deps)
        throws GraphException,
        CircularDependencyException
    {
        DependencyVertex vHead = findVertex(head);

        if (!getVertices().contains(vHead))
        {
            DAG.addVertex(vHead);
        }

        try
        {
            Iterator v = deps.iterator();

            while (v.hasNext())
            {
                DependencyVertex vDep = findVertex(v.next());

                if (!getVertices().contains(vDep))
                {
                    DAG.addVertex(vDep);
                }

                DAG.addEdge(new Dependency(vHead.getValue(),
					   vDep.getValue()),
			    vHead, vDep);
            }
        }
        catch (CycleException ex)
	  {
            throw new CircularDependencyException(ex);
	  }
    }

    /**
     * Description of the Method
     */
    public DependencyVertex findVertex(Object o)
    {
        if (vertices.containsKey(o))
        {
            return (DependencyVertex) vertices.get(o);
        }
        else
        {
            DependencyVertex RC = new DependencyVertex(o);
            vertices.put(o, RC);
            return RC;
        }
    }

    /**
     * Gets the sortedDependencies attribute of the DependencyGraph object
     */
    public List getSortedDependencies(Object head)
    {
        return visitor.getSortedDependencies(this, findVertex(head));
    }

    /** 
     * Retrieve the vertices in a flattened, sorted, valid order.
     *
     *  @return The list of vertices in a valid order.
     */
    public List getSortedDependencies()
    {
	return visitor.getSortedDependencies( this );
    }
}



