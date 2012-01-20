package org.apache.commons.graph.shortestpath;

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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.weight.OrderedMonoid;

/**
 * Stores and compares Graph Vertices weights.
 *
 * @param <V> the Graph vertices type
 * @param <W> the weight type
 */
final class ShortestDistances<V extends Vertex, W>
    implements Comparator<V>
{

    private final Map<V, W> distances = new HashMap<V, W>();

    private final OrderedMonoid<W> orderedMonoid;

    public ShortestDistances( OrderedMonoid<W> orderedMonoid )
    {
        this.orderedMonoid = orderedMonoid;
    }

    /**
     * Returns the distance related to input vertex, or null if it wasn't previously visited.
     *
     * <b>NOTE</b>: the method {@link alreadyVisited} should be used first to check if
     * the input vertex was already visited and a distance value is available for it.
     *
     * @param vertex the vertex whose distance has to be retrieved
     * @return the distance related to input vertex, or null if it wasn't previously visited.
     */
    public W getWeight( V vertex )
    {
        return distances.get( vertex );
    }

    /**
     * Checks if the input {@code Vertex} was already visited.
     *
     * @param vertex the input {@code Vertex}
     * @return true if the input {@code Vertex} was already visited, false otherwise.
     */
    public boolean alreadyVisited( V vertex )
    {
        return distances.containsKey( vertex );
    }

    /**
     * Update the input vertex distance.
     *
     * @param vertex the vertex for which the distance has to be updated
     * @param distance the new input vertex distance
     */
    public void setWeight( V vertex, W distance )
    {
        distances.put( vertex, distance );
    }

    /**
     * {@inheritDoc}
     */
    public int compare( V left, V right )
    {
        if ( !alreadyVisited( left ) && !alreadyVisited( right ) )
        {
            return 0;
        }
        else if ( !alreadyVisited( left ) )
        {
            return 1;
        }
        else if ( !alreadyVisited( right ) )
        {
            return -1;
        }
        return orderedMonoid.compare( getWeight( left ), getWeight( right ) );
    }

}
