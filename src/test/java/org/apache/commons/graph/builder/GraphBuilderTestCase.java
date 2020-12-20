package org.apache.commons.graph.builder;

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

import static org.apache.commons.graph.CommonsGraph.newUndirectedMutableGraph;
import static org.junit.Assert.assertEquals;

import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.junit.Test;

public final class GraphBuilderTestCase
{

    @Test
    public void verifyProducedGraphesAreEquals()
    {
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> expected =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>();

        // building Graph with traditional APIs...

        final BaseLabeledVertex start = new BaseLabeledVertex( "start" );
        final BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        final BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        final BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        final BaseLabeledVertex d = new BaseLabeledVertex( "d" );
        final BaseLabeledVertex e = new BaseLabeledVertex( "e" );
        final BaseLabeledVertex goal = new BaseLabeledVertex( "goal" );

        expected.addVertex( start );
        expected.addVertex( a );
        expected.addVertex( b );
        expected.addVertex( c );
        expected.addVertex( d );
        expected.addVertex( e );
        expected.addVertex( goal );

        expected.addEdge( start, new BaseLabeledWeightedEdge<Double>( "start <-> a", 1.5D ), a );
        expected.addEdge( start, new BaseLabeledWeightedEdge<Double>( "start <-> d", 2D ), d );

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 2D ), b );
        expected.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 3D ), c );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> goal", 3D ), goal );

        expected.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 3D ), e );
        expected.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> goal", 2D ), goal );

        // ... and using the EDSL :)

        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>> actual =
        newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>()
        {

            public void connect()
            {
                final BaseLabeledVertex start = addVertex( new BaseLabeledVertex( "start" ) );
                final BaseLabeledVertex a = addVertex( new BaseLabeledVertex( "a" ) );
                final BaseLabeledVertex b = addVertex( new BaseLabeledVertex( "b" ) );
                final BaseLabeledVertex c = addVertex( new BaseLabeledVertex( "c" ) );
                final BaseLabeledVertex d = addVertex( new BaseLabeledVertex( "d" ) );
                final BaseLabeledVertex e = addVertex( new BaseLabeledVertex( "e" ) );
                final BaseLabeledVertex goal = addVertex( new BaseLabeledVertex( "goal" ) );

                addEdge( new BaseLabeledWeightedEdge<Double>( "start <-> a", 1.5D ) ).from( start ).to( a );
                addEdge( new BaseLabeledWeightedEdge<Double>( "start <-> d", 2D ) ).from( start ).to( d );

                addEdge( new BaseLabeledWeightedEdge<Double>( "a <-> b", 2D ) ).from( a ).to( b );
                addEdge( new BaseLabeledWeightedEdge<Double>( "b <-> c", 3D ) ).from( b ).to( c );
                addEdge( new BaseLabeledWeightedEdge<Double>( "c <-> goal", 3D ) ).from( c ).to( goal );

                addEdge( new BaseLabeledWeightedEdge<Double>( "d <-> e", 3D ) ).from( d ).to( e );
                addEdge( new BaseLabeledWeightedEdge<Double>( "e <-> goal", 2D ) ).from( e ).to( goal );
            }

        } );

        assertEquals( expected, actual );
    }

}
