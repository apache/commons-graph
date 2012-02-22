package org.apache.commons.graph.flow;

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

import static org.apache.commons.graph.CommonsGraph.findMaxFlow;
import static org.apache.commons.graph.CommonsGraph.newDirectedMutableWeightedGraph;
import static org.junit.Assert.assertEquals;

import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.DirectedMutableWeightedGraph;
import org.apache.commons.graph.weight.primitive.IntegerWeightBaseOperations;
import org.junit.Test;

/**
 * Test for Edmonds-Karp algorithm implementation.
 * The test graph is available on
 * <a href="http://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm#Example">Wikipedia</a>.
 */
public class EdmondsKarpTestCase
{

    @Test( expected = NullPointerException.class )
    public void testNullGraph()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        final BaseLabeledVertex g = new BaseLabeledVertex( "G" );

        // actual max flow
        findMaxFlow( (DirectedMutableWeightedGraph<Vertex, WeightedEdge<Integer>, Integer>)  null ).from( a ).to( g ).applyingEdmondsKarp( new IntegerWeightBaseOperations() );
    }

    @Test( expected = NullPointerException.class )
    public void testNullVertices()
    {

        final BaseLabeledVertex a = null;
        final BaseLabeledVertex g = null;

        DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>, Integer> graph =
            newDirectedMutableWeightedGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>>()
            {
                @Override
                public void connect()
                {
                }

            } );

        // actual max flow
        findMaxFlow( graph ).from( a ).to( g ).applyingEdmondsKarp( new IntegerWeightBaseOperations() );
    }

    @Test
    public void testSparse()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        final BaseLabeledVertex g = new BaseLabeledVertex( "G" );

        DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>, Integer> graph =
            newDirectedMutableWeightedGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>>()
            {

                @Override
                public void connect()
                {
                    addVertex( a );
                    BaseLabeledVertex b = addVertex( new BaseLabeledVertex( "B" ) );
                    BaseLabeledVertex c = addVertex( new BaseLabeledVertex( "C" ) );
                    addVertex( new BaseLabeledVertex( "D" ) );
                    addVertex( new BaseLabeledVertex( "E" ) );
                    addVertex( new BaseLabeledVertex( "F" ) );
                    addVertex( g );

                    addEdge( new BaseLabeledWeightedEdge<Integer>( "A -> B", 3 ) ).from( a ).to( b );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "B -> C", 4 ) ).from( b ).to( c );
                }
            } );

        // expected max flow
        final Integer expected = 0;

        // actual max flow
        Integer actual = findMaxFlow( graph ).from( a ).to( g ).applyingEdmondsKarp( new IntegerWeightBaseOperations() );
        assertEquals( actual, expected );
    }

    @Test
    public void findMaxFlowAndVerify()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        final BaseLabeledVertex g = new BaseLabeledVertex( "G" );

        DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>, Integer> graph =
            newDirectedMutableWeightedGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>>()
            {

                @Override
                public void connect()
                {
                    addVertex( a );
                    BaseLabeledVertex b = addVertex( new BaseLabeledVertex( "B" ) );
                    BaseLabeledVertex c = addVertex( new BaseLabeledVertex( "C" ) );
                    BaseLabeledVertex d = addVertex( new BaseLabeledVertex( "D" ) );
                    BaseLabeledVertex e = addVertex( new BaseLabeledVertex( "E" ) );
                    BaseLabeledVertex f = addVertex( new BaseLabeledVertex( "F" ) );
                    addVertex( g );

                    addEdge( new BaseLabeledWeightedEdge<Integer>( "A -> B", 3 ) ).from( a ).to( b );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "A -> D", 3 ) ).from( a ).to( d );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "B -> C", 4 ) ).from( b ).to( c );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "C -> A", 3 ) ).from( c ).to( a );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "C -> D", 1 ) ).from( c ).to( d );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "C -> E", 2 ) ).from( c ).to( e );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "D -> E", 2 ) ).from( d ).to( e );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "D -> F", 6 ) ).from( d ).to( f );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "E -> B", 1 ) ).from( e ).to( b );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "E -> G", 1 ) ).from( e ).to( g );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "F -> G", 9 ) ).from( f ).to( g );
                }

            } );

        // expected max flow
        final Integer expected = 5;

        // actual max flow
        Integer actual = findMaxFlow( graph ).from( a ).to( g ).applyingEdmondsKarp( new IntegerWeightBaseOperations() );

        assertEquals( actual, expected );
    }

}
