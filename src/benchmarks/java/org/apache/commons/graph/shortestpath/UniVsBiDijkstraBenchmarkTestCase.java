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

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static org.apache.commons.graph.CommonsGraph.findShortestPath;
import static org.apache.commons.graph.CommonsGraph.newDirectedMutableGraph;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.Mapper;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.BaseWeightedEdge;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.weight.OrderedMonoid;
import org.apache.commons.graph.weight.primitive.DoubleWeightBaseOperations;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;

@AxisRange( min = 0, max = 2 )
@BenchmarkMethodChart( filePrefix = "dijkstras" )
@BenchmarkOptions( benchmarkRounds = 10, warmupRounds = 5 )
public final class UniVsBiDijkstraBenchmarkTestCase
{
    private static final int NODES = 5000;
    private static final int EDGES = 100000;

    @Rule
    public BenchmarkRule benchmarkRun = new BenchmarkRule();

    private static DirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> graph;

    private static Mapper<BaseLabeledWeightedEdge<Double>, Double> weightedEdges;

    private static LinkedList<BaseLabeledVertex> sourceListUni;

    private static LinkedList<BaseLabeledVertex> sourceListBi;

    private static LinkedList<BaseLabeledVertex> targetListUni;

    private static LinkedList<BaseLabeledVertex> targetListBi;

    private static List<BaseLabeledVertex> vertices;

    private static OrderedMonoid<Double> weightOperations;

    @BeforeClass
    public static void setUp()
    {
        weightOperations = new DoubleWeightBaseOperations();

        weightedEdges = new Mapper<BaseLabeledWeightedEdge<Double>, Double>()
        {

            public Double map(final BaseLabeledWeightedEdge<Double> input )
            {
                return input.getWeight();
            }

        };

        graph = newDirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>()
        {
            final Random r = new Random();

            public void connect()
            {
                vertices = new ArrayList<BaseLabeledVertex>();
                for ( int i = 0; i < NODES; i++ )
                {
                    final BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
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
                final int maxEdges = Math.max(0, EDGES - NODES);
                for ( int i = 0; i < maxEdges; i++)
                {
                    while ( ! addEdge( vertices.get( r.nextInt(NODES) ), vertices.get( r.nextInt(NODES) ) ) ) {
                        // do nothing
                    }
                }
            }

            private boolean addEdge(final BaseLabeledVertex src, final BaseLabeledVertex dst )
            {
                try {
                  addEdge( new BaseLabeledWeightedEdge<Double>( format( "%s -> %s", src, dst ),
                                                                10.0 * r.nextDouble() + 1.0 ) ).from( src ).to( dst );
                  return true;
              } catch (final GraphException e) {
                  // ignore duplicate edge exceptions
                  return false;
              }
            }
        } );

        sourceListUni = new LinkedList<BaseLabeledVertex>();
        targetListUni = new LinkedList<BaseLabeledVertex>();

        sourceListBi = new LinkedList<BaseLabeledVertex>();
        targetListBi = new LinkedList<BaseLabeledVertex>();

        final Random r = new Random();

        for ( int i = 0; i < 15; i++ )
        {
            final BaseLabeledVertex s = vertices.get( r.nextInt( vertices.size() ) );
            sourceListUni.add( s );
            sourceListBi.add( s );

            final BaseLabeledVertex t = vertices.get( r.nextInt( vertices.size() ) );
            targetListUni.add( t );
            targetListBi.add( t );
        }
    }

    @Test
    public void performUnidirectionalDijkstra() {
        final BaseLabeledVertex source = sourceListUni.removeFirst();
        final BaseLabeledVertex target = targetListUni.removeFirst();

        try {
            final WeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> path =
                    findShortestPath( graph )
                                .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                                .from( source )
                                .to( target )
                                .applyingDijkstra( weightOperations );

            assertTrue( path.getSize() > 0 );
            assertTrue( path.getWeight() > 0D );
        }
        catch ( final Exception e )
        {
            e.printStackTrace();
        }
    }

    @Test
    public void performBidirectionalDijkstra() {
        final BaseLabeledVertex source = sourceListBi.removeFirst();
        final BaseLabeledVertex target = targetListBi.removeFirst();

        try {
            final WeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> path =
                    findShortestPath( graph )
                                .whereEdgesHaveWeights( new BaseWeightedEdge<Double>() )
                                .from( source )
                                .to( target )
                                .applyingBidirectionalDijkstra( weightOperations );

            assertTrue( path.getSize() > 0 );
            assertTrue( path.getWeight() > 0D );
        }
        catch ( final Exception e )
        {
            e.printStackTrace();
        }
    }

}
