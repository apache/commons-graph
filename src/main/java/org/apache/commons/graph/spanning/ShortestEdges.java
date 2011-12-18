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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.model.MutableSpanningTree;

/**
 * The predecessor list is a list of {@link Vertex} of a {@link org.apache.commons.graph.Graph}.
 * Each {@link Vertex}' entry contains the index of its predecessor in a path through the graph.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
final class ShortestEdges<V extends Vertex, WE extends WeightedEdge<Double>>
    implements Comparator<V>
{

    private final Map<V, WE> predecessors = new HashMap<V, WE>();

    private final Graph<V, WE> graph;

    private final V source;

    public ShortestEdges(Graph<V, WE> graph, V source )
    {
        this.graph = graph;
        this.source = source;
    }

    /**
     * Add an {@link Edge} in the predecessor list associated to the input {@link Vertex}.
     *
     * @param tail the predecessor vertex
     * @param head the edge that succeeds to the input vertex
     */
    public void addPredecessor( V tail, WE head )
    {
        predecessors.put( tail, head );
    }

    /**
     * 
     *
     * @return
     */
    public SpanningTree<V, WE> createSpanningTree()
    {
        MutableSpanningTree<V, WE> spanningTree = new MutableSpanningTree<V, WE>();

        for ( WE edge : this.predecessors.values() )
        {
            VertexPair<V> vertices = graph.getVertices( edge );

            V head = vertices.getHead();
            V tail = vertices.getTail();

            addEdgeIgnoringExceptions( head, spanningTree );
            addEdgeIgnoringExceptions( tail, spanningTree );

            spanningTree.addEdge( head, graph.getEdge( head, tail ), tail );
        }

        return spanningTree;
    }

    private static <V extends Vertex, WE extends WeightedEdge<Double>> void addEdgeIgnoringExceptions( V vertex,
                                                                                               MutableSpanningTree<V, WE> spanningTree )
    {
        try
        {
            spanningTree.addVertex( vertex );
        }
        catch ( GraphException e )
        {
            // just swallow it
        }
    }

    /**
     * Checks the predecessor list has no elements.
     *
     * @return true, if the predecessor list has no elements, false otherwise.
     */
    public boolean isEmpty()
    {
        return predecessors.isEmpty();
    }

    /**
     * Returns the distance related to input vertex, or {@code INFINITY} if it wasn't previously visited.
     *
     * @param vertex the vertex for which the distance has to be retrieved
     * @return the distance related to input vertex, or {@code INFINITY} if it wasn't previously visited.
     */
    public Double getWeight( V vertex )
    {
        if ( source.equals( vertex ) )
        {
            return 0D;
        }

        WE edge = predecessors.get( vertex );

        if ( edge == null )
        {
            return Double.POSITIVE_INFINITY;
        }

        return edge.getWeight();
    }

    /**
     * {@inheritDoc}
     */
    public int compare( V left, V right )
    {
        return getWeight( left ).compareTo( getWeight( right ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return predecessors.toString();
    }

}
