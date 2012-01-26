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

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.collections.FibonacciHeap;
import org.apache.commons.graph.weight.OrderedMonoid;
import org.apache.commons.graph.weight.primitive.DoubleWeight;

/**
 * Contains the Dijkstra's shortest path algorithm implementation.
 */
public final class Dijkstra
{

    /**
     * This class can not be instantiated directly
     */
    private Dijkstra()
    {
        // do nothing
    }

    /**
     * Applies the classical Dijkstra's algorithm to find the shortest path from the source to the target, if exists.
     *
     * @param <V> the Graph vertices type
     * @param <W> the weight type
     * @param <WE> the Graph weighted edges type
     * @param <G> the Graph type
     * @param graph the Graph whose shortest path from {@code source} to {@code target} has to be found
     * @param source the shortest path source Vertex
     * @param target the shortest path target Vertex
     * @param orderedMonoid the {@link OrderedMonoid} needed to handle operations on weights
     * @return a path which describes the shortest path, if any,
     *         otherwise a {@link PathNotFoundException} will be thrown
     */
    public static <V extends Vertex, W, WE extends WeightedEdge<W>, G extends Graph<V, WE>> WeightedPath<V, WE, W> findShortestPath( G graph,
                                                                                                                                     V source,
                                                                                                                                     V target,
                                                                                                                                     OrderedMonoid<W> orderedMonoid )
    {
        final ShortestDistances<V, W> shortestDistances = new ShortestDistances<V, W>( orderedMonoid );
        shortestDistances.setWeight( source, orderedMonoid.zero() );

        final Queue<V> unsettledNodes = new FibonacciHeap<V>( shortestDistances );
        unsettledNodes.add( source );

        final Set<V> settledNodes = new HashSet<V>();

        final PredecessorsList<V, WE, W> predecessors = new PredecessorsList<V, WE, W>( graph, orderedMonoid );

        // extract the node with the shortest distance
        while ( !unsettledNodes.isEmpty() )
        {
            V vertex = unsettledNodes.remove();

            // destination reached, stop and build the path
            if ( target.equals( vertex ) )
            {
                return predecessors.buildPath( source, target );
            }

            settledNodes.add( vertex );

            for ( V v : graph.getConnectedVertices( vertex ) )
            {
                // skip node already settled
                if ( !settledNodes.contains( v ) )
                {
                    WE edge = graph.getEdge( vertex, v );
                    if ( shortestDistances.alreadyVisited( vertex ) )
                    {
                        W shortDist = orderedMonoid.append( shortestDistances.getWeight( vertex ), edge.getWeight() );

                        if ( !shortestDistances.alreadyVisited( v )
                                || orderedMonoid.compare( shortDist, shortestDistances.getWeight( v ) ) < 0 )
                        {
                            // assign new shortest distance and mark unsettled
                            shortestDistances.setWeight( v, shortDist );
                            unsettledNodes.add( v );

                            // assign predecessor in shortest path
                            predecessors.addPredecessor( v, vertex );
                        }
                    }

                }
            }
        }

        throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist in Graph '%s'", source, target, graph );
    }

    /**
     * Applies the classical Dijkstra's algorithm to an edge weighted graph with weights of type Double
     * to find the shortest path from the source to the target, if exists.
     *
     * @param <V> the Graph vertices type
     * @param <WE> the Graph weighted edges type
     * @param <G> the Graph type
     * @param graph the Graph whose shortest path from {@code source} to {@code target} has to be found
     * @param source the shortest path source Vertex
     * @param target the shortest path target Vertex
     * @return a path which describes the shortest path, if any,
     *         otherwise a {@link PathNotFoundException} will be thrown
     */
    public static <V extends Vertex, WE extends WeightedEdge<Double>, G extends Graph<V, WE>> WeightedPath<V, WE, Double> findShortestPath( G graph,
                                                                                                                                            V source,
                                                                                                                                            V target )
    {
        return findShortestPath( graph, source, target, new DoubleWeight() );
    }

}
