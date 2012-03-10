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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.graph.DirectedGraph;

/**
 * Applies the classical Cheriyan/Mehlhorn/Gabow's algorithm to find the strongly connected components, if exist.
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 * @param <G> the directed graph type
 */
final class CheriyanMehlhornGabowAlgorithm<V, E>
    implements SccAlgorithm<V>
{

    private final DirectedGraph<V, E> graph;

    private final Set<V> marked = new HashSet<V>();

    private final Map<V, Integer> preorder = new HashMap<V, Integer>();

    private final Map<V, Integer> sscId = new HashMap<V, Integer>();

    private final Stack<V> s = new Stack<V>();

    private final Stack<V> p = new Stack<V>();

    private int preorderCounter = 0;

    private int sscCounter = 0;

    public CheriyanMehlhornGabowAlgorithm( DirectedGraph<V, E> graph )
    {
        this.graph = graph;
    }

    /**
     */
    public Set<Set<V>> perform()
    {
        for ( V vertex : graph.getVertices() )
        {
            if ( !marked.contains( vertex ) )
            {
                dfs( vertex );
            }
        }

        final List<Set<V>> indexedSccComponents = new ArrayList<Set<V>>();
        for ( int i = 0; i < sscCounter; i++ )
        {
            indexedSccComponents.add( new HashSet<V>() );
        }

        for ( V w : graph.getVertices() )
        {
            Set<V> component = indexedSccComponents.get( sscId.get( w ) );
            component.add( w );
        }

        final Set<Set<V>> scc = new HashSet<Set<V>>();
        scc.addAll( indexedSccComponents );
        return scc;
    }

    private void dfs( V vertex )
    {
        marked.add( vertex );
        preorder.put( vertex, preorderCounter++ );
        s.push( vertex );
        p.push( vertex );
        for ( V w : graph.getConnectedVertices( vertex ) )
        {
            if ( !marked.contains( w ) )
            {
                dfs( w );
            }
            else if ( sscId.get( w ) == null )
            {
                while ( preorder.get( p.peek() ) > preorder.get( w ) )
                {
                    p.pop();
                }
            }
        }

        if ( p.peek().equals( vertex ) )
        {
            p.pop();
            V w = null;
            do
            {
                w = s.pop();
                sscId.put( w, sscCounter );
            }
            while ( !vertex.equals( w ) );
            sscCounter++;
        }
    }
}
