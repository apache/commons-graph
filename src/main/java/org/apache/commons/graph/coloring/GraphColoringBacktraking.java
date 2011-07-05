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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.UndirectedGraph;
import org.apache.commons.graph.Vertex;

/**
 * Graph m-coloring algorithm. This algorithm uses a bruteforce backtraking procedure to find a graph color using a
 * predefined set of colors.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
class GraphColoringBacktraking<V extends Vertex, E extends Edge>
{

    final private ColoredVertices<V> coloredVertices;

    final private UndirectedGraph<V, E> g;

    final private Set<Integer> colors;

    final private List<V> vertexList = new ArrayList<V>();

    /**
     * Creates a new instance of {@link GraphColoringBacktraking}.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param g The graph.
     * @param colors The list of colors.
     */
    GraphColoringBacktraking( UndirectedGraph<V, E> g, Set<Integer> colors, ColoredVertices<V> partialColoredVertex )
    {
        this.coloredVertices = partialColoredVertex;
        this.g = g;
        this.colors = colors;
    }

    /**
     * Tries to color the graph using a predefined set of colors.
     *
     * @return true if all vertices have been colored, false otherwise.
     */
    ColoredVertices<V> coloring()
    {
        for ( V v : g.getVertices() )
        {
            if ( !coloredVertices.containsColoredVertex( v ) )
            {
                vertexList.add( v );
            }
        }

        if ( backtraking( -1 ) )
        {
            return coloredVertices;
        }
        return null;
    }

    /**
     * This is the recursive step.
     * 
     * @param result The set that will be returned
     * @param element the element
     * @return true if there is a valid coloring for the graph, false otherwise.
     */
    private boolean backtraking( int currentVertexIndex )
    {
        if ( currentVertexIndex != -1 && isThereColorConflict( vertexList.get( currentVertexIndex ) ) )
        {
            return false;
        }
        if ( currentVertexIndex == vertexList.size() - 1 )
        {
            return true;
        }

        int next = currentVertexIndex + 1;
        V nextVertex = vertexList.get( next );
        for ( Integer color : colors )
        {
            coloredVertices.addColor( nextVertex, color );
            boolean isDone = backtraking( next );
            if ( isDone )
            {
                return true;
            }
        }
        coloredVertices.removeColor( nextVertex );
        return false;
    }

    /**
     * Tests if there is some adiacent vertices with the same color.
     * 
     * @param currentVertex
     * @return
     */
    private boolean isThereColorConflict( V currentVertex )
    {
        if ( currentVertex == null )
        {
            return false;
        }
        Integer nextVertecColor = coloredVertices.getColor( currentVertex );
        if ( nextVertecColor == null )
            return false;

        for ( V abj : g.getConnectedVertices( currentVertex ) )
        {
            Integer adjColor = coloredVertices.getColor( abj );
            if ( adjColor != null && nextVertecColor.equals( adjColor ) )
            {
                return true;
            }

        }
        return false;
    }

}
