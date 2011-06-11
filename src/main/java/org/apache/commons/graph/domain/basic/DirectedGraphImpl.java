package org.apache.commons.graph.domain.basic;

/*
 * Copyright 2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.lang.reflect.*;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

import org.apache.commons.graph.*;
import org.apache.commons.graph.contract.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class DirectedGraphImpl
     implements DirectedGraph,
		WeightedGraph,
		MutableDirectedGraph,
		InvocationHandler
{
    private Vertex root = null;

    private Set vertices = new HashSet();
    private Set edges = new HashSet();
    private List contracts = new ArrayList();

    private Map inbound = new HashMap();// VERTEX X SET( EDGE )
    private Map outbound = new HashMap();// - " " -

    private Map edgeSource = new HashMap();// EDGE X VERTEX
    private Map edgeTarget = new HashMap();// EDGE X TARGET

    private Map edgeWeights = new HashMap();// EDGE X WEIGHT

    /**
     * Constructor for the DirectedGraphImpl object
     */
    public DirectedGraphImpl() { }

    /**
     * Constructor for the DirectedGraphImpl object
     *
     * @param dg
     */
    public DirectedGraphImpl(DirectedGraph dg)
    {

        Iterator v = dg.getVertices().iterator();
        while (v.hasNext())
        {
            addVertexI((Vertex) v.next());
        }

        Iterator e = dg.getEdges().iterator();
        while (e.hasNext())
        {
            Edge edge = (Edge) e.next();
            addEdgeI(edge,
                dg.getSource(edge),
                dg.getTarget(edge));

            if (dg instanceof WeightedGraph)
            {
                setWeight(edge, ((WeightedGraph) dg).getWeight(edge));
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
    public void setWeight(Edge e, double value)
    {
        if (edgeWeights.containsKey(e))
        {
            edgeWeights.remove(e);
        }
        edgeWeights.put(e, new Double(value));
    }

    // Interface Methods
    // Graph
    /**
     * Gets the vertices attribute of the DirectedGraphImpl object
     */
    public Set getVertices()
    {
        return new HashSet(vertices);
    }

    /**
     * Gets the vertices attribute of the DirectedGraphImpl object
     */
    public Set getVertices(Edge e)
    {
        Set RC = new HashSet();
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
    public Set getEdges()
    {
        return new HashSet(edges);
    }

    /**
     * Gets the edges attribute of the DirectedGraphImpl object
     */
    public Set getEdges(Vertex v)
    {
        Set RC = new HashSet();
        if (inbound.containsKey(v))
        {
            RC.addAll((Set) inbound.get(v));
        }

        if (outbound.containsKey(v))
        {
            RC.addAll((Set) outbound.get(v));
        }

        return RC;
    }

    // Directed Graph
    /**
     * Gets the source attribute of the DirectedGraphImpl object
     */
    public Vertex getSource(Edge e)
    {
        return (Vertex) edgeSource.get(e);
    }

    /**
     * Gets the target attribute of the DirectedGraphImpl object
     */
    public Vertex getTarget(Edge e)
    {
        return (Vertex) edgeTarget.get(e);
    }

    /**
     * Gets the inbound attribute of the DirectedGraphImpl object
     */
    public Set getInbound(Vertex v)
    {
        if (inbound.containsKey(v))
        {
            return new HashSet((Set) inbound.get(v));
        }
        else
        {
            return new HashSet();
        }
    }

    /**
     * Gets the outbound attribute of the DirectedGraphImpl object
     */
    public Set getOutbound(Vertex v)
    {
        if (outbound.containsKey(v))
        {
            return new HashSet((Set) outbound.get(v));
        }
        else
        {
            return new HashSet();
        }
    }


    // MutableDirectedGraph
    /**
     * Adds a feature to the VertexI attribute of the DirectedGraphImpl object
     */
    private void addVertexI(Vertex v)
        throws GraphException
    {
	if (root == null) root = v;

        vertices.add(v);
    }

    /**
     * Adds a feature to the Vertex attribute of the DirectedGraphImpl object
     */
    public void addVertex(Vertex v)
        throws GraphException
    {
        Iterator conts = contracts.iterator();
        while (conts.hasNext())
        {
            ((Contract) conts.next()).addVertex(v);
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
        Iterator conts = contracts.iterator();
        while (conts.hasNext())
        {
            ((Contract) conts.next()).removeVertex(v);
        }

        removeVertexI(v);
    }


    /**
     * Adds a feature to the EdgeI attribute of the DirectedGraphImpl object
     */
    private void addEdgeI(Edge e, Vertex start, Vertex end)
        throws GraphException
    {
        edges.add(e);

        if (e instanceof WeightedEdge)
        {
            edgeWeights.put(e, new Double(((WeightedEdge) e).getWeight()));
        }
        else
        {
            edgeWeights.put(e, new Double(1.0));
        }

        edgeSource.put(e, start);
        edgeTarget.put(e, end);

        if (!outbound.containsKey(start))
        {
            Set edgeSet = new HashSet();
            edgeSet.add(e);

            outbound.put(start, edgeSet);
        }
        else
        {
            ((Set) outbound.get(start)).add(e);
        }

        if (!inbound.containsKey(end))
        {
            Set edgeSet = new HashSet();
            edgeSet.add(e);

            inbound.put(end, edgeSet);
        }
        else
        {
            ((Set) inbound.get(end)).add(e);
        }
    }

    /**
     * Adds a feature to the Edge attribute of the DirectedGraphImpl object
     */
    public void addEdge(Edge e,
                        Vertex start,
                        Vertex end)
        throws GraphException
    {
      Iterator conts = contracts.iterator();
      while (conts.hasNext())
	{
	  Contract cont = (Contract) conts.next();

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
            Set edgeSet = null;

            Vertex source = (Vertex) edgeSource.get(e);
            edgeSource.remove(e);
            edgeSet = (Set) outbound.get(source);
            edgeSet.remove(e);

            Vertex target = (Vertex) edgeTarget.get(e);
            edgeTarget.remove(e);
            edgeSet = (Set) inbound.get(target);
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
    public void removeEdge(Edge e)
        throws GraphException
    {
        Iterator conts = contracts.iterator();
        while (conts.hasNext())
        {
            ((Contract) conts.next()).removeEdge(e);
        }
        removeEdgeI(e);
    }

    // WeightedGraph
    /**
     * Gets the weight attribute of the DirectedGraphImpl object
     */
    public double getWeight(Edge e)
    {
        if (edgeWeights.containsKey(e))
        {
            return ((Double) edgeWeights.get(e)).doubleValue();
        }
        else
        {
            return 1.0;
        }
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
