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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.WeightedEdges;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.collections.FibonacciHeap;
import org.apache.commons.graph.weight.OrderedMonoid;

final class DefaultHeuristicBuilder<V, WE, W, G extends Graph<V, WE>, WO extends OrderedMonoid<W>>
    implements HeuristicBuilder<V, WE, W, G, WO>
{

    private final G graph;

    private final WeightedEdges<WE, W> weightedEdges;

    private final V start;

    private final V goal;

    private final WO weightOperations;

    public DefaultHeuristicBuilder( G graph, WeightedEdges<WE, W> weightedEdges, V source, V target, WO weightOperations )
    {
        this.graph = graph;
        this.weightedEdges = weightedEdges;
        this.start = source;
        this.goal = target;
        this.weightOperations = weightOperations;
    }

    /**
     * {@inheritDoc}
     */
    public <H extends Heuristic<V, W>> WeightedPath<V, WE, W> withHeuristic( H heuristic )
    {
        heuristic = checkNotNull( heuristic, "A* algorithm can not be applied using a null heuristic" );

        // Cost from start along best known path.
        final ShortestDistances<V, W> gScores = new ShortestDistances<V, W>( weightOperations );
        gScores.setWeight( start, weightOperations.zero() );

        // Estimated total cost from start to goal through y.
        final ShortestDistances<V, W> fScores = new ShortestDistances<V, W>( weightOperations );
        W hScore = heuristic.applyHeuristic( start, goal );
        fScores.setWeight( start, hScore );

        // The set of nodes already evaluated.
        final Set<V> closedSet = new HashSet<V>();

        // The set of tentative nodes to be evaluated.
        final Queue<V> openSet = new FibonacciHeap<V>( fScores );
        openSet.add( start );

        // The of navigated nodes
        final PredecessorsList<V, WE, W> predecessors = new PredecessorsList<V, WE, W>( graph, weightOperations );

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

            Iterable<V> connected = ( graph instanceof DirectedGraph ) ? ( (DirectedGraph<V, WE>) graph ).getOutbound( current )
                                                                       : graph.getConnectedVertices( current );
            for ( V v : connected )
            {
                if ( !closedSet.contains( v ) )
                {
                    WE edge = graph.getEdge( current, v );
                    // note that the weight of current can never be undefined
                    W tentativeGScore = weightOperations.append( gScores.getWeight( current ), weightedEdges.getWeightForEdge( edge ) );

                    // if the first condition fails, v has already been visited (its weight is defined)
                    if ( openSet.add( v ) || weightOperations.compare( tentativeGScore, gScores.getWeight( v ) ) < 0 )
                    {
                        predecessors.addPredecessor( v, current );
                        gScores.setWeight( v, tentativeGScore );
                        hScore = heuristic.applyHeuristic( v, goal );
                        fScores.setWeight( v, weightOperations.append( gScores.getWeight( v ), hScore ) );
                    }
                }
            }
        }

        throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist in Graph '%s'", start, goal, graph );
    }

}
