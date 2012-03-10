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

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Mapper;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.collections.FibonacciHeap;
import org.apache.commons.graph.weight.OrderedMonoid;

final class DefaultShortestPathAlgorithmSelector<V, WE, W, G extends Graph<V, WE>>
    implements ShortestPathAlgorithmSelector<V, WE, W, G>
{

    private final G graph;

    private final Mapper<WE, W> weightedEdges;

    private final V source;

    private final V target;

    public DefaultShortestPathAlgorithmSelector( G graph, Mapper<WE, W> weightedEdges, V source, V target )
    {
        this.graph = graph;
        this.weightedEdges = weightedEdges;
        this.source = source;
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    public <WO extends OrderedMonoid<W>> HeuristicBuilder<V, WE, W, G, WO> applyingAStar( WO weightOperations )
    {
        weightOperations = checkNotNull( weightOperations, "A* algorithm can not be applied using null weight operations" );
        return new DefaultHeuristicBuilder<V, WE, W, G, WO>( graph, weightedEdges, source, target, weightOperations );
    }

    /**
     * {@inheritDoc}
     */
    public <WO extends OrderedMonoid<W>> WeightedPath<V, WE, W> applyingDijkstra( WO weightOperations )
    {
        weightOperations = checkNotNull( weightOperations, "Dijkstra algorithm can not be applied using null weight operations" );

        final ShortestDistances<V, W> shortestDistances = new ShortestDistances<V, W>( weightOperations );
        shortestDistances.setWeight( source, weightOperations.identity() );

        final Queue<V> unsettledNodes = new FibonacciHeap<V>( shortestDistances );
        unsettledNodes.add( source );

        final Set<V> settledNodes = new HashSet<V>();

        final PredecessorsList<V, WE, W> predecessors = new PredecessorsList<V, WE, W>( graph, weightOperations, weightedEdges );

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
                        W shortDist = weightOperations.append( shortestDistances.getWeight( vertex ), weightedEdges.map( edge ) );

                        if ( !shortestDistances.alreadyVisited( v )
                                || weightOperations.compare( shortDist, shortestDistances.getWeight( v ) ) < 0 )
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

}
