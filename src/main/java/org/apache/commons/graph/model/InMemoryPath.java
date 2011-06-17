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
import static java.util.Collections.unmodifiableList;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Path;
import org.apache.commons.graph.Vertex;

/**
 * Support {@link Path} implementation, optimized for algorithms (such Dijkstra's) that need to rebuild the path
 * traversing the predecessor list bottom-up.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public class InMemoryPath<V extends Vertex, E extends Edge<V>>
    implements Path<V, E>
{

    private final V source;

    private final V target;

    private final LinkedList<V> vertices = new LinkedList<V>();

    private final LinkedList<E> edges = new LinkedList<E>();

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

    public void addVertexInHead( V vertex )
    {
        vertices.addFirst( vertex );
    }

    public void addVertexInTail( V vertex )
    {
        vertices.addLast( vertex );
    }

    /**
     * {@inheritDoc}
     */
    public List<V> getVertices()
    {
        return unmodifiableList( vertices );
    }

    public void addEdgeInHead( E edge )
    {
        edges.addFirst( edge );
    }

    public void addEdgeInTail( E edge )
    {
        edges.addLast( edge );
    }

    /**
     * {@inheritDoc}
     */
    public List<E> getEdges()
    {
        return unmodifiableList( edges );
    }

    /**
     * {@inheritDoc}
     */
    public int size()
    {
        return vertices.size();
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

        InMemoryPath other = (InMemoryPath) obj;
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
