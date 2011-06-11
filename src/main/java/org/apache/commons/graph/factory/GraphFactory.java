package org.apache.commons.graph.factory;

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

import java.lang.reflect.*;

import org.apache.commons.graph.*;
import org.apache.commons.graph.contract.*;
import org.apache.commons.graph.exception.*;
import org.apache.commons.graph.domain.basic.*;

/**
 * Description of the Class
 */
public class GraphFactory
{

    /**
     * Constructor for the GraphFactory object
     */
    public GraphFactory() { }

    /**
     * makeGraph
     *
     * @param contracts Which contracts to enforce.
     * @param baseGraph Is the actual *GraphImpl which will be at the core of
     *      the Proxy.
     * @param baseGraphType Interface which is returned.
     * @param isWeighted Does the graph handle Weights?
     * @param init Initialization Graph.
     */
    private Object makeGraph(Contract contracts[],
                             InvocationHandler baseGraph,
                             Class baseGraphType,
                             boolean isWeighted)
        throws GraphException
    {
        int interfaceCount = contracts.length;
        interfaceCount++;// BaseGraph Type
        if (isWeighted)
        {
            interfaceCount++;
        }// WeightedGraph Type

        Class inter[] = new Class[interfaceCount];

        int pos = 0;
        for (pos = 0; pos < contracts.length; pos++)
        {
            inter[pos] = contracts[pos].getInterface();
        }

        if (isWeighted)
        {
            inter[pos] = org.apache.commons.graph.WeightedGraph.class;
            pos++;
        }

        inter[pos] = baseGraphType;

        return Proxy.newProxyInstance(baseGraph.getClass().getClassLoader(),
            inter, baseGraph);
    }

    /**
     * makeDirectedGraph
     *
     * @param contracts - Array of Contracts this Graph should meet.
     * @param isWeighted - If true, the Graph will implement the WeightedGraph
     *      interface.
     * @param graph - If it is provided, the graph will initially be equal to
     *      the graph.
     */
    public DirectedGraph makeDirectedGraph(Contract contracts[],
                                           boolean isWeighted,
                                           DirectedGraph graph)
        throws GraphException
    {
        DirectedGraphImpl dgi = null;

        if (graph != null)
        {
            dgi = new DirectedGraphImpl(graph);
        }
        else
        {
            dgi = new DirectedGraphImpl();
        }

        for (int i = 0; i < contracts.length; i++)
        {
            dgi.addContract(contracts[i]);
        }

        return (DirectedGraph)
            makeGraph(contracts,
            dgi, org.apache.commons.graph.DirectedGraph.class,
            isWeighted);
    }

    /**
     * makeMutableDirectedGraph
     *
     * @param contracts - Array of Contracts this Graph should meet.
     * @param isWeighted - If true, the Graph will implement the WeightedGraph
     *      interface.
     * @param graph - If it is provided, the graph will initially be equal to
     *      the graph.
     */
    public MutableDirectedGraph
        makeMutableDirectedGraph(Contract contracts[],
                                 boolean isWeighted,
                                 DirectedGraph graph)
        throws GraphException
    {

        DirectedGraphImpl dgi = null;

        if (graph != null)
        {
            dgi = new DirectedGraphImpl(graph);
        }
        else
        {
            dgi = new DirectedGraphImpl();
        }

        for (int i = 0; i < contracts.length; i++)
        {
            dgi.addContract(contracts[i]);
        }

        return (MutableDirectedGraph)
            makeGraph(contracts,
            dgi,
            org.apache.commons.graph.MutableDirectedGraph.class,
            isWeighted);
    }
}
