package org.apache.commons.graph.flow;

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

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Mapper;

/**
 * {@link DefaultToTailBuilder} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph edges type
 * @param <W> the Graph weight type
 */
final class DefaultToTailBuilder<V, WE, W>
    implements ToTailBuilder<V, WE, W>
{

    private final DirectedGraph<V, WE> graph;

    private final Mapper<WE, W> weightedEdges;

    private final V head;

    public DefaultToTailBuilder(final DirectedGraph<V, WE> graph, final Mapper<WE, W> weightedEdges, final V head )
    {
        this.graph = graph;
        this.weightedEdges = weightedEdges;
        this.head = head;
    }

    public <T extends V> MaxFlowAlgorithmSelector<V, WE, W> to( T tail )
    {
        tail = checkNotNull( tail, "tail vertex has to be specifies when looking for the max flow" );
        return new DefaultMaxFlowAlgorithmSelector<V, WE, W>( graph, weightedEdges, head, tail );
    }

}
