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

import static java.lang.String.format;
import static org.apache.commons.graph.utils.Objects.eq;

import org.apache.commons.graph.Mapper;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.weight.Monoid;

/**
 * Support {@link WeightedPath} implementation, optimized for algorithms (such Dijkstra's) that need to rebuild the path
 * traversing the predecessor list bottom-up.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 * @param <W> the weight type
 */
public final class InMemoryWeightedPath<V, WE, W>
    extends InMemoryPath<V, WE>
    implements WeightedPath<V, WE, W>
{

    private static final long serialVersionUID = 7937494144459068796L;

    private final Monoid<W> weightOperations;

    private final Mapper<WE, W> weightedEdges;

    private W weight;

    /**
     * Creates a new instance of {@link InMemoryWeightedPath}.
     * 
     * @param start the start vertex
     * @param target the target vertex
     * @param weightOperations 
     * @param weightedEdges
     */
    public InMemoryWeightedPath(final V start, final V target, final Monoid<W> weightOperations, final Mapper<WE, W> weightedEdges )
    {
        super( start, target );
        this.weightOperations = weightOperations;
        this.weightedEdges = weightedEdges;

        this.weight = weightOperations.identity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addConnectionInHead(final V head, final WE edge, final V tail )
    {
        super.addConnectionInHead( head, edge, tail );
        increaseWeight( edge );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addConnectionInTail(final V head, final WE edge, final V tail )
    {
        super.addConnectionInTail( head, edge, tail );
        increaseWeight( edge );
    }

    /**
     * Increase the path weight with the weight of the input weighted edge.
     *
     * @param edge the edge whose weight is used to increase the path weight
     */
    private void increaseWeight(final WE edge )
    {
        weight = weightOperations.append( weightedEdges.map( edge ), weight );
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
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( weight == null ) ? 0 : weight.hashCode() );
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( !super.equals( obj ) )
        {
            return false;
        }

        if ( getClass() != obj.getClass() )
        {
            return false;
        }

        @SuppressWarnings( "unchecked" ) final // test against any WeightedPath typed instance
        InMemoryWeightedPath<Object, Object, W> other = (InMemoryWeightedPath<Object, Object, W>) obj;
        return eq( weight, other.getWeight() );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return format( "InMemoryPath [weigth=%s, vertices=%s, edges=%s]", weight, getVertices(), getEdges() );
    }

}
