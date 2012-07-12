package org.apache.commons.graph.coloring;

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

import java.util.Set;

import org.apache.commons.graph.UndirectedGraph;

/**
 * {@link ColorsBuilder} implementation
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public final class DefaultColorsBuilder<V, E>
    implements ColorsBuilder<V, E>
{

    private final UndirectedGraph<V, E> graph;

    public DefaultColorsBuilder( UndirectedGraph<V, E> graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public <C> ColoringAlgorithmsSelector<V, E, C> withColors( Set<C> colors )
    {
        colors = checkNotNull( colors, "Colors set must be not null" );
        return new DefaultColoringAlgorithmsSelector<V, E, C>( graph, colors );
    }

}
