package org.apache.commons.graph.visit;

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

import static org.apache.commons.graph.CommonsGraph.newUndirectedMutableGraph;
import static org.apache.commons.graph.CommonsGraph.visit;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.junit.Test;

public final class VisitTestCase
{

    @Test( expected = IllegalStateException.class )
    public void testNotExistVertex()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> input =
            newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
            {

                @Override
                public void connect()
                {
                }

            } );

        visit( input ).from( new BaseLabeledVertex( "NOT EXIST" ) );
    }

    /**
     * Graph picture can be see
     * <a href="http://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/GraphAlgor/breadthSearch.htm">here</a>
     */
    @Test
    public void verifyBreadthFirstSearch()
    {
        // input graph

        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> input =
        newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            @Override
            public void connect()
            {
                final BaseLabeledVertex r = addVertex( new BaseLabeledVertex( "r" ) );
                final BaseLabeledVertex s = addVertex( new BaseLabeledVertex( "s" ) );
                final BaseLabeledVertex t = addVertex( new BaseLabeledVertex( "t" ) );
                final BaseLabeledVertex u = addVertex( new BaseLabeledVertex( "u" ) );
                final BaseLabeledVertex v = addVertex( new BaseLabeledVertex( "v" ) );
                final BaseLabeledVertex w = addVertex( new BaseLabeledVertex( "w" ) );
                final BaseLabeledVertex x = addVertex( new BaseLabeledVertex( "x" ) );
                final BaseLabeledVertex y = addVertex( new BaseLabeledVertex( "y" ) );

                addEdge( new BaseLabeledEdge( "s <-> r" ) ).from( s ).to( r );
                addEdge( new BaseLabeledEdge( "s <-> w" ) ).from( s ).to( w );

                addEdge( new BaseLabeledEdge( "r <-> v" ) ).from( r ).to( v );

                addEdge( new BaseLabeledEdge( "w <-> t" ) ).from( w ).to( t );
                addEdge( new BaseLabeledEdge( "w <-> x" ) ).from( w ).to( x );

                addEdge( new BaseLabeledEdge( "t <-> u" ) ).from( t ).to( u );
                addEdge( new BaseLabeledEdge( "t <-> x" ) ).from( t ).to( x );

                addEdge( new BaseLabeledEdge( "y <-> u" ) ).from( y ).to( u );
                addEdge( new BaseLabeledEdge( "x <-> y" ) ).from( x ).to( y );
            }

        } );

        // expected graph

        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> expected =
        newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            @Override
            public void connect()
            {
                final BaseLabeledVertex r = addVertex( new BaseLabeledVertex( "r" ) );
                final BaseLabeledVertex s = addVertex( new BaseLabeledVertex( "s" ) );
                final BaseLabeledVertex t = addVertex( new BaseLabeledVertex( "t" ) );
                final BaseLabeledVertex u = addVertex( new BaseLabeledVertex( "u" ) );
                final BaseLabeledVertex v = addVertex( new BaseLabeledVertex( "v" ) );
                final BaseLabeledVertex w = addVertex( new BaseLabeledVertex( "w" ) );
                final BaseLabeledVertex x = addVertex( new BaseLabeledVertex( "x" ) );
                final BaseLabeledVertex y = addVertex( new BaseLabeledVertex( "y" ) );

                addEdge( new BaseLabeledEdge( "s <-> r" ) ).from( s ).to( r );
                addEdge( new BaseLabeledEdge( "s <-> w" ) ).from( s ).to( w );

                addEdge( new BaseLabeledEdge( "r <-> v" ) ).from( r ).to( v );

                addEdge( new BaseLabeledEdge( "w <-> t" ) ).from( w ).to( t );
                addEdge( new BaseLabeledEdge( "w <-> x" ) ).from( w ).to( x );

                addEdge( new BaseLabeledEdge( "t <-> u" ) ).from( t ).to( u );

                addEdge( new BaseLabeledEdge( "x <-> y" ) ).from( x ).to( y );
            }

        } );

        // actual graph

        final Graph<BaseLabeledVertex, BaseLabeledEdge> actual = visit( input ).from( new BaseLabeledVertex( "s" ) ).applyingBreadthFirstSearch();

        // assertion

        assertEquals( expected, actual );
    }

    /**
     * Graph picture can be see
     * <a href="http://aiukswkelasgkelompok7.wordpress.com/metode-pencarian-dan-pelacakan/">here</a>
     */
    @Test
    public void verifyDepthFirstSearch()
    {
        // expected node set
        final List<BaseLabeledVertex> expected = new ArrayList<BaseLabeledVertex>();

        // input graph

        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> input =
        newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            @Override
            public void connect()
            {
                final BaseLabeledVertex a = addVertex( new BaseLabeledVertex( "A" ) );
                final BaseLabeledVertex b = addVertex( new BaseLabeledVertex( "B" ) );
                final BaseLabeledVertex c = addVertex( new BaseLabeledVertex( "C" ) );
                final BaseLabeledVertex d = addVertex( new BaseLabeledVertex( "D" ) );
                final BaseLabeledVertex e = addVertex( new BaseLabeledVertex( "E" ) );
                final BaseLabeledVertex f = addVertex( new BaseLabeledVertex( "F" ) );
                final BaseLabeledVertex g = addVertex( new BaseLabeledVertex( "G" ) );
                final BaseLabeledVertex h = addVertex( new BaseLabeledVertex( "H" ) );
                final BaseLabeledVertex s = addVertex( new BaseLabeledVertex( "S" ) );

                addEdge( new BaseLabeledEdge( "S <-> A" ) ).from( s ).to( a );
                addEdge( new BaseLabeledEdge( "S <-> B" ) ).from( s ).to( b );

                addEdge( new BaseLabeledEdge( "A <-> C" ) ).from( a ).to( c );
                addEdge( new BaseLabeledEdge( "A <-> D" ) ).from( a ).to( d );

                addEdge( new BaseLabeledEdge( "B <-> E" ) ).from( b ).to( e );
                addEdge( new BaseLabeledEdge( "B <-> F" ) ).from( b ).to( f );

                addEdge( new BaseLabeledEdge( "E <-> H" ) ).from( e ).to( h );
                addEdge( new BaseLabeledEdge( "E <-> G" ) ).from( e ).to( g );

                // populate the expected list, order is not the same in the pic, due to Stack use
                expected.add( s );
                expected.add( b );
                expected.add( f );
                expected.add( e );
                expected.add( g );
                expected.add( h );
                expected.add( a );
                expected.add( d );
                expected.add( c );
            }

        } );

        // actual node set

        final List<BaseLabeledVertex> actual =
            visit( input ).from( new BaseLabeledVertex( "S" ) ).applyingDepthFirstSearch( new NodeSequenceVisitor() );

        // assertion

        assertEquals( expected, actual );
    }

}
