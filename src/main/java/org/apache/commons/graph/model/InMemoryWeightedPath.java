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

import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedPath;

/**
 * Support {@link WeightedPath} implementation, optimized for algorithms (such Dijkstra's) that need to rebuild the path
 * traversing the predecessor list bottom-up.
 *
 * @param <V> the Graph vertices type
 * @param <WE> the Graph weighted edges type
 */
public final class InMemoryWeightedPath<V extends Vertex, WE extends WeightedEdge>
    extends InMemoryPath<V, WE>
    implements WeightedPath<V, WE>
{

    private Double weigth = 0D;

    public InMemoryWeightedPath( V start, V target )
    {
        super( start, target );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdgeInHead( WE edge )
    {
        super.addEdgeInHead( edge );
        increaseWeight( edge );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdgeInTail( WE edge )
    {
        super.addEdgeInTail( edge );
        increaseWeight( edge );
    }

    /**
     * Increase the path weight
     *
     * @param edge the edge which weigth increase the path weigth
     */
    private void increaseWeight( WE edge )
    {
        weigth = edge.getWeight().doubleValue() + weigth.doubleValue();
    }

    /**
     * {@inheritDoc}
     */
    public Double getWeight()
    {
        return weigth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( weigth == null ) ? 0 : weigth.hashCode() );
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object obj )
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

        @SuppressWarnings( "unchecked" ) // test against any WeightedPath typed instance
        InMemoryWeightedPath<Vertex, WeightedEdge> other = (InMemoryWeightedPath<Vertex, WeightedEdge>) obj;
        if ( !weigth.equals( other.getWeight() ) )
        {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return format( "InMemoryPath [weigth=%s, vertices=%s, edges=%s]", weigth, getVertices(), getEdges() );
    }

}
