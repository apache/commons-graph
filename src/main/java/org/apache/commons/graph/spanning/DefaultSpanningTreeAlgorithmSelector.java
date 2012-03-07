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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;
import static org.apache.commons.graph.utils.Assertions.checkState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Mapper;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.VertexPair;
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
final class DefaultSpanningTreeAlgorithmSelector<V, W, WE, G extends Graph<V, WE>>
    implements SpanningTreeAlgorithmSelector<V, W, WE, G>
{
    /** The graph. */
    private final G graph;

    private final Mapper<WE, W> weightedEdges;

    /** The start {@link Vertex}. */
    private final V source;

    /**
     * Creates a default {@link SpanningTreeAlgorithmSelector} for the given {@link Graph} and
     * start {@link Vertex}.
     *
     * @param graph the {@link Graph} to be used.
     * @param source the start {@link Vertex}.
     */
    public DefaultSpanningTreeAlgorithmSelector( final G graph, Mapper<WE, W> weightedEdges, final V source )
    {
        this.graph = graph;
        this.weightedEdges = weightedEdges;
        this.source = source;
    }

    /** {@inheritDoc} */
    public <WO extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingBoruvkaAlgorithm( WO weightOperations )
    {
        /*
         * <pre>
         * procedure Boruvka MST(G(V; E)):
         *     T <= V
         *     while |T| < n do
         *         for all connected component C in T do
         *             e <= the smallest-weight edge from C to another component in T
         *             if e not exists in T then
         *                 T <= T u {e}
         *             end if
         *         end for
         *     end while
         * <pre>
         */

        checkNotNull( weightOperations, "The Boruvka algorithm cannot be calculated with null weight operations" );

        final MutableSpanningTree<V, WE, W> spanningTree = new MutableSpanningTree<V, WE, W>( weightOperations );

        final Set<SuperVertex<V, W, WE, G>> components = new HashSet<SuperVertex<V, W, WE, G>>( graph.getOrder() );

        final Map<V, SuperVertex<V, W, WE, G>> mapping = new HashMap<V, SuperVertex<V, W, WE, G>>( graph.getOrder() );

        for ( V v : graph.getVertices() )
        {
            // create a super vertex for each vertex
            final SuperVertex<V, W, WE, G> sv = new SuperVertex<V, W, WE, G>( v, graph, new WeightedEdgesComparator<W, WE>( weightOperations, weightedEdges ) );

            components.add( sv );

            // add a mapping for each vertex to its corresponding super vertex
            mapping.put( v, sv );

            // add each vertex to the spanning tree
            spanningTree.addVertex( v );
        }

        while ( components.size() > 1 )
        {
            final List<WE> edges = new LinkedList<WE>();
            for ( SuperVertex<V, W, WE, G> sv : components )
            {
                // get the minimum edge for each component to any other component
                final WE edge = sv.getMinimumWeightEdge();
                if ( edge != null )
                {
                    edges.add( edge );
                }
            }

            // if there is no edge anymore for a component, and there is still more than 1 component,
            // the graph is unconnected
            checkState( !edges.isEmpty() || components.size() == 1, "unconnected graph" );

            for ( final WE edge : edges )
            {
                final VertexPair<V> pair = graph.getVertices( edge );
                final V head = pair.getHead();
                final V tail = pair.getTail();

                // find the super vertices corresponding to this edge
                final SuperVertex<V, W, WE, G> headSv = mapping.get( head );
                final SuperVertex<V, W, WE, G> tailSv = mapping.get( tail );

                // merge them, if they are not the same
                if ( headSv != tailSv )
                {
                    headSv.merge( tailSv );

                    // update the mapping for each merged vertex
                    for ( final V v : tailSv )
                    {
                        mapping.put( v, headSv );
                    }

                    // remove the merged super vertex from the components set
                    components.remove( tailSv );

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
    public <WO extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingKruskalAlgorithm( WO weightOperations )
    {
        checkNotNull( weightOperations, "The Kruskal algorithm cannot be calculated with null weight operations" );
        final Set<V> settledNodes = new HashSet<V>();

        final PriorityQueue<WE> orderedEdges =
            new PriorityQueue<WE>( graph.getSize(), new WeightedEdgesComparator<W, WE>( weightOperations, weightedEdges ) );

        for ( WE edge : graph.getEdges() )
        {
            orderedEdges.add( edge );
        }

        final DisjointSet<V> disjointSet = new DisjointSet<V>();

        final MutableSpanningTree<V, WE, W> spanningTree = new MutableSpanningTree<V, WE, W>( weightOperations );

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
    public <WO extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingPrimAlgorithm( WO weightOperations )
    {
        checkNotNull( weightOperations, "The Prim algorithm cannot be calculated with null weight operations" );

        final ShortestEdges<V, WE, W> shortestEdges = new ShortestEdges<V, WE, W>( graph, source, weightOperations, weightedEdges );

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
                boolean weightLessThanCurrent = !shortestEdges.hasWeight( v ) ||
                        weightOperations.compare( weightedEdges.map( edge ), shortestEdges.getWeight( v ) ) < 0;
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
