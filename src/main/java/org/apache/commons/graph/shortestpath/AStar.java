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

import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.WeightedPath;

/**
 * Contains the A* shortest path algorithm implementation.
 */
public final class AStar
{

    /**
     * This class can not be instantiated directly
     */
    private AStar()
    {
        // do nothing
    }

    /**
     * Applies the classical A* algorithm to find the shortest path from the source to the target, if exists.
     *
     * @param <V> the Graph vertices type.
     * @param <WE> the Graph weighted edges type
     * @param graph the Graph which shortest path from {@code source} to {@code target} has to be found
     * @param source the shortest path source Vertex
     * @param target the shortest path target Vertex
     * @return a path wich describes the shortes path, if any, otherwise a {@link PathNotFoundException} will be thrown
     */
    public static <V extends Vertex, WE extends WeightedEdge<V>> WeightedPath<V, WE> findShortestPath( WeightedGraph<V, WE> graph,
                                                                                                       V source,
                                                                                                       V target )
    {
        return null;
    }

}
