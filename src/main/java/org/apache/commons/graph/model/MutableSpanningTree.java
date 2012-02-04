package org.apache.commons.graph.model;

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

import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.weight.Monoid;

/**
 * A memory-based implementation of a mutable spanning tree.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 * @param <W> the weight type
 */
public final class MutableSpanningTree<V extends Vertex, WE extends WeightedEdge<W>, W>
    extends UndirectedMutableGraph<V, WE>
    implements SpanningTree<V, WE, W>
{

    private static final long serialVersionUID = -4371938772248573879L;

    private Monoid<W> monoid;

    private W weight;

    public MutableSpanningTree(Monoid<W> monoid) {
        this.monoid = monoid;
        this.weight = monoid.zero();
    }

    /**
     * {@inheritDoc}
     */
    public W getWeight()
    {
        return weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateAddEdge( V head, WE e, V tail )
    {
        super.decorateAddEdge( head, e, tail );
        weight = monoid.append( weight, e.getWeight() );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateRemoveEdge( WE e )
    {
        weight = monoid.append( weight, monoid.inverse( e.getWeight() ) );
    }

}
