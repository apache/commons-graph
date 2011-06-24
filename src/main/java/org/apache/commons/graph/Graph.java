package org.apache.commons.graph;

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

/**
 * A Graph data structure consists of a finite (and possibly mutable) set of ordered pairs, called {@link Edge}s or
 * arcs, of certain entities called {@link Vertex} or node. As in mathematics, an {@link Edge} {@code (x,y)} is said to
 * point or go from {@code x} to {@code y}.
 * 
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public interface Graph<V extends Vertex, E extends Edge<V>>
{

    /**
     * Returns the total set of Vertices in the graph.
     * 
     * @return the total set of Vertices in the graph.
     */
    Iterable<V> getVertices();

    /**
     * Returns the <i>order</i> of a Graph (the number of Vertices);
     *
     * @return the <i>order</i> of a Graph (the number of Vertices);
     */
    int getOrder();

    /**
     * Returns the total set of Edges in the graph.
     * 
     * @return the total set of Edges in the graph.
     */
    Iterable<E> getEdges();

    /**
     * Returns the <i>size</i> of a Graph (the number of Edges)
     *
     * @return the <i>size</i> of a Graph (the number of Edges)
     */
    int getSize();

    /**
     * Returns all edges which touch this vertex, where the input vertex is in the edge head.
     * 
     * @return all edges which touch this vertex, where the input vertex is in the edge head.
     */
    Iterable<E> getEdges( V v );

    /**
     * Returns the edge with vertex source and target.
     * 
     * @param source the source vertex
     * @param target the target vertex
     * @return the edge with vertex source and target.
     */
    E getEdge( V source, V target );

    /**
     * Return the set of {@link Vertex} on the input {@link Edge} (2 for normal edges, > 2 for HyperEdges)
     * 
     * @return the set of {@link Vertex} on this Edge.
     */
    Iterable<V> getVertices( E e );

}
