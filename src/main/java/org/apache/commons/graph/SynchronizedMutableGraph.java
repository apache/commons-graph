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
 * 
 */
class SynchronizedMutableGraph<V, E>
    extends SynchronizedGraph<V, E>
    implements MutableGraph<V, E>
{
    private static final long serialVersionUID = -5985121601939852063L;

    private MutableGraph<V, E> mutableGraph;

    public SynchronizedMutableGraph( MutableGraph<V, E> g )
    {
        super( g );
        this.mutableGraph = g;
    }

    public void addVertex( V v )
    {
        synchronized ( lock )
        {
            mutableGraph.addVertex( v );
        }
    }

    public void removeVertex( V v )
    {
        synchronized ( lock )
        {
            mutableGraph.removeVertex( v );
        }
    }

    public void addEdge( V head, E e, V tail )
    {
        synchronized ( lock )
        {
            mutableGraph.addEdge( head, e, tail );
        }
    }

    public void removeEdge( E e )
    {
        synchronized ( lock )
        {
            mutableGraph.removeEdge( e );
        }
    }
}
