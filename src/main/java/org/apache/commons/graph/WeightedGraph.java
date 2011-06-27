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
 * A graph is a {@code WeightedGraph} if a number (weight) is assigned to each edge.
 * Such weights might represent, for example, costs, lengths or capacities, etc. depending on the problem.
 * The weight of the graph is the sum of the weights given to all edges.
 *
 * @param <V> the Graph vertices type.
 * @param <WE> the Graph weighted edges type
 */
public interface WeightedGraph<V extends Vertex, WE extends WeightedEdge>
    extends Graph<V, WE>
{

}
