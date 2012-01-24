package org.apache.commons.graph.flow;

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

import static org.apache.commons.graph.CommonsGraph.newDirectedMutableWeightedGraph;
import static org.apache.commons.graph.CommonsGraph.visit;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.weight.OrderedMonoid;
import org.apache.commons.graph.weight.primitive.IntegerWeight;

/**
 * Contains the implementation of the algorithm of Ford and Fulkerson
 * to calculate the maximum allowed flow in a graph.
 */
public final class FordFulkerson
{

    /**
     * This class can not be instantiated directly
     */
    private FordFulkerson()
    {
        // do nothing
    }

    /**
     * Applies the algorithm of Ford and Fulkerson to find the maximum flow on the input {@link Graph},
     * given a source and a target {@link Vertex}.
     * @param graph the input edge-weighted graph
     * @param source the source vertex
     * @param target the target vertex
     * @param orderedMonoid the ordered monoid needed for operations on edge weights
     * @return the maximum flow between source and target
     */
    public static <V extends Vertex, W, WE extends WeightedEdge<W>, G extends DirectedGraph<V, WE>> W findMaxFlow( final G graph,
                                                                                                                   V source,
                                                                                                                   V target,
                                                                                                                   final OrderedMonoid<W> orderedMonoid )
    {
        // create flow network
        DirectedGraph<V, WE> flowNetwork = newDirectedMutableWeightedGraph( new AbstractGraphConnection<V, WE>()
        {
            @SuppressWarnings( "unchecked" )
            @Override
            public void connect()
            {
                // vertices
                for ( V vertex : graph.getVertices() )
                {
                    addVertex( vertex );
                }
                // edges
                for ( WE edge : graph.getEdges() )
                {
                    VertexPair<V> edgeVertices = graph.getVertices( edge );
                    V head = edgeVertices.getHead();
                    V tail = edgeVertices.getTail();
                    addEdge( edge ).from( head ).to( tail );
                    addEdge( (WE) new BaseLabeledWeightedEdge<W>( "Inverse edge for " + edge.toString(), orderedMonoid.zero() ) );
                }
            }
        } );

        // create flow network handler
        FlowNetworkHandler<V, WE, W> flowNetworkHandler =
                        new FlowNetworkHandler<V, WE, W>( flowNetwork, source, target, orderedMonoid );

        // perform depth first search
        visit( flowNetwork ).from( source ).applyingDepthFirstSearch( flowNetworkHandler );

        while ( flowNetworkHandler.hasAugmentingPath() )
        {
            // update flow network
            flowNetworkHandler.updateResidualNetworkWithCurrentAugmentingPath();
            // look for another augmenting path
            visit( flowNetwork ).from( source ).applyingDepthFirstSearch( flowNetworkHandler );
        }

        return flowNetworkHandler.getMaxFlow();
    }

    /**
     * Applies the algorithm of Ford and Fulkerson to find the maximum flow on the input {@link Graph}
     * whose edges have weights of type Integer, given a source and a target {@link Vertex}.
     * @param graph the input edge-weighted graph
     * @param source the source vertex
     * @param target the target vertex
     * @return the maximum flow between source and target
     */
    public static <V extends Vertex, WE extends WeightedEdge<Integer>, G extends DirectedGraph<V, WE>> Integer findMaxFlow( G graph,
                                                                                                                            V source,
                                                                                                                            V target )
    {
        return findMaxFlow( graph, source, target, new IntegerWeight() );
    }

}
