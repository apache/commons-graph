package org.apache.commons.graph.ssc;

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

import static org.apache.commons.graph.visit.Visit.depthFirstSearch;

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
final class CheriyanMehlhornGabowVisitHandler<V extends Vertex, E extends Edge>
    extends BaseGraphVisitHandler<V, E>
{

    private final DirectedGraph<V, E> graph;

    private final Set<V> marked;

    private final Stack<V> s = new Stack<V>();

    private final Stack<V> p = new Stack<V>();

    public CheriyanMehlhornGabowVisitHandler( DirectedGraph<V, E> graph, Set<V> marked )
    {
        this.graph = graph;
        this.marked = marked;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void discoverVertex( V vertex )
    {
        marked.add( vertex );
        s.push( vertex );
        p.push( vertex );
    }

    /**
     * {@inheritDoc}
     */
    public void discoverEdge( V head, E edge, V tail )
    {
        if ( !marked.contains( tail ) )
        {
            depthFirstSearch( graph, tail, this );
        }
        // TODO else...
    }

}
