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
 * The {@code MutableDirectedGraph} is a directed graph that supports the addition and removal of
 * {@link Vertex} and {@link Edge}s.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public interface MutableDirectedGraph<V extends Vertex, E extends Edge<V>>
    extends DirectedGraph<V, E>
{

    /**
     * Adds a feature to the {@link Vertex} attribute of the {@code MutableDirectedGraph} object.
     *
     * @param v the {@link Vertex} has to be added in this {@code MutableDirectedGraph} instance.
     */
    public void addVertex( V v )
        throws GraphException;

    /**
     * Adds a feature to the {@link Edge} attribute of the {@code MutableDirectedGraph} object.
     *
     * @param e the {@link Edge} has to be added to this {@code MutableDirectedGraph} object.
     * @param source the input {@link Edge} source
     * @param target the input {@link Edge} target
     */
    public void addEdge( E e, V source, V target )
        throws GraphException;

    /**
     * Removes the {@link Vertex} from the {@code MutableGraph} object.
     *
     * @param v the {@link Vertex} has to be removed from this {@code MutableDirectedGraph} instance.
     */
    public void removeVertex( V v )
        throws GraphException;

    /**
     * Adds a feature to the {@link Edge} attribute of the {@code MutableDirectedGraph} object
     *
     * @param e the {@link Edge} has to be added in this {@code MutableDirectedGraph} instance.
     */
    public void removeEdge( E e )
        throws GraphException;

}
