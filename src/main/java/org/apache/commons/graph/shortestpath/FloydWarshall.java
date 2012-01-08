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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.UndirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.weight.OrderedMonoid;
import org.apache.commons.graph.weight.primitive.DoubleWeight;

/**
 * Contains the Floyd-Warshall's shortest paths algorithm implementation.
 */
public final class FloydWarshall
{

    /**
     * This class can't be explicitly instantiated.
     */
    private FloydWarshall()
    {
        // do nothing
    }

    /**
     * Applies the classical Floyd-Warshall's algorithm to find all vertex shortest path
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type
     * @param <W> the weight type
     * @param graph the input graph
     * @param orderedMonoid the {@link OrderedMonoid} needed to handle operations on weights
     * @return a data structure which contains all vertex pairs shortest path.
     */
    public static <V extends Vertex, W, WE extends WeightedEdge<W>> AllVertexPairsShortestPath<V, WE, W> findAllVertexPairsShortestPath( Graph<V, WE> graph, OrderedMonoid<W> orderedMonoid )
    {
        AllVertexPairsShortestPath<V, WE, W> shortestPaths = new AllVertexPairsShortestPath<V, WE, W>( orderedMonoid );
        Map<VertexPair<V>, V> next = new HashMap<VertexPair<V>, V>();

        // init
        for ( WE we : graph.getEdges() )
        {
            VertexPair<V> vertexPair = graph.getVertices( we );
            shortestPaths.addShortestDistance( vertexPair.getHead(), vertexPair.getTail(), we.getWeight() );

            if ( graph instanceof UndirectedGraph )
            {
                shortestPaths.addShortestDistance( vertexPair.getTail(), vertexPair.getHead(), we.getWeight() );
            }
        }

        // run the Floyd-Warshall algorithm.
        for ( V k : graph.getVertices() )
        {
            for ( V i : graph.getVertices() )
            {
                for ( V j : graph.getVertices() )
                {
                    if ( shortestPaths.hasShortestDistance( i, k ) && shortestPaths.hasShortestDistance( k, j ) )
                    {
                        W newDistance = orderedMonoid.append( shortestPaths.getShortestDistance( i, k ), shortestPaths.getShortestDistance( k, j ) );
                        if ( !shortestPaths.hasShortestDistance( i, j )
                                || orderedMonoid.compare( newDistance, shortestPaths.getShortestDistance( i, j ) ) < 0 )
                        {
                            shortestPaths.addShortestDistance( i, j, newDistance );

                            // store the intermediate vertex
                            next.put( new VertexPair<V>( i, j ), k );
                        }
                    }

                }
            }
        }

        // fills all WeightedPaths
        for ( V source : graph.getVertices() )
        {
            for ( V target : graph.getVertices() )
            {
                if ( !source.equals( target ) )
                {
                    PredecessorsList<V, WE, W> predecessorsList = new PredecessorsList<V, WE, W>( graph, orderedMonoid );

                    pathReconstruction( predecessorsList, source, target, next, graph );
                    if ( !predecessorsList.isEmpty() )
                    {
                        WeightedPath<V, WE, W> weightedPath = predecessorsList.buildPath( source, target );
                        if ( weightedPath.getOrder() > 0 )
                        {
                            shortestPaths.addShortestPath( source, target, weightedPath );
                        }
                    }
                }
            }
        }

        return shortestPaths;
    }

    /**
     * Applies the classical Floyd-Warshall's algorithm to an edge weighted graph with weights of type Double
     * to find all vertex shortest path.
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type
     * @param graph the input graph
     * @return a data structure which contains all vertex pairs shortest path.
     */
    public static <V extends Vertex, WE extends WeightedEdge<Double>> AllVertexPairsShortestPath<V, WE, Double> findAllVertexPairsShortestPath( Graph<V, WE> graph )
    {
        return findAllVertexPairsShortestPath( graph, new DoubleWeight() );
    }

    private static <V extends Vertex, WE extends WeightedEdge<W>, W> void pathReconstruction( PredecessorsList<V, WE, W> path,
                                                                                                V source, V target,
                                                                                                Map<VertexPair<V>, V> next,
                                                                                                Graph<V, WE> graph )
    {
        V k = next.get( new VertexPair<Vertex>( source, target ) );
        if ( k == null )
        {
            // there is a direct path between a and b
            WE edge = graph.getEdge( source, target );
            if ( edge != null )
            {
                path.addPredecessor( target, source );
            }
        }
        else
        {
            pathReconstruction( path, source, k, next, graph );
            pathReconstruction( path, k, target, next, graph );
        }
    }

}
