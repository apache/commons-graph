package org.apache.commons.graph;

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
public interface DirectedGraph<V, E>
    extends Graph<V, E>
{

    /**
     * For a vertex, the number of head endpoints adjacent to a node is called the indegree.
     *
     * @param v the vertex which indegree has to be returned.
     * @return the number of head endpoints adjacent to a vertex.
     */
    int getInDegree( V v );

    /**
     * Returns the set of edges which are inbound to the vertex.
     *
     * @param v the vertex which inbound vertexs have to be returned
     * @return the set of vertexs which are inbound to the vertex.
     */
    Iterable<V> getInbound( V v );

    /**
     * For a vertex, the number of tail endpoints adjacent to a node is called the outdegree.
     *
     * @param v the vertex which indegree has to be returned.
     * @return the number of head endpoints adjacent to a vertex.
     */
    int getOutDegree( V v );

    /**
     * Returns the set of vertexs which lead away from the vertex.
     *
     * @param v the vertex which outbound vertexs have to be returned
     * @return the set of vertexs which lead away from the vertex.
     */
    Iterable<V> getOutbound( V v );

}
