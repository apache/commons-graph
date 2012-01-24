package org.apache.commons.graph.flow;

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

import static org.junit.Assert.assertEquals;

import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.DirectedMutableWeightedGraph;
import org.junit.Test;

/**
 * Test for Ford-Fulkerson algorithm implementation.
 * The test graph is available on
 * <a href="http://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm#Integral_example">Wikipedia</a>.
 */
public final class FordFulkersonTestCase
{

    @Test
    public void findMaxFlowAndVerify()
    {
        DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>, Integer> graph =
            new DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Integer>, Integer>();

        // building Graph
        BaseLabeledVertex a = new BaseLabeledVertex("A");
        BaseLabeledVertex b = new BaseLabeledVertex("B");
        BaseLabeledVertex c = new BaseLabeledVertex("C");
        BaseLabeledVertex d = new BaseLabeledVertex("D");

        graph.addVertex( a );
        graph.addVertex( b );
        graph.addVertex( c );
        graph.addVertex( d );

        graph.addEdge( a, new BaseLabeledWeightedEdge<Integer>( "A -> B", 1000 ), b );
        graph.addEdge( a, new BaseLabeledWeightedEdge<Integer>( "A -> C", 1000 ), c );
        graph.addEdge( b, new BaseLabeledWeightedEdge<Integer>( "B -> C", 1 ), c );
        graph.addEdge( b, new BaseLabeledWeightedEdge<Integer>( "B -> D", 1000 ), d );
        graph.addEdge( c, new BaseLabeledWeightedEdge<Integer>( "C -> D", 1000 ), d );


        // expected max flow
        Integer expected = 2000;

        // actual max flow
        Integer actual = FordFulkerson.findMaxFlow( graph, a, d );

        assertEquals( actual, expected );
    }

}
