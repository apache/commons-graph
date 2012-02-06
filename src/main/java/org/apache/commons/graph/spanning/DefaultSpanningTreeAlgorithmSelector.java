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

import static org.apache.commons.graph.utils.Assertions.checkState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    /** {@inheritDoc} */
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
         *     end while
         * <pre>
         */

        final MutableSpanningTree<V, WE, W> spanningTree =
            new MutableSpanningTree<V, WE, W>( orderedMonoid );

        final Set<SuperVertex<V, W, WE, G, OM>> components =
            new HashSet<SuperVertex<V, W, WE, G, OM>>( graph.getOrder() );

        final Map<V, SuperVertex<V, W, WE, G, OM>> mapping =
            new HashMap<V, SuperVertex<V, W, WE, G, OM>>( graph.getOrder() );

        for ( V v : graph.getVertices() )
        {
            // create a super vertex for each vertex
            final SuperVertex<V, W, WE, G, OM> sv =
                new SuperVertex<V, W, WE, G, OM>( v, graph, orderedMonoid );
            components.add( sv );
            // add a mapping for each vertex to its corresponding super vertex
            mapping.put( v, sv );
            // add each vertex to the spanning tree
            spanningTree.addVertex( v );
        }

        while ( components.size() > 1 )
        {
            List<WE> edges = new LinkedList<WE>();
            for ( SuperVertex<V, W, WE, G, OM> v : components )
            {
                // get the minimum edge for each component to any other component
                WE edge = v.getMinimumWeightEdge();
                if ( edge != null )
                {
                    edges.add( edge );
                }
            }

            // if there is no edge anymore for a component, and there is still more than 1 component,
            // the graph is unconnected
            checkState( !edges.isEmpty() || components.size() == 1, "unconnected graph" );

            for ( WE edge : edges )
            {
                VertexPair<V> pair = graph.getVertices( edge );
                V head = pair.getHead();
                V tail = pair.getTail();

                // find the super vertices corresponding to this edge
                SuperVertex<V, W, WE, G, OM> headSuper = mapping.get( head );
                SuperVertex<V, W, WE, G, OM> tailSuper = mapping.get( tail );

                // merge them, if they are not the same
                if ( headSuper != tailSuper )
                {
                    headSuper.merge( tailSuper );

                    // update the mapping for each merged vertex
                    for ( V v : tailSuper )
                    {
                        mapping.put( v, headSuper );
                    }

                    // remove the merged super vertex from the components set
                    components.remove( tailSuper );

                    // add the edge to the spanning tree
                    if ( spanningTree.getVertices( edge ) == null )
                    {
                        spanningTree.addEdge( head, edge, tail );
                    }
                }
            }
        }

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
