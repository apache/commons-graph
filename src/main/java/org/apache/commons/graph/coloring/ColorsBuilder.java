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

import java.util.Set;

import org.apache.commons.graph.UndirectedGraph;

/**
 * Builder to specify the set of colors for coloring the graph.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 * @param <G> the Graph type
 */
public interface ColorsBuilder<V, E, G extends UndirectedGraph<V, E>>
{

    /**
     * Specifies the set of colors for coloring the graph.
     *
     * @param colors the set of colors for coloring the graph.
     * @return the coloring algorithm selector.
     */
    <C> ColoringAlgorithmsSelector<V, E, G, C> withColors( Set<C> colors );

}
