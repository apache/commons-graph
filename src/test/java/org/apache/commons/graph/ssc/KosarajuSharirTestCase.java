package org.apache.commons.graph.ssc;

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

import static org.apache.commons.graph.ssc.KosarajuSharir.hasStronglyConnectedComponent;

import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.junit.Test;

/**
 * Test for Kosaraju's algorithm implementation,
 * see the <a href="http://scienceblogs.com/goodmath/2007/10/computing_strongly_connected_c.php">online</a> testcase.
 */
public final class KosarajuSharirTestCase
{

    @Test
    public void verifyHasStronglyConnectedComponents()
    {
        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph =
            new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        // building Graph

        BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        BaseLabeledVertex b = new BaseLabeledVertex( "B" );
        BaseLabeledVertex c = new BaseLabeledVertex( "C" );
        BaseLabeledVertex d = new BaseLabeledVertex( "D" );
        BaseLabeledVertex e = new BaseLabeledVertex( "E" );
        BaseLabeledVertex f = new BaseLabeledVertex( "F" );
        BaseLabeledVertex g = new BaseLabeledVertex( "G" );
        BaseLabeledVertex h = new BaseLabeledVertex( "H" );

        graph.addVertex( a );
        graph.addVertex( b );
        graph.addVertex( c );
        graph.addVertex( d );
        graph.addVertex( e );
        graph.addVertex( f );
        graph.addVertex( g );
        graph.addVertex( h );

        graph.addEdge( a, new BaseLabeledEdge( "A -> F" ), f );
        graph.addEdge( a, new BaseLabeledEdge( "A -> B" ), b );
        graph.addEdge( b, new BaseLabeledEdge( "B -> D" ), d );
        graph.addEdge( c, new BaseLabeledEdge( "C -> G" ), g );
        graph.addEdge( d, new BaseLabeledEdge( "D -> A" ), a );
        graph.addEdge( d, new BaseLabeledEdge( "D -> G" ), g );
        graph.addEdge( e, new BaseLabeledEdge( "E -> C" ), c );
        graph.addEdge( e, new BaseLabeledEdge( "E -> F" ), f );
        graph.addEdge( f, new BaseLabeledEdge( "F -> E" ), e );
        graph.addEdge( g, new BaseLabeledEdge( "G -> H" ), h );
        graph.addEdge( h, new BaseLabeledEdge( "H -> C" ), c );


        hasStronglyConnectedComponent( graph, a );
    }

}
