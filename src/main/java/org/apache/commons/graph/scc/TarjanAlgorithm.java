package org.apache.commons.graph.scc;

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

import static java.lang.Math.min;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.graph.DirectedGraph;

/**
 * Implements Tarjan's algorithm is a variation (slightly faster) on KosarajuSharir's algorithm for finding
 * strongly-connected components in a directed graph.
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 */
final class TarjanAlgorithm<V, E>
    implements SccAlgorithm<V>
{

    private final DirectedGraph<V, E> graph;

    /**
     */
    public TarjanAlgorithm(final DirectedGraph<V, E> graph )
    {
        this.graph = graph;
    }

    /**
     * Tarjan's algorithm is a variation (slightly faster) on KosarajuSharir's algorithm for finding strongly-connected
     * components in a directed graph.
     *
     * @return the input graph strongly connected component.
     */
    public Set<Set<V>> perform()
    {
        final Map<V, TarjanVertexMetaInfo> verticesMetaInfo = new HashMap<V, TarjanVertexMetaInfo>();
        final Stack<V> s = new Stack<V>();
        final Set<Set<V>> stronglyConnectedComponents = new LinkedHashSet<Set<V>>();
        final Integer index = 0;

        for ( final V vertex : graph.getVertices() )
        {
            final TarjanVertexMetaInfo vertexMetaInfo = getMetaInfo( vertex, verticesMetaInfo );
            final Set<V> stronglyConnectedComponent = new LinkedHashSet<V>();

            if ( vertexMetaInfo.hasUndefinedIndex() )
            {
                strongConnect( graph, vertex, verticesMetaInfo, s, stronglyConnectedComponent, index );
                stronglyConnectedComponents.add( stronglyConnectedComponent );
            }
        }

        return stronglyConnectedComponents;
    }

    private static <V> TarjanVertexMetaInfo getMetaInfo(final V vertex, final Map<V, TarjanVertexMetaInfo> verticesMetaInfo )
    {
        TarjanVertexMetaInfo vertexMetaInfo = verticesMetaInfo.get( vertex );
        if ( vertexMetaInfo == null )
        {
            vertexMetaInfo = new TarjanVertexMetaInfo();
            verticesMetaInfo.put( vertex, vertexMetaInfo );
        }
        return vertexMetaInfo;
    }

    private static <V, E> void strongConnect(final DirectedGraph<V, E> graph,
                                             final V vertex,
                                             final Map<V, TarjanVertexMetaInfo> verticesMetaInfo,
                                             final Stack<V> s,
                                             final Set<V> stronglyConnectedComponent,
                                             Integer index )
    {
        final TarjanVertexMetaInfo vertexMetaInfo = getMetaInfo( vertex, verticesMetaInfo );
        vertexMetaInfo.setIndex( index );
        vertexMetaInfo.setLowLink( index );
        index++;
        s.push( vertex );

        for ( final V adjacent : graph.getOutbound( vertex ) )
        {
            final TarjanVertexMetaInfo adjacentMetaInfo = getMetaInfo( adjacent, verticesMetaInfo );
            if ( adjacentMetaInfo.hasUndefinedIndex() )
            {
                strongConnect( graph, adjacent, verticesMetaInfo, s, stronglyConnectedComponent, index );
                vertexMetaInfo.setLowLink( min( vertexMetaInfo.getLowLink(), adjacentMetaInfo.getLowLink() ) );
            }
            else if ( s.contains( adjacent ) )
            {
                vertexMetaInfo.setLowLink( min( vertexMetaInfo.getLowLink(), adjacentMetaInfo.getIndex() ) );
            }
        }

        if ( vertexMetaInfo.getLowLink() == vertexMetaInfo.getIndex() )
        {
            V v;
            do
            {
                v = s.pop();
                stronglyConnectedComponent.add( v );
            }
            while ( !vertex.equals( v ) );
        }
    }
}
