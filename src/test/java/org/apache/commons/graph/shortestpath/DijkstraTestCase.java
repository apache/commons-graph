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
import static org.apache.commons.graph.shortestpath.Dijkstra.findShortestPath;

import org.apache.commons.graph.Path;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.DirectedMutableWeightedGraph;
import org.apache.commons.graph.model.InMemoryWeightedPath;
import org.apache.commons.graph.weight.primitive.DoubleWeight;
import org.junit.Test;

public final class DijkstraTestCase
{

    /**
     * Test Graph and Dijkstra's solution can be seen on
     * <a href="http://en.wikipedia.org/wiki/Dijkstra's_algorithm>Wikipedia</a>
     */
    @Test
    public void findShortestPathAndVerify()
    {
        DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge> graph =
            new DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge>();

        // building Graph

        BaseLabeledVertex one = new BaseLabeledVertex( "1" );
        BaseLabeledVertex two = new BaseLabeledVertex( "2" );
        BaseLabeledVertex three = new BaseLabeledVertex( "3" );
        BaseLabeledVertex four = new BaseLabeledVertex( "4" );
        BaseLabeledVertex five = new BaseLabeledVertex( "5" );
        BaseLabeledVertex six = new BaseLabeledVertex( "6" );

        graph.addVertex( one );
        graph.addVertex( two );
        graph.addVertex( three );
        graph.addVertex( four );
        graph.addVertex( five );
        graph.addVertex( six );

        graph.addEdge( one, new BaseLabeledWeightedEdge( "1 -> 6", 14D ), six );
        graph.addEdge( one, new BaseLabeledWeightedEdge( "1 -> 3", 9D ), three );
        graph.addEdge( one, new BaseLabeledWeightedEdge( "1 -> 2", 7D ), two );

        graph.addEdge( two, new BaseLabeledWeightedEdge( "2 -> 3", 10D ), three );
        graph.addEdge( two, new BaseLabeledWeightedEdge( "2 -> 4", 15D ), four );

        graph.addEdge( three, new BaseLabeledWeightedEdge( "3 -> 6", 2D ), six );
        graph.addEdge( three, new BaseLabeledWeightedEdge( "3 -> 4", 11D ), four );

        graph.addEdge( four, new BaseLabeledWeightedEdge( "4 -> 5", 6D ), five );
        graph.addEdge( six, new BaseLabeledWeightedEdge( "6 -> 5", 9D ), five );

        // expected path

        InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge, Double> expected =
            new InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge, Double>( one, five, new DoubleWeight() );

        expected.addConnectionInTail( one, new BaseLabeledWeightedEdge( "1 -> 3", 9D ), three );
        expected.addConnectionInTail( three, new BaseLabeledWeightedEdge( "3 -> 6", 2D ), six );
        expected.addConnectionInTail( six, new BaseLabeledWeightedEdge( "6 -> 5", 9D ), five );

        // actual path

        Path<BaseLabeledVertex, BaseLabeledWeightedEdge> actual = findShortestPath( graph, one, five );

        // assert!

        assertEquals( expected, actual );
    }

}
