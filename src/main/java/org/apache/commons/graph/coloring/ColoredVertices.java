package org.apache.commons.graph.coloring;

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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;

/**
 * Maintains the color for each {@link Vertex} and the required number of colors
 * for {@link Graph} coloring.
 *
 * @param <V> the Graph vertices type.
 */
public final class ColoredVertices<V extends Vertex>
{

    private final Map<V, Integer> coloredVertices = new HashMap<V, Integer>();

    private Integer requiredColors;

    /**
     * This class can be instantiated only inside the package
     */
    ColoredVertices()
    {
        // do nothing
    }

    /**
     * Store the input {@link Vertex} color.
     *
     * @param v the {@link Vertex} for which storing the color.
     * @param color the input {@link Vertex} color.
     */
    void addColor( V v, Integer color )
    {
        if ( requiredColors == null || color.compareTo( requiredColors ) > 0 )
        {
            requiredColors = color;
        }
        coloredVertices.put( v, color );
    }

    /**
     * Returns the color associated to the input {@link Vertex}.
     *
     * @param v the {@link Vertex} for which getting the color.
     * @return the color associated to the input {@link Vertex}.
     */
    public Integer getColor( V v )
    {
        if ( v == null )
        {
            throw new IllegalArgumentException( "Impossible to get the color for a null Vertex" );
        }

        return coloredVertices.get( v );
    }

    /**
     * Returns the number of required colors for coloring the Graph.
     *
     * @return the number of required colors for coloring the Graph.
     */
    public int getRequiredColors()
    {
        // if requiredColors = 0, it would return 0, and that's wrong
        return requiredColors + 1;
    }

}