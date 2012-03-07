package org.apache.commons.graph.shortestpath;

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
import org.apache.commons.graph.WeightedEdges;

public final class DefaultWeightedEdgesSelector<V, WE, G extends Graph<V, WE>>
    implements WeightedEdgesSelector<V, WE, G>
{

    private final G graph;

    public DefaultWeightedEdgesSelector( G graph )
    {
        this.graph = graph;
    }

    public <W> PathSourceSelector<V, WE, W, G> whereEdgesHaveWeights( WeightedEdges<WE, W> weightedEdges )
    {
        weightedEdges = checkNotNull( weightedEdges, "Function to calculate edges weight can not be null." );
        return new DefaultPathSourceSelector<V, WE, W, G>( graph, weightedEdges );
    }

}
