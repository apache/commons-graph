package org.apache.commons.graph.decorator;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.algorithm.path.AllPairsShortestPath;
import org.apache.commons.graph.algorithm.spanning.MinimumSpanningForest;
import org.apache.commons.graph.domain.basic.DirectedGraphImpl;
import org.apache.commons.graph.domain.basic.DirectedGraphWrapper;
import org.apache.commons.graph.exception.GraphException;

/**
 * Description of the Class
 */
public class DDirectedGraph
     extends DirectedGraphWrapper
     implements DirectedGraph,
    WeightedGraph
{
    private WeightedGraph weighted;
    private Map weights = new HashMap();// EDGE X DOUBLE
    private static Map decoratedGraphs = new HashMap();// DGRAPH X DDGRAPH
    private AllPairsShortestPath allPaths = null;

  protected DDirectedGraph() {
    super();
  }

    /**
     * Constructor for the DDirectedGraph object
     *
     * @param impl
     */
    protected DDirectedGraph(DirectedGraph impl)
    {
        super(impl);

        if (impl instanceof WeightedGraph)
        {
            weighted = (WeightedGraph) impl;
        }
    }

    /**
     * Description of the Method
     */
    public static DDirectedGraph decorateGraph(DirectedGraph graph)
    {
        if (graph instanceof DDirectedGraph)
        {
            return (DDirectedGraph) graph;
        }

        if (decoratedGraphs.containsKey(graph))
        {
            return (DDirectedGraph) decoratedGraphs.get(graph);
        }

        DDirectedGraph RC = new DDirectedGraph(graph);
        decoratedGraphs.put(graph, RC);
        return RC;
    }

    // WeightedGraph Implementation
    /**
     * Gets the weight attribute of the DDirectedGraph object
     */
    public double getWeight(Edge e)
    {
        if (weighted != null)
        {
            return weighted.getWeight(e);
        }
        else
        {
            if (weights.containsKey(e))
            {
                return ((Double) weights.get(e)).doubleValue();
            }
            else
            {
                return 1.0;
            }
        }
    }

    /**
     * Sets the weight attribute of the DDirectedGraph object
     */
    public void setWeight(Edge e, double value)
        throws GraphException
    {
        if (weighted != null)
        {
            throw new GraphException("Unable to set weight.");
        }

        weights.put(e, new Double(value));
    }

    /**
     * Description of the Method
     */
    public DirectedGraph transpose()
        throws GraphException
    {
        try
        {
            DirectedGraphImpl RC = new DirectedGraphImpl();
            Set vertexSet = getVertices();
            Set edgeSet = getEdges();

            Iterator vertices = vertexSet.iterator();
            while (vertices.hasNext())
            {
                RC.addVertex((Vertex) vertices.next());
            }

            Iterator edges = edgeSet.iterator();
            while (edges.hasNext())
            {
                Edge edge = (Edge) edges.next();

                RC.addEdge(edge,
                    getTarget(edge),
                    getSource(edge));
            }

            return RC;
        }
        catch (GraphException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new GraphException(e);
        }
    }

    /**
     * Description of the Method
     */
    public boolean hasConnection(Vertex start, Vertex end)
        throws GraphException
    {
        if (start == end)
        {
            return true;
        }

        try
        {
            if (allPaths == null)
            {
                allPaths = new AllPairsShortestPath(this);
            }
            else
            {
                allPaths.update(this);
            }

            WeightedPath path =
                allPaths.getShortestPath(start, end);
        }
        catch (GraphException ex)
        {
            return false;
        }

        return true;
    }

  public MinimumSpanningForest minimumSpanningForest() {
    return new MinimumSpanningForest( this );
  }

  public MinimumSpanningForest maximumSpanningForest() {
    return new MinimumSpanningForest( false, this );
  }

}




