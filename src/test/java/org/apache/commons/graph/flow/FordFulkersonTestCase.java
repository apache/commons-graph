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
import static org.apache.commons.graph.CommonsGraph.newDirectedMutableGraph;
import static org.junit.Assert.assertEquals;

import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.BaseWeightedEdge;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.apache.commons.graph.weight.primitive.IntegerWeightBaseOperations;
import org.junit.Test;

/**
 * Test for Ford-Fulkerson algorithm implementation.
 * The test graph is available on
 * <a href="http://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm#Integral_example">Wikipedia</a>.
 */
public final class FordFulkersonTestCase
{

    @Test(expected=NullPointerException.class)
    public void testNullGraph()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        final BaseLabeledVertex d = new BaseLabeledVertex( "D" );

        findMaxFlow( (DirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>>) null )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Integer>() )
            .from( a )
            .to( d )
            .applyingFordFulkerson( new IntegerWeightBaseOperations() );
    }

    @Test(expected=NullPointerException.class)
    public void testNullGraphAndVertices()
    {
        findMaxFlow( (DirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>>) null )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Integer>() )
            .from( null )
            .to( null )
            .applyingFordFulkerson( new IntegerWeightBaseOperations() );
    }

    @Test
    public void testNotConnected()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        final BaseLabeledVertex d = new BaseLabeledVertex( "D" );

        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>> graph =
        newDirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>>()
        {

            @Override
            public void connect()
            {
                addVertex( a );
                addVertex( new BaseLabeledVertex( "B" ) );
                addVertex( new BaseLabeledVertex( "C" ) );
                addVertex( d );

            }

        } );

        // expected max flow
        final Integer expected = 0;

        // actual max flow
        Integer actual = findMaxFlow( graph )
                            .whereEdgesHaveWeights( new BaseWeightedEdge<Integer>() )
                            .from( a )
                            .to( d )
                            .applyingFordFulkerson( new IntegerWeightBaseOperations() );

        assertEquals( actual, expected );
    }

    @Test
    public void testNotConnected_2()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        final BaseLabeledVertex d = new BaseLabeledVertex( "D" );

        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>> graph =
        newDirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>>()
        {

            @Override
            public void connect()
            {
                addVertex( a );
                BaseLabeledVertex b = addVertex( new BaseLabeledVertex( "B" ) );
                addVertex( new BaseLabeledVertex( "C" ) );
                addVertex( d );
                addEdge( new BaseLabeledWeightedEdge<Integer>( "A -> B", 1000 ) ).from( a ).to( b );

            }

        } );

        // expected max flow
        final Integer expected = 0;

        // actual max flow
        Integer actual = findMaxFlow( graph )
                            .whereEdgesHaveWeights( new BaseWeightedEdge<Integer>() )
                            .from( a )
                            .to( d )
                            .applyingFordFulkerson( new IntegerWeightBaseOperations() );

        assertEquals( actual, expected );
    }

    @Test(expected=NullPointerException.class)
    public void testNullVertices()
    {
        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>> graph =
            new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>>();

        // actual max flow
        findMaxFlow( graph )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Integer>() )
            .from( null )
            .to( null )
            .applyingFordFulkerson( new IntegerWeightBaseOperations() );
    }

    @Test
    public void findMaxFlowAndVerify()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        final BaseLabeledVertex d = new BaseLabeledVertex( "D" );

        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>> graph =
            newDirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>>()
            {

                @Override
                public void connect()
                {
                    addVertex( a );
                    BaseLabeledVertex b = addVertex( new BaseLabeledVertex( "B" ) );
                    BaseLabeledVertex c = addVertex( new BaseLabeledVertex( "C" ) );
                    addVertex( d );

                    addEdge( new BaseLabeledWeightedEdge<Integer>( "A -> B", 1000 ) ).from( a ).to( b );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "A -> C", 1000 ) ).from( a ).to( c );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "B -> C", 1 ) ).from( b ).to( c );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "B -> D", 1000 ) ).from( b ).to( d );
                    addEdge( new BaseLabeledWeightedEdge<Integer>( "C -> D", 1000 ) ).from( c ).to( d );
                }

            } );

        // expected max flow
        final Integer expected = 2000;

        // actual max flow
        Integer actual = findMaxFlow( graph )
                            .whereEdgesHaveWeights( new BaseWeightedEdge<Integer>() )
                            .from( a )
                            .to( d )
                            .applyingFordFulkerson( new IntegerWeightBaseOperations() );

        assertEquals( expected, actual );
    }

}
