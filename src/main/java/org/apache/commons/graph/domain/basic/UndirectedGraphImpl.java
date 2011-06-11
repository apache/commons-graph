package org.apache.commons.graph.domain.basic;

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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.MutableGraph;
import org.apache.commons.graph.UndirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;

/**
 * Description of the Class
 */
public class UndirectedGraphImpl<V extends Vertex, WE extends WeightedEdge>
    implements UndirectedGraph<V, WE>, WeightedGraph<V, WE>, MutableGraph<V, WE>, InvocationHandler
{

    private final Set<V> vertices = new HashSet<V>();

    private final Set<WE> edges = new HashSet<WE>();

    private final Map<WE, Set<V>> edgeVerts = new HashMap<WE, Set<V>>(); // EDGE X SET( VERTS )

    private final Map<V, Set<WE>> vertEdges = new HashMap<V, Set<WE>>(); // VERTEX X SET( EDGE )

    private final Map<WE, Number> edgeWeights = new HashMap<WE, Number>(); // EDGE X WEIGHT

    /**
     * {@inheritDoc}
     */
    public void addVertex( V v )
        throws GraphException
    {
        vertices.add( v );
    }

    /**
     * {@inheritDoc}
     */
    public void removeVertex( V v )
        throws GraphException
    {
        vertices.remove( v );
    }

    /**
     * {@inheritDoc}
     */
    public void removeEdge( WE e )
        throws GraphException
    {
        edges.remove( e );
    }

    /**
     * {@inheritDoc}
     */
    public void addEdge( WE e )
        throws GraphException
    {
        edges.add( e );
    }

    /**
     * {@inheritDoc}
     */
    public void disconnect( WE e, V v )
    {
        if ( edgeVerts.containsKey( e ) )
        {
            edgeVerts.get( e ).remove( v );
        }

        if ( vertEdges.containsKey( v ) )
        {
            vertEdges.get( v ).remove( e );
        }
    }

    public void connect( WE e, V v )
    {
        Set<V> verts = null;
        if ( !edgeVerts.containsKey( e ) )
        {
            verts = new HashSet<V>();
            edgeVerts.put( e, verts );
        }
        else
        {
            verts = edgeVerts.get( e );
        }

        verts.add( v );

        Set<WE> edges = null;
        if ( !vertEdges.containsKey( v ) )
        {
            edges = new HashSet<WE>();
            vertEdges.put( v, edges );
        }
        else
        {
            edges = vertEdges.get( v );
        }

        edges.add( e );

    }

    /**
     * Adds a feature to the Edge attribute of the UndirectedGraphImpl object
     */
    public void addEdge( WE e, Set<V> vertices )
        throws GraphException
    {
        addEdge( e );

        Iterator<V> verts = vertices.iterator();
        while ( verts.hasNext() )
        {
            connect( e, verts.next() );
        }
    }

    // Interface Methods
    /**
     * {@inheritDoc}
     */
    public Set<V> getVertices()
    {
        return unmodifiableSet( vertices );
    }

    /**
     * {@inheritDoc}
     */
    public Set<V> getVertices( WE e )
    {
        if ( edgeVerts.containsKey( e ) )
        {
            return unmodifiableSet( edgeVerts.get( e ) );
        }
        return new HashSet<V>();
    }

    /**
     * {@inheritDoc}
     */
    public Set<WE> getEdges()
    {
        return unmodifiableSet( edges );
    }

    /**
     * {@inheritDoc}
     */
    public Set<WE> getEdges( V v )
    {
        if ( vertEdges.containsKey( v ) )
        {
            return unmodifiableSet( vertEdges.get( v ) );
        }
        return new HashSet<WE>();
    }

    public void setWeight( WE e, Number w )
    {
        if ( edgeWeights.containsKey( e ) )
        {
            edgeWeights.remove( e );
        }

        edgeWeights.put( e, w );
    }

    public Number getWeight( WE e )
    {
        if ( edgeWeights.containsKey( e ) )
        {
            return edgeWeights.get( e );
        }
        return 1;
    }

    /**
     * Description of the Method
     */
    public Object invoke( Object proxy, Method method, Object args[] )
        throws Throwable
    {
        try
        {
            return method.invoke( this, args );
        }
        catch ( InvocationTargetException ex )
        {
            throw ex.getTargetException();
        }
    }

}
