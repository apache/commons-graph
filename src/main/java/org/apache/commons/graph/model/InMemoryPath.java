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
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.graph.utils.Assertions.checkArgument;
import static org.apache.commons.graph.utils.Assertions.checkNotNull;
import static org.apache.commons.graph.utils.Objects.eq;
import static org.apache.commons.graph.utils.Objects.hash;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.graph.Path;
import org.apache.commons.graph.VertexPair;

/**
 * Support {@link Path} implementation, optimized for algorithms (such Dijkstra's) that need to rebuild the path
 * traversing the predecessor list bottom-up.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public class InMemoryPath<V, E>
    implements Path<V, E>
{

    private static final long serialVersionUID = -7248626031673230570L;

    private final V source;

    private final V target;

    private final LinkedList<V> vertices = new LinkedList<V>();

    private final LinkedList<E> edges = new LinkedList<E>();

    private final Map<V, V> successors = new HashMap<V, V>();

    private final Map<VertexPair<V>, E> indexedEdges = new HashMap<VertexPair<V>, E>();

    private final Map<E, VertexPair<V>> indexedVertices = new HashMap<E, VertexPair<V>>();

    /**
     * Creates a new instance of {@link InMemoryPath} from {@code start} vertex to {@code taget} vertex
     * @param start the start vertex.
     * @param target the target vertex.
     */
    public InMemoryPath(final V start, final V target )
    {
        this.source = checkNotNull( start, "Path source cannot be null" );
        this.target = checkNotNull( target, "Path target cannot be null" );
    }

    /**
     * {@inheritDoc}
     */
    public V getSource()
    {
        return source;
    }

    /**
     * {@inheritDoc}
     */
    public V getTarget()
    {
        return target;
    }

    /**
     * {@inheritDoc}
     */
    public Iterable<V> getVertices()
    {
        return unmodifiableList( vertices );
    }

    /**
     * {@inheritDoc}
     */
    public int getOrder()
    {
        return vertices.size();
    }

    /**
     * Adds the edge in head.
     * 
     * @param head the head vertex
     * @param edge the edge
     * @param tail the tail vertex
     */
    public void addConnectionInHead(final V head, final E edge, final V tail )
    {
        if ( target.equals( tail ) )
        {
            vertices.addFirst( tail );
        }

        vertices.addFirst( head );
        edges.addFirst( edge );

        addConnection( head, edge, tail );
    }

    /**
     * Adds the edge in tail.
     * 
     * @param head the head vertex
     * @param edge the edge
     * @param tail the tail vertex
     */
    public void addConnectionInTail(final V head, final E edge, final V tail )
    {
        vertices.addLast( head );
        edges.addLast( edge );

        if ( target.equals( tail ) )
        {
            vertices.addLast( tail );
        }

        addConnection( head, edge, tail );
    }

    private void addConnection(final V head, final E edge, final V tail )
    {
        successors.put( head, tail );

        final VertexPair<V> vertexPair = new VertexPair<V>( head, tail );
        indexedEdges.put( vertexPair, edge );
        indexedVertices.put( edge, vertexPair );
    }

    /**
     * {@inheritDoc}
     */
    public Iterable<E> getEdges()
    {
        return unmodifiableList( edges );
    }

    /**
     * {@inheritDoc}
     */
    public int getSize()
    {
        return edges.size();
    }

    /**
     * {@inheritDoc}
     */
    public int getDegree( V v )
    {
        v = checkNotNull( v, "Impossible to get the degree of a null vertex" );
        checkArgument( successors.containsKey( v ),
                       "Impossible to get the degree of input vertex; %s not contained in this path", v );

        if ( source.equals( v ) || target.equals( v ) )
        {
            return 1;
        }

        return 2;
    }

    /**
     * {@inheritDoc}
     */
    public Iterable<V> getConnectedVertices( V v )
    {
        v = checkNotNull( v, "Impossible to get the degree of a null vertex" );

        if ( target.equals( v ) )
        {
            return null;
        }

        checkArgument( successors.containsKey( v ),
                       "Impossible to get the degree of input vertex; %s not contained in this path", v );

        @SuppressWarnings( "unchecked" ) final // type driven by input type
        List<V> connected = asList( successors.get( v ) );
        return connected;
    }

    /**
     * {@inheritDoc}
     */
    public E getEdge(final V source, final V target )
    {
        return indexedEdges.get( new VertexPair<V>( source, target ) );
    }

    /**
     * {@inheritDoc}
     */
    public VertexPair<V> getVertices(final E e )
    {
        return indexedVertices.get( e );
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsVertex(final V v )
    {
        return vertices.contains( v );
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsEdge(final E e )
    {
        return edges.contains( e );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        return hash( 1, prime, edges, source, target, vertices );
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

        @SuppressWarnings( "unchecked" ) final // test against any Path typed instance
        InMemoryPath<Object, Object> other = (InMemoryPath<Object, Object>) obj;
        return eq( source, other.getSource() )
            && eq( target, other.getTarget() )
            && eq( vertices, other.getVertices() )
            && eq( edges, other.getEdges() );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return format( "InMemoryPath [vertices=%s, edges=%s]", vertices, edges );
    }

}
