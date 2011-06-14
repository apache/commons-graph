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

import static java.lang.String.format;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.WeightedPath;

/**
 * 
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
     * Applies the classical Dijkstra algorithm to find the shortest path from the source to the target, if exists.
     *
     * @param <V>
     * @param <WE>
     * @param graph
     * @param source
     * @param target
     * @return
     */
    public static <V extends Vertex, WE extends WeightedEdge<V>> WeightedPath<V, WE> findShortestPath( WeightedGraph<V, WE> graph,
                                                                                                       V source,
                                                                                                       V target )
    {
        final ShortestDistances<V> shortestDistances = new ShortestDistances<V>();

        final PriorityQueue<V> unsettledNodes =
            new PriorityQueue<V>( graph.getVertices().size(), new ShortestDistanceComparator<V>( shortestDistances ) );
        unsettledNodes.add( source );

        final Set<V> settledNodes = new HashSet<V>();

        final Map<V, WE> predecessors = new HashMap<V, WE>();

        // the current node
        V vertex;

        // extract the node with the shortest distance
        while ( ( vertex = unsettledNodes.poll() ) != null )
        {
            assert !settledNodes.contains( vertex );

            // destination reached, stop and build the path
            if ( target == vertex )
            {
                InMemoryPath<V, WE> path = new InMemoryPath<V, WE>( source, target, shortestDistances.get( target ) );

                V v = target;
                while ( v != source )
                {
                    WE edge = predecessors.get( v );

                    path.addEdgeInHead( edge );
                    path.addVertexInHead( v );

                    v = edge.getHead();
                }

                return path;
            }

            settledNodes.add( vertex );

            for ( WE edge : graph.getEdges( vertex ) )
            {
                V v = edge.getTail();

                // skip node already settled
                if ( !settledNodes.contains( v ) )
                {
                    Double shortDist = shortestDistances.get( vertex ) + edge.getWeight();

                    if ( shortDist < shortestDistances.get( v ) )
                    {
                        // assign new shortest distance and mark unsettled
                        shortestDistances.put( v, shortDist );
                        unsettledNodes.add( v );

                        // assign predecessor in shortest path
                        predecessors.put( v, edge );
                    }
                }
            }
        }

        throw new PathNotFoundException( format( "Path from '%s' to '%s' doesn't exist in Graph '%s'", source, target,
                                                 graph ) );
    }

}
