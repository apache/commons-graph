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
import org.apache.commons.graph.coloring.ColorsBuilder;
import org.apache.commons.graph.coloring.DefaultColorsBuilder;
import org.apache.commons.graph.connectivity.ConnectivityBuilder;
import org.apache.commons.graph.connectivity.DefaultConnectivityBuilder;
import org.apache.commons.graph.export.DefaultToStreamBuilder;
import org.apache.commons.graph.export.ToStreamBuilder;
import org.apache.commons.graph.flow.DefaultFromHeadBuilder;
import org.apache.commons.graph.flow.FromHeadBuilder;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.apache.commons.graph.model.DirectedMutableWeightedGraph;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.apache.commons.graph.model.UndirectedMutableWeightedGraph;
import org.apache.commons.graph.scc.DefaultSccAlgorithmSelector;
import org.apache.commons.graph.scc.SccAlgorithmSelector;
import org.apache.commons.graph.shortestpath.DefaultPathSourceSelector;
import org.apache.commons.graph.shortestpath.PathSourceSelector;
import org.apache.commons.graph.spanning.DefaultSpanningTreeSourceSelector;
import org.apache.commons.graph.spanning.SpanningTreeSourceSelector;
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

    public static <V extends Vertex, E extends Edge, G extends UndirectedGraph<V, E>> ColorsBuilder<V, E, G> coloring( G graph )
    {
        graph = checkNotNull( graph, "Coloring can not be calculated on null graph"  );
        return new DefaultColorsBuilder<V, E, G>( graph );
    }

    /**
     * Find the maximum flow on the input {@link Graph}.
     *
     * @param <V>
     * @param <WE>
     * @param <W>
     * @param <G>
     * @param graph the input edge-weighted graph
     * @return
     */
    public static <V extends Vertex, WE extends WeightedEdge<W>, W, G extends DirectedGraph<V, WE> & WeightedGraph<V, WE, W>> FromHeadBuilder<V, WE, W, G> findMaxFlow( G graph )
    {
        graph = checkNotNull( graph, "Max flow can not be calculated on null graph" );
        return new DefaultFromHeadBuilder<V, WE, W, G>( graph );
    }

    /**
     *
     * @param graph
     * @return
     */
    public static <V extends Vertex, WE extends WeightedEdge<W>, W, G extends WeightedGraph<V, WE, W>> SpanningTreeSourceSelector<V, W, WE, G> minimumSpanningTree( G graph )
    {
        graph = checkNotNull( graph, "Minimum spanning tree can not be calculated on null graph" );
        return new DefaultSpanningTreeSourceSelector<V, W, WE, G>( graph );
    }

    /**
     *
     *
     * @param <V>
     * @param <W>
     * @param <WE>
     * @param <G>
     * @param graph
     */
    public static <V extends Vertex, WE extends WeightedEdge<W>, W, G extends WeightedGraph<V, WE, W>> PathSourceSelector<V, WE, W, G> findShortestPath( G graph )
    {
        graph = checkNotNull( graph, "Minimum spanning tree can not be calculated on null graph" );
        return new DefaultPathSourceSelector<V, WE, W, G>( graph );
    }

    /**
     * Calculates the input graph Strongly Connected Component.
     *
     * @param <V> the Graph vertices type.
     * @param <E> the Graph edges type.
     * @param <G> the directed graph type
     * @param graph the Graph which strongly connected component has to be verified.
     * @return the SCC algoritm selector
     */
    public static <V extends Vertex, E extends Edge, G extends DirectedGraph<V, E>> SccAlgorithmSelector<V, E, G> findStronglyConnectedComponent( G graph )
    {
        graph = checkNotNull( graph, "Strongly Connected Component cannot be calculated from a null graph" );
        return new DefaultSccAlgorithmSelector<V, E, G>( graph );
    }

    /**
     * Calculates the input graph Connected Component.
     *
     * @param <V> the Graph vertices type.
     * @param <E> the Graph edges type.
     * @param <G> the directed graph type
     * @param graph the Graph which connected component has to be verified.
     * @return the Connectivity algorithm builder
     */
    public static <V extends Vertex, E extends Edge, G extends Graph<V, E>> ConnectivityBuilder<V, E, G> findConnectedComponent( G graph )
    {
        graph = checkNotNull( graph, "Connected Component cannot be calculated from a null graph" );
        return new DefaultConnectivityBuilder<V, E, G>( graph );
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
