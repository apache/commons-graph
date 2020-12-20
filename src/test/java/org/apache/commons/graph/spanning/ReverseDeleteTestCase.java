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

import static org.junit.Assert.assertEquals;
import static org.apache.commons.graph.CommonsGraph.minimumSpanningTree;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.BaseWeightedEdge;
import org.apache.commons.graph.model.MutableSpanningTree;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.apache.commons.graph.weight.primitive.DoubleWeightBaseOperations;
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
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input = null;

        minimumSpanningTree( input )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
            .applyingReverseDeleteAlgorithm( null );
    }

    @Test
    public void testEmptyGraph()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        final SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> tree =
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
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        final BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        final BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        final BaseLabeledVertex c = new BaseLabeledVertex( "c" );

        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );

        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 21D ), c );
        input.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        // expected

        final MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( final BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        final SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
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
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        final BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        final BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        final BaseLabeledVertex c = new BaseLabeledVertex( "c" );

        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );

        // expected

        final MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( final BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        final SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
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
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        final BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        final BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        final BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        final BaseLabeledVertex d = new BaseLabeledVertex( "d" );
        final BaseLabeledVertex e = new BaseLabeledVertex( "e" );


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

        final MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( final BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        expected.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 4D ), e );

        final SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
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
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        final BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        final BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        final BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        final BaseLabeledVertex d = new BaseLabeledVertex( "d" );
        final BaseLabeledVertex e = new BaseLabeledVertex( "e" );
        final BaseLabeledVertex f = new BaseLabeledVertex( "f" );


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

        final MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( final BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        expected.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 4D ), e );
        expected.addEdge( f, new BaseLabeledWeightedEdge<Double>( "f <-> d", 4D ), d );

        final SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
                        minimumSpanningTree( input )
                            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                            .applyingReverseDeleteAlgorithm( new DoubleWeightBaseOperations() );
        assertEquals( expected, actual );
    }

}
