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

import static org.apache.commons.graph.shortestpath.FloydWarshall.findAllVertexPairsShortestPath;
import static junit.framework.Assert.assertEquals;

import org.apache.commons.graph.MutableGraph;
import org.apache.commons.graph.UndirectedGraph;
import org.apache.commons.graph.WeightedGraph;
import org.apache.commons.graph.WeightedPath;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.DirectedMutableWeightedGraph;
import org.apache.commons.graph.model.InMemoryWeightedPath;
import org.apache.commons.graph.model.UndirectedMutableWeightedGraph;
import org.junit.Test;

/**
 */
public class FloydWarshallTestCase
{

    @Test
    public void undirectedShortestPath()
    {
        findShortestPathAndVerify( new UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge>() );
    }

    @Test
    public void directedShortestPath()
    {
        findShortestPathAndVerify( new DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge>() );
    }

    @SuppressWarnings( "unchecked" )
    private void findShortestPathAndVerify( WeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge> weighted )
    {
        // mutable by definition, generic types driven by input graph
        MutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge> mutable =
            (MutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge>) weighted;

        // building Graph

        BaseLabeledVertex one = new BaseLabeledVertex( "1" );
        BaseLabeledVertex two = new BaseLabeledVertex( "2" );
        BaseLabeledVertex three = new BaseLabeledVertex( "3" );
        BaseLabeledVertex four = new BaseLabeledVertex( "4" );
        BaseLabeledVertex five = new BaseLabeledVertex( "5" );
        BaseLabeledVertex six = new BaseLabeledVertex( "6" );

        mutable.addVertex( one );
        mutable.addVertex( two );
        mutable.addVertex( three );
        mutable.addVertex( four );
        mutable.addVertex( five );
        mutable.addVertex( six );

        mutable.addEdge( new BaseLabeledWeightedEdge( "", one, six, 14D ) );
        mutable.addEdge( new BaseLabeledWeightedEdge( "", one, three, 9D ) );
        mutable.addEdge( new BaseLabeledWeightedEdge( "", one, two, 7D ) );

        mutable.addEdge( new BaseLabeledWeightedEdge( "", two, three, 10D ) );
        mutable.addEdge( new BaseLabeledWeightedEdge( "", two, four, 15D ) );

        mutable.addEdge( new BaseLabeledWeightedEdge( "", three, six, 2D ) );
        mutable.addEdge( new BaseLabeledWeightedEdge( "", three, four, 11D ) );

        mutable.addEdge( new BaseLabeledWeightedEdge( "", four, five, 6D ) );
        mutable.addEdge( new BaseLabeledWeightedEdge( "", six, five, 9D ) );

        // reverse all edges
        if ( weighted instanceof UndirectedGraph )
        {
            mutable.addEdge( new BaseLabeledWeightedEdge( "", six, one, 14D ) );
            mutable.addEdge( new BaseLabeledWeightedEdge( "", three, one, 9D ) );
            mutable.addEdge( new BaseLabeledWeightedEdge( "", two, one, 7D ) );

            mutable.addEdge( new BaseLabeledWeightedEdge( "", three, two, 10D ) );
            mutable.addEdge( new BaseLabeledWeightedEdge( "", four, two, 15D ) );

            mutable.addEdge( new BaseLabeledWeightedEdge( "", six, three, 2D ) );
            mutable.addEdge( new BaseLabeledWeightedEdge( "", four, three, 11D ) );

            mutable.addEdge( new BaseLabeledWeightedEdge( "", five, four, 6D ) );
            mutable.addEdge( new BaseLabeledWeightedEdge( "", five, six, 9D ) );
        }

        AllVertexPairsShortestPath<BaseLabeledVertex, BaseLabeledWeightedEdge> p =
            findAllVertexPairsShortestPath( weighted );

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

            WeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge> wp = p.findShortestPath( one, six );

            // Expected
            InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge> expected =
                new InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge>( one, six );
            expected.addVertexInTail( one );
            expected.addVertexInTail( three );
            expected.addVertexInTail( six );

            expected.addEdgeInTail( new BaseLabeledWeightedEdge( "", one, three, 9D ) );
            expected.addEdgeInTail( new BaseLabeledWeightedEdge( "", three, six, 2D ) );

            // Actual
            assertEquals( expected, wp );
        }
        else
        {
            assertEquals( 11D, p.getShortestDistance( one, six ) );

            assertEquals( 6D, p.getShortestDistance( four, five ) );

            assertEquals( 20D, p.getShortestDistance( one, five ) );
            assertEquals( Double.POSITIVE_INFINITY, p.getShortestDistance( five, one ) );

            // Verify shortest paths
            WeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge> wp = p.findShortestPath( five, one );
            assertEquals( null, wp );

            InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge> expected =
                new InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge>( one, six );
            expected.addVertexInTail( one );
            expected.addVertexInTail( three );
            expected.addVertexInTail( six );

            expected.addEdgeInTail( new BaseLabeledWeightedEdge( "", one, three, 9D ) );
            expected.addEdgeInTail( new BaseLabeledWeightedEdge( "", three, six, 2D ) );

            // Actual
            wp = p.findShortestPath( one, six );
            assertEquals( expected, wp );
        }
    }

}
