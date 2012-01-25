package org.apache.commons.graph.spanning;

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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;

/**
 * {@link SpanningTreeSourceSelector} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <W> the weight type
 * @param <WE> the Graph weighted edges type
 * @param <G> the input Graph type
 */
public final class DefaultSpanningTreeSourceSelector<V extends Vertex, W, WE extends WeightedEdge<W>, G extends Graph<V, WE>>
    implements SpanningTreeSourceSelector<V, W, WE, G>
{

    private final G graph;

    public DefaultSpanningTreeSourceSelector( G graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpanningTreeAlgorithmSelector<V, W, WE, G> fromArbitrarySource()
    {
        return fromSource( graph.getVertices().iterator().next() );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpanningTreeAlgorithmSelector<V, W, WE, G> fromSource( V source )
    {
        source = checkNotNull( source, "Spanning tree cannot be calculated without expressing the source vertex" );
        return new DefaultSpanningTreeAlgorithmSelector<V, W, WE, G>( graph, source );
    }

}
