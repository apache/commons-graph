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

import static org.apache.commons.graph.utils.Assertions.checkState;

/**
 * TODO fill me!!
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public abstract class AbstractGraphConnection<V, E>
    implements GraphConnection<V, E>
{

    private GraphConnector<V, E> connector;

    /**
     * {@inheritDoc}
     */
    public final void connect( GraphConnector<V, E> connector )
    {
        checkState( this.connector == null, "Re-entry not allowed!" );
        this.connector = connector;

        try
        {
            connect();
        }
        finally
        {
            this.connector = null;
        }
    }

    /**
     * Adds a new vertex to graph connector.
     *
     * @param <N> the Graph vertex type
     * @param node the vertex to add
     * @return the vertex added
     */
    protected final <N extends V> N addVertex( N node )
    {
        return connector.addVertex( node );
    }

    /**
     * Adds a new edge to graph connector.
     *
     * @param <A> the Graph edges type
     * @param arc the edge to add.
     * @return the {@link HeadVertexConnector}
     */
    protected final <A extends E> HeadVertexConnector<V, E> addEdge( A arc )
    {
        return connector.addEdge( arc );
    }

    /**
     * Connects the graph.
     */
    public abstract void connect();

}
