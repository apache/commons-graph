package org.apache.commons.graph.flow;

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

import org.apache.commons.graph.DirectedGraph;

/**
 * {@link FromHeadBuilder} implementation.
 *
 * @param <V>
 * @param <W>
 * @param <WE>
 * @param <G>
 */
public final class DefaultFromHeadBuilder<V, WE, W, G extends DirectedGraph<V, WE>>
    implements FromHeadBuilder<V, WE, W, G>
{

    private final G graph;

    public DefaultFromHeadBuilder( G graph )
    {
        this.graph = graph;
    }

    public ToTailBuilder<V, WE, W, G> from( V head )
    {
        head = checkNotNull( head, "head vertex has to be specifies when looking for the max flow" );
        return new DefaultToTailBuilder<V, WE, W, G>( graph, head );
    }

}
