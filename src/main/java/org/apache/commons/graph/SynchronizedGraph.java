package org.apache.commons.graph;

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

import static org.apache.commons.graph.utils.Objects.eq;

/**
 * A synchronized (thread-safe) {@link Graph} backed by the specified Graph.
 */
class SynchronizedGraph<V, E>
    implements Graph<V, E>
{

    private static final long serialVersionUID = 4472623111635514693L;

    protected final Object lock;

    protected final Graph<V, E> g;

    /**
     * Creates a new thread-safe instence of {@link SynchronizedGraph}.
     * @param g The {@link Graph} that has to be synchronized
     */
    public SynchronizedGraph( Graph<V, E> g )
    {
        this.g = g;
        this.lock = this;
    }

    /**
     * {@inheritDoc}
     */
    public Iterable<V> getVertices()
    {
        synchronized ( lock )
        {
            return g.getVertices();
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getOrder()
    {
        synchronized ( lock )
        {
            return g.getOrder();
        }
    }

    /**
     * {@inheritDoc}
     */
    public Iterable<E> getEdges()
    {
        synchronized ( lock )
        {
            return g.getEdges();
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getSize()
    {
        synchronized ( lock )
        {
            return g.getSize();
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getDegree( V v )
    {
        synchronized ( lock )
        {
            return g.getDegree( v );
        }
    }

    /**
     * {@inheritDoc}
     */
    public Iterable<V> getConnectedVertices( V v )
    {
        synchronized ( lock )
        {
            return g.getConnectedVertices( v );
        }
    }

    /**
     * {@inheritDoc}
     */
    public E getEdge( V source, V target )
    {
        synchronized ( lock )
        {
            return g.getEdge( source, target );
        }
    }

    /**
     * {@inheritDoc}
     */
    public VertexPair<V> getVertices( E e )
    {
        synchronized ( lock )
        {
            return g.getVertices( e );
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsVertex( V v )
    {
        synchronized ( lock )
        {
            return g.containsVertex( v );
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsEdge( E e )
    {
        synchronized ( lock )
        {
            return g.containsEdge( e );
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( g == null ) ? 0 : g.hashCode() );
        result = prime * result + ( ( lock == null ) ? 0 : lock.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( obj == null || getClass() != obj.getClass() )
        {
            return false;
        }

        @SuppressWarnings( "unchecked" )
        // test against any Graph typed instance
        SynchronizedGraph<Object, Object> other = (SynchronizedGraph<Object, Object>) obj;
        return eq( g, other.g );
    }

    @Override
    public String toString()
    {
        return g.toString();
    }

}
