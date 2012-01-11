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

import static junit.framework.Assert.assertEquals;
import static org.apache.commons.graph.spanning.Kruskal.minimumSpanningTree;

import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.MutableSpanningTree;
import org.apache.commons.graph.model.UndirectedMutableWeightedGraph;
import org.apache.commons.graph.weight.primitive.DoubleWeight;
import org.junit.Test;

public final class KruskalTestCase
{

    /**
     * Test Graph and Prim's solution can be seen on
     * <a href="http://en.wikipedia.org/wiki/Prim%27s_algorithm">Wikipedia</a>
     */
    @Test
    public void verifyWikipediaMinimumSpanningTree()
    {
        UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge> input
            = new UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge>();

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

        input.addEdge( a, new BaseLabeledWeightedEdge( "a <-> b", 7D ), b );
        input.addEdge( a, new BaseLabeledWeightedEdge( "a <-> d", 5D ), d );

        input.addEdge( b, new BaseLabeledWeightedEdge( "b <-> c", 8D ), c );
        input.addEdge( b, new BaseLabeledWeightedEdge( "b <-> d", 9D ), d );
        input.addEdge( b, new BaseLabeledWeightedEdge( "b <-> e", 7D ), e );

        input.addEdge( c, new BaseLabeledWeightedEdge( "c <-> e", 5D ), e );

        input.addEdge( d, new BaseLabeledWeightedEdge( "d <-> e", 15D ), e );
        input.addEdge( d, new BaseLabeledWeightedEdge( "d <-> f", 6D ), f );

        input.addEdge( e, new BaseLabeledWeightedEdge( "e <-> f", 8D ), f );
        input.addEdge( e, new BaseLabeledWeightedEdge( "e <-> g", 9D ), g );

        input.addEdge( f, new BaseLabeledWeightedEdge( "f <-> g", 11D ), g );

        // expected

        MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge, Double>( new DoubleWeight() );

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge( "a <-> b", 7D ), b );
        expected.addEdge( a, new BaseLabeledWeightedEdge( "a <-> d", 5D ), d );
        expected.addEdge( b, new BaseLabeledWeightedEdge( "b <-> e", 9D ), e );
        expected.addEdge( c, new BaseLabeledWeightedEdge( "c <-> e", 5D ), e );
        expected.addEdge( d, new BaseLabeledWeightedEdge( "d <-> f", 6D ), f );
        expected.addEdge( e, new BaseLabeledWeightedEdge( "e <-> g", 9D ), g );

        // Actual

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge, Double> actual = minimumSpanningTree( input );
        
        // assert!

        assertEquals( expected, actual );
    }

}
