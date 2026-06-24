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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.apache.commons.graph.CommonsGraph.minimumSpanningTree;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.BaseWeightedEdge;
import org.apache.commons.graph.model.MutableSpanningTree;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.apache.commons.graph.weight.primitive.DoubleWeightBaseOperations;
import org.junit.jupiter.api.Test;

public final class BoruvkaTestCase
{

    @Test
    public void testEmptyGraph()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        SpanningTreeSourceSelector<BaseLabeledVertex, Double, BaseLabeledWeightedEdge<Double>> selector = minimumSpanningTree(input)
                .whereEdgesHaveWeights(new BaseWeightedEdge<Double>());

        assertThrows(IllegalStateException.class, selector::fromArbitrarySource);
    }

    @Test
    public void testNotExistVertex()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        SpanningTreeSourceSelector<BaseLabeledVertex, Double, BaseLabeledWeightedEdge<Double>> selector = minimumSpanningTree(input)
                .whereEdgesHaveWeights(new BaseWeightedEdge<Double>());
        BaseLabeledVertex notExist = new BaseLabeledVertex("NOT EXIST");

        assertThrows(IllegalStateException.class, () -> selector.fromSource(notExist));
    }

    @Test
    public void testNullGraph()
    {
        assertThrows(NullPointerException.class, () -> minimumSpanningTree( (Graph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>) null ));
    }

    @Test
    public void testNullMonoid()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();
        BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        input.addVertex( a );

        SpanningTreeAlgorithmSelector<BaseLabeledVertex, Double, BaseLabeledWeightedEdge<Double>> selector = minimumSpanningTree(input)
                .whereEdgesHaveWeights(new BaseWeightedEdge<Double>())
                .fromSource(a);

        assertThrows(NullPointerException.class, () -> selector.applyingBoruvkaAlgorithm( null ));
    }

    @Test
    public void testNullVertex()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();
        SpanningTreeSourceSelector<BaseLabeledVertex, Double, BaseLabeledWeightedEdge<Double>> selector = minimumSpanningTree(input)
                .whereEdgesHaveWeights(new BaseWeightedEdge<Double>());

        assertThrows(NullPointerException.class, () -> selector.fromSource(null));
    }

    /**
     * Test Graph and boruvka's solution can be seen on
     */
    @Test
    public void testVerifyWikipediaMinimumSpanningTree()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        BaseLabeledVertex b = new BaseLabeledVertex( "B" );
        BaseLabeledVertex c = new BaseLabeledVertex( "C" );
        BaseLabeledVertex d = new BaseLabeledVertex( "D" );
        BaseLabeledVertex e = new BaseLabeledVertex( "E" );
        BaseLabeledVertex f = new BaseLabeledVertex( "F" );
        BaseLabeledVertex g = new BaseLabeledVertex( "G" );

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

        MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> c", 14D ), c );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> d", 10D ), d );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> e", 1D ), e );
        expected.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> f", 6D ), f );
        expected.addEdge( f, new BaseLabeledWeightedEdge<Double>( "e <-> g", 9D ), g );

        // Actual

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
            minimumSpanningTree( input )
                .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                .fromArbitrarySource()
                .applyingBoruvkaAlgorithm( new DoubleWeightBaseOperations() );

        // assert!

        assertEquals( expected, actual );
    }

    /**
     * Test Boruvka's solution on a not-connected graph.
     */
    @Test
    public void verifySparseGraphMinimumSpanningTree()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> input =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        input.addVertex( new BaseLabeledVertex( "A" ) );
        input.addVertex( new BaseLabeledVertex( "B" ) );
        input.addVertex( new BaseLabeledVertex( "C" ) );
        input.addVertex( new BaseLabeledVertex( "D" ) );
        input.addVertex( new BaseLabeledVertex( "E" ) );
        input.addVertex( new BaseLabeledVertex( "F" ) );
        input.addVertex( new BaseLabeledVertex( "G" ) );

        SpanningTreeAlgorithmSelector<BaseLabeledVertex, Double, BaseLabeledWeightedEdge<Double>> selector = minimumSpanningTree(input)
                .whereEdgesHaveWeights(new BaseWeightedEdge<Double>())
                .fromArbitrarySource();
        DoubleWeightBaseOperations operations = new DoubleWeightBaseOperations();

        assertThrows(IllegalStateException.class, () -> selector.applyingBoruvkaAlgorithm(operations));
    }

}
