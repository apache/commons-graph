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

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.weight.OrderedMonoid;

/**
 * Spanning Tree source selector.
 *
 * @param <V> the Graph vertices type
 * @param <W> the weight type
 * @param <WE> the Graph weighted edges type
 * @param <G> the input Graph type
 */
public interface SpanningTreeSourceSelector<V extends Vertex, W, WE extends WeightedEdge<W>, G extends Graph<V, WE>>
{

    /**
     * Selects an arbitrary source from the input Graph to calculate the spanning tree.
     *
     * @return the linked spanning tree algorithm builder
     */
    SpanningTreeAlgorithmSelector<V, W, WE, G> fromArbitrarySource();

    /**
     * Allows specify a source vertex to calculate the spanning tree.
     *
     * @param source the source vertex to calculate the spanning tree.
     * @return the linked spanning tree algorithm builder
     */
    SpanningTreeAlgorithmSelector<V, W, WE, G> fromSource( V source );

    /**
     * Applies the <a href="http://en.wikipedia.org/wiki/Reverse-Delete_algorithm">Reverse-Delete</a> algorithm.
     *
     * <pre>
     * function ReverseDelete(edges[] E)
     *    sort E in decreasing order
     *    Define an index i = 0
     *    while i < size(E)
     *       Define edge temp = E[i]
     *         delete E[i]
     *         if temp.v1 is not connected to temp.v2
     *             E[i] = temp
     *         i = i + 1
     *   return edges[] E
     * </pre>
     *
     * @param orderedMonoid the graph weights monoid
     * @return the calculated spanning tree
     */
    <OM extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingReverseDeleteAlgorithm( OM orderedMonoid );

}