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
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.collections.DisjointSet;
import org.apache.commons.graph.model.MutableSpanningTree;

/**
 * Kruskal's algorithm is an algorithm in graph theory that finds a minimum spanning tree
 * for a connected weighted graph.
 */
public final class Kruskal
{

    /**
     * Calculates the minimum spanning tree (or forest) of the input Graph.
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type.
     * @param graph the Graph for which minimum spanning tree (or forest) has to be calculated.
     * @return  the minimum spanning tree (or forest) of the input Graph.
     */
    public static <V extends Vertex, WE extends WeightedEdge> SpanningTree<V, WE> minimumSpanningTree( WeightedGraph<V, WE> graph )
    {
        final Set<V> settledNodes = new HashSet<V>();

        final PriorityQueue<WE> orderedEdges = new PriorityQueue<WE>( graph.getSize() );

        for ( WE edge : graph.getEdges() )
        {
            orderedEdges.add( edge );
        }

        final DisjointSet<V> disjointSet = new DisjointSet<V>();

        MutableSpanningTree<V, WE> spanningTree = new MutableSpanningTree<V, WE>();

        while ( settledNodes.size() < graph.getOrder() )
        {
            WE edge = orderedEdges.remove();

            VertexPair<V> vertices = graph.getVertices( edge );
            V head = vertices.getHead();
            V tail = vertices.getTail();

            if ( settledNodes.add( head ) )
            {
                spanningTree.addVertex( head );
            }
            if ( settledNodes.add( tail ) )
            {
                spanningTree.addVertex( tail );
            }

            if ( !disjointSet.find( head ).equals( disjointSet.find( tail ) ) )
            {
                disjointSet.union( head, tail );
                spanningTree.addEdge( head, edge, tail );
            }
        }

        return spanningTree;
    }

}
