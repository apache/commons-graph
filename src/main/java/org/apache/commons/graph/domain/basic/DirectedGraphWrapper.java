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
 */
public class DirectedGraphWrapper<V extends Vertex, E extends Edge>
     extends GraphWrapper<V, E>
     implements DirectedGraph<V, E>
{
    private DirectedGraph<V, E> impl;

    /**
     * Constructor for the DirectedGraphWrapper object
     *
     * @param graph
     */
    public DirectedGraphWrapper(DirectedGraph<V, E> graph)
    {
        super(graph);
        impl = graph;
    }

    /**
     * {@inheritDoc}
     */
    public Set<E> getInbound(V v)
    {
        return impl.getInbound(v);
    }

    /**
     * {@inheritDoc}
     */
    public Set<E> getOutbound(V v)
    {
        return impl.getOutbound(v);
    }

    /**
     * {@inheritDoc}
     */
    public V getSource(E e)
    {
        return impl.getSource(e);
    }

    /**
     * {@inheritDoc}
     */
    public V getTarget(E e)
    {
        return impl.getTarget(e);
    }

}
