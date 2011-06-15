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

import org.apache.commons.graph.Vertex;

final class ShortestDistances<V extends Vertex>
    extends HashMap<V, Double>
    implements Comparator<V>
{

    private static final long serialVersionUID = 568538689459177637L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Double get( Object key )
    {
        Double distance = super.get( key );
        return (distance == null) ? Double.POSITIVE_INFINITY : distance;
    }

    /**
     * {@inheritDoc}
     */
    public int compare( V left, V right )
    {
        return get( left ).compareTo( get( right ) );
    }

}
