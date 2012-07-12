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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;
import static org.apache.commons.graph.utils.Assertions.checkState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.model.RevertedGraph;

/**
 * Implements the classical Kosaraju's algorithm to find the strongly connected components
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 * @param <G> the directed graph type
 */
final class KosarajuSharirAlgorithm<V, E>
    implements SccAlgorithm<V>
{
    /** The graph. */
    private final DirectedGraph<V, E> graph;

    /**
     * Create a new {@link KosarajuSharirAlgorithm} instance for the given {@link org.apache.commons.graph.Graph}.
     *
     * @param graph the {@link org.apache.commons.graph.Graph} on which to apply the algorithm
     */
    public KosarajuSharirAlgorithm( final DirectedGraph<V, E> graph )
    {
        this.graph = graph;
    }

    /**
     * Applies the classical Kosaraju's algorithm to find the strongly connected components of
     * a vertex <code>source</code>.
     *
     * @param source the source vertex to start the search from
     * @return the input graph strongly connected component.
     */
    public Set<V> perform( final V source )
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
     * Applies the classical Kosaraju's algorithm to find the strongly connected components.
     *
     * @return the input graph strongly connected component.
     */
    public Set<Set<V>> perform()
    {
        final Set<V> visitedVertices = new HashSet<V>();
        final List<V> expandedVertexList = getExpandedVertexList( null, visitedVertices );
        final DirectedGraph<V, E> reverted = new RevertedGraph<V, E>( graph );

        final Set<Set<V>> sccs = new HashSet<Set<V>>();

        // insert the expanded vertices in reverse order into a linked hash set
        // this is needed to quickly remove already found SCCs from the stack
        final LinkedHashSet<V> stack = new LinkedHashSet<V>();
        for ( int i = expandedVertexList.size() - 1; i >= 0; i-- )
        {
            stack.add( expandedVertexList.get( i ) );
        }

        while ( stack.size() > 0 )
        {
            // remove the last element from the expanded vertices list
            final V v = stack.iterator().next();
            final Set<V> sccSet = new HashSet<V>();
            searchRecursive( reverted, v, sccSet, visitedVertices, false );

            // remove all strongly connected components from the expanded list
            stack.removeAll( sccSet );
            sccs.add( sccSet );
        }

        return sccs;
    }

    /**
     * Performs a depth-first search to create a recursive vertex list.
     *
     * @param source the starting vertex
     * @param visitedVertices a {@link Set} containing all visited vertices
     * @return the recursively expanded vertex list for Kosaraju's algorithm
     */
    private List<V> getExpandedVertexList( final V source, final Set<V> visitedVertices )
    {
        final int size = ( source != null ) ? 13 : graph.getOrder();
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
     * @param g the graph to be search
     * @param source the start vertex
     * @param coll the recursive collection of visited vertices
     * @param visited contains vertices that have been already visited
     * @param forward <code>true</code> for the first step of Kosaraju's algorithm,
     * <code>false</code> for the second step.
     */
    private void searchRecursive( final DirectedGraph<V, E> g, final V source,
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
            for ( V w : g.getOutbound( v ) )
            {
                if ( ! ( forward ^ ! visited.contains( w ) ) )
                {
                    stack.addLast( w );
                }
            }
        }
    }

}
