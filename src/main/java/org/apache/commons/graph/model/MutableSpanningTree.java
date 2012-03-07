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

import org.apache.commons.graph.weight.Monoid;

/**
 * A memory-based implementation of a mutable spanning tree.
 *
 * This class is NOT thread safe!
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 * @param <W> the weight type
 */
public final class MutableSpanningTree<V, WE, W>
    extends UndirectedMutableGraph<V, WE>
{

    private static final long serialVersionUID = -4371938772248573879L;

    private Monoid<W> weightOperations;

    private W weight;

    public MutableSpanningTree(Monoid<W> weightOperations) {
        this.weightOperations = weightOperations;
        this.weight = weightOperations.zero();
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
        // TODO weight = weightOperations.append( weight, e.getWeight() );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decorateRemoveEdge( WE e )
    {
        // TODO weight = weightOperations.append( weight, weightOperations.inverse( e.getWeight() ) );
    }

}
