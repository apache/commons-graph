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
import static org.apache.commons.graph.visit.VisitState.ABORT;
import static org.apache.commons.graph.visit.VisitState.CONTINUE;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.VertexPair;

/**
 * {@link VisitAlgorithmsSelector} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 * @param <G> the Graph type
 */
final class DefaultVisitAlgorithmsSelector<V, E, G extends Graph<V, E>>
    implements VisitAlgorithmsSelector<V, E, G>
{

    /** The graph. */
    private final G graph;

    /** The start vertex for the search. */
    private final V source;

    /**
     * Create a default {@link VisitAlgorithmsSelector} for the given {@link Graph} and start vertex.
     *
     * @param graph the {@link Graph} to be used.
     * @param source the start vertex.
     */
    public DefaultVisitAlgorithmsSelector( final G graph, final V source )
    {
        this.graph = graph;
        this.source = source;
    }

    /**
     * {@inheritDoc}
     */
    public Graph<V, E> applyingBreadthFirstSearch()
    {
        return applyingBreadthFirstSearch( new VisitGraphBuilder<V, E, G>() );
    }

    /**
     * {@inheritDoc}
     */
    public <O> O applyingBreadthFirstSearch( GraphVisitHandler<V, E, G, O> handler )
    {
        return applyingSearch( handler, true );
    }

    /**
     * {@inheritDoc}
     */
    public Graph<V, E> applyingDepthFirstSearch()
    {
        return applyingDepthFirstSearch( new VisitGraphBuilder<V, E, G>() );
    }

    /**
     * {@inheritDoc}
     */
    public <O> O applyingDepthFirstSearch( GraphVisitHandler<V, E, G, O> handler )
    {
        return applyingSearch( handler, false );
    }

    /**
     * A generalized graph search algorithm to be used to implement depth-first and breadth-first searches. Depending on
     * the used collection, the algorithm traverses the graph in a different way:
     * <ul>
     * <li>Queue (FIFO): breadth-first</li>
     * <li>Stack (LIFO): depth-first</li>
     * </ul>
     *
     * @param handler the handler intercepts visits
     * @param enqueue defines the collection behavior used to traverse the graph: true is a Queue, false is a Stack
     * @return the result of {@link GraphVisitHandler#onCompleted()}
     */
    private <O> O applyingSearch( GraphVisitHandler<V, E, G, O> handler, boolean enqueue )
    {
        handler = checkNotNull( handler, "Graph visitor handler can not be null." );

        handler.discoverGraph( graph );

        final LinkedList<VertexPair<V>> vertexList = new LinkedList<VertexPair<V>>();

        vertexList.addLast( new VertexPair<V>( source, source ) );

        final Set<V> visitedVertices = new HashSet<V>();
        visitedVertices.add( source );

        boolean visitingGraph = true;

        while ( visitingGraph && !vertexList.isEmpty() )
        {
            // if dequeue, remove the first element, otherwise the last
            final VertexPair<V> pair = enqueue ? vertexList.removeFirst() : vertexList.removeLast();
            final V v = pair.getHead();
            final V prevHead = pair.getTail();
            final E e = prevHead.equals( v ) ? null : graph.getEdge( prevHead, v );

            boolean skipVertex = false;

            if ( e != null )
            {
                // if the vertex was already visited, do not discover
                // another edge leading to the same vertex
                if ( visitedVertices.contains( v ) )
                {
                    skipVertex = true;
                }
                else
                {
                    VisitState stateAfterEdgeDiscovery = handler.discoverEdge( prevHead, e, v );
                    if ( CONTINUE != stateAfterEdgeDiscovery )
                    {
                        skipVertex = true;
                        if ( ABORT == stateAfterEdgeDiscovery )
                        {
                            visitingGraph = false;
                        }
                    }

                    if ( ABORT == handler.finishEdge( prevHead, e, v ) )
                    {
                        skipVertex = true;
                        visitingGraph = false;
                    }
                }
            }

            // only mark the current vertex as visited, if the
            // edge leading to it should be expanded
            boolean vertexWasDiscovered = false;
            if ( !skipVertex )
            {
                visitedVertices.add( v );
                VisitState stateAfterVertexDiscovery = handler.discoverVertex( v );
                vertexWasDiscovered = true;
                if ( CONTINUE != stateAfterVertexDiscovery )
                {
                    skipVertex = true;
                    if ( ABORT == stateAfterVertexDiscovery )
                    {
                        visitingGraph = false;
                    }
                }
            }

            if ( !skipVertex )
            {
                Iterator<V> connected =
                    ( graph instanceof DirectedGraph ) ? ( (DirectedGraph<V, E>) graph ).getOutbound( v ).iterator()
                                    : graph.getConnectedVertices( v ).iterator();

                while ( connected.hasNext() )
                {
                    V w = connected.next();
                    if ( !visitedVertices.contains( w ) )
                    {
                        vertexList.addLast( new VertexPair<V>( w, v ) );
                    }
                }
            }

            if ( vertexWasDiscovered && ABORT == handler.finishVertex( v ) )
            {
                visitingGraph = false;
            }
        }

        handler.finishGraph( graph );

        return handler.onCompleted();
    }

}
