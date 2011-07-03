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

import static java.util.Arrays.asList;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Path;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;

/**
 * Support {@link Path} implementation, optimized for algorithms (such Dijkstra's) that need to rebuild the path
 * traversing the predecessor list bottom-up.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public class InMemoryPath<V extends Vertex, E extends Edge>
    implements Path<V, E>
{

    private final V source;

    private final V target;

    private final LinkedList<V> vertices = new LinkedList<V>();

    private final LinkedList<E> edges = new LinkedList<E>();

    private final Map<V, V> successors = new HashMap<V, V>();

    private final Map<VertexPair<V>, E> indexedEdges = new HashMap<VertexPair<V>, E>();

    private final Map<E, VertexPair<V>> indexedVertices = new HashMap<E, VertexPair<V>>();

    public InMemoryPath( V start, V target )
    {
        if ( start == null )
        {
            throw new IllegalArgumentException( "Path source cannot be null" );
        }
        if ( target == null )
        {
            throw new IllegalArgumentException( "Path target cannot be null" );
        }

        this.source = start;
        this.target = target;
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

    public void addConnectionInHead( V head, E edge, V tail )
    {
        vertices.addFirst( head );
        edges.addFirst( edge );
        addConnection( head, edge, tail );
    }

    public void addConnectionInTail( V head, E edge, V tail )
    {
        vertices.addLast( head );
        edges.addLast( edge );
        addConnection( head, edge, tail );
    }

    private void addConnection( V head, E edge, V tail )
    {
        successors.put( head, tail );

        VertexPair<V> vertexPair = new VertexPair<V>( head, tail );
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
        if ( v == null )
        {
            throw new IllegalArgumentException( "Impossible to get the degree of a null vertex" );
        }

        if ( !successors.containsKey( v ) )
        {
            throw new IllegalArgumentException( "Impossible to get the degree of vertex not contained in this path" );
        }

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
        if ( v == null )
        {
            throw new IllegalArgumentException( "Impossible to get the successor of a null vertex" );
        }

        if ( target.equals( v ) )
        {
            return null;
        }

        if ( !successors.containsKey( v ) )
        {
            throw new IllegalArgumentException( "Impossible to get the successor of vertex not contained in this path" );
        }

        @SuppressWarnings( "unchecked" ) // type driven by input type
        List<V> connected = asList( successors.get( v ) );
        return connected;
    }

    /**
     * {@inheritDoc}
     */
    public E getEdge( V source, V target )
    {
        return indexedEdges.get( new VertexPair<V>( source, target ) );
    }

    /**
     * {@inheritDoc}
     */
    public VertexPair<V> getVertices( E e )
    {
        return indexedVertices.get( e );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( edges == null ) ? 0 : edges.hashCode() );
        result = prime * result + ( ( source == null ) ? 0 : source.hashCode() );
        result = prime * result + ( ( target == null ) ? 0 : target.hashCode() );
        result = prime * result + ( ( vertices == null ) ? 0 : vertices.hashCode() );
        return result;
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

        @SuppressWarnings( "unchecked" ) // test against any Path typed instance
        InMemoryPath<Vertex, Edge> other = (InMemoryPath<Vertex, Edge>) obj;
        if ( !source.equals( other.getSource() ) )
        {
            return false;
        }

        if ( !target.equals( other.getTarget() ) )
        {
            return false;
        }

        if ( !vertices.equals( other.getVertices() ) )
        {
            return false;
        }

        if ( !edges.equals( other.getEdges() ) )
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
        return format( "InMemoryPath [vertices=%s, edges=%s]", vertices, edges );
    }

}
