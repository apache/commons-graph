package org.apache.commons.graph.shared;

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

/**
 * Stores and compares Graph Vertices weights.
 *
 * @param <V> the Graph vertices type
 */
public final class ShortestDistances<V extends Vertex>
    implements Comparator<V>
{

    private static final long serialVersionUID = 568538689459177637L;

    private final Map<V, Double> distances = new HashMap<V, Double>();

    /**
     * Returns the distance related to input vertex, or {@code INFINITY} if it wasn't previously visited.
     *
     * @param vertex the vertex for which the distance has to be retrieved
     * @return the distance related to input vertex, or {@code INFINITY} if it wasn't previously visited.
     */
    public Double getWeight( V vertex )
    {
        Double distance = distances.get( vertex );

        if ( distance == null )
        {
            return Double.POSITIVE_INFINITY;
        }

        return distance;
    }

    /**
     * Update the input vertex distance.
     *
     * @param vertex the vertex for which the distance has to be updated
     * @param distance the new input vertex distance
     */
    public void setWeight( V vertex, Double distance )
    {
        distances.put( vertex, distance );
    }

    /**
     * {@inheritDoc}
     */
    public int compare( V left, V right )
    {
        return getWeight( left ).compareTo( getWeight( right ) );
    }

}
