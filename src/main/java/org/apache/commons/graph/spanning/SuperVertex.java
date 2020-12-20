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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.VertexPair;

/**
 * A {@link SuperVertex} is a collection of vertex objects and is only
 * used internally by Boruvka's algorithm to find a minimum spanning tree.
 *
 * @param <V>  the Graph vertices type
 * @param <W>  the weight type
 * @param <WE> the Graph weighted edges type
 */
class SuperVertex<V, W, WE>
    implements Iterable<V>
{

    /** The reference to the graph. */
    private final Graph<V, WE> graph;

    /** The set of vertices combined in this {@link SuperVertex}. */
    private final Set<V> vertices;

    /** The ordered set of edges to other {@link SuperVertex} objects. */
    private final TreeSet<WE> orderedEdges;

    /**
     * Create a new {@link SuperVertex} instance with <code>source</code>
     * as start vertex.
     *
     * @param source the start vertex
     * @param graph the underlying graph
     * @param weightComparator the comparator used to sort the weighted edges
     */
    public SuperVertex( final V source, final Graph<V, WE> graph, final WeightedEdgesComparator<W, WE> weightComparator )
    {
        this.graph = graph;

        vertices = new HashSet<V>();
        vertices.add( source );

        orderedEdges = new TreeSet<WE>( weightComparator );

        // add all edges for this vertex to the sorted set
        for ( final V w : graph.getConnectedVertices( source ) )
        {
            final WE edge = graph.getEdge( source, w );
            orderedEdges.add( edge );
        }
    }

    /** {@inheritDoc} */
    public Iterator<V> iterator()
    {
        return vertices.iterator();
    }

    /**
     * Merges another {@link SuperVertex} instance into this one.
     * The edges from the other {@link SuperVertex} are only added in case
     * they are not to vertices already contained in this {@link SuperVertex}.
     *
     * @param other the {@link SuperVertex} to be merged into this
     */
    public void merge( final SuperVertex<V, W, WE> other )
    {
        for ( final V v : other.vertices )
        {
            vertices.add( v );
        }

        for ( final WE edge : other.orderedEdges )
        {
            final VertexPair<V> pair = graph.getVertices( edge );
            if ( !vertices.contains( pair.getHead() ) || !vertices.contains( pair.getTail() ) )
            {
                orderedEdges.add( edge );
            }
        }
    }

    /**
     * Returns the edge with the minimum weight to other {@link SuperVertex}
     * instances.
     *
     * @return the minimum weight edge or <code>null</code> if there is no edge
     */
    public WE getMinimumWeightEdge()
    {
        boolean found = false;
        WE edge = null;
        while ( !found && !orderedEdges.isEmpty() )
        {
            edge = orderedEdges.pollFirst();
            final VertexPair<V> pair = graph.getVertices( edge );
            if ( !vertices.contains( pair.getHead() ) || !vertices.contains( pair.getTail() ) )
            {
                found = true;
            }
        }
        return edge;
    }

}
