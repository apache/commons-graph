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

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.apache.commons.graph.CommonsGraph.minimumSpanningTree;
import static org.apache.commons.graph.CommonsGraph.newUndirectedMutableGraph;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.Mapper;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.apache.commons.graph.weight.primitive.DoubleWeightBaseOperations;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Rule;
import org.junit.jupiter.api.Test;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;

@AxisRange( min = 0, max = 2 )
@BenchmarkMethodChart( filePrefix = "minimum-spanning-tree" )
public final class MinimumSpanningTreeBenchmarkTestCase
{
    private static final int NODES = 1000;
    private static final int EDGES = 6000;

    private static UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> graph;

    private static Mapper<BaseLabeledWeightedEdge<Double>, Double> weightedEdges;

    @BeforeAll
    public static void setUp()
    {
        weightedEdges = new Mapper<BaseLabeledWeightedEdge<Double>, Double>()
        {

            public Double map( BaseLabeledWeightedEdge<Double> input )
            {
                return input.getWeight();
            }

        };

        graph = newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>()
        {
            Random r = new Random();

            private boolean addEdge( BaseLabeledVertex src, BaseLabeledVertex dst )
            {
                try {
                  addEdge( new BaseLabeledWeightedEdge<Double>( format( "%s -> %s", src, dst ),
                                                                (double) r.nextInt(10) ) ).from( src ).to( dst );
                  return true;
              } catch (GraphException e) {
                  // ignore duplicate edge exceptions
                  return false;
              }
            }

            public void connect()
            {
                List<BaseLabeledVertex> vertices = new ArrayList<BaseLabeledVertex>();
                for ( int i = 0; i < NODES; i++ )
                {
                    BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
                    addVertex( v );
                    vertices.add( v );
                }

                // form a connected graph
                for ( int i = 0; i < NODES - 1; i++ )
                {
                    addEdge( vertices.get( i ), vertices.get( i + 1 ) );
                }

                addEdge( vertices.get( NODES - 1 ) , vertices.get( 0 ) );
                // we have already created #NODES edges
                int maxEdges = Math.max(0, EDGES - NODES);
                for ( int i = 0; i < maxEdges; i++)
                {
                    while ( ! addEdge( vertices.get( r.nextInt(NODES) ), vertices.get( r.nextInt(NODES) ) ) ) {
                        // do nothing
                    }
                }
            }
        } );
    }

    @Rule
    public BenchmarkRule benchmarkRun = new BenchmarkRule();

    @Test
    public void testPerformBoruvka()
    {
        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
            minimumSpanningTree( graph )
                .whereEdgesHaveWeights( weightedEdges )
                .fromArbitrarySource()
                .applyingBoruvkaAlgorithm( new DoubleWeightBaseOperations() );

        assertTrue( actual.getSize() > 0 );
    }

    @Test
    public void testPerformKruskal()
    {
        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
            minimumSpanningTree( graph )
                .whereEdgesHaveWeights( weightedEdges )
                .fromArbitrarySource()
                .applyingKruskalAlgorithm( new DoubleWeightBaseOperations() );

        assertTrue( actual.getSize() > 0 );
    }

    @Test
    public void testPerformPrim()
    {
        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
            minimumSpanningTree( graph )
                .whereEdgesHaveWeights( weightedEdges )
                .fromArbitrarySource()
                .applyingPrimAlgorithm( new DoubleWeightBaseOperations() );

        assertTrue( actual.getSize() > 0 );
    }

}
