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
import org.apache.commons.graph.elo.DefaultRankingSelector;
import org.apache.commons.graph.elo.GameResult;
import org.apache.commons.graph.elo.RankingSelector;
import org.apache.commons.graph.export.DefaultExportSelector;
import org.apache.commons.graph.export.NamedExportSelector;
import org.apache.commons.graph.flow.DefaultFlowWeightedEdgesBuilder;
import org.apache.commons.graph.flow.FlowWeightedEdgesBuilder;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.apache.commons.graph.scc.DefaultSccAlgorithmSelector;
import org.apache.commons.graph.scc.SccAlgorithmSelector;
import org.apache.commons.graph.shortestpath.DefaultWeightedEdgesSelector;
import org.apache.commons.graph.shortestpath.PathWeightedEdgesBuilder;
import org.apache.commons.graph.spanning.DefaultSpanningWeightedEdgeMapperBuilder;
import org.apache.commons.graph.spanning.SpanningWeightedEdgeMapperBuilder;
import org.apache.commons.graph.visit.DefaultVisitSourceSelector;
import org.apache.commons.graph.visit.VisitSourceSelector;

/**
 * The Apache Commons Graph package is a toolkit for managing graphs and graph based data structures.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 * @param <G> the Graph type
 */
public final class CommonsGraph<V, E, G extends Graph<V, E>>
{

    /**
     * Export the graph in DOT or GraphML format.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param <G> the Graph type
     * @param graph the input graph
     * @return an instance of {@link NamedExportSelector}
     */
    public static <V, E, G extends Graph<V, E>> NamedExportSelector<V, E> export( G graph )
    {
        graph = checkNotNull( graph, "Null graph can not be exported" );
        return new DefaultExportSelector<V, E>( graph );
    }

    /**
     * Create a color builder.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param <G> the Graph type
     * @param graph the input graph
     * @return an instance of {@link ColorsBuilder}
     */
    public static <V, E, G extends UndirectedGraph<V, E>> ColorsBuilder<V, E> coloring( G graph )
    {
        graph = checkNotNull( graph, "Coloring can not be calculated on null graph"  );
        return new DefaultColorsBuilder<V, E>( graph );
    }

    /**
     * Find the maximum flow on the input {@link Graph}.
     *
     * @param <V> the Graph vertices type
     * @param <WE> the Graph edges type
     * @param <G> the Graph type
     * @param graph the input edge-weighted graph
     * @return an instance of {@link FlowWeightedEdgesBuilder}
     */
    public static <V, WE, G extends DirectedGraph<V, WE>> FlowWeightedEdgesBuilder<V, WE> findMaxFlow( G graph )
    {
        graph = checkNotNull( graph, "Max flow can not be calculated on null graph" );
        return new DefaultFlowWeightedEdgesBuilder<V, WE>( graph );
    }

    /**
     * Find the minimum spanning tree on the input {@link Graph}
     *
     * @param <V> the Graph vertices type
     * @param <WE> the Graph edges type
     * @param <G> the Graph type
     * @param graph the input edge-weighted graph
     * @return the caluculated minimun spanning tree
     */
    public static <V, WE, G extends Graph<V, WE>> SpanningWeightedEdgeMapperBuilder<V, WE> minimumSpanningTree( G graph )
    {
        graph = checkNotNull( graph, "Minimum spanning tree can not be calculated on null graph" );
        return new DefaultSpanningWeightedEdgeMapperBuilder<V, WE>( graph );
    }

    /**
     * Find the sortest on the input {@link Graph}
     *
     * @param <V> the Graph vertices type
     * @param <WE> the Graph edges type
     * @param <G> the Graph type
     * @param graph the input edge-weighted graph
     * @return the caluculated the sortest
     */
    public static <V, WE, G extends Graph<V, WE>> PathWeightedEdgesBuilder<V, WE> findShortestPath( G graph )
    {
        graph = checkNotNull( graph, "Shortest path can not be calculated on null graph" );
        return new DefaultWeightedEdgesSelector<V, WE>( graph );
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
    public static <V, E, G extends DirectedGraph<V, E>> SccAlgorithmSelector<V, E> findStronglyConnectedComponent( G graph )
    {
        graph = checkNotNull( graph, "Strongly Connected Component cannot be calculated from a null graph" );
        return new DefaultSccAlgorithmSelector<V, E>( graph );
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
    public static <V, E, G extends Graph<V, E>> ConnectivityBuilder<V, E> findConnectedComponent( G graph )
    {
        graph = checkNotNull( graph, "Connected Component cannot be calculated from a null graph" );
        return new DefaultConnectivityBuilder<V, E>( graph );
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
    public static <V, E, G extends Graph<V, E>> VisitSourceSelector<V, E, G> visit( G graph )
    {
        graph = checkNotNull( graph, "No algorithm can be applied on null graph!" );
        return new DefaultVisitSourceSelector<V, E, G>( graph );
    }

    /**
     * Ranks the players (vertices) that took part in a tournament (graph) depending on the game results (edges),
     * applying the <a href="http://en.wikipedia.org/wiki/Elo_rating_system.">Elo Rating System</a>.
     *
     * @param <P> the players involved in the tournament
     * @param <TG> the Tournament Graph type
     * @param tournamentGraph the graph representing the tournament
     * @return the builder for the functor which returns/update the players ranking
     */
    public static <P, TG extends DirectedGraph<P, GameResult>> RankingSelector<P> eloRate( TG tournamentGraph )
    {
        tournamentGraph = checkNotNull( tournamentGraph, "ELO ranking can not be applied on null graph!" );
        return new DefaultRankingSelector<P>( tournamentGraph );
    }

    /**
     * Creates a new {@link DirectedMutableGraph} instance where vertices
     * are connected as described in the input {@link GraphConnection} instance.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graphConnection the {@link GraphConnection} instance that describes vertices
     * @return a new {@link DirectedMutableGraph} instance
     */
    public static <V, E> DirectedMutableGraph<V, E> newDirectedMutableGraph( GraphConnection<V, E> graphConnection )
    {
        return populate( new DirectedMutableGraph<V, E>() ).withConnections( graphConnection );
    }

    /**
     * Creates a new {@link UndirectedMutableGraph} instance where vertices
     * are connected as described in the input {@link GraphConnection} instance.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graphConnection the {@link GraphConnection} instance that describes vertices
     * @return a new {@link UndirectedMutableGraph} instance
     */
    public static <V, E> UndirectedMutableGraph<V, E> newUndirectedMutableGraph( GraphConnection<V, E> graphConnection )
    {
        return populate( new UndirectedMutableGraph<V, E>() ).withConnections( graphConnection );
    }

    /**
     * Allows populate the given {@link MutableGraph}.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param <G> the Graph type
     * @param graph the graph has to be populated
     * @return the builder to configure vertices connection
     */
    public static <V, E, G extends MutableGraph<V, E>> LinkedConnectionBuilder<V, E, G> populate( G graph )
    {
        return new DefaultLinkedConnectionBuilder<V, E, G>( checkNotNull( graph, "Impossible to configure null graph!" ) );
    }

    /**
     * Returns a synchronized (thread-safe) {@link Graph} backed by the specified Graph.
     *
     * It is imperative that the user manually synchronize on the returned graph when iterating over iterable collections:
     * <pre>
     *     Graph syncGraph = synchronize( graph );
     *         ...
     *     synchronized(syncGraph) {
     *         for ( Vertex v : g.getVertices() ) // Must be in synchronized block
     *         {
     *            foo( v )
     *         }
     *   }
     * </pre>
     *
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * The returned {@link Graph} will be serializable if the specified {@link Graph} is serializable.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the input {@link Graph}
     * @return the syncronyzed graph
     */
    public static <V, E> Graph<V, E> synchronize( Graph<V, E> graph )
    {
        return new SynchronizedGraph<V, E>( graph );
    }

    /**
     * Returns a synchronized (thread-safe) {@link DirectedGraph} backed by the specified Graph.
     *
     * It is imperative that the user manually synchronize on the returned graph when iterating over iterable collections:
     * <pre>
     *     Graph syncGraph = synchronize( graph );
     *         ...
     *     synchronized(syncGraph) {
     *         for ( Vertex v : g.getVertices() ) // Must be in synchronized block
     *         {
     *            foo( v )
     *         }
     *   }
     * </pre>
     *
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * The returned {@link Graph} will be serializable if the specified {@link Graph} is serializable.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the input {@link Graph}
     * @return the syncronyzed graph
     */
    public static <V, E> Graph<V, E> synchronize( DirectedGraph<V, E> graph )
    {
        return new SynchronizedDirectedGraph<V, E>( graph );
    }

    /**
     * Returns a synchronized (thread-safe) {@link UndirectedGraph} backed by the specified Graph.
     *
     * It is imperative that the user manually synchronize on the returned graph when iterating over iterable collections:
     * <pre>
     *     Graph syncGraph = synchronize( graph );
     *         ...
     *     synchronized(syncGraph) {
     *         for ( Vertex v : g.getVertices() ) // Must be in synchronized block
     *         {
     *            foo( v )
     *         }
     *   }
     * </pre>
     *
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * The returned {@link Graph} will be serializable if the specified {@link Graph} is serializable.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the input {@link Graph}
     * @return the syncronyzed graph
     */
    public static <V, E> Graph<V, E> synchronize( UndirectedGraph<V, E> graph )
    {
        return new SynchronizedUndirectedGraph<V, E>( graph );
    }

    /**
     * Returns a synchronized (thread-safe) {@link MutableGraph} backed by the specified Graph.
     *
     * It is imperative that the user manually synchronize on the returned graph when iterating over iterable collections:
     * <pre>
     *     Graph syncGraph = synchronize( graph );
     *         ...
     *     synchronized(syncGraph) {
     *         for ( Vertex v : g.getVertices() ) // Must be in synchronized block
     *         {
     *            foo( v )
     *         }
     *   }
     * </pre>
     *
     * Failure to follow this advice may result in non-deterministic behavior.
     *
     * The returned {@link Graph} will be serializable if the specified {@link Graph} is serializable.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the input {@link Graph}
     * @return the synchronized graph
     */
    public static <V, E> Graph<V, E> synchronize( MutableGraph<V, E> graph )
    {
        return new SynchronizedMutableGraph<V, E>( graph );
    }

    /**
     * Hidden constructor, this class cannot be instantiated.
     */
    private CommonsGraph()
    {
        // do nothing
    }

}
