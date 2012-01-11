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
import org.apache.commons.graph.weight.OrderedMonoid;
import org.apache.commons.graph.weight.primitive.DoubleWeight;

/**
 * Prim's algorithm is a greedy algorithm that finds a minimum spanning tree for a connected weighted undirected graph.
 */
public final class Prim
{
    
    /**
     * Calculates the minimum spanning tree of the input Graph,
     * selecting the Vertex source from the input Graph.
     *
     * @param <V> the Graph vertices type
     * @param <WE> the Graph weighted edges type
     * @param <W> the weight type
     * @param <G> the weighted-undirected input graph type
     * @param graph the Graph for which minimum spanning tree has to be calculated
     * @param orderedMonoid the OrderedMonoid needed to handle operations on weights
     * @return the minimum spanning tree of the input Graph
     */
    public static <V extends Vertex, WE extends WeightedEdge<W>, W, G extends UndirectedGraph<V, WE>> SpanningTree<V, WE, W> minimumSpanningTree( G graph,
                                                                                                                                                  OrderedMonoid<W> orderedMonoid)
    {
        return minimumSpanningTree( graph, graph.getVertices().iterator().next(), orderedMonoid );
    }

    /**
     * Calculates the minimum spanning tree of the input Graph, given a known Vertex source.
     *
     * @param <V> the Graph vertices type
     * @param <WE> the Graph weighted edges type
     * @param <W> the weight type
     * @param <G> the weighted-undirected input graph type
     * @param graph the Graph for which minimum spanning tree has to be calculated
     * @param source the Prim's Vertex source
     * @param orderedMonoid the OrderedMonoid needed to handle operations on weights
     * @return the minimum spanning tree of the input Graph
     */
    public static <V extends Vertex, WE extends WeightedEdge<W>, W, G extends UndirectedGraph<V, WE>> SpanningTree<V, WE, W> minimumSpanningTree( G graph,
                                                                                                                                                  V source,
                                                                                                                                                  OrderedMonoid<W> orderedMonoid )
    {
        final ShortestEdges<V, WE, W> shortestEdges = new ShortestEdges<V, WE, W>( graph, source, orderedMonoid );

        final PriorityQueue<V> unsettledNodes = new PriorityQueue<V>( graph.getOrder(), shortestEdges );
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
                boolean weightLessThanCurrent = !shortestEdges.hasWeight( v )
                        || orderedMonoid.compare( edge.getWeight(), shortestEdges.getWeight( v ) ) < 0;
                if ( settledEdges.add( edge ) && weightLessThanCurrent )
                {
                    if ( !unsettledNodes.contains( v ) )
                    {
                        unsettledNodes.add( v );
                    }

                    shortestEdges.addPredecessor( v, edge );
                }
            }
        }

        return shortestEdges.createSpanningTree();
    }
    
    /**
     * Calculates the minimum spanning tree of the input edge weighted Graph with weights of type Double,
     * selecting the Vertex source from the input Graph.
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type.
     * @param <G> the weighted-undirected input graph type
     * @param graph the Graph for which minimum spanning tree has to be calculated.
     * @return the minimum spanning tree of the input Graph.
     */
    public static <V extends Vertex, WE extends WeightedEdge<Double>, G extends UndirectedGraph<V, WE>> SpanningTree<V, WE, Double> minimumSpanningTree( G graph )
    {
        return minimumSpanningTree( graph, graph.getVertices().iterator().next(), new DoubleWeight() );
    }
    
    /**
     * Calculates the minimum spanning tree of the input edge weighted Graph with weights of type Double, 
     * given a known Vertex source.
     *
     * @param <V> the Graph vertices type
     * @param <WE> the Graph weighted edges type
     * @param <G> the weighted-undirected input graph type
     * @param graph the Graph for which minimum spanning tree has to be calculated
     * @param source the Prim's Vertex source
     * @return the minimum spanning tree of the input Graph
     */
    public static <V extends Vertex, WE extends WeightedEdge<Double>, G extends UndirectedGraph<V, WE>> SpanningTree<V, WE, Double> minimumSpanningTree( G graph,
                                                                                                                                                         V source )
    {
        return minimumSpanningTree( graph, source, new DoubleWeight() );
    }

}
