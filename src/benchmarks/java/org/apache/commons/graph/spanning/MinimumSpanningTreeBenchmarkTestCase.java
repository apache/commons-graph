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
import static org.junit.Assert.assertTrue;

import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.UndirectedMutableWeightedGraph;
import org.apache.commons.graph.weight.primitive.DoubleWeight;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.carrotsearch.junitbenchmarks.annotation.AxisRange;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;

@AxisRange( min = 0, max = 2 )
@BenchmarkMethodChart( filePrefix = "minimum-spanning-tree" )
public final class MinimumSpanningTreeBenchmarkTestCase
{

    @Rule
    public BenchmarkRule benchmarkRun = new BenchmarkRule();

    private static final UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> G
        = new UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>();

    @BeforeClass
    public static void setUp()
    {
        for ( int i = 0; i < 1000; i++ )
        {
            BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
            G.addVertex( v );
        }

        for ( BaseLabeledVertex v1 : G.getVertices() )
        {
            int cnt = 0;
            for ( BaseLabeledVertex v2 : G.getVertices() )
            {
                if (cnt++ > 20)
                {
                    break;
                }
                if ( !v1.equals( v2 ) )
                {
                    try
                    {
                        G.addEdge( v1, new BaseLabeledWeightedEdge<Double>( format( "%s -> %s", v1, v2 ), 7D ), v2 );
                    }
                    catch ( GraphException e )
                    {
                        // ignore
                    }
                }
            }
        }
    }

    @Test
    public void performBoruvka()
    {
        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
            minimumSpanningTree( G ).fromArbitrarySource().applyingBoruvkaAlgorithm( new DoubleWeight() );

        assertTrue( actual.getSize() > 0 );
    }

    @Test
    public void performKruskal()
    {
        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
            minimumSpanningTree( G ).fromArbitrarySource().applyingKruskalAlgorithm( new DoubleWeight() );

        assertTrue( actual.getSize() > 0 );
    }

    @Test
    public void performPrim()
    {
        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
            minimumSpanningTree( G ).fromArbitrarySource().applyingPrimAlgorithm( new DoubleWeight() );

        assertTrue( actual.getSize() > 0 );
    }

}
