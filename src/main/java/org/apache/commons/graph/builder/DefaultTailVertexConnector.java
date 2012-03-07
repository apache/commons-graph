package org.apache.commons.graph.builder;

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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import org.apache.commons.graph.MutableGraph;

final class DefaultTailVertexConnector<V, E>
    implements TailVertexConnector<V, E>
{

    private final MutableGraph<V, E> graph;

    private final E edge;

    private final V head;

    public DefaultTailVertexConnector( MutableGraph<V, E> graph, E edge, V head )
    {
        this.graph = graph;
        this.edge = edge;
        this.head = head;
    }

    public void to( V tail )
    {
        tail = checkNotNull( tail, "Null tail vertex not admitted" );
        graph.addEdge( head, edge, tail );
    }

}
