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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import static java.lang.String.format;

/**
 * Indicates a {@link Vertex} pair.
 *
 * @param <V> the Graph vertices type
 */
public final class VertexPair<V extends Vertex>
{
    /** The head vertex. */
    private final V head;
    /** The tail vertex. */
    private final V tail;

    /**
     * Initializes a new {@link Vertex} pair.
     *
     * @param head the head Vertex
     * @param tail the tail Vertex
     */
    public VertexPair( V head, V tail )
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
        int result = 1;
        result = prime * result + ( ( head == null ) ? 0 : head.hashCode() );
        result = prime * result + ( ( tail == null ) ? 0 : tail.hashCode() );
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

        if ( obj == null )
        {
            return false;
        }

        if ( getClass() != obj.getClass() )
        {
            return false;
        }

        @SuppressWarnings( "unchecked" ) // equals() invoked against only same VertexPair type
        VertexPair<V> other = (VertexPair<V>) obj;
        if ( !head.equals( other.getHead() ) )
        {
            return false;
        }

        if ( !tail.equals( other.getTail() ) )
        {
            return false;
        }

        return true;
    }

    @Override
    public String toString()
    {
        return format( "[head=%s, tail=%s]", head, tail );
    }

}
