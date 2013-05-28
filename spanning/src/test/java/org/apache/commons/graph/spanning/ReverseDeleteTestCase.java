package org.apache.commons.graph.spanning;

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

import static org.apache.commons.graph.spanning.SpanningTreeSolver.minimumSpanningTree;
import static org.junit.Assert.assertEquals;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.math.monoid.primitive.DoubleWeightBaseOperations;
import org.apache.commons.graph.model.MutableSpanningTree;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.apache.commons.graph.model.labeled.BaseLabeledVertex;
import org.apache.commons.graph.model.labeled.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.labeled.BaseWeightedEdge;
import org.junit.Test;

/**
 *
 */
public class ReverseDeleteTestCase
{

    @Test( expected = NullPointerException.class )
    public void testNullGraph()
    {
        minimumSpanningTree( (Graph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>) null )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
            .applyingReverseDeleteAlgorithm( new DoubleWeightBaseOperations() );
    }

    @Test( expected = NullPointerException.class )
    public void testNullMonoid()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input = null;

        minimumSpanningTree( input )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
            .applyingReverseDeleteAlgorithm( null );
    }

    @Test
    public void testEmptyGraph()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> tree =
            minimumSpanningTree( input )
                .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                .applyingReverseDeleteAlgorithm( new DoubleWeightBaseOperations() );

        assertEquals( 0, tree.getOrder() );
        assertEquals( 0, tree.getSize() );

    }

    /**
     * Test Graph and Reverse-Delete Algorithm
     */
    @Test
    public void verifyMinimumSpanningTree()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );

        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );

        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 21D ), c );
        input.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        // expected

        MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
                        minimumSpanningTree( input )
                            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                            .applyingReverseDeleteAlgorithm( new DoubleWeightBaseOperations() );
        assertEquals( expected, actual );
    }

    /**
     * Test Graph and Reverse-Delete Algorithm
     */
    @Test
    public void verifyNotConnectGraph()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );

        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );

        // expected

        MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
                        minimumSpanningTree( input )
                            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                            .applyingReverseDeleteAlgorithm( new DoubleWeightBaseOperations() );
        assertEquals( expected, actual );
    }

    /**
     * Test Graph and Reverse-Delete Algorithm
     */
    @Test
    public void verifyNotConnectGraph2()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        BaseLabeledVertex d = new BaseLabeledVertex( "d" );
        BaseLabeledVertex e = new BaseLabeledVertex( "e" );


        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );
        input.addVertex( d );
        input.addVertex( e );

        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 21D ), c );
        input.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        input.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 4D ), e );

        // expected

        MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        expected.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 4D ), e );

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
                        minimumSpanningTree( input )
                            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                            .applyingReverseDeleteAlgorithm( new DoubleWeightBaseOperations() );
        assertEquals( expected, actual );
    }


    /**
     * Test Graph and Reverse-Delete Algorithm
     */
    @Test
    public void verifyNotConnectGraph3()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        BaseLabeledVertex d = new BaseLabeledVertex( "d" );
        BaseLabeledVertex e = new BaseLabeledVertex( "e" );
        BaseLabeledVertex f = new BaseLabeledVertex( "f" );


        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );
        input.addVertex( d );
        input.addVertex( e );
        input.addVertex( f );

        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 21D ), c );
        input.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        input.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 7D ), e );
        input.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> f", 21D ), f );
        input.addEdge( f, new BaseLabeledWeightedEdge<Double>( "f <-> d", 4D ), d );

        // expected

        MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        expected.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 4D ), e );
        expected.addEdge( f, new BaseLabeledWeightedEdge<Double>( "f <-> d", 4D ), d );

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
                        minimumSpanningTree( input )
                            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                            .applyingReverseDeleteAlgorithm( new DoubleWeightBaseOperations() );
        assertEquals( expected, actual );
    }

}
