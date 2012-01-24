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
import static org.apache.commons.graph.CommonsGraph.visit;
import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.model.RevertedGraph;
import org.apache.commons.graph.visit.GraphVisitHandler;

/**
 * {@link SccAlgorithmSelector} implementation
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 * @param <G> the directed graph type
 */
public final class DefaultSccAlgorithmSelector<V extends Vertex, E extends Edge, G extends DirectedGraph<V, E>>
    implements SccAlgorithmSelector<V, E, G>
{

    private final G graph;

    public DefaultSccAlgorithmSelector( G graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public Set<V> applyingKosarajuSharir( V source )
    {
        source = checkNotNull( source, "KosarajuSharir algorithm requires a non-null source vertex" );

        visit( graph ).from( source ).applyingDepthFirstSearch( new KosarajuSharirVisitHandler<V, E>( source ) );

        DirectedGraph<V, E> reverted = new RevertedGraph<V, E>( graph );

        // TODO FILL ME, algorithm is incomplete

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Set<V> applyingCheriyanMehlhornGabow()
    {
        final Set<V> marked = new HashSet<V>();

        final GraphVisitHandler<V, E> visitHandler = new CheriyanMehlhornGabowVisitHandler<V, E>( graph, marked );

        for ( V vertex : graph.getVertices() )
        {
            if ( !marked.contains( vertex ) )
            {
                visit( graph ).from( vertex ).applyingDepthFirstSearch( visitHandler );
            }
        }

        // TODO FILL ME, algorithm is incomplete

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Set<V> applyingTarjan()
    {
        final Map<V, TarjanVertexMetaInfo> verticesMetaInfo = new HashMap<V, TarjanVertexMetaInfo>();
        final Stack<V> s = new Stack<V>();
        final Set<V> stronglyConnectedComponent = new LinkedHashSet<V>();
        Integer index = 0;

        for ( V vertex : graph.getVertices() )
        {
            TarjanVertexMetaInfo vertexMetaInfo = getMetaInfo( vertex, verticesMetaInfo );

            if ( vertexMetaInfo.hasUndefinedIndex() )
            {
                strongConnect( graph, vertex, verticesMetaInfo, s, stronglyConnectedComponent, index );
            }
        }

        return stronglyConnectedComponent;
    }

    private static <V> TarjanVertexMetaInfo getMetaInfo( V vertex, Map<V, TarjanVertexMetaInfo> verticesMetaInfo )
    {
        TarjanVertexMetaInfo vertexMetaInfo = verticesMetaInfo.get( vertex );
        if ( vertexMetaInfo == null )
        {
            vertexMetaInfo = new TarjanVertexMetaInfo();
            verticesMetaInfo.put( vertex, vertexMetaInfo );
        }
        return vertexMetaInfo;
    }

    private static <V extends Vertex, E extends Edge> void strongConnect( DirectedGraph<V, E> graph,
                                                                          V vertex,
                                                                          Map<V, TarjanVertexMetaInfo> verticesMetaInfo,
                                                                          Stack<V> s,
                                                                          Set<V> stronglyConnectedComponent,
                                                                          Integer index )
    {
        TarjanVertexMetaInfo vertexMetaInfo = getMetaInfo( vertex, verticesMetaInfo );
        vertexMetaInfo.setIndex( index );
        vertexMetaInfo.setLowLink( index );
        index++;
        s.push( vertex );

        for ( V adjacent : graph.getOutbound( vertex ) )
        {
            TarjanVertexMetaInfo adjacentMetaInfo = getMetaInfo( adjacent, verticesMetaInfo );
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
