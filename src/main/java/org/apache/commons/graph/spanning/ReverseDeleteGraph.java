package org.apache.commons.graph.spanning;

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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;

/**
 * @param <V>
 * @param <WE>
 * @param <W>
 */
final class ReverseDeleteGraph<V extends Vertex, WE extends WeightedEdge<W>, W>
    implements WeightedGraph<V, WE, W>
{

    private static final long serialVersionUID = -543197749473412325L;

    private final Graph<V, WE> graph;

    private final List<WE> sortedEdge;

    private final List<WE> visitedEdge;

    public ReverseDeleteGraph( Graph<V, WE> graph, List<WE> sortedEdge, List<WE> visitedEdge )
    {
        this.graph = graph;
        this.sortedEdge = sortedEdge;
        this.visitedEdge = visitedEdge;
    }

    public Iterable<V> getVertices()
    {
        return graph.getVertices();
    }

    public int getOrder()
    {
        return graph.getOrder();
    }

    public Iterable<WE> getEdges()
    {
        List<WE> e = new ArrayList<WE>();
        e.addAll( sortedEdge );
        e.addAll( visitedEdge );
        return e;
    }

    public int getSize()
    {
        return sortedEdge.size() + visitedEdge.size();
    }

    public int getDegree( V v )
    {
        throw new GraphException( "Unused Method" );
    }

    public Iterable<V> getConnectedVertices( V v )
    {

        List<V> tmp = new ArrayList<V>();
        for ( V originalVertex : graph.getConnectedVertices( v ) )
        {
            if ( getEdge( v, originalVertex ) != null )
            {
                tmp.add( originalVertex );
            }
        }

        return tmp;
    }

    public WE getEdge( V source, V target )
    {
        WE edge = graph.getEdge( source, target );
        if ( sortedEdge.contains( edge ) )
        {
            return edge;
        }

        if ( visitedEdge.contains( edge ) )
        {
            return edge;
        }
        return null;
    }

    public VertexPair<V> getVertices( WE e )
    {
        for ( WE edge : sortedEdge )
        {
            if ( edge.equals( e ) )
            {
                return graph.getVertices( e );

            }
        }

        if ( sortedEdge.contains( e ) )
        {
            return graph.getVertices( e );
        }

        if ( visitedEdge.contains( e ) )
        {
            return graph.getVertices( e );
        }
        return null;
    }

    public boolean containsVertex( V v )
    {
        return graph.containsVertex( v );
    }
    
    public boolean containsEdge( WE e )
    {
        return graph.containsEdge( e );
    }

}
