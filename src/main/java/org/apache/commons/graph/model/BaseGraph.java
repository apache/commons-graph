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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;

/**
 * Basic abstract in-memory based of a simple read-only {@link Graph} implementation. Subclasses may load adjacency
 * list/edges set in the constructor, or expose {@link org.apache.commons.graph.MutableGraph} APIs.
 * 
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public abstract class BaseGraph<V extends Vertex, E extends Edge<V>>
    implements Graph<V, E>
{

    private final Map<V, Set<E>> adjacencyList = new HashMap<V, Set<E>>();

    private final Map<VertexPair<V>, E> indexedEdges = new HashMap<VertexPair<V>, E>();

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
    public final int getOrder()
    {
        return adjacencyList.size();
    }

    /**
     * {@inheritDoc}
     */
    public final Set<E> getEdges()
    {
        return unmodifiableSet( new HashSet<E>( indexedEdges.values() ) );
    }

    /**
     * {@inheritDoc}
     */
    public int getSize()
    {
        return indexedEdges.size();
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
    public final E getEdge( V source, V target )
    {
        return indexedEdges.get( new VertexPair<Vertex>( source, target ) );
    }

    /**
     * {@inheritDoc}
     */
    public final Set<V> getVertices( E e )
    {
        Set<V> vertices = new LinkedHashSet<V>();

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
    private final Set<E> getAllEdges()
    {
        return unmodifiableSet( new HashSet<E>( indexedEdges.values() ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( obj == null )
        {
            return false;
        }

        if ( getClass() != obj.getClass() )
        {
            return false;
        }

        @SuppressWarnings( "unchecked" )
        // test against any Graph typed instance
        BaseGraph<Vertex, Edge<Vertex>> other = (BaseGraph<Vertex, Edge<Vertex>>) obj;
        if ( !adjacencyList.equals( other.getAdjacencyList() ) )
        {
            return false;
        }
        if ( !getAllEdges().equals( other.getAllEdges() ) )
        {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return String.valueOf( adjacencyList );
    }

    /**
     * @return the indexedEdges
     */
    protected Map<VertexPair<V>, E> getIndexedEdges()
    {
        return indexedEdges;
    }

}
