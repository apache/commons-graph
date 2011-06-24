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

import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.model.VertexPair;

/**
 * Contains the Floyd�Warshall's shortest paths algorithm implementation.
 */
public final class FloydWarshall
{

    /**
     * This class can't be explicitly instantiated.
     */
    private FloydWarshall()
    {
        // do nothgin
    }

    /**
     * Applies the classical Floyd�Warshall's algorithm to find all vertex shortest path
     * 
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type
     * @return a data structure which contains all vertex pairs shortest path.
     */
    public static <V extends Vertex, WE extends WeightedEdge<V>> AllVertexPairsShortestPath<V, WE> findAllVertexPairsShortestPath( WeightedGraph<V, WE> graph )
    {
        AllVertexPairsShortestPath<V, WE> shortesPaths = new AllVertexPairsShortestPath<V, WE>();
        Map<VertexPair<V>, V> next = new HashMap<VertexPair<V>, V>();

        // init
        for ( WE we : graph.getEdges() )
        {
            shortesPaths.addShortestDistance( we.getHead(), we.getTail(), we.getWeight() );
        }

        // run the Floyd-Warshall algorithm.

        for ( V k : graph.getVertices() )
        {
            for ( V i : graph.getVertices() )
            {
                for ( V j : graph.getVertices() )
                {
                    Double newDistance =
                        shortesPaths.getShortestDistance( i, k ) + shortesPaths.getShortestDistance( k, j );
                    if ( newDistance < shortesPaths.getShortestDistance( i, j ) )
                    {
                        shortesPaths.addShortestDistance( i, j, newDistance );

                        // store the intermediate vertex
                        next.put( new VertexPair<V>( i, j ), k );
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
                    PredecessorsList<V, WE> predecessorsList = new PredecessorsList<V, WE>();

                    pathReconstruction( predecessorsList, source, target, next, graph );
                    if ( !predecessorsList.predecessors.isEmpty() )
                    {
                        WeightedPath<V, WE> weightedPath = predecessorsList.buildPath( source, target );
                        if ( weightedPath.size() > 0 )
                        {
                            shortesPaths.addShortestPath( source, target, weightedPath );
                        }
                    }

                }
            }
        }

        return shortesPaths;
    }

    private static <V extends Vertex, WE extends WeightedEdge<V>> void pathReconstruction( PredecessorsList<V, WE> path,
                                                                                           V source, V target,
                                                                                           Map<VertexPair<V>, V> next,
                                                                                           WeightedGraph<V, WE> graph )
    {
        V k = next.get( new VertexPair<Vertex>( source, target ) );
        if ( k == null )
        {
            // there is a direct path between a and b
            WE edge = graph.getEdge( source, target );
            if ( edge != null )
            {
                path.addPredecessor( target, edge );
            }
        }
        else
        {
            pathReconstruction( path, source, k, next, graph );
            pathReconstruction( path, k, target, next, graph );
        }

    }

}
