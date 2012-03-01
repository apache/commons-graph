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
import static org.apache.commons.graph.utils.Assertions.checkState;
import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
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
    public Set<V> applyingKosarajuSharir( final V source )
    {
        checkNotNull( source, "Kosaraju Sharir algorithm cannot be calculated without expressing the source vertex" );
        checkState( graph.containsVertex( source ), "Vertex %s does not exist in the Graph", source );
 
        final Set<V> visitedVertices = new HashSet<V>();
        final List<V> expandedVertexList = getExpandedVertexList( source, visitedVertices );
        final DirectedGraph<V, E> reverted = new RevertedGraph<V, E>( graph );
        
        // remove the last element from the expanded vertices list
        final V v = expandedVertexList.remove( expandedVertexList.size() - 1 );
        final Set<V> sccSet = new HashSet<V>();
        searchRecursive( reverted, v, sccSet, visitedVertices, false );
        return sccSet;
    }
    
    /**
     * {@inheritDoc}
     */
    public Set<Set<V>> applyingKosarajuSharir()
    {
        final Set<V> visitedVertices = new HashSet<V>();
        final List<V> expandedVertexList = getExpandedVertexList( null, visitedVertices );
        final DirectedGraph<V, E> reverted = new RevertedGraph<V, E>( graph );

        final Set<Set<V>> sccs = new HashSet<Set<V>>();

        while ( expandedVertexList.size() > 0 )
        {
            // remove the last element from the expanded vertices list
            final V v = expandedVertexList.remove( expandedVertexList.size() - 1 );
            final Set<V> sccSet = new HashSet<V>();
            searchRecursive( reverted, v, sccSet, visitedVertices, false );
            
            // remove all strongly connected components from the expanded list 
            expandedVertexList.removeAll( sccSet );
            sccs.add( sccSet );
        }
        
        return sccs;
    }
    
    private List<V> getExpandedVertexList( final V source, final Set<V> visitedVertices )
    {
        final int size = (source != null) ? 13 : graph.getOrder();
        final Set<V> vertices = new HashSet<V>( size );
        
        if ( source != null )
        {
            vertices.add( source );
        }
        else
        {
            for ( V vertex : graph.getVertices() )
            {
                vertices.add( vertex );
            }
        }
    
        // use an ArrayList so that subList is fast
        final ArrayList<V> expandedVertexList = new ArrayList<V>();

        int idx = 0;
        while ( ! vertices.isEmpty() )
        {
            // get the next vertex that has not yet been added to the expanded list
            final V v = vertices.iterator().next();            
            searchRecursive( graph, v, expandedVertexList, visitedVertices, true );
            // remove all expanded vertices from the list of vertices that have to be
            // still processed. To improve performance, only the items that have been
            // added to the list since the last iteration are removed
            vertices.removeAll( expandedVertexList.subList( idx, expandedVertexList.size() ) );
            idx = expandedVertexList.size();
        }
        
        return expandedVertexList;
    }

    /**
     * Searches a directed graph in iterative depth-first order, while adding the visited
     * vertices in a recursive manner, i.e. a vertex is added to the result list only 
     * when the search has finished expanding the vertex (and its subsequent childs).
     * 
     * <p><b>Implementation Note:</b> in the first step we look for vertices that have not
     * been visited yet, while in the second step we search for vertices that have already
     * been visited.</p>
     * @param graph the graph to be search
     * @param source the start vertex
     * @param coll the recursive collection of visited vertices
     * @param visited contains vertices that have been already visited
     * @param forward <code>true</code> for the first step of Kosaraju's algorithm,
     * <code>false</code> for the second step.
     */
    private void searchRecursive( final DirectedGraph<V, E> graph, final V source,
                                  final Collection<V> coll, final Set<V> visited,
                                  final boolean forward )
    {
        final LinkedList<V> stack = new LinkedList<V>();
        stack.addLast( source );
        
        while ( !stack.isEmpty() )
        {
            final V v = stack.removeLast();

            // if the vertex has already been visited it can be put into the
            // collection, as we are now finished expanding this vertex
            // the if takes both cases into account:
            //  * step1: forward && visited.contains(v)
            //  * step2: !forward && !visited.contains(v)
            if ( ! ( forward ^ visited.contains( v ) ) )
            {
                coll.add( v );
                continue;                    
            }

            // add the current vertex to the stack, so it is visited again
            // when all connected vertices have been visited
            stack.addLast( v );
            if ( forward )
            {
                visited.add( v );
            }
            else
            {
                visited.remove( v );
            }

            // add all not yet visited vertices that can be reached from this
            // vertex to the stack
            for ( V w : graph.getOutbound( v ) )
            {
                if ( ! ( forward ^ ! visited.contains( w ) ) )
                {
                    stack.addLast( w );
                }
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public Set<V> applyingCheriyanMehlhornGabow()
    {
        final Set<V> marked = new HashSet<V>();

        final GraphVisitHandler<V, E, G, Void> visitHandler = new CheriyanMehlhornGabowVisitHandler<V, E, G>( graph, marked );

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
    public Set<Set<V>> applyingTarjan()
    {
        final Map<V, TarjanVertexMetaInfo> verticesMetaInfo = new HashMap<V, TarjanVertexMetaInfo>();
        final Stack<V> s = new Stack<V>();
        final Set<Set<V>> stronglyConnectedComponents = new LinkedHashSet<Set<V>>();
        Integer index = 0;

        for ( V vertex : graph.getVertices() )
        {
            TarjanVertexMetaInfo vertexMetaInfo = getMetaInfo( vertex, verticesMetaInfo );
            final Set<V> stronglyConnectedComponent = new LinkedHashSet<V>();

            if ( vertexMetaInfo.hasUndefinedIndex() )
            {
                strongConnect( graph, vertex, verticesMetaInfo, s, stronglyConnectedComponent, index );
                stronglyConnectedComponents.add( stronglyConnectedComponent );
            }
        }

        return stronglyConnectedComponents;
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
