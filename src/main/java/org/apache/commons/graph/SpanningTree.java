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
 * A {@link SpanningTree} of a connected, undirected graph is a tree
 * composed of all the vertices and some (or perhaps all) of the edges of the Graph.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 * @param <W> the weight type
 */
public interface SpanningTree<V, WE, W>
    extends UndirectedGraph<V, WE>, Weighted<W>
{

}
