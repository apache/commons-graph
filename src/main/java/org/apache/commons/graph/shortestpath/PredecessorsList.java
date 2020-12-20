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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Mapper;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.model.InMemoryWeightedPath;
import org.apache.commons.graph.weight.Monoid;

/**
 * The predecessor list is a list of vertex of a {@link org.apache.commons.graph.Graph}.
 * Each vertex' entry contains the index of its predecessor in a path through the graph.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 * @param <W> the weight type
 */
public final class PredecessorsList<V, WE, W>
{

    private final Graph<V, WE> graph;

    private final Monoid<W> weightOperations;

    private final Mapper<WE, W> weightedEdges;

    private final Map<V, V> predecessors = new HashMap<V, V>();

    public PredecessorsList(final Graph<V, WE> graph, final Monoid<W> weightOperations, final Mapper<WE, W> weightedEdges )
    {
        this.graph = graph;
        this.weightOperations = weightOperations;
        this.weightedEdges = weightedEdges;
    }

    /**
     * Add an edge in the predecessor list associated to the input vertex.
     *
     * @param tail the predecessor vertex
     * @param head the edge that succeeds to the input vertex
     */
    public void addPredecessor(final V tail, final V head )
    {
        predecessors.put( tail, head );
    }

    /**
     * Build a {@link WeightedPath} instance related to source-target path.
     *
     * @param source the path source vertex
     * @param target the path target vertex
     * @return the weighted path related to source to target
     */
    public WeightedPath<V, WE, W> buildPath(final V source, final V target )
    {
        final InMemoryWeightedPath<V, WE, W> path = new InMemoryWeightedPath<V, WE, W>( source, target, weightOperations, weightedEdges );

        V vertex = target;
        while ( !source.equals( vertex ) )
        {
            final V predecessor = predecessors.get( vertex );
            if ( predecessor == null )
            {
                throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist", source, target );
            }
            final WE edge = graph.getEdge( predecessor, vertex );

            path.addConnectionInHead( predecessor, edge, vertex );

            vertex = predecessor;
        }

        return path;
    }

    /**
     * Build a {@link WeightedPath} instance related to source-target path.
     *
     * @param source the path source vertex
     * @param touch the node where search frontiers meet, producing the shortest path
     * @param target the path target vertex
     * @param backwardsList the predecessor list in backwards search space along reversed edges
     * @return the weighted path related to source to target
     */
    public WeightedPath<V, WE, W> buildPath(final V source, final V touch, final V target, final PredecessorsList<V, WE, W> backwardsList ) {
        final InMemoryWeightedPath<V, WE, W> path = new InMemoryWeightedPath<V, WE, W>( source, target, weightOperations, weightedEdges );

        V vertex = touch;
        while ( !source.equals( vertex ) )
        {
            final V predecessor = predecessors.get( vertex );
            if ( predecessor == null )
            {
                throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist", source, target );
            }
            final WE edge = graph.getEdge( predecessor, vertex );

            path.addConnectionInHead(predecessor, edge, vertex);

            vertex = predecessor;
        }

        vertex = touch;

        while ( !target.equals( vertex ) )
        {
            // 'predecessor' is actually a successor.
            final V predecessor = backwardsList.predecessors.get( vertex );
            if ( predecessor == null )
            {
                throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist", source, target );
            }
            final WE edge = graph.getEdge( vertex, predecessor );

            path.addConnectionInTail( vertex, edge, predecessor );

            vertex = predecessor;
        }

        return path;
    }

    /**
     * Checks the predecessor list has no elements.
     *
     * @return true, if the predecessor list has no elements, false otherwise.
     */
    public boolean isEmpty()
    {
        return predecessors.isEmpty();
    }

}
