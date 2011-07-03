package org.apache.commons.graph.shortestpath;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.shared.ShortestDistances;

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

/**
 * Contains the Bellman–Ford's shortest path algorithm implementation.
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
     * Applies the classical Bellman–Ford's algorithm to find the shortest path from the source to the target, if exists.
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type
     * @param graph the Graph which shortest path from {@code source} to {@code target} has to be found
     * @param source the shortest path source Vertex
     * @param target the shortest path target Vertex
     * @return a path which describes the shortest path, if any, otherwise a {@link PathNotFoundException} will be thrown
     */
    public static <V extends Vertex, WE extends WeightedEdge, G extends WeightedGraph<V, WE> & DirectedGraph<V, WE>> AllVertexPairsShortestPath<V, WE> findShortestPath( G graph,
                                                                                                                                                                         V source)
    {
        final ShortestDistances<V> shortestDistances = new ShortestDistances<V>();
        shortestDistances.setWeight( source, 0D );

        final PredecessorsList<V, WE> predecessors = new PredecessorsList<V, WE>( graph );

        for ( int i = 0; i < graph.getOrder(); i++ )
        {
            for ( WE edge : graph.getEdges() )
            {
                VertexPair<V> vertexPair = graph.getVertices( edge );
                V u = vertexPair.getHead();
                V v = vertexPair.getTail();

                Double shortDist = shortestDistances.getWeight( u ) + edge.getWeight();

                if ( shortDist.compareTo( shortestDistances.getWeight( v ) ) < 0 )
                {
                    // assign new shortest distance and mark unsettled
                    shortestDistances.setWeight( v, shortDist );

                    // assign predecessor in shortest path
                    predecessors.addPredecessor( v, u );
                }
            }
        }

        for ( WE edge : graph.getEdges() )
        {
            VertexPair<V> vertexPair = graph.getVertices( edge );
            V u = vertexPair.getHead();
            V v = vertexPair.getTail();

            Double shortDist = shortestDistances.getWeight( u ) + edge.getWeight();

            if ( shortDist.compareTo( shortestDistances.getWeight( v ) ) < 0 )
            {
                // TODO it would be nice printing the cycle
                throw new NegativeWeightedCycleException( "Graph contains a negative-weight cycle in vertex %s",
                                                          v, graph );
            }
        }

        AllVertexPairsShortestPath<V, WE> allVertexPairsShortestPath = new AllVertexPairsShortestPath<V, WE>();

        for ( V target : graph.getVertices() )
        {
            if ( !source.equals( target ) )
            {
                WeightedPath<V, WE> weightedPath = predecessors.buildPath( source, target );
                allVertexPairsShortestPath.addShortestPath( source, target, weightedPath );
            }
        }

        return allVertexPairsShortestPath;
    }

}
