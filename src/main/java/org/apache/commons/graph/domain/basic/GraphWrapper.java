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

import java.util.Set;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public class GraphWrapper<V extends Vertex, E extends Edge<V>>
    implements Graph<V, E>
{

    private final Graph<V, E> impl;

    /**
     * Constructor for the GraphWrapper object
     *
     * @param impl
     */
    public GraphWrapper( Graph<V, E> impl )
    {
        if ( impl == null )
        {
            throw new IllegalArgumentException( "Wrapped Graph must be not null" );
        }
        this.impl = impl;
    }

    // Graph Implementation. . .
    /**
     * {@inheritDoc}
     */
    public Set<V> getVertices()
    {
        return impl.getVertices();
    }

    /**
     * {@inheritDoc}
     */
    public Set<E> getEdges()
    {
        return impl.getEdges();
    }

    /**
     * {@inheritDoc}
     */
    public Set<V> getVertices( E e )
    {
        return impl.getVertices( e );
    }

    /**
     * {@inheritDoc}
     */
    public Set<E> getEdges( V v )
    {
        return impl.getEdges( v );
    }

}
