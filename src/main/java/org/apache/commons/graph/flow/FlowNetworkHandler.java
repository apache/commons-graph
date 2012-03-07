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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.shortestpath.PredecessorsList;
import org.apache.commons.graph.visit.BaseGraphVisitHandler;
import org.apache.commons.graph.weight.Monoid;

/**
 * Provides standard operations for max-flow algorithms,
 * like Ford-Fulkerson or Edmonds-Karp.
 *
 * @param <V> the vertex type
 * @param <W> the weight type
 */
class FlowNetworkHandler<V extends Vertex, W, WO extends Monoid<W> & Comparator<W>>
    extends BaseGraphVisitHandler<V, WeightedEdge<W>, DirectedGraph<V, WeightedEdge<W>>, W>
{

    private final DirectedGraph<V, WeightedEdge<W>> flowNetwork;

    private final V source;

    private final V target;

    private final WO weightOperations;

    private W maxFlow;

    private final Map<WeightedEdge<W>, W> residualEdgeCapacities = new HashMap<WeightedEdge<W>, W>();

    // these are new for each new visit of the graph
    private PredecessorsList<V, WeightedEdge<W>, W> predecessors;

    private boolean foundAugmentingPath;

    FlowNetworkHandler( DirectedGraph<V, WeightedEdge<W>> flowNetwork, V source, V target, WO weightOperations )
    {
        this.flowNetwork = flowNetwork;
        this.source = source;
        this.target = target;
        this.weightOperations = weightOperations;

        maxFlow = weightOperations.identity();

        for ( WeightedEdge<W> edge : flowNetwork.getEdges() )
        {
            residualEdgeCapacities.put( edge, edge.getWeight() );
        }

        predecessors = null;
    }

    /**
     * Checks whether there is an augmenting path in the flow network,
     * given the current residual capacities.
     * @return true if there is an augmenting path, false otherwise
     */
    boolean hasAugmentingPath()
    {
        return foundAugmentingPath;
    }

    /**
     * Updates the residual capacities in the flow network,
     * based on the most recent augmenting path.
     */
    void updateResidualNetworkWithCurrentAugmentingPath()
    {
        // build actual augmenting path
        WeightedPath<V, WeightedEdge<W>, W> augmentingPath = predecessors.buildPath( source, target );
        
        // find flow increment
        W flowIncrement = null;
        for ( WeightedEdge<W> edge : augmentingPath.getEdges() )
        {
            W edgeCapacity = residualEdgeCapacities.get( edge );
            if ( flowIncrement == null
                     || weightOperations.compare( edgeCapacity, flowIncrement ) < 0 )
            {
                flowIncrement = edgeCapacity;
            }
        }

        // update max flow and capacities accordingly
        maxFlow = weightOperations.append( maxFlow, flowIncrement );
        for ( WeightedEdge<W> edge : augmentingPath.getEdges() )
        {
            // decrease capacity for direct edge
            W directCapacity = residualEdgeCapacities.get( edge );
            residualEdgeCapacities.put( edge, weightOperations.append( directCapacity, weightOperations.inverse( flowIncrement ) ) );

            // increase capacity for inverse edge
            VertexPair<V> vertexPair = flowNetwork.getVertices( edge );
            WeightedEdge<W> inverseEdge = flowNetwork.getEdge( vertexPair.getTail(), vertexPair.getHead() );
            W inverseCapacity = residualEdgeCapacities.get( inverseEdge );
            residualEdgeCapacities.put( inverseEdge, weightOperations.append( inverseCapacity, flowIncrement ) );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void discoverGraph( DirectedGraph<V, WeightedEdge<W>> graph )
    {
        // reset ausiliary structures for a new graph visit
        predecessors = new PredecessorsList<V, WeightedEdge<W>, W>( graph, weightOperations );
        foundAugmentingPath = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean discoverEdge( V head, WeightedEdge<W> edge, V tail )
    {
        W residualEdgeCapacity = residualEdgeCapacities.get( edge );
        // avoid expanding the edge when it has no residual capacity
        if ( weightOperations.compare( residualEdgeCapacity, weightOperations.identity() ) <= 0 )
        {
            return false;
        }
        predecessors.addPredecessor( tail, head );
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean discoverVertex( V vertex )
    {
        return !vertex.equals( target );
    }

    /**
     * {@inheritDoc}
     */
    public boolean finishVertex( V vertex )
    {
        if ( vertex.equals( target ) )
        {
            // search ends when target vertex is reached
            foundAugmentingPath = true;
            return true;
        }
        return false;
    }

    @Override
    public W onCompleted()
    {
        return maxFlow;
    }

}
