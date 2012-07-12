package org.apache.commons.graph.shortestpath;

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

import org.apache.commons.graph.weight.OrderedMonoid;

/**
 *
 *
 * @param <V> the Graph vertices type.
 * @param <WE> the Graph weighted edges type
 * @param <W> the weight type
 */
public interface TargetSourceSelector<V, WE, W>
{

    /**
     *  Calculates the shortest path using the BellmannFord's algorithm.
     *
     * @param <WO> the type of weight operations
     * @param weightOperations the weight operations needed for the algorithm
     * @return a data structure which contains all vertex pairs shortest path.
     */
    <WO extends OrderedMonoid<W>> AllVertexPairsShortestPath<V, WE, W> applyingBelmannFord( WO weightOperations );

    /**
     * Specifies the shortest path source.
     *
     * @param target
     */
    <T extends V> ShortestPathAlgorithmSelector<V, WE, W> to( T target );

}
