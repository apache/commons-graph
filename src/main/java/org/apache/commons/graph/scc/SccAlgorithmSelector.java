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

import java.util.Set;

/**
 * Allows selecting the algorithm for calculating the strongly connected component.
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 */
public interface SccAlgorithmSelector<V, E>
{

    /**
     * Applies the classical Kosaraju's algorithm to find the strongly connected components of
     * a vertex <code>source</code>.
     *
     * @param source the source vertex to start the search from
     * @return the input graph strongly connected component.
     */
    Set<V> applyingKosarajuSharir( V source );

    /**
     * Applies the classical Kosaraju's algorithm to find the strongly connected components.
     *
     * <p>Note: the runtime complexity is O(V + E) and this algorithm should be chosen
     * if the number of vertices outweighs the number of edges.</p>
     *
     * @return the input graph strongly connected components.
     */
    Set<Set<V>> applyingKosarajuSharir();

    /**
     * Applies the classical Cheriyan/Mehlhorn/Gabow's algorithm to find the strongly connected components, if exist.
     *
     * @return the input graph strongly connected component.
     */
    Set<Set<V>> applyingCheriyanMehlhornGabow();

    /**
     * Tarjan's algorithm is a variation (slightly faster) on KosarajuSharir's algorithm for finding
     * strongly-connected components in a directed graph.
     *
     * <p>Note: the runtime complexity is O(V + E) and this algorithm should be chosen
     * if the number of edges outweighs the number of vertices.</p>
     *
     * @return the input graph strongly connected component.
     */
    Set<Set<V>> applyingTarjan();

}
