package org.apache.commons.graph.jmh;

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
import static org.apache.commons.graph.CommonsGraph.findStronglyConnectedComponent;
import static org.apache.commons.graph.CommonsGraph.newDirectedMutableGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.DirectedMutableGraph;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class SCCAlgorithmBenchmarkTestCase
{
    private static final int NODES = 5000;
    private static final int EDGES = 5000;

    private static DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph;

    @Setup(Level.Trial)
    public static void setUp()
    {
        graph = newDirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {
            public void connect()
            {
                List<BaseLabeledVertex> vertices = new ArrayList<BaseLabeledVertex>();
                for ( int i = 0; i < NODES; i++ )
                {
                    BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
                    addVertex( v );
                    vertices.add( v );
                }

                Random r = new Random();
                for ( int i = 0; i < EDGES; i++ ) {
                    int v1 = r.nextInt(NODES);
                    int v2 = r.nextInt(NODES);

                    try {
                        addEdge( new BaseLabeledEdge( format( "%s -> %s", v1, v2 ) ) ).from( vertices.get( v1 ) ).to( vertices.get( v2 ) );
                    } catch (GraphException e) {
                        // ignore duplicate edge exceptions
                    }
                }
            }
        } );
    }

    @Benchmark
    public void testPerformCheriyanMehlhornGabow(final Blackhole blackhole)
    {
        blackhole.consume(findStronglyConnectedComponent( graph ).applyingCheriyanMehlhornGabow());
    }

    @Benchmark
    public void testPerformKosaraju(final Blackhole blackhole)
    {
        blackhole.consume(findStronglyConnectedComponent( graph ).applyingKosarajuSharir());
    }

    @Benchmark
    public void testPerformTarjan(final Blackhole blackhole)
    {
        blackhole.consume(findStronglyConnectedComponent( graph ).applyingTarjan());
    }

}
