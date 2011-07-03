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
public interface Graph<V extends Vertex, E extends Edge>
{

    /**
     * Returns the total set of Vertices in the graph.
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @return the total set of Vertices in the graph.
     */
    Iterable<V> getVertices();

    /**
     * Returns the <i>order</i> of a Graph (the number of Vertices);
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @return the <i>order</i> of a Graph (the number of Vertices);
     */
    int getOrder();

    /**
     * Returns the total set of Edges in the graph.
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @return the total set of Edges in the graph.
     */
    Iterable<E> getEdges();

    /**
     * Returns the <i>size</i> of a Graph (the number of Edges)
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @return the <i>size</i> of a Graph (the number of Edges)
     */
    int getSize();

    /**
     * The degree (or valency) of a {@link Vertex} of a {@link Graph}
     * is the number of {@link Edge}s incident to the {@link Vertex}.
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param v the {@link Vertex} which degree has to be returned.
     * @return the number of {@link Edge}s incident to the {@link Vertex}.
     */
    int getDegree( V v );

    /**
     * Returns all vertices which touch this vertex.
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @return all vertices which touch this vertex.
     */
    Iterable<V> getConnectedVertices( V v );

    /**
     * Returns the edge with vertex source and target.
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param source the source vertex
     * @param target the target vertex
     * @return the edge with vertex source and target.
     */
    E getEdge( V source, V target );

    /**
     * Return the set of {@link Vertex} on the input {@link Edge} (2 for normal edges, > 2 for HyperEdges)
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @return the set of {@link Vertex} on this Edge.
     */
    VertexPair<V> getVertices( E e );

}
