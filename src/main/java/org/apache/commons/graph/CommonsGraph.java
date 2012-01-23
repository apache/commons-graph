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

import org.apache.commons.graph.builder.DefaultLinkedConnectionBuilder;
import org.apache.commons.graph.builder.GraphConnection;
import org.apache.commons.graph.builder.LinkedConnectionBuilder;
import org.apache.commons.graph.export.DefaultToStreamBuilder;
import org.apache.commons.graph.export.ToStreamBuilder;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.apache.commons.graph.model.DirectedMutableWeightedGraph;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.apache.commons.graph.model.UndirectedMutableWeightedGraph;
import org.apache.commons.graph.visit.DefaultVisitSourceSelector;
import org.apache.commons.graph.visit.VisitSourceSelector;

/**
 * The Apache Commons Graph package is a toolkit for managing graphs and graph based data structures.
 */
public final class CommonsGraph<V extends Vertex, E extends Edge, G extends Graph<V, E>>
{

    public static <V extends Vertex, E extends Edge, G extends Graph<V, E>> ToStreamBuilder<V, E, G> export( G graph )
    {
        graph = checkNotNull( graph, "Null graph can not be exported" );
        return new DefaultToStreamBuilder<V, E, G>( graph );
    }

    /**
     * Allows select a series of algorithms to apply on input graph.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param <G> the Graph type
     * @param graph the Graph instance to apply graph algorithms
     * @return the graph algorithms selector
     */
    public static <V extends Vertex, E extends Edge, G extends Graph<V, E>> VisitSourceSelector<V, E, G> visit( G graph )
    {
        graph = checkNotNull( graph, "No algorithm can be applied on null graph!" );
        return new DefaultVisitSourceSelector<V, E, G>( graph );
    }

    /**
     * Creates a new {@link DirectedMutableGraph} instance where vertices
     * are connected as described in the input {@link GraphConnection} instance.
     *
     * @param graphConnection the {@link GraphConnection} instance that describes vertices
     * @return a new {@link DirectedMutableGraph} instance
     */
    public static <V extends Vertex, E extends Edge> DirectedMutableGraph<V, E> newDirectedMutableGraph( GraphConnection<V, E> graphConnection )
    {
        return populate( new DirectedMutableGraph<V, E>() ).withConnections( graphConnection );
    }

    /**
     * Creates a new {@link DirectedMutableWeightedGraph} instance where vertices
     * are connected as described in the input {@link GraphConnection} instance.
     *
     * @param graphConnection the {@link GraphConnection} instance that describes vertices
     * @return a new {@link DirectedMutableWeightedGraph} instance
     */
    public static <V extends Vertex, WE extends WeightedEdge<W>, W> DirectedMutableWeightedGraph<V, WE, W> newDirectedMutableWeightedGraph( GraphConnection<V, WE> graphConnection )
    {
        return populate( new DirectedMutableWeightedGraph<V, WE, W>() ).withConnections( graphConnection );
    }

    /**
     * Creates a new {@link UndirectedMutableGraph} instance where vertices
     * are connected as described in the input {@link GraphConnection} instance.
     *
     * @param graphConnection the {@link GraphConnection} instance that describes vertices
     * @return a new {@link UndirectedMutableGraph} instance
     */
    public static <V extends Vertex, E extends Edge> UndirectedMutableGraph<V, E> newUndirectedMutableGraph( GraphConnection<V, E> graphConnection )
    {
        return populate( new UndirectedMutableGraph<V, E>() ).withConnections( graphConnection );
    }

    /**
     * Creates a new {@link UndirectedMutableWeightedGraph} instance where vertices
     * are connected as described in the input {@link GraphConnection} instance.
     *
     * @param graphConnection the {@link GraphConnection} instance that describes vertices
     * @return a new {@link UndirectedMutableWeightedGraph} instance
     */
    public static <V extends Vertex, WE extends WeightedEdge<W>, W> UndirectedMutableWeightedGraph<V, WE, W> newUndirectedMutableWeightedGraph( GraphConnection<V, WE> graphConnection )
    {
        return populate( new UndirectedMutableWeightedGraph<V, WE, W>() ).withConnections( graphConnection );
    }

    /**
     * Allows populate the given {@link MutableGraph}.
     *
     * @param graph the graph has to be populated
     * @return the builder to configure vertices connection
     */
    public static <V extends Vertex, E extends Edge, G extends MutableGraph<V, E>> LinkedConnectionBuilder<V, E, G> populate( G graph )
    {
        return new DefaultLinkedConnectionBuilder<V, E, G>( checkNotNull( graph, "Impossible to configure null graph!" ) );
    }

    /**
     * Hidden constructor, this class cannot be instantiated.
     */
    private CommonsGraph()
    {
        // do nothing
    }

}
