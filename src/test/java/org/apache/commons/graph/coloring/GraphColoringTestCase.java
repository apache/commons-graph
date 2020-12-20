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

import static org.apache.commons.graph.CommonsGraph.coloring;
import static org.apache.commons.graph.CommonsGraph.newUndirectedMutableGraph;
import static org.apache.commons.graph.utils.GraphUtils.buildBipartedGraph;
import static org.apache.commons.graph.utils.GraphUtils.buildCompleteGraph;
import static org.apache.commons.graph.utils.GraphUtils.buildCrownGraph;
import static org.apache.commons.graph.utils.GraphUtils.buildSudokuGraph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.apache.commons.graph.UndirectedGraph;
import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.junit.Test;

/**
 *
 */
public class GraphColoringTestCase extends AbstractColoringTest
{

    private final Set<Integer> colors = createColorsList( 11 );

    @Test( expected = NullPointerException.class )
    public void testNullGraph()
    {
        coloring( (UndirectedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>) null ).withColors( null ).applyingGreedyAlgorithm();
    }

    @Test( expected = NullPointerException.class )
    public void testNullColorGraph()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        coloring( g ).withColors( null ).applyingBackTrackingAlgorithm();
    }

    @Test
    public void testEmptyGraph()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        final ColoredVertices<BaseLabeledVertex, Integer> coloredVertices = coloring( g ).withColors( createColorsList( 1 ) ).applyingGreedyAlgorithm();
        assertNotNull( coloredVertices );
        assertEquals( 0, coloredVertices.getRequiredColors() );
    }

    @Test( expected = NotEnoughColorsException.class )
    public void testNotEnoughtColorGreedyGraph()
    {
        final BaseLabeledVertex two = new BaseLabeledVertex( "2" );

        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
            {

                @Override
                public void connect()
                {
                    final BaseLabeledVertex one = addVertex( new BaseLabeledVertex( "1" ) );
                    addVertex( two );
                    final BaseLabeledVertex three = addVertex( new BaseLabeledVertex( "3" ) );

                    addEdge( new BaseLabeledEdge( "1 -> 2" ) ).from( one ).to( two );
                    addEdge( new BaseLabeledEdge( "2 -> 3" ) ).from( two ).to( three );
                    addEdge( new BaseLabeledEdge( "3 -> 1" ) ).from( three ).to( one );
                }

            } );
        coloring( g ).withColors( createColorsList( 1 ) ).applyingGreedyAlgorithm();
    }

    @Test
    public void testCromaticNumber()
        throws Exception
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
        newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            @Override
            public void connect()
            {
                final BaseLabeledVertex one = addVertex( new BaseLabeledVertex( "1" ) );
                final BaseLabeledVertex two = addVertex( new BaseLabeledVertex( "2" ) );
                final BaseLabeledVertex three = addVertex( new BaseLabeledVertex( "3" ) );

                addEdge( new BaseLabeledEdge( "1 -> 2" ) ).from( one ).to( two );
                addEdge( new BaseLabeledEdge( "2 -> 3" ) ).from( two ).to( three );
                addEdge( new BaseLabeledEdge( "3 -> 1" ) ).from( three ).to( one );
            }

        } );

        final ColoredVertices<BaseLabeledVertex, Integer> coloredVertices =
                        coloring( g ).withColors( colors ).applyingGreedyAlgorithm();
        checkColoring( g, coloredVertices );
    }

    @Test
    public void testCromaticNumberComplete()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 100, g1 );

        final ColoredVertices<BaseLabeledVertex, Integer> coloredVertices =
                        coloring( g1 ).withColors( createColorsList( 100 ) ).applyingGreedyAlgorithm();
        checkColoring( g1, coloredVertices );
    }

    @Test
    public void testCromaticNumberBiparted()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildBipartedGraph( 100, g1 );

        final ColoredVertices<BaseLabeledVertex, Integer> coloredVertices =
                        coloring( g1 ).withColors( colors ).applyingGreedyAlgorithm();

        checkColoring( g1, coloredVertices );
    }

    @Test
    public void testCromaticNumberSparseGraph()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        for ( int i = 0; i < 100; i++ )
        {
            g1.addVertex( new BaseLabeledVertex( String.valueOf( i ) ) );
        }

        final ColoredVertices<BaseLabeledVertex, Integer> coloredVertices =
                        coloring( g1 ).withColors( colors ).applyingGreedyAlgorithm();

        assertEquals( 1, coloredVertices.getRequiredColors() );
        checkColoring( g1, coloredVertices );
    }

    /**
     * see <a href="http://en.wikipedia.org/wiki/Crown_graph">wiki</a> for more details
     */
    @Test
    public void testCrawnGraph()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCrownGraph( 6, g );

        final ColoredVertices<BaseLabeledVertex, Integer> coloredVertices =
                        coloring( g ).withColors( colors ).applyingGreedyAlgorithm();
        checkColoring( g, coloredVertices );
    }

    @Test
    public void testSudoku()
        throws Exception
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g1 =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildSudokuGraph( g1 );

        //Optimal solution is to use 9 colors, not all algorithms can find solution with minimal colors.
        //greedy algorithm needs 13 colors to fetch solution ATM.

        final Set<Integer> col = createColorsList( 9 );

        final ColoredVertices<BaseLabeledVertex, Integer> sudoku =
                        coloring( g1 ).withColors( col ).applyingBackTrackingAlgorithm();
        checkColoring( g1, sudoku );
    }

}
