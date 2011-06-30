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

import static org.apache.commons.graph.coloring.GraphColoring.coloring;
import static org.apache.commons.graph.utils.GraphUtils.buildBipartedGraph;
import static org.apache.commons.graph.utils.GraphUtils.buildCompleteGraph;
import static org.apache.commons.graph.utils.GraphUtils.buildSudokuGraph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.junit.Test;

/**
 * 
 */
public class GraphColoringTestCase
{

    @Test
    public void testCromaticNumber()
        throws Exception
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        BaseLabeledVertex one = new BaseLabeledVertex( "1" );
        BaseLabeledVertex two = new BaseLabeledVertex( "2" );
        BaseLabeledVertex three = new BaseLabeledVertex( "3" );

        g.addVertex( one );
        g.addVertex( two );
        g.addVertex( three );

        g.addEdge( one, new BaseLabeledEdge( "1 -> 2" ), two );
        g.addEdge( two, new BaseLabeledEdge( "2 -> 3" ), three );
        g.addEdge( three, new BaseLabeledEdge( "3 -> 1" ), one );

        ColoredVertices<BaseLabeledVertex> coloredVertices = coloring( g );
        assertEquals( 3, coloredVertices.getRequiredColors() );

        checkColoring( g, coloredVertices );
    }

    @Test
    public void testCromaticNumberComplete()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 100, g1 );

        ColoredVertices<BaseLabeledVertex> coloredVertices = coloring( g1 );
        assertEquals( 100, coloredVertices.getRequiredColors() );

        checkColoring( g1, coloredVertices );
    }

    @Test
    public void testCromaticNumberBiparted()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildBipartedGraph( 100, g1 );

        ColoredVertices<BaseLabeledVertex> coloredVertices = coloring( g1 );

        assertEquals( 2, coloredVertices.getRequiredColors() );
        checkColoring( g1, coloredVertices );
    }

    @Test
    public void testCromaticNumberSparseGraph()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        for ( int i = 0; i < 100; i++ )
        {
            g1.addVertex( new BaseLabeledVertex( "" + i ) );
        }

        ColoredVertices<BaseLabeledVertex> coloredVertices = coloring( g1 );

        assertEquals( 1, coloredVertices.getRequiredColors() );
        checkColoring( g1, coloredVertices );
    }

    /**
     * see <a href="http://en.wikipedia.org/wiki/Crown_graph">wiki</a> for more details
     */
    @Test
    public void testCrawnGraph()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        BaseLabeledVertex one = new BaseLabeledVertex( "1" );
        BaseLabeledVertex two = new BaseLabeledVertex( "2" );
        BaseLabeledVertex three = new BaseLabeledVertex( "3" );
        BaseLabeledVertex four = new BaseLabeledVertex( "4" );
        BaseLabeledVertex five = new BaseLabeledVertex( "5" );
        BaseLabeledVertex six = new BaseLabeledVertex( "6" );

        g.addVertex( one );
        g.addVertex( two );
        g.addVertex( three );
        g.addVertex( four );
        g.addVertex( five );
        g.addVertex( six );

        g.addEdge( one, new BaseLabeledEdge( "1 -> 2" ), two );
        g.addEdge( two, new BaseLabeledEdge( "2 -> 3" ), three );
        g.addEdge( three, new BaseLabeledEdge( "3 -> 4" ), four );
        g.addEdge( four, new BaseLabeledEdge( "4 -> 5" ), five );
        g.addEdge( five, new BaseLabeledEdge( "5 -> 6" ), six );
        g.addEdge( six, new BaseLabeledEdge( "5 -> 1" ), one );

        ColoredVertices<BaseLabeledVertex> coloredVertices = coloring( g );
        assertEquals( 2, coloring( g ).getRequiredColors() );

        checkColoring( g, coloredVertices );
    }

    @Test
    public void testSudoku()
        throws Exception
    {
        UndirectedMutableGraph<Vertex, Edge> g1 = buildSudokuGraph();

        // The true color number for this graph is 9. but the greedy heuristic is not the best and returns 11.
        ColoredVertices<Vertex> sudoku = GraphColoring.coloring( g1 );
        assertEquals( 11, sudoku.getRequiredColors() );

        checkColoring( g1, sudoku );
    }

    /**
     * This method checks if all connected vertices have different colors.
     * 
     * @param g
     * @param coloredVertices
     */
    private <V extends Vertex, E extends Edge> void checkColoring( UndirectedMutableGraph<V, E> g,
                                                                   ColoredVertices<V> coloredVertices )
    {
        for ( E e : g.getEdges() )
        {
            VertexPair<V> vp = g.getVertices( e );
            assertTrue( coloredVertices.getColor( vp.getHead() ) != coloredVertices.getColor( vp.getTail() ) );
        }
    }

}
