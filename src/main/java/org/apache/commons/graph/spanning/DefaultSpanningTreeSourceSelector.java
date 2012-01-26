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
import static java.util.Collections.sort;
import static org.apache.commons.graph.shortestpath.Dijkstra.findShortestPath;
import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.model.MutableSpanningTree;
import org.apache.commons.graph.shortestpath.PathNotFoundException;
import org.apache.commons.graph.weight.OrderedMonoid;

/**
 * {@link SpanningTreeSourceSelector} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <W> the weight type
 * @param <WE> the Graph weighted edges type
 * @param <G> the input Graph type
 */
public final class DefaultSpanningTreeSourceSelector<V extends Vertex, W, WE extends WeightedEdge<W>, G extends Graph<V, WE>>
    implements SpanningTreeSourceSelector<V, W, WE, G>
{

    private final G graph;

    public DefaultSpanningTreeSourceSelector( G graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public SpanningTreeAlgorithmSelector<V, W, WE, G> fromArbitrarySource()
    {
        return fromSource( graph.getVertices().iterator().next() );
    }

    /**
     * {@inheritDoc}
     */
    public SpanningTreeAlgorithmSelector<V, W, WE, G> fromSource( V source )
    {
        source = checkNotNull( source, "Spanning tree cannot be calculated without expressing the source vertex" );
        return new DefaultSpanningTreeAlgorithmSelector<V, W, WE, G>( graph, source );
    }

    /**
     * {@inheritDoc}
     */
    public <OM extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingReverseDeleteAlgorithm( OM orderedMonoid )
    {
        final List<WE> sortedEdge = new ArrayList<WE>();
        final List<WE> visitedEdge = new ArrayList<WE>();

        Iterable<WE> edges = graph.getEdges();
        for ( WE we : edges )
        {
            sortedEdge.add( we );
        }

        sort( sortedEdge, reverseOrder( new WeightedEdgesComparator<W, WE>( orderedMonoid ) ) );

        Graph<V, WE> tmpGraph = new ReverseDeleteGraph<V, WE, W>( graph, sortedEdge, visitedEdge );

        for ( Iterator<WE> iterator = sortedEdge.iterator(); iterator.hasNext(); )
        {
            WE we = iterator.next();
            iterator.remove();

            VertexPair<V> vertices = graph.getVertices( we );

            try
            {
                findShortestPath( tmpGraph, vertices.getHead(), vertices.getTail(), orderedMonoid );
            }
            catch ( PathNotFoundException ex )
            {
                // only if a path doesn't exist
                visitedEdge.add( we );
            }
        }

        final MutableSpanningTree<V, WE, W> res = new MutableSpanningTree<V, WE, W>( orderedMonoid );
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
