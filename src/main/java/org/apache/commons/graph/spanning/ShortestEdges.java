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

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.Mapper;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.model.MutableSpanningTree;
import org.apache.commons.graph.weight.OrderedMonoid;

/**
 * The predecessor list is a list of vertex of a {@link org.apache.commons.graph.Graph}.
 * Each vertex' entry contains the index of its predecessor in a path through the graph.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph edges type
 * @param <W> the weight type
 */
final class ShortestEdges<V, WE, W>
    implements Comparator<V>
{

    private final Map<V, WE> predecessors = new HashMap<V, WE>();

    private final OrderedMonoid<W> weightOperations;

    private final Mapper<WE, W> weightedEdges;

    private final Graph<V, WE> graph;

    private final V source;

    public ShortestEdges(final Graph<V, WE> graph, final V source, final OrderedMonoid<W> weightOperations, final Mapper<WE, W> weightedEdges )
    {
        this.graph = graph;
        this.source = source;
        this.weightOperations = weightOperations;
        this.weightedEdges = weightedEdges;
    }

    /**
     * Add an edge in the predecessor list associated to the input vertex.
     *
     * @param tail the predecessor vertex
     * @param head the edge that succeeds to the input vertex
     */
    public void addPredecessor(final V tail, final WE head )
    {
        predecessors.put( tail, head );
    }

    /**
     * Creates a spanning tree using the current data.
     *
     * @return a spanning tree using current data
     */
    public SpanningTree<V, WE, W> createSpanningTree()
    {
        final MutableSpanningTree<V, WE, W> spanningTree = new MutableSpanningTree<V, WE, W>( weightOperations, weightedEdges );

        for ( final WE edge : this.predecessors.values() )
        {
            final VertexPair<V> vertices = graph.getVertices( edge );

            final V head = vertices.getHead();
            final V tail = vertices.getTail();

            addEdgeIgnoringExceptions( head, spanningTree );
            addEdgeIgnoringExceptions( tail, spanningTree );

            spanningTree.addEdge( head, graph.getEdge( head, tail ), tail );
        }

        return spanningTree;
    }

    private static <V, WE, W> void addEdgeIgnoringExceptions(final V vertex, final MutableSpanningTree<V, WE, W> spanningTree )
    {
        try
        {
            spanningTree.addVertex( vertex );
        }
        catch ( final GraphException e )
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
     * Returns the distance related to input vertex, or null if it does not exist.
     *
     * <b>NOTE</b>: the method {@link #hasWeight} should be used first to check if
     * the input vertex has an assiged weight.
     *
     * @param vertex the vertex for which the distance has to be retrieved
     * @return the distance related to input vertex, or null if it does not exist
     */
    public W getWeight(final V vertex )
    {
        if ( source.equals( vertex ) )
        {
            return weightOperations.identity();
        }

        final WE edge = predecessors.get( vertex );

        if ( edge == null )
        {
            return null;
        }

        return weightedEdges.map( edge );
    }

    /**
     * Checks if there is a weight related to the input {@code Vertex}.
     *
     * @param vertex the input {@code Vertex}
     * @return true if there is a weight for the input {@code Vertex}, false otherwise
     */
    public boolean hasWeight(final V vertex )
    {
        return predecessors.containsKey( vertex );
    }

    /**
     * {@inheritDoc}
     */
    public int compare(final V left, final V right )
    {
        if ( !hasWeight( left ) && !hasWeight( right ) )
        {
            return 0;
        }
        else if ( !hasWeight( left ) )
        {
            return 1;
        }
        else if ( !hasWeight( right ) )
        {
            return -1;
        }
        return weightOperations.compare( getWeight( left ), getWeight( right ) );
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
