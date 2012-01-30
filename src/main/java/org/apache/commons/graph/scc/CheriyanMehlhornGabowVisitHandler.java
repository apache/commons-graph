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

import static org.apache.commons.graph.CommonsGraph.visit;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.visit.BaseGraphVisitHandler;

/**
 * Cheriyan/Mehlhorn/Gabow's strongly connected component algorithm is based on DFS (or BFS) algorithm
 * and needs to execute specific actions during the visit.
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 */
final class CheriyanMehlhornGabowVisitHandler<V extends Vertex, E extends Edge, G extends DirectedGraph<V, E>>
    extends BaseGraphVisitHandler<V, E, G, Void>
{

    private final G graph;

    private final Map<V, Integer> preorder = new HashMap<V, Integer>();

    private final Map<V, Integer> sscId = new HashMap<V, Integer>();

    private final Set<V> marked;

    private final Stack<V> s = new Stack<V>();

    private final Stack<V> p = new Stack<V>();

    private int preorderCounter = 0;

    private int sscCounter = 0;

    public CheriyanMehlhornGabowVisitHandler( G graph, Set<V> marked )
    {
        this.graph = graph;
        this.marked = marked;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean discoverVertex( V vertex )
    {
        marked.add( vertex );
        preorder.put( vertex, preorderCounter++ );
        s.push( vertex );
        p.push( vertex );
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean discoverEdge( V head, E edge, V tail )
    {
        if ( !marked.contains( tail ) )
        {
            visit( graph ).from( tail ).applyingDepthFirstSearch( this );
        }
        else if ( !sscId.containsKey( tail ) )
        {
            while ( preorder.get( p.peek() ) > preorder.get( tail ) )
            {
                p.pop();
            }
        }

        // found strong component containing head
        if ( head.equals( p.peek() ) )
        {
            p.pop();
            V w;
            do
            {
                w = s.pop();
                sscId.put( w, sscCounter );
            }
            while ( !head.equals( w ) );
            sscCounter++;
        }

        return true;
    }

}
