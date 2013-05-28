package org.apache.commons.graph.connectivity;

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
 * Builder to specify the set of vertices included into a connected component.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public interface ConnectivityBuilder<V, E>
{

    /**
     * Specifies the set of vertices included into a connected component.
     *
     * @param vertices the set of vertices included into a connected component.
     * @return the connectivity algorithm selector.
     */
    ConnectivityAlgorithmsSelector<V, E> includingVertices( V... vertices );

    /**
     * Find all the connected components included into the specified graph
     *
     * @return the connectivity algorithm selector.
     */
    ConnectivityAlgorithmsSelector<V, E> includingAllVertices();

}
