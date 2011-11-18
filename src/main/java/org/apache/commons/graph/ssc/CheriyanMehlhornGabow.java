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

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;

/**
 * Contains the Cheriyan/Mehlhorn/Gabow's strongly connected component algorithm implementation.
 */
public final class CheriyanMehlhornGabow
{

    /**
     * This class can not be instantiated directly.
     */
    private CheriyanMehlhornGabow()
    {
        // do nothing
    }

    /**
     * Applies the classical Cheriyan/Mehlhorn/Gabow's algorithm to find the strongly connected components, if exist.
     *
     * @param <V> the Graph vertices type.
     * @param <E> the Graph edges type.
     * @param graph the Graph which strongly connected component has to be verified.
     */
    public static <V extends Vertex, E extends Edge> void hasStronglyConnectedComponent( DirectedGraph<V, E> graph )
    {
        final Set<V> marked = new HashSet<V>();
        final Stack<V> s = new Stack<V>();
        final Stack<V> p = new Stack<V>();

        for ( V vertex : graph.getVertices() )
        {
            if ( marked.add( vertex ) )
            {
                s.push( vertex );
                p.push( vertex );

                depthFirstSearch( graph, vertex, new CheriyanMehlhornGabowVisitHandler<V, E>() );
            }
        }
    }

}
