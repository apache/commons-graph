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
import static org.junit.Assert.fail;
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

public final class PrimTestCase
{
    @Test( expected = NullPointerException.class )
    public void testNullGraph()
    {
        minimumSpanningTree( (Graph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>) null )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
            .fromArbitrarySource()
            .applyingPrimAlgorithm( new DoubleWeightBaseOperations() );
    }

    @Test( expected = NullPointerException.class )
    public void testNullVertex()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        minimumSpanningTree( input )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
            .fromSource( null )
            .applyingPrimAlgorithm( new DoubleWeightBaseOperations() );
    }

    @Test( expected = NullPointerException.class )
    public void testNullMonoid()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input = null;
        BaseLabeledVertex a = null;
        try
        {
            input = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();
            a = new BaseLabeledVertex( "A" );
            input.addVertex( a );
        }
        catch ( final NullPointerException e )
        {
            //try..catch need to avoid a possible test success even if a NPE is thorw during graph population
            fail( e.getMessage() );
        }

        minimumSpanningTree( input )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
            .fromSource( a )
            .applyingBoruvkaAlgorithm( null );
    }

    @Test( expected = IllegalStateException.class )
    public void testNotExistVertex()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        minimumSpanningTree( input )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
            .fromSource( new BaseLabeledVertex( "NOT EXIST" ) );
    }

    @Test( expected = IllegalStateException.class )
    public void testEmptyGraph()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        minimumSpanningTree( input )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
            .fromArbitrarySource()
            .applyingPrimAlgorithm( new DoubleWeightBaseOperations() );
    }

    /**
     * Test Graph and Prim's solution can be seen on these
     * <a href="http://gauguin.info.uniroma2.it/~italiano/Teaching/Algoritmi/Lezioni/cap12.pdf">slides</a>
     */
    @Test
    public void verifyMinimumSpanningTree2()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input
            = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        final BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        final BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        final BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        final BaseLabeledVertex d = new BaseLabeledVertex( "d" );
        final BaseLabeledVertex e = new BaseLabeledVertex( "e" );
        final BaseLabeledVertex f = new BaseLabeledVertex( "f" );
        final BaseLabeledVertex g = new BaseLabeledVertex( "g" );

        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );
        input.addVertex( d );
        input.addVertex( e );
        input.addVertex( f );
        input.addVertex( g );

        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> c", 14D ), c );
        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> d", 30D ), d );

        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 21D ), c );

        input.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> d", 10D ), d );
        input.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> e", 1D ), e );

        input.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> f", 6D ), f );
        input.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> g", 9D ), g );

        input.addEdge( f, new BaseLabeledWeightedEdge<Double>( "f <-> g", 4D ), g );

        // expected

        final MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( final BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> c", 14D ), c );

        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> d", 10D ), d );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> e", 1D ), e );

        expected.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> f", 6D ), f );

        expected.addEdge( f, new BaseLabeledWeightedEdge<Double>( "f <-> g", 4D ), g );

        internalPrimAssertion( input, a, expected );
    }

    /**
     * Test Graph and Prim's solution can be seen on
     * <a href="http://en.wikipedia.org/wiki/Prim%27s_algorithm">Wikipedia</a>
     */
    @Test
    public void verifyWikipediaMinimumSpanningTree()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input
            = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        final BaseLabeledVertex b = new BaseLabeledVertex( "B" );
        final BaseLabeledVertex c = new BaseLabeledVertex( "C" );
        final BaseLabeledVertex d = new BaseLabeledVertex( "D" );
        final BaseLabeledVertex e = new BaseLabeledVertex( "E" );
        final BaseLabeledVertex f = new BaseLabeledVertex( "F" );
        final BaseLabeledVertex g = new BaseLabeledVertex( "G" );

        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );
        input.addVertex( d );
        input.addVertex( e );
        input.addVertex( f );
        input.addVertex( g );

        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> d", 5D ), d );

        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 8D ), c );
        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> d", 9D ), d );
        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> e", 7D ), e );

        input.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> e", 5D ), e );

        input.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 15D ), e );
        input.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> f", 6D ), f );

        input.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> f", 8D ), f );
        input.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> g", 9D ), g );

        input.addEdge( f, new BaseLabeledWeightedEdge<Double>( "f <-> g", 11D ), g );

        // expected

        final MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( final BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> d", 5D ), d );
        expected.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> e", 7D ), e );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> e", 5D ), e );
        expected.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> f", 6D ), f );
        expected.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> g", 9D ), g );

        internalPrimAssertion( input, d, expected );
    }

    private static void internalPrimAssertion(final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input,
                                              final BaseLabeledVertex source,
                                              final MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected )
    {
     // actual

        final SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
                        minimumSpanningTree( input )
                            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                            .fromSource( source )
                            .applyingPrimAlgorithm( new DoubleWeightBaseOperations() );

        // assert!

        assertEquals( expected, actual );
    }

}
