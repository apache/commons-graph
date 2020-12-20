package org.apache.commons.graph.shortestpath;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.apache.commons.graph.CommonsGraph.findShortestPath;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.MutableGraph;
import org.apache.commons.graph.UndirectedGraph;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.BaseWeightedEdge;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.apache.commons.graph.model.InMemoryWeightedPath;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.apache.commons.graph.weight.primitive.DoubleWeightBaseOperations;
import org.junit.Test;

/**
 */
public class FloydWarshallTestCase
{

    @Test( expected = NullPointerException.class )
    public void testNullGraph()
    {
        // the actual weighted path
        findShortestPath( (Graph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>) null )
            .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
            .from( null )
            .to( null )
            .applyingDijkstra( new DoubleWeightBaseOperations() );
    }

    @Test( expected = PathNotFoundException.class )
    public void testNotConnectGraph()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> graph =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        final BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        final BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        graph.addVertex( a );
        graph.addVertex( b );

        // the actual weighted path
        final AllVertexPairsShortestPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> p =
            findShortestPath( graph )
                .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                .applyingFloydWarshall( new DoubleWeightBaseOperations() );

        p.findShortestPath( a, b );
    }

    @Test
    public void undirectedShortestPath()
    {
        findShortestPathAndVerify( new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>() );
    }

    @Test
    public void directedShortestPath()
    {
        findShortestPathAndVerify( new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>() );
    }

    private void findShortestPathAndVerify(final Graph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> weighted )
    {
        final MutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> mutable =
            (MutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>) weighted;

        // building Graph

        final BaseLabeledVertex one = new BaseLabeledVertex( "1" );
        final BaseLabeledVertex two = new BaseLabeledVertex( "2" );
        final BaseLabeledVertex three = new BaseLabeledVertex( "3" );
        final BaseLabeledVertex four = new BaseLabeledVertex( "4" );
        final BaseLabeledVertex five = new BaseLabeledVertex( "5" );
        final BaseLabeledVertex six = new BaseLabeledVertex( "6" );

        mutable.addVertex( one );
        mutable.addVertex( two );
        mutable.addVertex( three );
        mutable.addVertex( four );
        mutable.addVertex( five );
        mutable.addVertex( six );

        mutable.addEdge( one, new BaseLabeledWeightedEdge<Double>( "1 -> 6", 14D ), six );
        mutable.addEdge( one, new BaseLabeledWeightedEdge<Double>( "1 -> 3", 9D ), three );
        mutable.addEdge( one, new BaseLabeledWeightedEdge<Double>( "1 -> 2", 7D ), two );

        mutable.addEdge( two, new BaseLabeledWeightedEdge<Double>( "2 -> 3", 10D ), three );
        mutable.addEdge( two, new BaseLabeledWeightedEdge<Double>( "2 -> 4", 15D ), four );

        mutable.addEdge( three, new BaseLabeledWeightedEdge<Double>( "3 -> 6", 2D ), six );
        mutable.addEdge( three, new BaseLabeledWeightedEdge<Double>( "3 -> 4", 11D ), four );

        mutable.addEdge( four, new BaseLabeledWeightedEdge<Double>( "4 -> 5", 6D ), five );
        mutable.addEdge( six, new BaseLabeledWeightedEdge<Double>( "6 -> 5", 9D ), five );

        final AllVertexPairsShortestPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> p =
            findShortestPath( weighted )
                .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                .applyingFloydWarshall( new DoubleWeightBaseOperations() );

        if ( weighted instanceof UndirectedGraph )
        {
            // Verify shortestDistance
            assertEquals( 11D, p.getShortestDistance( one, six ) );

            assertEquals( 11D, p.getShortestDistance( six, one ) );

            assertEquals( 6D, p.getShortestDistance( four, five ) );
            assertEquals( 6D, p.getShortestDistance( five, four ) );

            assertEquals( 20D, p.getShortestDistance( one, five ) );
            assertEquals( 20D, p.getShortestDistance( five, one ) );

            // Verify shortest paths

            final WeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> wp = p.findShortestPath( one, six );

            // Expected
            final InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
                new InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( one, six, new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );
            expected.addConnectionInTail( one, new BaseLabeledWeightedEdge<Double>( "1 -> 3", 9D ), three );
            expected.addConnectionInTail( three, new BaseLabeledWeightedEdge<Double>( "3 -> 6", 2D ), six );

            // Actual
            assertEquals( expected, wp );
        }
        else
        {
            assertEquals( 11D, p.getShortestDistance( one, six ) );

            assertEquals( 6D, p.getShortestDistance( four, five ) );

            assertEquals( 20D, p.getShortestDistance( one, five ) );
            assertFalse( p.hasShortestDistance( five, one ) );

            WeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> wp;
            // Verify shortest paths
            try
            {
                wp = p.findShortestPath( five, one );
                fail( "Path from {5} to {1} doesn't exist" );
            }
            catch (final PathNotFoundException e) {
                // wallow it
            }

            final InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
                new InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( one, six, new DoubleWeightBaseOperations(), new BaseWeightedEdge<Double>() );
            expected.addConnectionInTail( one, new BaseLabeledWeightedEdge<Double>( "1 -> 3", 9D ), three );
            expected.addConnectionInTail( three, new BaseLabeledWeightedEdge<Double>( "3 -> 6", 2D ), six );

            // Actual
            wp = p.findShortestPath( one, six );
            assertEquals( expected, wp );
        }
    }

}
