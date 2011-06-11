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
 * A Graph data structure consists of a finite (and possibly mutable) set of ordered pairs,
 * called {@link Edge}s or arcs, of certain entities called {@link Vertex} or node.
 * As in mathematics, an {@link Edge} {@code (x,y)} is said to point or go from {@code x} to {@code y}.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public interface Graph<V extends Vertex, E extends Edge>
{

    /**
     * Returns the total set of Vertices in the graph.
     *
     * @return the total set of Vertices in the graph.
     */
    Set<V> getVertices();

    /**
     * Returns the total set of Edges in the graph.
     *
     * @return the total set of Edges in the graph.
     */
    Set<E> getEdges();

    /**
     * Returns all edges which touch this vertex.
     *
     * @return all edges which touch this vertex.
     */
    Set<E> getEdges( Vertex v );

    /**
     * Return the set of {@link Vertex} on the input {@link Edge} (2 for normal edges, > 2 for HyperEdges)
     *
     * @return the set of {@link Vertex} on this Edge.
     */
    Set<V> getVertices( Edge e );

}
