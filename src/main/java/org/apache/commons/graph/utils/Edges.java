package org.apache.commons.graph.utils;

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

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;

/**
 * Commons utility methods for {@link Edge} manipulation.
 */
public final class Edges
{

    /**
     * This class can't be instantiated.
     */
    private Edges()
    {
        // do nothing
    }

    /**
     * 
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param vertex
     * @param edge
     * @return
     */
    public static <V extends Vertex, E extends Edge<V>> V getConnectedVertex( V vertex, E edge )
    {
        if ( vertex == null )
        {
            throw new IllegalArgumentException( "Argument 'vertex' must be not null." );
        }
        if ( edge == null )
        {
            throw new IllegalArgumentException( "Argument 'edge' must be not null." );
        }

        if ( vertex.equals( edge.getHead() ) )
        {
            return edge.getTail();
        }
        else if ( vertex.equals( edge.getTail() ) )
        {
            return edge.getHead();
        }
        throw new IllegalArgumentException( format( "Input Vertex %s not contained in Edge %s", vertex, edge ) );
    }

}
