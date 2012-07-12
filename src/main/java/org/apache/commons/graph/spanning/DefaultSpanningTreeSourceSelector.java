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

import static java.util.Collections.reverseOrder;
import static org.apache.commons.graph.CommonsGraph.findShortestPath;
import static org.apache.commons.graph.utils.Assertions.checkNotNull;
import static org.apache.commons.graph.utils.Assertions.checkState;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Mapper;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.model.MutableSpanningTree;
import org.apache.commons.graph.shortestpath.PathNotFoundException;
import org.apache.commons.graph.weight.OrderedMonoid;

/**
 * {@link SpanningTreeSourceSelector} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <W> the weight type
 * @param <WE> the Graph weighted edges type
 */
final class DefaultSpanningTreeSourceSelector<V, W, WE>
    implements SpanningTreeSourceSelector<V, W, WE>
{

    private final Graph<V, WE> graph;

    private final Mapper<WE, W> weightedEdges;

    public DefaultSpanningTreeSourceSelector( Graph<V, WE> graph, Mapper<WE, W> weightedEdges )
    {
        this.graph = graph;
        this.weightedEdges = weightedEdges;
    }

    /**
     * {@inheritDoc}
     */
    public SpanningTreeAlgorithmSelector<V, W, WE> fromArbitrarySource()
    {
        checkState( graph.getOrder() > 0, "Spanning tree cannot be calculated on an empty graph" );
        return fromSource( graph.getVertices().iterator().next() );
    }

    /**
     * {@inheritDoc}
     */
    public <S extends V> SpanningTreeAlgorithmSelector<V, W, WE> fromSource( S source )
    {
        source = checkNotNull( source, "Spanning tree cannot be calculated without expressing the source vertex" );
        checkState( graph.containsVertex( source ), "Vertex %s does not exist in the Graph", source );
        return new DefaultSpanningTreeAlgorithmSelector<V, W, WE>( graph, weightedEdges, source );
    }

    /**
     * {@inheritDoc}
     */
    public <WO extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingReverseDeleteAlgorithm( WO weightOperations )
    {

        checkNotNull( weightOperations, "The Reverse-Delete algorithm cannot be calulated with null weight operations" );

        final Queue<WE> sortedEdge = new PriorityQueue<WE>( 11, reverseOrder( new WeightedEdgesComparator<W, WE>( weightOperations, weightedEdges ) ) );
        final List<WE> visitedEdge = new ArrayList<WE>();

        Iterable<WE> edges = graph.getEdges();
        for ( WE we : edges )
        {
            sortedEdge.offer( we );
        }

        Graph<V, WE> tmpGraph = new ReverseDeleteGraph<V, WE>( graph, sortedEdge, visitedEdge );

        while ( !sortedEdge.isEmpty() )
        {
            WE we = sortedEdge.poll();

            VertexPair<V> vertices = graph.getVertices( we );

            try
            {
                findShortestPath( tmpGraph )
                    .whereEdgesHaveWeights( weightedEdges )
                    .from( vertices.getHead() )
                    .to( vertices.getTail() )
                    .applyingDijkstra( weightOperations );
            }
            catch ( PathNotFoundException ex )
            {
                // only if a path doesn't exist
                visitedEdge.add( we );
            }
        }

        final MutableSpanningTree<V, WE, W> res = new MutableSpanningTree<V, WE, W>( weightOperations, weightedEdges );
        for ( V v : graph.getVertices() )
        {
            res.addVertex( v );
        }

        for ( WE we : edges )
        {
            VertexPair<V> pair = tmpGraph.getVertices( we );
            if ( pair != null )
            {
                res.addEdge( pair.getHead(), we, pair.getTail() );
            }
        }

        return res;
    }

}
