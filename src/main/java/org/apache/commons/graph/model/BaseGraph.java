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

import static java.lang.String.format;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableSet;
import static org.apache.commons.graph.utils.Objects.eq;
import static org.apache.commons.graph.utils.Objects.hash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.VertexPair;

/**
 * Basic abstract in-memory based of a simple read-only {@link Graph} implementation. Subclasses may load adjacency
 * list/edges set in the constructor, or expose {@link org.apache.commons.graph.MutableGraph} APIs.
 *
 * This class is NOT thread safe!
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public abstract class BaseGraph<V, E>
    implements Graph<V, E>
{

    private static final long serialVersionUID = -8066786787634472712L;

    private final Map<V, Set<V>> adjacencyList = new HashMap<V, Set<V>>();

    private final Set<E> allEdges = new HashSet<E>();

    private final Map<VertexPair<V>, E> indexedEdges = new HashMap<VertexPair<V>, E>();

    private final Map<E, VertexPair<V>> indexedVertices = new HashMap<E, VertexPair<V>>();

    /**
     * {@inheritDoc}
     */
    public final Iterable<V> getVertices()
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
    public final Iterable<E> getEdges()
    {
        return unmodifiableCollection( allEdges );
    }

    /**
     * {@inheritDoc}
     */
    public int getSize()
    {
        return allEdges.size();
    }

    /**
     * {@inheritDoc}
     */
    public final Iterable<V> getConnectedVertices(final V v )
    {
        checkGraphCondition( containsVertex( v ), "Vertex %s does not exist in the Graph", v );
        final Set<V> adj = adjacencyList.get( v );
        return unmodifiableSet( adj );
    }

    /**
     * {@inheritDoc}
     */
    public final E getEdge(final V source, final V target )
    {
        checkGraphCondition( containsVertex( source ), "Vertex %s does not exist in the Graph", source );
        checkGraphCondition( containsVertex( target ), "Vertex %s does not exist in the Graph", target );

        return indexedEdges.get( new VertexPair<V>( source, target ) );
    }

    /**
     * {@inheritDoc}
     */
    public final VertexPair<V> getVertices(final E e )
    {
        return indexedVertices.get( e );
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsVertex(final V v )
    {
        return adjacencyList.containsKey( v );
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsEdge(final E e )
    {
        return indexedVertices.containsKey( e );
    }

    /**
     * Returns the adjacency list where stored vertex/edges.
     *
     * @return the adjacency list where stored vertex/edges.
     */
    protected final Map<V, Set<V>> getAdjacencyList()
    {
        return adjacencyList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        return hash( 1, prime, adjacencyList, allEdges, indexedEdges, indexedVertices );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( obj == null || getClass() != obj.getClass() )
        {
            return false;
        }

        @SuppressWarnings( "unchecked" ) final
        // test against any Graph typed instance
        BaseGraph<Object, Object> other = (BaseGraph<Object, Object>) obj;
        return eq( adjacencyList, other.getAdjacencyList() );
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
     * Return the edge {@link Set}
     * @return the edge {@link Set}
     */
    protected Set<E> getAllEdges()
    {
        return allEdges;
    }

    /**
     * Returns the {@code Map} of indexed edges.
     * 
     * @return the {@link Map} of indexed edges
     */
    protected Map<VertexPair<V>, E> getIndexedEdges()
    {
        return indexedEdges;
    }

    /**
     * Returns the {@code Map} of indexed vertices.
     * 
     * @return the indexed vertices {@link Map}
     */
    protected Map<E, VertexPair<V>> getIndexedVertices()
    {
        return indexedVertices;
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the
     * calling method.
     *
     * @param expression a boolean expression
     * @param errorMessageTemplate a template for the exception message should the
     *     check fail. The message is formed by replacing each {@code %s}
     *     placeholder in the template with an argument. These are matched by
     *     position - the first {@code %s} gets {@code errorMessageArgs[0]}, etc.
     *     Unmatched arguments will be appended to the formatted message in square
     *     braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message
     *     template. Arguments are converted to strings using
     *     {@link String#valueOf(Object)}.
     * @throws GraphException if {@code expression} is false
     */
    protected static void checkGraphCondition(final boolean expression, final String errorMessageTemplate, final Object...errorMessageArgs )
    {
        if ( !expression )
        {
            throw new GraphException( format( errorMessageTemplate, errorMessageArgs ) );
        }
    }
}
