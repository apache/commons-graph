package org.apache.commons.graph.spanning;

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
import java.util.PriorityQueue;
import java.util.Set;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.shared.ShortestDistances;

/**
 * Prim's algorithm is a greedy algorithm that finds a minimum spanning tree for a connected weighted undirected graph.
 */
public final class Prim
{

    /**
     * Calculates the minimum spanning tree (or forest) of the input Graph.
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type.
     * @param graph the Graph for which minimum spanning tree (or forest) has to be calculated.
     * @return  the minimum spanning tree (or forest) of the input Graph.
     */
    public static <V extends Vertex, WE extends WeightedEdge> WeightedGraph<V, WE> minimumSpanningTree( WeightedGraph<V, WE> graph,
                                                                                                        V source )
    {
        final ShortestDistances<V> shortestDistances = new ShortestDistances<V>();
        shortestDistances.setWeight( source, 0D );

        final PriorityQueue<V> unsettledNodes = new PriorityQueue<V>( graph.getOrder(), shortestDistances );
        unsettledNodes.offer( source );

        final Set<V> settledNodes = new HashSet<V>();

        // extract the node with the shortest distance
        while ( !unsettledNodes.isEmpty() )
        {
            V vertex = unsettledNodes.poll();

            @SuppressWarnings( "unchecked" ) // Vertex/Edge type driven by input class
            Iterable<V> connectedVertices = ( graph instanceof DirectedGraph )
                                            ? ( ( DirectedGraph<V, WE> ) graph ).getOutbound( vertex )
                                            : graph.getConnectedVertices( vertex );

            for ( V v : connectedVertices )
            {
                // skip node already settled
                if ( !settledNodes.contains( v ) )
                {
                    WE edge = graph.getEdge( vertex, v );

                    if ( edge.getWeight().compareTo( shortestDistances.getWeight( v ) ) < 0 )
                    {
                        // assign new shortest distance and mark unsettled
                        shortestDistances.setWeight( v, edge.getWeight() );
                        unsettledNodes.offer( v );

                        // TODO assign predecessor in shortest path
                    }
                }
            }
        }

        return null;
    }

}
