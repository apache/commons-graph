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

import java.util.LinkedHashSet;

import org.apache.commons.graph.MutableGraph;
import org.apache.commons.graph.VertexPair;

/**
 * Basic abstract in-memory based of a simple mutable {@link org.apache.commons.graph.Graph} implementation.
 *
 * This class is NOT thread safe!
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public abstract class BaseMutableGraph<V, E>
    extends BaseGraph<V, E>
    implements MutableGraph<V, E>
{

    private static final long serialVersionUID = 1549113549446254183L;

    /**
     * {@inheritDoc}
     */
    public void addEdge( V head, E e, V tail )
    {
        checkGraphCondition( head != null, "Null head Vertex not admitted" );
        checkGraphCondition( e != null, "Impossible to add a null Edge in the Graph" );
        checkGraphCondition( tail != null, "Null tail Vertex not admitted" );
        checkGraphCondition( containsVertex( head ), "Head Vertex '%s' not present in the Graph", head );
        checkGraphCondition( containsVertex( tail ), "Head Vertex '%s' not present in the Graph", tail );
        checkGraphCondition( getEdge( head, tail ) == null, "Edge %s is already present in the Graph", e );

        getAllEdges().add( e );

        internalAddEdge( head, e, tail );

        decorateAddEdge( head, e, tail );
    }

    /**
     * {@inheritDoc}
     */
    public final void addVertex( V v )
    {
        checkGraphCondition( v != null, "Impossible to add a null Vertex to the Graph" );
        checkGraphCondition( !containsVertex( v ), "Vertex '%s' already present in the Graph", v );

        getAdjacencyList().put( v, new LinkedHashSet<V>() );

        decorateAddVertex( v );
    }

    /**
     * Executes additional actions to edge that will be added  
     * @param head the head vertex
     * @param e the edge
     * @param tail the tail vertex
     */
    protected abstract void decorateAddEdge( V head, E e, V tail );

    /**
     * Executes additional actions to vertex that will be added  
     * @param v the vertex
     */
    protected abstract void decorateAddVertex( V v );

    /**
     * Executes additional actions to edge that will be removed  
     * @param e the edge
     */
    protected abstract void decorateRemoveEdge( E e );

    /**
     * Executes additional actions to vertex that will be removed  
     * @param v the vertex
     */
    protected abstract void decorateRemoveVertex( V v );

    /**
     * Performs the internal operations to add the edge
     * 
     * @param head the head vertex
     * @param e the edge
     * @param tail the tail vertex
     */
    protected void internalAddEdge( V head, E e, V tail )
    {
        getAdjacencyList().get( head ).add( tail );

        final VertexPair<V> vertexPair = new VertexPair<V>( head, tail );
        getIndexedEdges().put( vertexPair, e );

        if ( !getIndexedVertices().containsKey( e ) )
        {
            getIndexedVertices().put( e, vertexPair );
        }
    }

    /**
     * Performs the internal operations to remove the edge.
     * 
     * @param head the head vertex
     * @param e the edge
     * @param tail the tail vertex
     */
    protected void internalRemoveEdge( V head, E e, V tail )
    {
        final VertexPair<V> vertexPair = new VertexPair<V>( head, tail );
        getIndexedVertices().remove( e );
        getIndexedEdges().remove( vertexPair );
        getAdjacencyList().get( vertexPair.getHead() ).remove( vertexPair.getTail() );
    }

    /**
     * {@inheritDoc}
     */
    public final void removeEdge( E e )
    {
        checkGraphCondition( e != null, "Impossible to remove a null Edge from the Graph" );
        checkGraphCondition( containsEdge( e ), "Edge '%s' not present in the Graph", e );
        final VertexPair<V> vertexPair = getVertices( e );
        decorateRemoveEdge( e );
        internalRemoveEdge( vertexPair.getHead(), e, vertexPair.getTail() );
        getAllEdges().remove( e );

    }

    /**
     * {@inheritDoc}
     */
    public final void removeVertex( V v )
    {
        checkGraphCondition( v != null, "Impossible to remove a null Vertex from the Graph" );
        checkGraphCondition( containsVertex( v ), "Vertex '%s' not present in the Graph", v );

        for ( V tail : getAdjacencyList().get( v ) )
        {
            getIndexedEdges().remove( new VertexPair<V>( v, tail ) );
        }
        getAdjacencyList().remove( v );

        decorateRemoveVertex( v );
    }

}
