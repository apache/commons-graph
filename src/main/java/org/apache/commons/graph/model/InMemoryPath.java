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

import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedPath;

/**
 * Support {@link WeightedPath} implementation, optimized for algorithms (such Dijkstra's) that need to rebuild the path
 * traversing the predecessor list bottom-up.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 */
public final class InMemoryPath<V extends Vertex, WE extends WeightedEdge<V>>
    implements WeightedPath<V, WE>
{

    private final V source;

    private final V target;

    private final Double weigth;

    private final LinkedList<V> vertices = new LinkedList<V>();

    private final LinkedList<WE> edges = new LinkedList<WE>();

    public InMemoryPath( V start, V end, Double weigth )
    {
        this.source = start;
        this.target = end;
        this.weigth = weigth;
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

    public void addEdgeInHead( WE edge )
    {
        edges.addFirst( edge );
    }

    public void addEdgeInTail( WE edge )
    {
        edges.addLast( edge );
    }

    /**
     * {@inheritDoc}
     */
    public List<WE> getEdges()
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
    public Double getWeight()
    {
        return weigth;
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
        result = prime * result + ( ( weigth == null ) ? 0 : weigth.hashCode() );
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

        if ( !target.equals( other.target ) )
        {
            return false;
        }

        if ( !vertices.equals( other.vertices ) )
        {
            return false;
        }

        if ( !edges.equals( other.getEdges() ) )
        {
            return false;
        }

        if ( !weigth.equals( other.weigth ) )
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
        return format( "InMemoryPath [weigth=%s, vertices=%s, edges=%s]", weigth, vertices, edges );
    }

}
