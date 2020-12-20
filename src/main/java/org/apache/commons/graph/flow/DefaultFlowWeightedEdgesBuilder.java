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
 * {@link FlowWeightedEdgesBuilder} implementation
 * 
 * @param <V> the Graph vertices type
 * @param <WE> the Graph edges type
 */
public final class DefaultFlowWeightedEdgesBuilder<V, WE>
    implements FlowWeightedEdgesBuilder<V, WE>
{

    private final DirectedGraph<V, WE> graph;

    /**
     * Creates  a new instance of flow weighted edges builder for the given graph
     * @param graph the graph
     */
    public DefaultFlowWeightedEdgesBuilder(final DirectedGraph<V, WE> graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public <W, M extends Mapper<WE, W>> FromHeadBuilder<V, WE, W> whereEdgesHaveWeights( M weightedEdges )
    {
        weightedEdges = checkNotNull( weightedEdges, "Function to calculate edges weight can not be null." );
        return new DefaultFromHeadBuilder<V, WE, W>( graph, weightedEdges );
    }

}
