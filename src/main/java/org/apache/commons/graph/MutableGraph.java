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
 * The {@code MutableGraph} is a graph that supports the addition and removal of vertex and edges.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public interface MutableGraph<V, E>
    extends Graph<V, E>
{

    /**
     * Adds a feature to the vertex attribute of the {@code MutableGraph} object.
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param v the vertex has to be added in this {@code MutableGraph} instance.
     */
    void addVertex( V v );

    /**
     * Removes the vertex from the {@code MutableGraph} object.
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param v the vertex has to be removed from this {@code MutableGraph} instance.
     */
    void removeVertex( V v );

    /**
     * Adds a feature to the edge attribute of the {@code MutableGraph} object
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param head the head vertex
     * @param e the edge has to be added in this {@code MutableGraph} instance.
     * @param tail the tail vertex
     */
    void addEdge( V head, E e, V tail );

    /**
     * Removed the edge from the {@code MutableGraph} object.
     *
     * <b>NOTE</b>: implementors have to take in consideration throwing a {@link GraphException}
     * if an error occurs while performing that operation.
     *
     * @param e the edge has to be removed from this {@code MutableGraph} instance.
     */
    void removeEdge( E e );

}
