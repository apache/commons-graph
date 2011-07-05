package org.apache.commons.graph.model;

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

import static org.apache.commons.graph.utils.GraphUtils.buildCompleteGraph;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 */
public class BaseMutableGraphTestCase
{

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseMutableGraph#addVertex(org.apache.commons.graph.Vertex)}.
     */
    @Test
    public final void testAddVertexAndEdge()
    {

        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 50, g );

        assertEquals( 50, g.getOrder() );
        assertEquals( 1225, g.getSize() );
        for ( BaseLabeledVertex v : g.getVertices() )
        {
            assertEquals( 49, g.getDegree( v ) );
        }

        // Test a complete direct graph.
        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> gDirect =
            new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 50, gDirect );

        assertEquals( 50, gDirect.getOrder() );
        assertEquals( 2450, gDirect.getSize() );
        for ( BaseLabeledVertex v : gDirect.getVertices() )
        {
            assertEquals( 98, gDirect.getDegree( v ) );
        }

        // Test
        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> gSimple =
            new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        BaseLabeledVertex one = new BaseLabeledVertex( "1" );
        BaseLabeledVertex two = new BaseLabeledVertex( "2" );
        gSimple.addVertex( one );
        gSimple.addVertex( two );

        gSimple.addEdge( one, new BaseLabeledEdge( "1 -> 2" ), two );

        assertEquals( 2, gSimple.getOrder() );
        assertEquals( 1, gSimple.getSize() );
        assertEquals( 1, gSimple.getDegree( one ) );
        assertEquals( 1, gSimple.getDegree( two ) );
        assertEquals( 0, gSimple.getInDegree( one ) );
        assertEquals( 1, gSimple.getInDegree( two ) );
        assertEquals( 1, gSimple.getOutDegree( one ) );
        assertEquals( 0, gSimple.getOutDegree( two ) );
    }

}
