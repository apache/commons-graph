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
import static org.apache.commons.graph.CommonsGraph.findShortestPath;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.graph.Path;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.InMemoryWeightedPath;
import org.apache.commons.graph.model.UndirectedMutableWeightedGraph;
import org.apache.commons.graph.weight.primitive.DoubleWeight;
import org.junit.Test;

public final class AStarTestCase
{

    /**
     * Test Graph and Dijkstra's solution can be seen on
     * <a href="http://en.wikipedia.org/wiki/A*_search_algorithm">Wikipedia</a>
     */
    @Test
    public void findShortestPathAndVerify()
    {
        UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> graph =
            new UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>();

        // building Graph

        BaseLabeledVertex start = new BaseLabeledVertex( "start" );
        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        BaseLabeledVertex d = new BaseLabeledVertex( "d" );
        BaseLabeledVertex e = new BaseLabeledVertex( "e" );
        BaseLabeledVertex goal = new BaseLabeledVertex( "goal" );

        graph.addVertex( start );
        graph.addVertex( a );
        graph.addVertex( b );
        graph.addVertex( c );
        graph.addVertex( d );
        graph.addVertex( e );
        graph.addVertex( goal );

        graph.addEdge( start, new BaseLabeledWeightedEdge<Double>( "start <-> a", 1.5D ), a );
        graph.addEdge( start, new BaseLabeledWeightedEdge<Double>( "start <-> d", 2D ), d );

        graph.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 2D ), b );
        graph.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 3D ), c );
        graph.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> goal", 3D ), goal );

        graph.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 3D ), e );
        graph.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> goal", 2D ), goal );

        // euristics

        final Map<BaseLabeledVertex, Double> heuristics = new HashMap<BaseLabeledVertex, Double>();
        heuristics.put( a, 4D );
        heuristics.put( b, 2D );
        heuristics.put( c, 4D );
        heuristics.put( d, 4.5D );
        heuristics.put( e, 2D );
        heuristics.put( goal, 6D );

        Heuristic<BaseLabeledVertex, Double> heuristic = new Heuristic<BaseLabeledVertex, Double>()
        {

            public Double applyHeuristic( BaseLabeledVertex current, BaseLabeledVertex goal )
            {
                return heuristics.get( current );
            }

        };

        // expected path

        InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( start, goal, new DoubleWeight() );

        expected.addConnectionInTail( start, new BaseLabeledWeightedEdge<Double>( "start <-> a", 1.5D ), a );
        expected.addConnectionInTail( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 2D ), b );
        expected.addConnectionInTail( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 3D ), c );
        expected.addConnectionInTail( c, new BaseLabeledWeightedEdge<Double>( "c <-> goal", 3D ), goal );

        // actual path

        Path<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> actual =
            findShortestPath( graph ).from( start ).to( goal ).applyingAStar( new DoubleWeight() ).withHeuristic( heuristic );

        // assert!

        assertEquals( expected, actual );
    }
}
