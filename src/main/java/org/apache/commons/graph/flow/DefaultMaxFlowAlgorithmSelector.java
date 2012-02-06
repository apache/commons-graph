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
import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.weight.OrderedMonoid;

/**
 * {@link MaxFlowAlgorithmSelector} implementation.
 *
 * @param <V>
 * @param <W>
 * @param <WE>
 * @param <G>
 */
final class DefaultMaxFlowAlgorithmSelector<V extends Vertex, WE extends WeightedEdge<W>, W, G extends DirectedGraph<V, WE>>
    implements MaxFlowAlgorithmSelector<V, WE, W, G>
{

    private final G graph;

    private final V source;

    private final V target;

    public DefaultMaxFlowAlgorithmSelector( G graph, V source, V target )
    {
        this.graph = graph;
        this.source = source;
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    public <OM extends OrderedMonoid<W>> W applyingFordFulkerson( OM orderedMonoid )
    {
        final OM checkedOrderedMonoid = checkNotNull( orderedMonoid, "Weight monoid can not be null to find the max flow in the graph" );

        // create flow network
        final DirectedGraph<V, WeightedEdge<W>> flowNetwork = newFlowNetwok( graph, checkedOrderedMonoid );

        // create flow network handler
        final FlowNetworkHandler<V, W> flowNetworkHandler = new FlowNetworkHandler<V, W>( flowNetwork, source, target, checkedOrderedMonoid );

        // perform depth first search
        visit( flowNetwork ).from( source ).applyingDepthFirstSearch( flowNetworkHandler );

        while ( flowNetworkHandler.hasAugmentingPath() )
        {
            // update flow network
            flowNetworkHandler.updateResidualNetworkWithCurrentAugmentingPath();
            // look for another augmenting path with depth first search
            visit( flowNetwork ).from( source ).applyingDepthFirstSearch( flowNetworkHandler );
        }

        return flowNetworkHandler.onCompleted();
    }

    /**
     * {@inheritDoc}
     */
    public <OM extends OrderedMonoid<W>> W applyingEdmondsKarp( OM orderedMonoid )
    {
        final OM checkedOrderedMonoid = checkNotNull( orderedMonoid, "Weight monoid can not be null to find the max flow in the graph" );

        // create flow network
        final DirectedGraph<V, WeightedEdge<W>> flowNetwork = newFlowNetwok( graph, checkedOrderedMonoid );

        // create flow network handler
        final FlowNetworkHandler<V, W> flowNetworkHandler = new FlowNetworkHandler<V, W>( flowNetwork, source, target, checkedOrderedMonoid );

        // perform breadth first search
        visit( flowNetwork ).from( source ).applyingBreadthFirstSearch( flowNetworkHandler );

        while ( flowNetworkHandler.hasAugmentingPath() )
        {
            // update flow network
            flowNetworkHandler.updateResidualNetworkWithCurrentAugmentingPath();
            // look for another augmenting path with breadth first search
            visit( flowNetwork ).from( source ).applyingBreadthFirstSearch( flowNetworkHandler );
        }

        return flowNetworkHandler.onCompleted();
    }

    private <OM extends OrderedMonoid<W>> DirectedGraph<V, WeightedEdge<W>> newFlowNetwok( final G graph, final OM orderedMonoid )
    {
        return newDirectedMutableWeightedGraph( new AbstractGraphConnection<V, WeightedEdge<W>>()
        {
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

                    if ( graph.getEdge( tail, head ) == null )
                    {
                        // complete the flow network with a zero-capacity inverse edge
                        addEdge( new BaseLabeledWeightedEdge<W>( "Inverse edge for " + edge, orderedMonoid.zero() ) )
                            .from( tail ).to( head );
                    }
                }
            }
        } );
    }

}
