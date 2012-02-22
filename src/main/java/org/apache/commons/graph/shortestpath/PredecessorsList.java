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

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.model.InMemoryWeightedPath;
import org.apache.commons.graph.weight.Monoid;

/**
 * The predecessor list is a list of {@link Vertex} of a {@link org.apache.commons.graph.Graph}.
 * Each {@link Vertex}' entry contains the index of its predecessor in a path through the graph.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 * @param <W> the weight type
 */
public final class PredecessorsList<V extends Vertex, WE extends WeightedEdge<W>, W>
{

    private final Graph<V, WE> graph;

    private final Monoid<W> weightOperations;

    private final Map<V, V> predecessors = new HashMap<V, V>();

    public PredecessorsList( Graph<V, WE> graph, Monoid<W> weightOperations )
    {
        this.graph = graph;
        this.weightOperations = weightOperations;
    }

    /**
     * Add an {@link Edge} in the predecessor list associated to the input {@link Vertex}.
     *
     * @param tail the predecessor vertex
     * @param head the edge that succeeds to the input vertex
     */
    public void addPredecessor( V tail, V head )
    {
        predecessors.put( tail, head );
    }

    /**
     * Build a {@link WeightedPath} instance related to source-target path.
     *
     * @param source the path source vertex
     * @param target the path target vertex
     * @param cost the path cost
     * @return the weighted path related to source to target
     */
    public WeightedPath<V, WE, W> buildPath( V source, V target )
    {
        InMemoryWeightedPath<V, WE, W> path = new InMemoryWeightedPath<V, WE, W>( source, target, weightOperations );

        V vertex = target;
        while ( !source.equals( vertex ) )
        {
            V predecessor = predecessors.get( vertex );
            if ( predecessor == null )
            {
                throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist", source, target );
            }
            WE edge = graph.getEdge( predecessor, vertex );

            path.addConnectionInHead( predecessor, edge, vertex );

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
