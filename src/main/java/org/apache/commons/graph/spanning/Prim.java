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

import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.UndirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;

/**
 * Prim's algorithm is a greedy algorithm that finds a minimum spanning tree for a connected weighted undirected graph.
 */
public final class Prim
{

    /**
     * Calculates the minimum spanning tree of the input Graph,
     * selecting the Vertex source from the input Graph.
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type.
     * @param <G> the weighted-undirected input graph type
     * @param graph the Graph for which minimum spanning tree has to be calculated.
     * @return the minimum spanning tree of the input Graph.
     */
    public static <V extends Vertex, WE extends WeightedEdge, G extends UndirectedGraph<V, WE>> SpanningTree<V, WE> minimumSpanningTree( G graph )
    {
        return minimumSpanningTree( graph, graph.getVertices().iterator().next() );
    }

    /**
     * Calculates the minimum spanning tree of the input Graph, given a known Vertex source.
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type.
     * @param <G> the weighted-undirected input graph type
     * @param graph the Graph for which minimum spanning tree has to be calculated.
     * @param source the Prim's Vertex source
     * @return the minimum spanning tree of the input Graph.
     */
    public static <V extends Vertex, WE extends WeightedEdge, G extends UndirectedGraph<V, WE>> SpanningTree<V, WE> minimumSpanningTree( G graph,
                                                                                                                                         V source )
    {
        final ShortestEdges<V, WE> shortesEdges = new ShortestEdges<V, WE>( graph, source );

        final PriorityQueue<V> unsettledNodes = new PriorityQueue<V>( graph.getOrder(), shortesEdges );
        unsettledNodes.add( source );

        final Set<WE> settledEdges = new HashSet<WE>();

        // extract the node with the shortest distance
        while ( !unsettledNodes.isEmpty() )
        {
            V vertex = unsettledNodes.remove();

            for ( V v : graph.getConnectedVertices( vertex ) )
            {
                WE edge = graph.getEdge( vertex, v );

                // if the edge has not been already visited and its weight is less then the current Vertex weight
                if ( settledEdges.add( edge ) && edge.getWeight().compareTo( shortesEdges.getWeight( v ) ) < 0 )
                {
                    if ( !unsettledNodes.contains( v ) )
                    {
                        unsettledNodes.add( v );
                    }

                    shortesEdges.addPredecessor( v, edge );
                }
            }
        }

        return shortesEdges.createSpanningTree();
    }

}
