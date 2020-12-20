package org.apache.commons.graph.connectivity;

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

import static java.util.Arrays.asList;
import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import org.apache.commons.graph.Graph;

/**
 * TODO Fill me!!
 * 
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public class DefaultConnectivityBuilder<V, E>
    implements ConnectivityBuilder<V, E>
{

    private final Graph<V, E> graph;

    /**
     * Creates a nw instance of {@link DefaultConnectivityBuilder}
     * @param graph the graph
     */
    public DefaultConnectivityBuilder(final Graph<V, E> graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public ConnectivityAlgorithmsSelector<V, E> includingVertices( V... vertices )
    {
        vertices = checkNotNull( vertices,
                                 "Graph connectivity cannote be applied on null vertices array, use no-args if you intend specify no vertices" );
        return new DefaultConnectivityAlgorithmsSelector<V, E>( graph, asList( vertices ) );
    }

    /**
     * {@inheritDoc}
     */
    public ConnectivityAlgorithmsSelector<V, E> includingAllVertices()
    {
        return new DefaultConnectivityAlgorithmsSelector<V, E>( graph, graph.getVertices() );
    }

}
