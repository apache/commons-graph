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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.model.UndirectedMutableGraph;

/**
 * Abstract class used for test coloring.
 */
abstract class AbstractColoringTest
{

    AbstractColoringTest()
    {
    }

    /**
     * Return a random association with index and a color string in RGB.
     */
    protected Map<Integer, String> createColorMap(final int numColor )
    {
        final Map<Integer, String> colorCodes = new HashMap<Integer, String>();
        for ( int i = 0; i < 100; i++ )
        {
            final Random rnd = new Random( i );
            colorCodes.put( i, String.format( "\"#%2x%2x%2x\"", rnd.nextInt( 255 ), rnd.nextInt( 255 ), rnd.nextInt( 255 ) ) );
        }
        return colorCodes;
    }

    /**
     * Creates a list of integer colors.
     *
     * @param colorNumber number of colors
     * @return the list.
     */
    protected Set<Integer> createColorsList(final int colorNumber )
    {
        final Set<Integer> colors = new HashSet<Integer>();
        for ( int j = 0; j < colorNumber; j++ )
        {
            colors.add( j );
        }
        return colors;
    }

    /**
     * This method checks if all connected vertices have different colors.
     *
     * @param g
     * @param coloredVertices
     */
    protected <V, E, C> void checkColoring(final UndirectedMutableGraph<V, E> g,
                                           final ColoredVertices<V, C> coloredVertices )
    {
        for ( final E e : g.getEdges() )
        {
            final VertexPair<V> vp = g.getVertices( e );
            final C h = coloredVertices.getColor( vp.getHead() );
            final C t = coloredVertices.getColor( vp.getTail() );

            assertNotNull( h );
            assertNotNull( t );
            assertTrue( !h.equals( t ) );
        }
    }

}
