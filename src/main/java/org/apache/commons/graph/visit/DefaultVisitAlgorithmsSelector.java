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
import org.apache.commons.graph.VertexPair;

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
        return applyingBreadthFirstSearch( new VisitGraphBuilder<V, E, G>() );
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
    public <O> O applyingBreadthFirstSearch( GraphVisitHandler<V, E, G, O> handler )
    {
        return applyingSearch( handler, new QueueOrStack<V>( true ) );
    }

    /**
     * {@inheritDoc}
     */
    public <O> O applyingDepthFirstSearch( GraphVisitHandler<V, E, G, O> handler )
    {
        return applyingSearch( handler, new QueueOrStack<V>( false ) );
    }

    /**
     * A generalized graph search algorithm to be used to implement depth-first and
     * breadth-first searches. Depending on the used collection, the algorithm traverses
     * the graph in a different way:
     *
     * <ul>
     *  <li>Queue (FIFO): breadth-first</li>
     *  <li>Stack (LIFO): depth-first</li>
     * </ul>
     *
     * @param handler the handler intercepts visits
     * @param vertexList the collection used to traverse the graph
     * @return the result of {@link GraphVisitHandler#onCompleted()}
     */
    private <O> O applyingSearch( GraphVisitHandler<V, E, G, O> handler, QueueOrStack<V> vertexList )
    {
        handler = checkNotNull( handler, "Graph visitor handler can not be null." );

        handler.discoverGraph( graph );

        vertexList.push( new VertexPair<V>( source, source ) );

        final Set<V> visitedVertices = new HashSet<V>();
        visitedVertices.add( source );

        boolean visitingGraph = true;

        while ( visitingGraph && !vertexList.isEmpty() )
        {
            final VertexPair<V> pair = vertexList.pop();
            final V v = pair.getHead();
            final V prevHead = pair.getTail();
            final E e = prevHead.equals( v ) ? null : graph.getEdge( prevHead, v );

            boolean skipVertex = false;

            if ( e != null )
            {
                if ( !handler.discoverEdge( prevHead, e, v ) )
                {
                    skipVertex = true;
                }

                if ( handler.finishEdge( prevHead, e, v ) )
                {
                    skipVertex = true;
                    visitingGraph = false;
                }
            }

            if ( !skipVertex && handler.discoverVertex( v ) )
            {
                Iterator<V> connected = ( graph instanceof DirectedGraph ) ?
                        ( (DirectedGraph<V, E>) graph ).getOutbound( v ).iterator() :
                            graph.getConnectedVertices( v ).iterator();

                while ( connected.hasNext() )
                {
                    V w = connected.next();
                    if ( !visitedVertices.contains( w ) )
                    {
                        vertexList.push( new VertexPair<V>( w, v ) );
                        visitedVertices.add( w );
                    }
                }
            }

            if ( !skipVertex && handler.finishVertex( v ) )
            {
                visitingGraph = false;
            }
        }

        handler.finishGraph( graph );

        return handler.onCompleted();
    }

    /**
     * A wrapper class around a {@link LinkedList} to provide a common
     * interface for {@link Stack} or {@link Queue} implementations. The class
     * is used to support a common algorithm for depth-first and breadth-first
     * search, by switching from queue (FIFO) to stack (LIFO) behavior.
     *
     * @param <V> the Graph vertices type
     */
    private static class QueueOrStack<V extends Vertex>
    {
        /** indicated the collection behavior. */
        private boolean isQueue;

        /** the underlying linked list implementation. */
        private final LinkedList<VertexPair<V>> list;

        /**
         * Create a new {@link QueueOrStack} instance with the desired
         * behavior, indicated by <code>isQueue</code>.
         *
         * @param isQueue if <code>true</code> the collection behaves as a FIFO queue,
         * otherwise as a LIFO stack.
         */
        public QueueOrStack( final boolean isQueue )
        {
            this.isQueue = isQueue;
            this.list = new LinkedList<VertexPair<V>>();
        }

        /**
         * Add an element to the collection.
         *
         * @param element the element to be added
         */
        public void push( VertexPair<V> element )
        {
            list.addLast( element );
        }

        /**
         * Removes and returns the element from the collection according to the
         * defined behavior (LIFO vs. FIFO).
         * @return the next element
         */
        public VertexPair<V> pop()
        {
            return isQueue ? list.removeFirst() : list.removeLast();
        }

        /**
         * Returns <code>true</code> if the collection contains no elements.
         * @return <code>true</code> if the collection is empty, <code>false</code> otherwise
         */
        public boolean isEmpty()
        {
            return list.isEmpty();
        }
    }

}
