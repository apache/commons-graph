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

import java.util.HashSet;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.MutableGraph;
import org.apache.commons.graph.Vertex;

/**
 * Basic abstract in-memory based of a simple mutable {@link Graph} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public abstract class BaseMutableGraph<V extends Vertex, E extends Edge<V>>
    extends BaseGraph<V, E>
    implements MutableGraph<V, E>
{

    /**
     * {@inheritDoc}
     */
    public final void addVertex( V v )
    {
        if ( v == null )
        {
            throw new GraphException( "Impossible to add a null Vertex to the Graph" );
        }

        if ( getAdjacencyList().containsKey( v ) ){
            throw new GraphException( format( "Vertex '%s' already present in the Graph", v ) );
        }

        getAdjacencyList().put( v, new HashSet<E>() );

        decorateAddVertex( v );
    }

    /**
     * 
     *
     * @param v
     */
    protected abstract void decorateAddVertex( V v );

    /**
     * {@inheritDoc}
     */
    public final void removeVertex( V v )
    {
        if ( v == null )
        {
            throw new GraphException( "Impossible to remove a null Vertex from the Graph" );
        }

        if ( !getAdjacencyList().containsKey( v ) ){
            throw new GraphException( format( "Vertex '%s' not present in the Graph", v ) );
        }

        getAdjacencyList().remove( v );

        decorateRemoveVertex( v );
    }

    /**
     * 
     *
     * @param v
     */
    protected abstract void decorateRemoveVertex( V v );

    /**
     * {@inheritDoc}
     */
    public final void addEdge( E e )
    {
        checkEdge( e );

        getAllEdges().add( e );
        getAdjacencyList().get( e.getHead() ).add( e );
        getAdjacencyList().get( e.getTail() ).add( e );

        decorateAddEdge( e );
    }

    /**
     * 
     *
     * @param e
     */
    protected abstract void decorateAddEdge( E e );

    /**
     * {@inheritDoc}
     */
    public final void removeEdge( E e )
    {
        checkEdge( e );

        getAllEdges().remove( e );
        getAdjacencyList().get( e.getHead() ).remove( e );
        getAdjacencyList().get( e.getTail() ).remove( e );

        decorateRemoveEdge( e );
    }

    /**
     * 
     *
     * @param e
     */
    protected abstract void decorateRemoveEdge( E e );

    /**
     * Utility method to check if Vertices in the given Edge are present in the Graph.
     *
     * @param e the Edge which Vertices have to be checked
     */
    private final void checkEdge( E e )
    {
        if ( !getAdjacencyList().containsKey( e.getHead() ) )
        {
            throw new GraphException( format( "Vertex '%s' not present in the Graph", e.getHead() ) );
        }
        if ( !getAdjacencyList().containsKey( e.getTail() ) )
        {
            throw new GraphException( format( "Vertex '%s' not present in the Graph", e.getTail() ) );
        }
    }

}
