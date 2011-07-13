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

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.collections.FibonacciHeap;

/**
 * Contains the A* shortest path algorithm implementation.
 */
public final class AStar
{

    /**
     * This class can not be instantiated directly
     */
    private AStar()
    {
        // do nothing
    }

    /**
     * Applies the classical A* algorithm to find the shortest path from the source to the target, if exists.
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type
     * @param graph the Graph which shortest path from {@code source} to {@code target} has to be found
     * @param start the shortest path source Vertex
     * @param goal the shortest path target Vertex
     * @param heuristic the <i>h(x)</i> function
     * @return a path which describes the shortest path, if any, otherwise a {@link PathNotFoundException} will be thrown
     */
    public static <V extends Vertex, WE extends WeightedEdge> WeightedPath<V, WE> findShortestPath( WeightedGraph<V, WE> graph,
                                                                                                       V start,
                                                                                                       V goal,
                                                                                                       Heuristic<V> heuristic )
    {
        // Cost from start along best known path.
        final ShortestDistances<V> gScores = new ShortestDistances<V>();
        gScores.setWeight( start, 0D );

        // Estimated total cost from start to goal through y.
        final ShortestDistances<V> fScores = new ShortestDistances<V>();
        Double hScore = heuristic.applyHeuristic( start, goal );
        fScores.setWeight( start, hScore );

        // The set of nodes already evaluated.
        final Set<V> closedSet = new HashSet<V>();

        // The set of tentative nodes to be evaluated.
        final Queue<V> openSet = new FibonacciHeap<V>( fScores );
        openSet.add( start );

        // The of navigated nodes
        final PredecessorsList<V, WE> predecessors = new PredecessorsList<V, WE>( graph );

        // extract the node in openset having the lowest f_score[] value
        while ( !openSet.isEmpty() )
        {
            V current = openSet.remove();

            // destination reached, stop and build the path
            if ( goal.equals( current ) )
            {
                return predecessors.buildPath( start, goal );
            }

            closedSet.add( current );

            @SuppressWarnings( "unchecked" )
            Iterable<V> connected = ( graph instanceof DirectedGraph ) ? ( (DirectedGraph<V, WE>) graph ).getOutbound( current )
                                                                       : graph.getConnectedVertices( current );
            for ( V v : connected )
            {
                if ( !closedSet.contains( v ) )
                {
                    WE edge = graph.getEdge( current, v );
                    Double tentativeGScore = gScores.getWeight( current ) + edge.getWeight();

                    if ( openSet.add( v ) || tentativeGScore.compareTo( gScores.getWeight( v ) ) < 0 )
                    {
                        predecessors.addPredecessor( v, current );
                        gScores.setWeight( v, tentativeGScore );
                        hScore = heuristic.applyHeuristic( v, goal );
                        fScores.setWeight( v, gScores.getWeight( v ) + hScore );
                    }
                }
            }
        }

        throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist in Graph '%s'", start, goal, graph );
    }

}
