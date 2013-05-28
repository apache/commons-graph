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

/**
 * An {@link Heuristic} instance is an admissible "heuristic estimate" of the distance to the goal
 * (usually denoted <i>h(x)</i>).
 *
 * @param <V> the Graph vertices type
 * @param <W> the weight type
 */
public interface Heuristic<V, W>
{

    /**
     * Applies the <i>h(x)</i> "heuristic estimate" of the distance from the start to the goal.
     *
     * @param current the current visited Vertex in the Graph.
     * @param goal the goal of the Graph visit.
     * @return the "heuristic estimate" of the distance from the start to the goal.
     */
    W applyHeuristic( V current, V goal );

}
