package org.apache.commons.graph.domain.basic;

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

import static java.util.Collections.unmodifiableSet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.MutableDirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.contract.Contract;

/**
 * Description of the Class
 */
public class DirectedGraphImpl<V extends Vertex, WE extends WeightedEdge>
     implements DirectedGraph<V, WE>,
		WeightedGraph<V, WE>,
		MutableDirectedGraph<V, WE>,
		InvocationHandler
{
    private Vertex root = null;

    private Set<V> vertices = new HashSet<V>();
    private Set<WE> edges = new HashSet<WE>();
    private List<Contract> contracts = new ArrayList<Contract>();

    private Map<V, Set<WE>> inbound = new HashMap<V, Set<WE>>();// VERTEX X SET( EDGE )
    private Map<V, Set<WE>> outbound = new HashMap<V, Set<WE>>();// - " " -

    private Map<WE, V> edgeSource = new HashMap<WE, V>();// EDGE X VERTEX
    private Map<WE, V> edgeTarget = new HashMap<WE, V>();// EDGE X TARGET

    private Map<WE, Number> edgeWeights = new HashMap<WE, Number>();// EDGE X WEIGHT

    /**
     * Constructor for the DirectedGraphImpl object
     */
    public DirectedGraphImpl() { }

    /**
     * Constructor for the DirectedGraphImpl object
     *
     * @param dg
     */
    public DirectedGraphImpl(DirectedGraph<V, WE> dg)
    {

        Iterator<V> v = dg.getVertices().iterator();
        while (v.hasNext())
        {
            addVertexI(v.next());
        }

        Iterator<WE> e = dg.getEdges().iterator();
        while (e.hasNext())
        {
            WE edge = e.next();
            addEdgeI(edge,
                dg.getSource(edge),
                dg.getTarget(edge));

            if (dg instanceof WeightedGraph)
            {
                @SuppressWarnings( "unchecked" ) // it is a DirectedGraph<V, WE>
                WeightedGraph<V, WE> weightedGraph = (WeightedGraph<V, WE>) dg;
                setWeight(edge, weightedGraph.getWeight(edge));
            }
        }
    }

    /**
     * Adds a feature to the Contract attribute of the DirectedGraphImpl object
     */
    public void addContract(Contract c)
        throws GraphException
    {
        c.setImpl(this);
        c.verify();
        contracts.add(c);
    }

    /**
     * Description of the Method
     */
    public void removeContract(Contract c)
    {
        contracts.remove(c);
    }

    /**
     * Sets the weight attribute of the DirectedGraphImpl object
     */
    public void setWeight(WE e, Number value)
    {
        if (edgeWeights.containsKey(e))
        {
            edgeWeights.remove(e);
        }
        edgeWeights.put(e, value);
    }

    // Interface Methods
    // Graph
    /**
     * Gets the vertices attribute of the DirectedGraphImpl object
     */
    public Set<V> getVertices()
    {
        return unmodifiableSet(vertices);
    }

    /**
     * Gets the vertices attribute of the DirectedGraphImpl object
     */
    public Set<V> getVertices(WE e)
    {
        Set<V> RC = new HashSet<V>();
        if (edgeSource.containsKey(e))
        {
            RC.add(edgeSource.get(e));
        }

        if (edgeTarget.containsKey(e))
        {
            RC.add(edgeTarget.get(e));
        }

        return RC;
    }

    /**
     * Gets the edges attribute of the DirectedGraphImpl object
     */
    public Set<WE> getEdges()
    {
        return unmodifiableSet(edges);
    }

    /**
     * Gets the edges attribute of the DirectedGraphImpl object
     */
    public Set<WE> getEdges(V v)
    {
        Set<WE> RC = new HashSet<WE>();
        if (inbound.containsKey(v))
        {
            RC.addAll(inbound.get(v));
        }

        if (outbound.containsKey(v))
        {
            RC.addAll(outbound.get(v));
        }

        return RC;
    }

    // Directed Graph
    /**
     * Gets the source attribute of the DirectedGraphImpl object
     */
    public V getSource(WE e)
    {
        return edgeSource.get(e);
    }

    /**
     * Gets the target attribute of the DirectedGraphImpl object
     */
    public V getTarget(WE e)
    {
        return edgeTarget.get(e);
    }

    /**
     * Gets the inbound attribute of the DirectedGraphImpl object
     */
    public Set<WE> getInbound(Vertex v)
    {
        if (inbound.containsKey(v))
        {
            return unmodifiableSet(inbound.get(v));
        }
        return new HashSet<WE>();
    }

    /**
     * Gets the outbound attribute of the DirectedGraphImpl object
     */
    public Set<WE> getOutbound(Vertex v)
    {
        if (outbound.containsKey(v))
        {
            return unmodifiableSet(outbound.get(v));
        }
        return new HashSet<WE>();
    }


    // MutableDirectedGraph
    /**
     * Adds a feature to the VertexI attribute of the DirectedGraphImpl object
     */
    private void addVertexI(V v)
        throws GraphException
    {
	if (root == null) root = v;

        vertices.add(v);
    }

    /**
     * Adds a feature to the Vertex attribute of the DirectedGraphImpl object
     */
    public void addVertex(V v)
        throws GraphException
    {
        Iterator<Contract> conts = contracts.iterator();
        while (conts.hasNext())
        {
            conts.next().addVertex(v);
        }
        addVertexI(v);
    }

    /**
     * Description of the Method
     */
    private void removeVertexI(Vertex v)
        throws GraphException
    {
        try
        {
            vertices.remove(v);
        }
        catch (Exception ex)
        {
            throw new GraphException(ex);
        }
    }

    /**
     * Description of the Method
     */
    public void removeVertex(Vertex v)
        throws GraphException
    {
        Iterator<Contract> conts = contracts.iterator();
        while (conts.hasNext())
        {
            conts.next().removeVertex(v);
        }

        removeVertexI(v);
    }


    /**
     * Adds a feature to the EdgeI attribute of the DirectedGraphImpl object
     */
    private void addEdgeI(WE e, V start, V end)
        throws GraphException
    {
        edges.add(e);

        edgeWeights.put(e, e.getWeight());

        edgeSource.put(e, start);
        edgeTarget.put(e, end);

        if (!outbound.containsKey(start))
        {
            Set<WE> edgeSet = new HashSet<WE>();
            edgeSet.add(e);

            outbound.put(start, edgeSet);
        }
        else
        {
            outbound.get(start).add(e);
        }

        if (!inbound.containsKey(end))
        {
            Set<WE> edgeSet = new HashSet<WE>();
            edgeSet.add(e);

            inbound.put(end, edgeSet);
        }
        else
        {
            inbound.get(end).add(e);
        }
    }

    /**
     * Adds a feature to the Edge attribute of the DirectedGraphImpl object
     */
    public void addEdge(WE e,
                        V start,
                        V end)
        throws GraphException
    {
      Iterator<Contract> conts = contracts.iterator();
      while (conts.hasNext())
	{
	  Contract cont = conts.next();

	  cont.addEdge(e, start, end);
	}
      
      addEdgeI(e, start, end);
    }


    /**
     * Description of the Method
     */
    private void removeEdgeI(Edge e)
        throws GraphException
    {
        try
        {
            Set<WE> edgeSet = null;

            V source = edgeSource.get(e);
            edgeSource.remove(e);
            edgeSet = outbound.get(source);
            edgeSet.remove(e);

            V target = edgeTarget.get(e);
            edgeTarget.remove(e);
            edgeSet = inbound.get(target);
            edgeSet.remove(e);

            if (edgeWeights.containsKey(e))
            {
                edgeWeights.remove(e);
            }
        }
        catch (Exception ex)
        {
            throw new GraphException(ex);
        }
    }

    /**
     * Description of the Method
     */
    public void removeEdge(WE e)
        throws GraphException
    {
        Iterator<Contract> conts = contracts.iterator();
        while (conts.hasNext())
        {
            conts.next().removeEdge(e);
        }
        removeEdgeI(e);
    }

    // WeightedGraph
    /**
     * Gets the weight attribute of the DirectedGraphImpl object
     */
    public Number getWeight(WE e)
    {
        if (edgeWeights.containsKey(e))
        {
            return edgeWeights.get(e);
        }
        return 1.0;
    }

    /**
     * Description of the Method
     */
    public Object invoke(Object proxy,
                         Method method,
                         Object args[])
        throws Throwable
    {
      try {
	return method.invoke(this, args);
      } catch (InvocationTargetException ex) {
	throw ex.getTargetException();
      }
    }

}
