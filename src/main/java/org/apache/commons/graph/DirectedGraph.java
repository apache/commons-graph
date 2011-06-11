package org.apache.commons.graph;

/*
 * Copyright 2001-2011 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Set;

/**
 * A {@code DirectedGraph} or <i>digraph</i> is an ordered pair {@code D = ( V, E )} with
 * <ul>
 * {@code V} a set whose elements are called vertices or nodes, and
 * {@code E} a set of ordered pairs of vertices, called arcs, directed edges, or arrows.
 * </ul>
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public interface DirectedGraph<V extends Vertex, E extends Edge>
    extends Graph<V, E>
{

    /**
     * getInbound( Vertex ) Returns the set of edges which are inbound to the
     * Vertex.
     */
    Set getInbound( Vertex v );

    /**
     * getOutbound( Vertex ) Returns the set of edges which lead away from the
     * Vertex.
     */
    Set getOutbound( Vertex v );

    /**
     * getSource( Edge ) Returns the vertex which originates the edge.
     */
    Vertex getSource( Edge e );

    /**
     * getTarget( Edge ) Returns the vertex which terminates the edge.
     */
    Vertex getTarget( Edge e );

}
