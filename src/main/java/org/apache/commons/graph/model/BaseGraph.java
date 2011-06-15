package org.apache.commons.graph.model;

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

import static java.util.Collections.unmodifiableSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;

/**
 * Basic abstract in-memory based of a simple read-only {@link Graph} implementation.
 *
 * Subclasses may load adjacency list/edges set in the constructor,
 * or expose {@link org.apache.commons.graph.MutableGraph} APIs.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public abstract class BaseGraph<V extends Vertex, E extends Edge<V>>
    implements Graph<V, E>
{

    private final Map<V, Set<E>> adjacencyList = new HashMap<V, Set<E>>();

    private final Set<E> allEdges = new HashSet<E>();

    /**
     * {@inheritDoc}
     */
    public final Set<V> getVertices()
    {
        return unmodifiableSet( adjacencyList.keySet() );
    }

    /**
     * {@inheritDoc}
     */
    public final Set<E> getEdges()
    {
        return unmodifiableSet( allEdges );
    }

    /**
     * {@inheritDoc}
     */
    public final Set<E> getEdges( V v )
    {
        return unmodifiableSet( adjacencyList.get( v ) );
    }

    /**
     * {@inheritDoc}
     */
    public final Set<V> getVertices( E e )
    {
        Set<V> vertices = new HashSet<V>();

        vertices.add( e.getHead() );
        vertices.add( e.getTail() );

        return unmodifiableSet( vertices );
    }

    /**
     * Returns the adjacency list where stored vertex/edges.
     *
     * @return the adjacency list where stored vertex/edges.
     */
    protected final Map<V, Set<E>> getAdjacencyList()
    {
        return adjacencyList;
    }

    /**
     * Returns the set with all Graph edges.
     *
     * @return the set with all Graph edges.
     */
    protected final Set<E> getAllEdges()
    {
        return allEdges;
    }

}
