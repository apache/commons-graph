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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;

/**
 * Contains different implementations of Graph visitor algorithms.
 */
public final class Visit
{

    /**
     * Breadth-first search algorithm implementation.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph instance has to be visited
     * @param source the root node the search begins from
     * @return the breadth first search tree
     */
    public static final <V extends Vertex, E extends Edge> Graph<V, E> breadthFirstSearch( Graph<V, E> graph, V source )
    {
        VisitGraphBuilder<V, E> visitGraphBuilder = new VisitGraphBuilder<V, E>();
        breadthFirstSearch( graph, source, visitGraphBuilder );
        return visitGraphBuilder.getVisitGraph();
    }

    /**
     * Breadth-first search algorithm implementation.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph instance has to be visited
     * @param source the root node the search begins from
     * @param handler the handler intercepts visit actions
     */
    public static final <V extends Vertex, E extends Edge> void breadthFirstSearch( Graph<V, E> graph, V source,
                                                                                       GraphVisitHandler<V, E> handler )
    {
        if ( graph == null )
        {
            throw new IllegalArgumentException( "Graph has to be visited can not be null." );
        }
        if ( source == null )
        {
            throw new IllegalArgumentException( "Root node the search begins from can not be null." );
        }
        if ( handler == null )
        {
            throw new IllegalArgumentException( "Graph visitor handler can not be null." );
        }

        handler.discoverGraph( graph );

        Queue<V> vertexQueue = new LinkedList<V>();
        vertexQueue.add( source );

        Set<V> visitedVetices = new HashSet<V>();
        visitedVetices.add( source );

        while ( !vertexQueue.isEmpty() )
        {
            V v = vertexQueue.remove();

            handler.discoverVertex( v );

            Iterable<V> connected = ( graph instanceof DirectedGraph ) ? ( (DirectedGraph<V, E>) graph ).getOutbound( v )
                                                                       : graph.getConnectedVertices( v );
            for ( V w : connected )
            {
                if ( visitedVetices.add( w ) )
                {
                    E e = graph.getEdge( v, w );

                    handler.discoverEdge( v, e, w );

                    vertexQueue.offer( w );

                    handler.finishEdge( v, e, w );
                }
            }

            handler.finishVertex( v );
        }

        handler.finishGraph( graph );
    }

    /**
     * Depth-first search algorithm implementation.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph instance has to be visited
     * @param source the root node the search begins from
     * @return the depth first search tree
     */
    public static final <V extends Vertex, E extends Edge> Graph<V, E> depthFirstSearch( Graph<V, E> graph, V source )
    {
        VisitGraphBuilder<V, E> visitGraphBuilder = new VisitGraphBuilder<V, E>();
        depthFirstSearch( graph, source, visitGraphBuilder );
        return visitGraphBuilder.getVisitGraph();
    }

    /**
     * Depth-first search algorithm implementation.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph instance has to be visited
     * @param source the root node the search begins from
     * @param handler the handler intercepts visit actions
     */
    public static final <V extends Vertex, E extends Edge> void depthFirstSearch( Graph<V, E> graph, V source,
                                                                                     GraphVisitHandler<V, E> handler )
    {
        if ( graph == null )
        {
            throw new IllegalArgumentException( "Graph has to be visited can not be null." );
        }
        if ( source == null )
        {
            throw new IllegalArgumentException( "Root node the search begins from can not be null." );
        }
        if ( handler == null )
        {
            throw new IllegalArgumentException( "Graph visitor handler can not be null." );
        }

        handler.discoverGraph( graph );

        Stack<V> vertexStack = new Stack<V>();
        vertexStack.push( source );

        Set<V> visitedVetices = new HashSet<V>();
        visitedVetices.add( source );

        while ( !vertexStack.isEmpty() )
        {
            V v = vertexStack.pop();

            handler.discoverVertex( v );

            Iterable<V> connected = ( graph instanceof DirectedGraph ) ? ( (DirectedGraph<V, E>) graph ).getOutbound( v )
                            : graph.getConnectedVertices( v );
            for ( V w : connected )
            {
                if ( visitedVetices.add( w ) )
                {
                    E e = graph.getEdge( v, w );

                    handler.discoverEdge( v, e, w );

                    vertexStack.push( w );

                    handler.finishEdge( v, e, w );
                }
            }

            handler.finishVertex( v );
        }

        handler.finishGraph( graph );
    }

    /**
     * Hidden constructor, this class can't be instantiated
     */
    private Visit()
    {
        // do nothing
    }

}
