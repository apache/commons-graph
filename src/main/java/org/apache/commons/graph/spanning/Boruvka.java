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
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;

/**
 * Boruvka's algorithm is an algorithm for finding a minimum spanning tree in a graph
 * for which all edge weights are distinct.
 */
public final class Boruvka
{

    /**
     * Calculates the minimum spanning tree (or forest) of the input Graph.
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type.
     * @param graph the Graph for which minimum spanning tree (or forest) has to be calculated.
     * @return  the minimum spanning tree (or forest) of the input Graph.
     */
    public static <V extends Vertex, WE extends WeightedEdge> Graph<V, WE> minimumSpanningTree( WeightedGraph<V, WE> graph )
    {
        return null;
    }

}
