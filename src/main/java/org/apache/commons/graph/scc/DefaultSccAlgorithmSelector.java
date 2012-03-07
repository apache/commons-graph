package org.apache.commons.graph.scc;

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

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;

/**
 * {@link SccAlgorithmSelector} implementation
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 * @param <G> the directed graph type
 */
public final class DefaultSccAlgorithmSelector<V extends Vertex, E extends Edge, G extends DirectedGraph<V, E>>
    implements SccAlgorithmSelector<V, E, G>
{
    /** The graph. */
    private final G graph;

    /**
     * Create a default {@link SccAlgorithmSelector} for the given {@link Graph}.
     *
     * @param graph the {@link Graph}.
     */
    public DefaultSccAlgorithmSelector( final G graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public Set<V> applyingKosarajuSharir( final V source )
    {
        return new KosarajuSharirAlgorithm<V, E, G>( graph ).perform( source );
    }

    /**
     * {@inheritDoc}
     */
    public Set<Set<V>> applyingKosarajuSharir()
    {
        return applying( new KosarajuSharirAlgorithm<V, E, G>( graph ) );
    }

    /**
     * {@inheritDoc}
     */
    public Set<Set<V>> applyingCheriyanMehlhornGabow()
    {
        return applying( new CheriyanMehlhornGabowAlgorithm<V, E, G>( graph ) );
    }

    /**
     * {@inheritDoc}
     */
    public Set<Set<V>> applyingTarjan()
    {
        return applying( new TarjanAlgorithm<V, E, G>( graph ) );
    }

    /**
     * Just calculates the SCC depending on the selected algorithm.
     *
     * @param algorithm
     * @return
     */
    private Set<Set<V>> applying( SccAlgorithm<V> algorithm )
    {
        return algorithm.perform();
    }

}
