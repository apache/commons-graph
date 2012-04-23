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

/**
 * A synchronized (thread-safe) {@link Graph} backed by the specified Graph.
 */
final class SynchronizedDirectedGraph<V, E>
    extends SynchronizedGraph<V, E>
    implements DirectedGraph<V, E>
{

    private static final long serialVersionUID = 2275587906693672253L;

    private final DirectedGraph<V, E> directedGraph;

    /**
     * Creates a new thread-safe instence of {@link SynchronizedDirectedGraph}.
     * @param g The {@link Graph} that has to be synchronized
     */
    public SynchronizedDirectedGraph( DirectedGraph<V, E> g )
    {
        super( g );
        directedGraph = g;
    }

    /**
     * {@inheritDoc}
     */
    public int getInDegree( V v )
    {
        synchronized ( lock )
        {
            return directedGraph.getInDegree( v );
        }
    }

    /**
     * {@inheritDoc}
     */
    public Iterable<V> getInbound( V v )
    {
        synchronized ( lock )
        {
            return directedGraph.getInbound( v );
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getOutDegree( V v )
    {
        synchronized ( lock )
        {
            return directedGraph.getOutDegree( v );
        }
    }

    /**
     * {@inheritDoc}
     */
    public Iterable<V> getOutbound( V v )
    {
        synchronized ( lock )
        {
            return directedGraph.getOutbound( v );
        }
    }

}
