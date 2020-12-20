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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.UndirectedGraph;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.Mapper;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.weight.OrderedMonoid;

final class DefaultPathSourceSelector<V, WE, W>
    implements PathSourceSelector<V, WE, W>
{

    private final Graph<V, WE> graph;

    private final Mapper<WE, W> weightedEdges;

    public DefaultPathSourceSelector(final Graph<V, WE> graph, final Mapper<WE, W> weightedEdges )
    {
        this.graph = graph;
        this.weightedEdges = weightedEdges;
    }

    /**
     * {@inheritDoc}
     */
    public <WO extends OrderedMonoid<W>> AllVertexPairsShortestPath<V, WE, W> applyingFloydWarshall( WO weightOperations )
    {
        weightOperations = checkNotNull( weightOperations, "Floyd-Warshall algorithm can not be applied using null weight operations" );

        final AllVertexPairsShortestPath<V, WE, W> shortestPaths = new AllVertexPairsShortestPath<V, WE, W>( weightOperations );
        final Map<VertexPair<V>, V> next = new HashMap<VertexPair<V>, V>();

        // init
        for ( final WE we : graph.getEdges() )
        {
            final VertexPair<V> vertexPair = graph.getVertices( we );
            shortestPaths.addShortestDistance( vertexPair.getHead(), vertexPair.getTail(), weightedEdges.map( we ) );

            if ( graph instanceof UndirectedGraph )
            {
                shortestPaths.addShortestDistance( vertexPair.getTail(), vertexPair.getHead(), weightedEdges.map( we ) );
            }
        }

        // run the Floyd-Warshall algorithm.
        for ( final V k : graph.getVertices() )
        {
            for ( final V i : graph.getVertices() )
            {
                for ( final V j : graph.getVertices() )
                {
                    if ( shortestPaths.hasShortestDistance( i, k ) && shortestPaths.hasShortestDistance( k, j ) )
                    {
                        final W newDistance = weightOperations.append( shortestPaths.getShortestDistance( i, k ), shortestPaths.getShortestDistance( k, j ) );
                        if ( !shortestPaths.hasShortestDistance( i, j )
                                || weightOperations.compare( newDistance, shortestPaths.getShortestDistance( i, j ) ) < 0 )
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
        for ( final V source : graph.getVertices() )
        {
            for ( final V target : graph.getVertices() )
            {
                if ( !source.equals( target ) )
                {
                    final PredecessorsList<V, WE, W> predecessorsList = new PredecessorsList<V, WE, W>( graph, weightOperations, weightedEdges );

                    pathReconstruction( predecessorsList, source, target, next );
                    if ( !predecessorsList.isEmpty() )
                    {
                        final WeightedPath<V, WE, W> weightedPath = predecessorsList.buildPath( source, target );
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

    private void pathReconstruction(final PredecessorsList<V, WE, W> path,
                                    final V source, final V target,
                                    final Map<VertexPair<V>, V> next )
    {
        final V k = next.get( new VertexPair<V>( source, target ) );
        if ( k == null )
        {
            // there is a direct path between a and b
            final WE edge = graph.getEdge( source, target );
            if ( edge != null )
            {
                path.addPredecessor( target, source );
            }
        }
        else
        {
            pathReconstruction( path, source, k, next );
            pathReconstruction( path, k, target, next );
        }
    }

    /**
     * {@inheritDoc}
     */
    public <H extends V> TargetSourceSelector<V, WE, W> from( H source )
    {
        source = checkNotNull( source, "Shortest path can not be calculated from a null source" );
        return new DefaultTargetSourceSelector<V, WE, W>( graph, weightedEdges, source );
    }

}