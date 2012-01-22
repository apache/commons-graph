package org.apache.commons.graph.visit;

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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;

/**
 * {@link VisitAlgorithmsSelector} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 * @param <G> the Graph type
 */
final class DefaultVisitAlgorithmsSelector<V extends Vertex, E extends Edge, G extends Graph<V, E>>
    implements VisitAlgorithmsSelector<V, E, G>
{

    private final G graph;

    private final V source;

    public DefaultVisitAlgorithmsSelector( G graph, V source )
    {
        this.graph = graph;
        this.source = source;
    }

    /**
     * {@inheritDoc}
     */
    public Graph<V, E> applyingBreadthFirstSearch()
    {
        VisitGraphBuilder<V, E> visitGraphBuilder = new VisitGraphBuilder<V, E>();
        applyingBreadthFirstSearch( visitGraphBuilder );
        return visitGraphBuilder.getVisitGraph();
    }

    /**
     * {@inheritDoc}
     */
    public Graph<V, E> applyingDepthFirstSearch()
    {
        VisitGraphBuilder<V, E> visitGraphBuilder = new VisitGraphBuilder<V, E>();
        applyingDepthFirstSearch( visitGraphBuilder );
        return visitGraphBuilder.getVisitGraph();
    }

    /**
     * {@inheritDoc}
     */
    public void applyingBreadthFirstSearch( GraphVisitHandler<V, E> handler )
    {
        handler = checkNotNull( handler, "Graph visitor handler can not be null." );

        handler.discoverGraph( graph );

        Queue<V> vertexQueue = new LinkedList<V>();
        vertexQueue.add( source );

        Set<V> visitedVertices = new HashSet<V>();
        visitedVertices.add( source );

        boolean visitingGraph = true;

        while ( visitingGraph && !vertexQueue.isEmpty() )
        {
            V v = vertexQueue.remove();

            if ( handler.discoverVertex( v ) ) {

                Iterator<V> connected = ( graph instanceof DirectedGraph ) ? ( (DirectedGraph<V, E>) graph ).getOutbound( v ).iterator()
                                : graph.getConnectedVertices( v ).iterator();

                while ( connected.hasNext() )
                {
                    V w = connected.next();
                    if ( visitedVertices.add( w ) )
                    {
                        E e = graph.getEdge( v, w );

                        if ( handler.discoverEdge( v, e, w ) )
                        {
                            vertexQueue.offer( w );
                        }

                        if ( handler.finishEdge( v, e, w ) )
                        {
                            visitingGraph = false;
                        }
                    }
                }

            }

            if ( handler.finishVertex( v ) )
            {
                visitingGraph = false;
            }
        }

        handler.finishGraph( graph );
    }

    /**
     * {@inheritDoc}
     */
    public void applyingDepthFirstSearch( GraphVisitHandler<V, E> handler )
    {
        handler = checkNotNull( handler, "Graph visitor handler can not be null." );

        handler.discoverGraph( graph );

        Stack<V> vertexStack = new Stack<V>();
        vertexStack.push( source );

        Set<V> visitedVertices = new HashSet<V>();
        visitedVertices.add( source );

        boolean visitingGraph = true;

        while ( visitingGraph && !vertexStack.isEmpty() )
        {
            V v = vertexStack.pop();

            if ( handler.discoverVertex( v ) )
            {
                Iterator<V> connected = ( graph instanceof DirectedGraph ) ? ( (DirectedGraph<V, E>) graph ).getOutbound( v ).iterator()
                                : graph.getConnectedVertices( v ).iterator();

                while ( connected.hasNext() )
                {
                    V w = connected.next();
                    if ( visitedVertices.add( w ) )
                    {
                        E e = graph.getEdge( v, w );

                        if ( handler.discoverEdge( v, e, w ) )
                        {
                            vertexStack.push( w );
                        }

                        if ( handler.finishEdge( v, e, w ) ) {
                            visitingGraph = false;
                        }
                    }
                }
            }

            if ( handler.finishVertex( v ) )
            {
                visitingGraph = false;
            }
        }

        handler.finishGraph( graph );
    }

}
