package org.apache.commons.graph.spanning;

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

import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.weight.OrderedMonoid;

/**
 * Spanning Tree algoritms selector.
 *
 * @param <V> the Graph vertices type
 * @param <W> the weight type
 * @param <WE> the Graph weighted edges type
 */
public interface SpanningTreeAlgorithmSelector<V, W, WE>
{

    /**
     * Applies the <a href="http://en.wikipedia.org/wiki/Bor%C5%AFvka's_algorithm">Boruvka</a>'s algorithm.
     *
     * @param <WO> the type of weight operations
     * @param weightOperations the class responsible for operations on weights
     * @return the calculated spanning tree
     */
    <WO extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingBoruvkaAlgorithm( WO weightOperations );

    /**
     * Applies the <a href="http://en.wikipedia.org/wiki/Kruskal%27s_algorithm">Kruskal</a>'s algorithm.
     *
     * @param <WO> the type of weight operations
     * @param weightOperations the class responsible for operations on weights
     * @return the calculated spanning tree
     */
    <WO extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingKruskalAlgorithm( WO weightOperations );

    /**
     * Applies the <a href="http://en.wikipedia.org/wiki/Prim%27s_algorithm">Prim</a>'s algorithm.
     *
     * @param <WO> the type of weight operations
     * @param weightOperations the class responsible for operations on weights
     * @return the calculated spanning tree
     */
    <WO extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingPrimAlgorithm( WO weightOperations );

}
