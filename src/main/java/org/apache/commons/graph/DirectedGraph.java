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
     * Returns the set of {@link Edge}s which are inbound to the {@link Vertex}.
     *
     * @param v the {@link Vertex} which inbound {@link Edge}s have to be returned
     * @return the set of {@link Edge}s which are inbound to the {@link Vertex}.
     */
    Set<E> getInbound( V v );

    /**
     * Returns the set of {@link Edge}s which lead away from the {@link Vertex}.
     *
     * @param v the {@link Vertex} which outbound {@link Edge}s have to be returned
     * @return the set of {@link Edge}s which lead away from the {@link Vertex}.
     */
    Set<E> getOutbound( V v );

    /**
     * Returns the {@link Vertex} which originates the {@link Edge}.
     *
     * @param e the {@link Edge} which has the returned {@link Vertex} as source.
     * @return the {@link Vertex} which originates the {@link Edge}.
     */
    V getSource( E e );

    /**
     * Returns the {@link Vertex} which terminates the {@link Edge}.
     *
     * @param e the {@link Edge} which has the returned {@link Vertex} as target.
     * @return the {@link Vertex} which terminates the {@link Edge}.
     */
    V getTarget( E e );

}
