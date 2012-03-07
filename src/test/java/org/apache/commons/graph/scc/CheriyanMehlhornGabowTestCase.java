package org.apache.commons.graph.scc;

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

import static org.apache.commons.graph.CommonsGraph.findStronglyConnectedComponent;
import static org.apache.commons.graph.CommonsGraph.newDirectedMutableGraph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.junit.Test;

/**
 * Test for Tarjan's algorithm implementation,
 * see the <a href="http://scienceblogs.com/goodmath/2007/10/computing_strongly_connected_c.php">online</a> testcase.
 */
public final class CheriyanMehlhornGabowTestCase
{

    @Test( expected = NullPointerException.class )
    public void testNullGraph()
    {
        DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>, Integer> graph = null;
        findStronglyConnectedComponent( graph ).applyingCheriyanMehlhornGabow();
    }

    @Test
    public void testEmptyGraph()
    {
        DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>, Integer> graph =
            new DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>, Integer>();

        findStronglyConnectedComponent( graph ).applyingCheriyanMehlhornGabow();
    }

    @Test
    public void testSparse()
    {
        DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>, Integer> graph =
            newDirectedMutableWeightedGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>>()
            {

                @Override
                public void connect()
                {
                    addVertex( new BaseLabeledVertex( "A" ) );
                    addVertex( new BaseLabeledVertex( "B" ) );
                    addVertex( new BaseLabeledVertex( "C" ) );
                    addVertex( new BaseLabeledVertex( "D" ) );
                    addVertex( new BaseLabeledVertex( "E" ) );
                    addVertex( new BaseLabeledVertex( "F" ) );
                }
            } );

        // expected strong components
        final int expected = 6;

        // actual strong components
        Set<Set<BaseLabeledVertex>> actual = findStronglyConnectedComponent( graph ).applyingCheriyanMehlhornGabow();

        assertEquals( actual.size(), expected );
    }
    
    @Test
    public void verifyHasStronglyConnectedComponents()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        final BaseLabeledVertex b = new BaseLabeledVertex( "B" );
        final BaseLabeledVertex c = new BaseLabeledVertex( "C" );
        final BaseLabeledVertex d = new BaseLabeledVertex( "D" );
        final BaseLabeledVertex e = new BaseLabeledVertex( "E" );
        final BaseLabeledVertex f = new BaseLabeledVertex( "F" );
        final BaseLabeledVertex g = new BaseLabeledVertex( "G" );
        final BaseLabeledVertex h = new BaseLabeledVertex( "H" );

        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph =
        newDirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            public void connect()
            {
                addVertex( a );
                addVertex( b );
                addVertex( c );
                addVertex( d );
                addVertex( e );
                addVertex( f );
                addVertex( g );
                addVertex( h );

                addEdge( new BaseLabeledEdge( "A -> F" ) ).from( a ).to( f );
                addEdge( new BaseLabeledEdge( "A -> B" ) ).from( a ).to( b );
                addEdge( new BaseLabeledEdge( "B -> D" ) ).from( b ).to( d );
                addEdge( new BaseLabeledEdge( "C -> G" ) ).from( c ).to( g );
                addEdge( new BaseLabeledEdge( "D -> A" ) ).from( d ).to( a );
                addEdge( new BaseLabeledEdge( "D -> G" ) ).from( d ).to( g );
                addEdge( new BaseLabeledEdge( "E -> C" ) ).from( e ).to( c );
                addEdge( new BaseLabeledEdge( "E -> F" ) ).from( e ).to( f );
                addEdge( new BaseLabeledEdge( "F -> E" ) ).from( f ).to( e );
                addEdge( new BaseLabeledEdge( "G -> H" ) ).from( g ).to( h );
                addEdge( new BaseLabeledEdge( "H -> C" ) ).from( h ).to( c );
            }

        } );

        Set<Set<BaseLabeledVertex>> expected = new HashSet<Set<BaseLabeledVertex>>();
        Set<BaseLabeledVertex> scc1 = new HashSet<BaseLabeledVertex>();
        Collections.addAll( scc1, a, b, d );
        expected.add( scc1 );
        Set<BaseLabeledVertex> scc2 = new HashSet<BaseLabeledVertex>();
        Collections.addAll( scc2, e, f );
        expected.add( scc2 );
        Set<BaseLabeledVertex> scc3 = new HashSet<BaseLabeledVertex>();
        Collections.addAll( scc3, g, h, c );
        expected.add( scc3 );

        Set<Set<BaseLabeledVertex>> actual = findStronglyConnectedComponent( graph ).applyingCheriyanMehlhornGabow();
        
        assertFalse( actual.isEmpty() );
        assertEquals( expected, actual );
    }

}
