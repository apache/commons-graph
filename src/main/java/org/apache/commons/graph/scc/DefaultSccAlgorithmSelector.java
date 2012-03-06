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
import org.apache.commons.graph.Graph;
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
        return new KosarajuSharirAlgorithm<V, E, G>( graph ).applyingKosarajuSharir( source );
    }

    /**
     * {@inheritDoc}
     */
    public Set<Set<V>> applyingKosarajuSharir()
    {
        return new KosarajuSharirAlgorithm<V, E, G>( graph ).applyingKosarajuSharir();
    }

    /**
     * {@inheritDoc}
     */
    public Set<Set<V>> applyingCheriyanMehlhornGabow()
    {
        return new CheriyanMehlhornGabowAlgorithm<V, E, G>( graph ).applyingCheriyanMehlhornGabow();
    }

    /**
     * {@inheritDoc}
     */
    public Set<Set<V>> applyingTarjan()
    {
        return new TarjanAlgorithm<V, E, G>( graph ).applyingTarjan();
    }
}
