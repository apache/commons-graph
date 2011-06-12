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
 * An {@code Edge} is the link that connect a pair of {@link Vertex}.
 *
 * A {@link Graph} in which {@link Edge}s have no orientation, {@link Vertex} members are not ordered pairs.
 *
 * In a {@link DirectedGraph}, {@link Edge}s have orientation, so relation expressed by the {@link Edge} has to be
 * intended from {@link #getHead()} to {@link #getTail()}.
 *
 * @param <V> the Graph vertices type
 */
public interface Edge<V extends Vertex>
{

    /**
     * Return the head of this edge.
     *
     * @return the head of this edge.
     */
    V getHead();

    /**
     * Return the tail of this edge.
     *
     * @return the tail of this edge.
     */
    V getTail();

}
