package org.apache.commons.graph.decorator;

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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.algorithm.path.AllPairsShortestPath;
import org.apache.commons.graph.algorithm.spanning.MinimumSpanningForest;
import org.apache.commons.graph.domain.basic.DirectedGraphImpl;
import org.apache.commons.graph.domain.basic.DirectedGraphWrapper;

/**
 * Description of the Class
 */
public class DDirectedGraph<V extends Vertex, WE extends WeightedEdge>
    extends DirectedGraphWrapper<V, WE>
    implements DirectedGraph<V, WE>, WeightedGraph<V, WE>
{

    private static final Map decoratedGraphs = new HashMap();// DGRAPH X DDGRAPH

    /**
     * Description of the Method
     */
    public static <V extends Vertex, WE extends WeightedEdge> DDirectedGraph<V, WE> decorateGraph( DirectedGraph<V, WE> graph )
    {
        if ( graph instanceof DDirectedGraph )
        {
            return (DDirectedGraph<V, WE>) graph;
        }

        if ( decoratedGraphs.containsKey( graph ) )
        {
            return (DDirectedGraph<V, WE>) decoratedGraphs.get( graph );
        }

        DDirectedGraph<V, WE> RC = new DDirectedGraph<V, WE>( graph );
        decoratedGraphs.put( graph, RC );
        return RC;
    }

    private final WeightedGraph<V, WE> weighted;

    private Map<WE, Number> weights = new HashMap<WE, Number>();// EDGE X DOUBLE

    private AllPairsShortestPath allPaths = null;

    /**
     * Constructor for the DDirectedGraph object
     *
     * @param impl
     */
    protected DDirectedGraph( DirectedGraph<V, WE> impl )
    {
        super( impl );

        if ( impl instanceof WeightedGraph )
        {
            weighted = (WeightedGraph<V, WE>) impl;
        }
        else
        {
            weighted = null;
        }
    }

    // WeightedGraph Implementation
    /**
     * Gets the weight attribute of the DDirectedGraph object
     */
    public Number getWeight( WE e )
    {
        if ( weighted != null )
        {
            return weighted.getWeight( e );
        }
        if ( weights.containsKey( e ) )
        {
            return weights.get( e );
        }
        return 1.0;
    }

    /**
     * Sets the weight attribute of the DDirectedGraph object
     */
    public void setWeight( WE e, Number value )
        throws GraphException
    {
        if ( weighted != null )
        {
            throw new GraphException( "Unable to set weight." );
        }

        weights.put( e, value );
    }

    /**
     * Description of the Method
     */
    public DirectedGraph<V, WE> transpose()
        throws GraphException
    {
        try
        {
            DirectedGraphImpl<V, WE> directedGraph = new DirectedGraphImpl<V, WE>();
            Set<V> vertexSet = getVertices();
            Set<WE> edgeSet = getEdges();

            Iterator<V> vertices = vertexSet.iterator();
            while ( vertices.hasNext() )
            {
                directedGraph.addVertex( vertices.next() );
            }

            Iterator<WE> edges = edgeSet.iterator();
            while ( edges.hasNext() )
            {
                WE edge = edges.next();

                directedGraph.addEdge( edge, getTarget( edge ), getSource( edge ) );
            }

            return directedGraph;
        }
        catch ( GraphException e )
        {
            throw e;
        }
        catch ( Exception e )
        {
            throw new GraphException( e );
        }
    }

    /**
     * Description of the Method
     */
    public boolean hasConnection( Vertex start, Vertex end )
        throws GraphException
    {
        if ( start == end )
        {
            return true;
        }

        try
        {
            if ( allPaths == null )
            {
                allPaths = new AllPairsShortestPath( this );
            }
            else
            {
                allPaths.update( this );
            }

            WeightedPath<V, WE> path = allPaths.getShortestPath( start, end );
        }
        catch ( GraphException ex )
        {
            return false;
        }

        return true;
    }

    public MinimumSpanningForest minimumSpanningForest()
    {
        return new MinimumSpanningForest( this );
    }

    public MinimumSpanningForest maximumSpanningForest()
    {
        return new MinimumSpanningForest( false, this );
    }

}
