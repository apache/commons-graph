package org.apache.commons.graph.scc;

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

import static org.apache.commons.graph.CommonsGraph.findStronglyConnectedComponent;
import static org.apache.commons.graph.CommonsGraph.newDirectedMutableGraph;

import static org.junit.Assert.assertFalse;

import java.util.Set;

import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test for Kosaraju's algorithm implementation,
 * see the <a href="http://scienceblogs.com/goodmath/2007/10/computing_strongly_connected_c.php">online</a> testcase.
 */
public final class KosarajuSharirTestCase
{

    @Test
    @Ignore
    public void verifyHasStronglyConnectedComponents()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );

        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph =
        newDirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            public void connect()
            {
                addVertex( a );
                BaseLabeledVertex b = addVertex( new BaseLabeledVertex( "B" ) );
                BaseLabeledVertex c = addVertex( new BaseLabeledVertex( "C" ) );
                BaseLabeledVertex d = addVertex( new BaseLabeledVertex( "D" ) );
                BaseLabeledVertex e = addVertex( new BaseLabeledVertex( "E" ) );
                BaseLabeledVertex f = addVertex( new BaseLabeledVertex( "F" ) );
                BaseLabeledVertex g = addVertex( new BaseLabeledVertex( "G" ) );
                BaseLabeledVertex h = addVertex( new BaseLabeledVertex( "H" ) );

                addEdge( new BaseLabeledEdge( "A -> F" ) ).from( a ).to( f );
                addEdge( new BaseLabeledEdge( "A -> B" ) ).from( a ).to( b );
                addEdge( new BaseLabeledEdge( "B -> D" ) ).from( b ).to( d );
                addEdge( new BaseLabeledEdge( "C -> G" ) ).from( c ).to( g );
                addEdge( new BaseLabeledEdge( "D -> A" ) ).from( d ).to( a );
                addEdge( new BaseLabeledEdge( "D -> G" ) ).from( d ).to( g );
                addEdge( new BaseLabeledEdge( "E -> C" ) ).from( e ).to( c );
                addEdge( new BaseLabeledEdge( "E -> F" ) ).from( e ).to( f );
                addEdge( new BaseLabeledEdge( "F -> E" ) ).from( f ).to( e );
                addEdge( new BaseLabeledEdge( "G -> H" ) ).from( g ).to( h );
                addEdge( new BaseLabeledEdge( "H -> C" ) ).from( h ).to( c );
            }

        } );

        Set<BaseLabeledVertex> ssc = findStronglyConnectedComponent( graph ).applyingKosarajuSharir( a );

        assertFalse( ssc.isEmpty() );
    }

}
