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

import org.apache.commons.graph.exception.GraphException;

/**
 * The {@code MutableGraph} is a graph that supports the addition and removal of {@link Vertex} and {@link Edge}s.
 */
public interface MutableGraph<V extends Vertex, E extends Edge>
    extends Graph<V, E>
{

    /**
     * Adds a feature to the {@link Vertex} attribute of the {@code MutableGraph} object.
     *
     * @param v the {@link Vertex} has to be added in this {@code MutableGraph} instance.
     */
    void addVertex( V v )
        throws GraphException;

    /**
     * Removes the {@link Vertex} from the {@code MutableGraph} object.
     *
     * @param v the {@link Vertex} has to be removed from this {@code MutableGraph} instance.
     */
    void removeVertex( V v )
        throws GraphException;

    /**
     * Adds a feature to the {@link Edge} attribute of the {@code MutableGraph} object
     *
     * @param e the {@link Edge} has to be added in this {@code MutableGraph} instance.
     */
    void addEdge( E e )
        throws GraphException;

    /**
     * Removed the {@link Edge} from the {@code MutableGraph} object.
     *
     * @param e the {@link Edge} has to be removed from this {@code MutableGraph} instance.
     */
    void removeEdge( E e )
        throws GraphException;

    /**
     * Creates a connection between the input {@link Edge} and {@link Vertex}.
     *
     * @param e the {@link Edge} has to be connected
     * @param v the {@link Vertex} has to be connected
     */
    void connect( E e, V v )
        throws GraphException;

    /**
     * Breaks a connection between the input {@link Edge} and {@link Vertex}.
     *
     * @param e the {@link Edge} has to be disconnected
     * @param v the {@link Vertex} has to be disconnected
     */
    void disconnect( E e, V v )
        throws GraphException;

}
