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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;

/**
 * A {@link SuperVertex} is a collection of {@link Vertex} objects and is only
 * used internally by Boruvka's algorithm to find a minimum spanning tree.
 *
 * @param <V>  the Graph vertices type
 * @param <W>  the weight type
 * @param <WE> the Graph weighted edges type
 * @param <G>  the input Graph type
 * @param <WC> the weight operations 
 */
class SuperVertex<V extends Vertex, W, WE extends WeightedEdge<W>, G extends Graph<V, WE>, WC extends Comparator<W>>
    implements Iterable<V> {

    /** The reference to the graph. */
    private final G graph;

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
    public SuperVertex( final V source, final G graph, final WC weightComparator ) {
        this.graph = graph;

        vertices = new HashSet<V>();
        vertices.add( source );

        orderedEdges = new TreeSet<WE>( new WeightedEdgesComparator<W, WE>( weightComparator ) );

        // add all edges for this vertex to the sorted set
        for ( V w : graph.getConnectedVertices( source )) {
            WE edge = graph.getEdge( source, w );
            orderedEdges.add( edge );
        }
    }

    /** {@inheritDoc} */
    public Iterator<V> iterator() {
        return vertices.iterator();
    }

    /**
     * Merges another {@link SuperVertex} instance into this one.
     * The edges from the other {@link SuperVertex} are only added in case
     * they are not to vertices already contained in this {@link SuperVertex}.
     *
     * @param other the {@link SuperVertex} to be merged into this
     */
    public void merge( final SuperVertex<V, W, WE, G, WC> other ) {
        for ( V v : other.vertices ) {
            vertices.add(v);
        }

        for (WE edge : other.orderedEdges) {
            VertexPair<V> pair = graph.getVertices( edge );
            if ( !vertices.contains(pair.getHead()) ||
                 !vertices.contains(pair.getTail()) ) {
                orderedEdges.add( edge );
            }
        }
    }

    /**
     * Returns the edge with the minimum weight to other {@link SuperVertex}
     * instances.
     *
     * @return the minimum weight edge
     */
    public WE getMinimumWeightEdge() {
        return orderedEdges.pollFirst();
    }

}
