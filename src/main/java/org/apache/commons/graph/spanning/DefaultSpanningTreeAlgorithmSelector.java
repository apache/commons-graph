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

import static java.util.Collections.sort;
import static org.apache.commons.graph.CommonsGraph.findConnectedComponent;
import static org.apache.commons.graph.utils.Assertions.checkNotNull;
import static org.apache.commons.graph.utils.Assertions.checkState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.collections.DisjointSet;
import org.apache.commons.graph.model.MutableSpanningTree;
import org.apache.commons.graph.weight.OrderedMonoid;

/**
 * {@link SpanningTreeAlgorithmSelector} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <W> the weight type
 * @param <WE> the Graph weighted edges type
 * @param <G> the input Graph type
 */
final class DefaultSpanningTreeAlgorithmSelector<V extends Vertex, W, WE extends WeightedEdge<W>, G extends Graph<V, WE>>
    implements SpanningTreeAlgorithmSelector<V, W, WE, G>
{

    private final G graph;

    private final V source;

    public DefaultSpanningTreeAlgorithmSelector( G graph, V source )
    {
        this.graph = graph;
        this.source = source;
    }

    /**
     * {@inheritDoc}
     */
    public <OM extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingBoruvkaAlgorithm( OM orderedMonoid )
    {
        /*
         * <pre>
         * procedure Boruvka MST(G(V; E)):
         *     T <= V
         *     while |T| < n  1 do
         *         for all connected component C in T do
         *             e <= the smallest-weight edge from C to another component in T
         *             if e not exists in T then
         *                 T <= T U {e}
         *             end if
         *         end for
         * end while
         *
         * <pre>
         */

        Collection<List<V>> connectedComponents =
            findConnectedComponent( graph ).includingAllVertices().applyingMinimumSpanningTreeAlgorithm();

        checkNotNull( connectedComponents, "Connectivity algorithms returns a null pointer" );
        checkState( connectedComponents.size() == 1, "Boruvka's Algorithm cannot be calculated on not-connected graph." );

        final List<WE> sortedEdge = new ArrayList<WE>();

        for ( WE we : graph.getEdges() )
        {
            sortedEdge.add( we );
        }

        sort( sortedEdge, new WeightedEdgesComparator<W, WE>( orderedMonoid ) );

        final MutableSpanningTree<V, WE, W> spanningTree = new MutableSpanningTree<V, WE, W>( orderedMonoid );

        for ( V v : graph.getVertices() )
        {
            spanningTree.addVertex( v );
        }

        // find connected component into spanning tree.
        connectedComponents = findConnectedComponent( spanningTree ).includingAllVertices().applyingMinimumSpanningTreeAlgorithm();
        do
        {
            Iterator<WE> it = sortedEdge.iterator();

            while ( it.hasNext() )
            {
                WE edge = it.next();
                VertexPair<V> pair = graph.getVertices( edge );
                // find the vertices into the connected component.
                for ( List<V> list : connectedComponents )
                {
                    boolean listContainsHead = list.contains( pair.getHead() );
                    boolean listContainsTail = list.contains( pair.getTail() );

                    if ( listContainsHead && listContainsTail )
                    {
                        // this edge is included into a connected component.
                        it.remove();
                        break;
                    }
                    else if ( listContainsHead )
                    {
                        spanningTree.addEdge( pair.getHead(), edge, pair.getTail() );
                        it.remove();

                        for ( List<V> l : connectedComponents )
                        {
                            if ( l == list )
                            {
                                continue;
                            }

                            if ( l.contains( pair.getTail() ) )
                            {
                                list.addAll( l );
                                l.clear();
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        while ( spanningTree.getSize() < spanningTree.getOrder() - 1 );

        return spanningTree;
    }

    /**
     * {@inheritDoc}
     */
    public <OM extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingKruskalAlgorithm( OM orderedMonoid )
    {
        final Set<V> settledNodes = new HashSet<V>();

        final PriorityQueue<WE> orderedEdges =
            new PriorityQueue<WE>( graph.getSize(), new WeightedEdgesComparator<W, WE>( orderedMonoid ) );

        for ( WE edge : graph.getEdges() )
        {
            orderedEdges.add( edge );
        }

        final DisjointSet<V> disjointSet = new DisjointSet<V>();

        final MutableSpanningTree<V, WE, W> spanningTree = new MutableSpanningTree<V, WE, W>( orderedMonoid );

        // fill the spanning tree with vertices.
        for ( V v : graph.getVertices() )
        {
            spanningTree.addVertex( v );
        }

        while ( !orderedEdges.isEmpty() && settledNodes.size() < graph.getOrder() )
        {
            WE edge = orderedEdges.remove();

            VertexPair<V> vertices = graph.getVertices( edge );
            V head = vertices.getHead();
            V tail = vertices.getTail();
            settledNodes.add( head );
            settledNodes.add( tail );

            if ( !disjointSet.find( head ).equals( disjointSet.find( tail ) ) )
            {
                disjointSet.union( head, tail );
                spanningTree.addEdge( head, edge, tail );
            }
        }

        return spanningTree;
    }

    /**
     * {@inheritDoc}
     */
    public <OM extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingPrimAlgorithm( OM orderedMonoid )
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

                // if the edge has not been already visited and its weight is
                // less then the current Vertex weight
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

}
