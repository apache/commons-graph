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

import static org.apache.commons.graph.visit.Visit.breadthFirstSearch;
import static org.apache.commons.graph.visit.Visit.depthFirstSearch;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.junit.Test;

public final class VisitTestCase
{

    /**
     * Graph picture can be see
     * <a href="http://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/GraphAlgor/breadthSearch.htm">here</a>
     */
    @Test
    public void verifyBreadthFirstSearch()
    {
        // vertices

        BaseLabeledVertex r = new BaseLabeledVertex( "r" );
        BaseLabeledVertex s = new BaseLabeledVertex( "s" );
        BaseLabeledVertex t = new BaseLabeledVertex( "t" );
        BaseLabeledVertex u = new BaseLabeledVertex( "u" );
        BaseLabeledVertex v = new BaseLabeledVertex( "v" );
        BaseLabeledVertex w = new BaseLabeledVertex( "w" );
        BaseLabeledVertex x = new BaseLabeledVertex( "x" );
        BaseLabeledVertex y = new BaseLabeledVertex( "y" );

        // input graph

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        input.addVertex( r );
        input.addVertex( s );
        input.addVertex( t );
        input.addVertex( u );
        input.addVertex( v );
        input.addVertex( w );
        input.addVertex( x );
        input.addVertex( y );

        input.addEdge( s, new BaseLabeledEdge( "s <-> r" ), r );
        input.addEdge( s, new BaseLabeledEdge( "s <-> w" ), w );

        input.addEdge( r, new BaseLabeledEdge( "r <-> v" ), v );

        input.addEdge( w, new BaseLabeledEdge( "w <-> t" ), t );
        input.addEdge( w, new BaseLabeledEdge( "w <-> x" ), x );

        input.addEdge( t, new BaseLabeledEdge( "t <-> u" ), u );
        input.addEdge( t, new BaseLabeledEdge( "t <-> x" ), x );

        input.addEdge( y, new BaseLabeledEdge( "y <-> u" ), u );
        input.addEdge( x, new BaseLabeledEdge( "x <-> y" ), y );

        // expected graph

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> expected =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( s, new BaseLabeledEdge( "s <-> r" ), r );
        expected.addEdge( s, new BaseLabeledEdge( "s <-> w" ), w );
        expected.addEdge( r, new BaseLabeledEdge( "r <-> v" ), v );
        expected.addEdge( w, new BaseLabeledEdge( "w <-> t" ), t );
        expected.addEdge( w, new BaseLabeledEdge( "w <-> x" ), x );
        expected.addEdge( t, new BaseLabeledEdge( "t <-> u" ), u );
        expected.addEdge( y, new BaseLabeledEdge( "y <-> x" ), x );

        // actual graph

        Graph<BaseLabeledVertex, BaseLabeledEdge> actual = breadthFirstSearch( input, s );

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
        // vertices

        BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        BaseLabeledVertex b = new BaseLabeledVertex( "B" );
        BaseLabeledVertex c = new BaseLabeledVertex( "C" );
        BaseLabeledVertex d = new BaseLabeledVertex( "D" );
        BaseLabeledVertex e = new BaseLabeledVertex( "E" );
        BaseLabeledVertex f = new BaseLabeledVertex( "F" );
        BaseLabeledVertex g = new BaseLabeledVertex( "G" );
        BaseLabeledVertex h = new BaseLabeledVertex( "H" );
        BaseLabeledVertex s = new BaseLabeledVertex( "S" );

        // input graph

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );
        input.addVertex( d );
        input.addVertex( e );
        input.addVertex( f );
        input.addVertex( g );
        input.addVertex( h );
        input.addVertex( s );

        input.addEdge( s, new BaseLabeledEdge( "S <-> A" ), a );
        input.addEdge( a, new BaseLabeledEdge( "A <-> S" ), s );
        input.addEdge( s, new BaseLabeledEdge( "S <-> B" ), b );
        input.addEdge( b, new BaseLabeledEdge( "B <-> S" ), s );

        input.addEdge( a, new BaseLabeledEdge( "A <-> C" ), c );
        input.addEdge( c, new BaseLabeledEdge( "C <-> A" ), a );
        input.addEdge( a, new BaseLabeledEdge( "A <-> D" ), d );
        input.addEdge( d, new BaseLabeledEdge( "D <-> A" ), a );

        input.addEdge( b, new BaseLabeledEdge( "B <-> E" ), e );
        input.addEdge( e, new BaseLabeledEdge( "E <-> B" ), b );
        input.addEdge( b, new BaseLabeledEdge( "B <-> F" ), f );
        input.addEdge( f, new BaseLabeledEdge( "F <-> B" ), b );

        input.addEdge( e, new BaseLabeledEdge( "E <-> H" ), h );
        input.addEdge( h, new BaseLabeledEdge( "H <-> E" ), e );
        input.addEdge( e, new BaseLabeledEdge( "E <-> G" ), g );
        input.addEdge( g, new BaseLabeledEdge( "G <-> E" ), e );

        // expected node set

        // order is not the same in the pic, due to Stack use
        final List<BaseLabeledVertex> expected = new ArrayList<BaseLabeledVertex>();
        expected.add( s );
        expected.add( b );
        expected.add( f );
        expected.add( e );
        expected.add( g );
        expected.add( h );
        expected.add( a );
        expected.add( d );
        expected.add( c );

        // actual node set

        NodeSequenceVisitor<BaseLabeledVertex, BaseLabeledEdge> visitor =
            new NodeSequenceVisitor<BaseLabeledVertex, BaseLabeledEdge>();
        depthFirstSearch( input, s, visitor );
        final List<BaseLabeledVertex> actual = visitor.getVertices();

        // assertion

        assertEquals( expected, actual );
    }

}
