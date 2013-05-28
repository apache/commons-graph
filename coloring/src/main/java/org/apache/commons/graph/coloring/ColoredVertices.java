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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains the color for each vertex and the required number of colors for {@link org.apache.commons.graph.Graph} coloring.
 *
 * @param <V> the Graph vertices type.
 * @param <C> the Color type.
 */
public final class ColoredVertices<V, C>
{

    private final Map<V, C> coloredVertices = new HashMap<V, C>();

    private final List<C> usedColor = new ArrayList<C>();

    /**
     * This class can be instantiated only inside the package
     */
    ColoredVertices()
    {
        // do nothing
    }

    /**
     * Store the input vertex color.
     *
     * @param v the vertex for which storing the color.
     * @param color the input vertex color.
     */
    void addColor( V v, C color )
    {
        coloredVertices.put( v, color );
        int idx = usedColor.indexOf( color );
        if ( idx == -1 )
        {
            usedColor.add( color );
        }
        else
        {
            usedColor.set( idx, color );
        }
    }

    /**
     * Remove the input vertex color.
     *
     * @param v the vertex for which storing the color.
     */
    void removeColor( V v )
    {
        C color = coloredVertices.remove( v );
        usedColor.remove( color );
    }

    /**
     * Returns the color associated to the input vertex.
     *
     * @param v the vertex for which getting the color.
     * @return the color associated to the input vertex.
     */
    public C getColor( V v )
    {
        v = checkNotNull( v, "Impossible to get the color for a null Vertex" );

        return coloredVertices.get( v );
    }

    /**
     * Returns the number of required colors for coloring the Graph.
     *
     * @return the number of required colors for coloring the Graph.
     */
    public int getRequiredColors()
    {
        return usedColor.size();
    }

    /**
     * Tests if the 'vertex' is colored.
     * 
     * @param vertex the vertex 
     * @return true if the colored vertex is contained into the map, false otherwise
     */
    public boolean containsColoredVertex( V vertex )
    {
        return coloredVertices.containsKey( vertex );
    }

}
