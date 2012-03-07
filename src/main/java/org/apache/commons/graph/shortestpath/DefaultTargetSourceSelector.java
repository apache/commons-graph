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

import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.weight.OrderedMonoid;

final class DefaultTargetSourceSelector<V, WE, W, G extends WeightedGraph<V, WE, W>>
    implements TargetSourceSelector<V, WE, W, G>
{

    private final G graph;

    private final V source;

    public DefaultTargetSourceSelector( G graph, V source )
    {
        this.graph = graph;
        this.source = source;
    }

    /**
     * {@inheritDoc}
     */
    public <WO extends OrderedMonoid<W>> AllVertexPairsShortestPath<V, WE, W> applyingBelmannFord( WO weightOperations )
    {
        weightOperations = checkNotNull( weightOperations, "Belmann-Ford algorithm can not be applied using null weight operations" );

        final ShortestDistances<V, W> shortestDistances = new ShortestDistances<V, W>( weightOperations );
        shortestDistances.setWeight( source, weightOperations.zero() );

        final PredecessorsList<V, WE, W> predecessors = new PredecessorsList<V, WE, W>( graph, weightOperations );

        for ( int i = 0; i < graph.getOrder(); i++ )
        {
            for ( WE edge : graph.getEdges() )
            {
                VertexPair<V> vertexPair = graph.getVertices( edge );
                V u = vertexPair.getHead();
                V v = vertexPair.getTail();

                if ( shortestDistances.alreadyVisited( u ) )
                {
                    W shortDist = weightOperations.append( shortestDistances.getWeight( u ), edge.getWeight() );

                    if ( !shortestDistances.alreadyVisited( v )
                            || weightOperations.compare( shortDist, shortestDistances.getWeight( v ) ) < 0 )
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
                W shortDist = weightOperations.append( shortestDistances.getWeight( u ), edge.getWeight() );

                if ( !shortestDistances.alreadyVisited( v )
                        || weightOperations.compare( shortDist, shortestDistances.getWeight( v ) ) < 0 )
                {
                    // TODO it would be nice printing the cycle
                    throw new NegativeWeightedCycleException( "Graph contains a negative-weight cycle in vertex %s",
                                                              v, graph );
                }
            }
        }

        AllVertexPairsShortestPath<V, WE, W> allVertexPairsShortestPath = new AllVertexPairsShortestPath<V, WE, W>( weightOperations );

        for ( V target : graph.getVertices() )
        {
            if ( !source.equals( target ) )
            {
                try
                {
                    WeightedPath<V, WE, W> weightedPath = predecessors.buildPath( source, target );
                    allVertexPairsShortestPath.addShortestPath( source, target, weightedPath );
                }
                catch ( PathNotFoundException e )
                {
                    continue;
                }
            }
        }

        return allVertexPairsShortestPath;
    }

    /**
     * {@inheritDoc}
     */
    public ShortestPathAlgorithmSelector<V, WE, W, G> to( V target )
    {
        target = checkNotNull( target, "Shortest path can not be calculated to a null target" );
        return new DefaultShortestPathAlgorithmSelector<V, WE, W, G>( graph, source, target );
    }

}
