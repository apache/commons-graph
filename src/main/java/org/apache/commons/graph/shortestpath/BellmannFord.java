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

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.weight.OrderedMonoid;
import org.apache.commons.graph.weight.primitive.DoubleWeight;

/**
 * Contains the Bellman-Ford's shortest path algorithm implementation.
 */
public final class BellmannFord
{

    /**
     * This class can not be instantiated directly
     */
    private BellmannFord()
    {
        // do nothing
    }

    /**
     * Applies the classical Bellman-Ford's algorithm to find the shortest path from the source to the target, if exists.
     *
     * @param <V> the Graph vertices type
     * @param <WE> the Graph weighted edges type
     * @param <W> the weight type
     * @param <G> the Graph type
     * @param graph the Graph whose shortest path from {@code source} to {@code target} has to be found
     * @param source the shortest path source Vertex
     * @param orderedMonoid the {@code OrderedMonoid} needed to handle weights
     * @return a path which describes the shortest path, if any, otherwise a {@link PathNotFoundException} will be thrown
     */
    public static <V extends Vertex, W, WE extends WeightedEdge<W>, G extends DirectedGraph<V, WE>> AllVertexPairsShortestPath<V, WE, W> findShortestPath( G graph,
                                                                                                                                                           V source,
                                                                                                                                                           OrderedMonoid<W> orderedMonoid )
    {
        final ShortestDistances<V, W> shortestDistances = new ShortestDistances<V, W>( orderedMonoid );
        shortestDistances.setWeight( source, orderedMonoid.zero() );

        final PredecessorsList<V, WE, W> predecessors = new PredecessorsList<V, WE, W>( graph, orderedMonoid );

        for ( int i = 0; i < graph.getOrder(); i++ )
        {
            for ( WE edge : graph.getEdges() )
            {
                VertexPair<V> vertexPair = graph.getVertices( edge );
                V u = vertexPair.getHead();
                V v = vertexPair.getTail();

                if ( shortestDistances.alreadyVisited( u ) )
                {
                    W shortDist = orderedMonoid.append( shortestDistances.getWeight( u ), edge.getWeight() );

                    if ( !shortestDistances.alreadyVisited( v )
                            || orderedMonoid.compare( shortDist, shortestDistances.getWeight( v ) ) < 0 )
                    {
                        // assign new shortest distance and mark unsettled
                        shortestDistances.setWeight( v, shortDist );

                        // assign predecessor in shortest path
                        predecessors.addPredecessor( v, u );
                    }
                }
            }
        }

        for ( WE edge : graph.getEdges() )
        {
            VertexPair<V> vertexPair = graph.getVertices( edge );
            V u = vertexPair.getHead();
            V v = vertexPair.getTail();

            if ( shortestDistances.alreadyVisited( u ) )
            {
                W shortDist = orderedMonoid.append( shortestDistances.getWeight( u ), edge.getWeight() );

                if ( !shortestDistances.alreadyVisited( v )
                        || orderedMonoid.compare( shortDist, shortestDistances.getWeight( v ) ) < 0 )
                {
                    // TODO it would be nice printing the cycle
                    throw new NegativeWeightedCycleException( "Graph contains a negative-weight cycle in vertex %s",
                                                              v, graph );
                }
            }
        }

        AllVertexPairsShortestPath<V, WE, W> allVertexPairsShortestPath = new AllVertexPairsShortestPath<V, WE, W>( orderedMonoid );

        for ( V target : graph.getVertices() )
        {
            if ( !source.equals( target ) )
            {
                WeightedPath<V, WE, W> weightedPath = predecessors.buildPath( source, target );
                allVertexPairsShortestPath.addShortestPath( source, target, weightedPath );
            }
        }

        return allVertexPairsShortestPath;
    }

    /**
     * Applies the classical Bellman-Ford's algorithm to an edge weighted graph with weights of type Double
     * to find the shortest path from the source to the target, if exists.
     *
     * @param <V> the Graph vertices type
     * @param <WE> the Graph weighted edges type
     * @param <G> the Graph type
     * @param graph the Graph whose shortest path from {@code source} to {@code target} has to be found
     * @param source the shortest path source Vertex
     * @return a path which describes the shortest path, if any, otherwise a {@link PathNotFoundException} will be thrown
     */
    public static <V extends Vertex, WE extends WeightedEdge<Double>, G extends DirectedGraph<V, WE>> AllVertexPairsShortestPath<V, WE, Double> findShortestPath( G graph,
                                                                                                                                                                  V source )
    {
        return findShortestPath( graph, source, new DoubleWeight() );
    }

}
