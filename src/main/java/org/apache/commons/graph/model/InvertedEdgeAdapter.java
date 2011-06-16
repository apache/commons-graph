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

import static java.lang.reflect.Proxy.newProxyInstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;

/**
 * Simple {@link Edge} Proxy that inverts head/tail for undirectedGraph implementations.
 *
 * @param <V> the Graph vertices type
 */
final class InvertedEdgeAdapter<V extends Vertex>
    implements Edge<V>, InvocationHandler
{

    /**
     * Creates a new inverted {@link Edge}.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param edge the edge has to be inverted
     * @return
     */
    public static <V extends Vertex, E extends Edge<V>> E invertHeadAndTail( E edge )
    {
        @SuppressWarnings( "unchecked" ) // type driven by input
        E edgeProxy = (E) newProxyInstance( edge.getClass().getClassLoader(), edge.getClass().getInterfaces(),
                                            new InvertedEdgeAdapter<V>( edge ) );
        return edgeProxy;
    }

    private final Edge<V> adapted;

    /**
     * Creates a new inverted {@link Edge}, wrapping a default one.
     *
     * @param adapted the wrapped Edge
     */
    private InvertedEdgeAdapter( Edge<V> adapted )
    {
        this.adapted = adapted;
    }

    /**
     * {@inheritDoc}
     */
    public V getHead()
    {
        return adapted.getTail();
    }

    /**
     * {@inheritDoc}
     */
    public V getTail()
    {
        return adapted.getHead();
    }

    /**
     * {@inheritDoc}
     */
    public Object invoke( Object proxy, Method method, Object[] args )
        throws Throwable
    {
        try
        {
            return method.invoke( this, args );
        }
        catch ( Throwable t )
        {
            return method.invoke( adapted, args );
        }
    }

}
