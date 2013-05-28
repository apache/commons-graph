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

import java.io.Serializable;

/**
 * A Graph data structure consists of a finite (and possibly mutable) set of ordered pairs, called edges or
 * arcs, of certain entities called vertex or node. As in mathematics, an edge {@code (x,y)} is said to
 * point or go from {@code x} to {@code y}.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public interface Graph<V, E>
    extends Serializable
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
     * The degree (or valency) of a vertex of a {@link Graph}
     * is the number of edges incident to the vertex.
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param v the vertex which degree has to be returned.
     * @return the number of edges incident to the vertex.
     */
    int getDegree( V v );

    /**
     * Returns all vertices which touch this vertex.
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param v the vertex which connected vertices have to be returned.
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
     * Return the set of vertex on the input edge (2 for normal edges, > 2 for HyperEdges)
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param e the input edge
     * @return the set of vertex on this Edge.
     */
    VertexPair<V> getVertices( E e );

    /**
     * Returns true if the vertex is contained into the graph
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param v the vertex to be checked
     * @return Returns true if the vertex is contained into the graph, false otherwise
     */
    boolean containsVertex( V v );

    /**
     * Returns true if the edge is contained into the graph
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param e the edge to be checked
     * @return Returns true if the edge is contained into the graph, false otherwise
     */
    boolean containsEdge( E e );

}
