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

import static java.lang.String.format;
import static org.apache.commons.graph.utils.Assertions.checkNotNull;
import static org.apache.commons.graph.utils.Objects.eq;
import static org.apache.commons.graph.utils.Objects.hash;

import java.io.Serializable;

/**
 * Indicates a vertex pair.
 *
 * @param <V> the Graph vertices type
 */
public final class VertexPair<V>
    implements Serializable
{
    private static final long serialVersionUID = 2333503391707156055L;

    /** The head vertex. */
    private final V head;

    /** The tail vertex. */
    private final V tail;

    /**
     * Initializes a new vertex pair.
     *
     * @param head the head Vertex
     * @param tail the tail Vertex
     */
    public VertexPair(final V head, final V tail )
    {
        this.head = checkNotNull( head, "Impossible to construct a Vertex with a null head" );
        this.tail = checkNotNull( tail, "Impossible to construct a Vertex with a null tail" );
    }

    /**
     * @return the source
     */
    public V getHead()
    {
        return head;
    }

    /**
     * @return the target
     */
    public V getTail()
    {
        return tail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {        
        final int prime = 31;
        return hash( 1, prime, head, tail );
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

        if ( obj == null || getClass() != obj.getClass() )
        {
            return false;
        }

        @SuppressWarnings( "unchecked" ) final // equals() invoked against only same VertexPair type
        VertexPair<V> other = (VertexPair<V>) obj;
        return eq( head, other.getHead() )
            && eq( tail, other.getTail() );
    }

    @Override
    public String toString()
    {
        return format( "[head=%s, tail=%s]", head, tail );
    }

}
