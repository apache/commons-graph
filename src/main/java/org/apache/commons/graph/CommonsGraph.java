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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import org.apache.commons.graph.visit.DefaultVisitSourceSelector;
import org.apache.commons.graph.visit.VisitSourceSelector;

/**
 * The Apache Commons Graph package is a toolkit for managing graphs and graph based data structures.
 */
public final class CommonsGraph<V extends Vertex, E extends Edge, G extends Graph<V, E>>
{

    /**
     * Allows select a series of algorithms to apply on input graph.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param <G> the Graph type
     * @param graph the Graph instance to apply graph algorithms
     * @return the graph algorithms selector
     */
    public static <V extends Vertex, E extends Edge, G extends Graph<V, E>> CommonsGraph<V, E, G> on( G graph )
    {
        graph = checkNotNull( graph, "No algorithm can be applied on null graph!" );
        return new CommonsGraph<V, E, G>( graph );
    }

    /**
     * The Graph instance to apply graph algorithms.
     */
    private final G graph;

    /**
     * Hidden constructor, this class cannot be instantiated.
     */
    private CommonsGraph( G graph )
    {
        this.graph = graph;
    }

    /**
     * Applies graph visit algorithms on input graph.
     *
     * @return the graph visit algorithms selector
     */
    public VisitSourceSelector<V, E, G> visit()
    {
        return new DefaultVisitSourceSelector<V, E, G>( graph );
    }

}
