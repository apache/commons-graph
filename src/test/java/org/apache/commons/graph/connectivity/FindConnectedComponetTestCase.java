package org.apache.commons.graph.connectivity;

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

import static org.apache.commons.graph.CommonsGraph.findConnectedComponent;
import static org.apache.commons.graph.CommonsGraph.newUndirectedMutableGraph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.List;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.builder.AbstractGraphConnection;
import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.UndirectedMutableGraph;
import org.junit.Test;

/**
 */
public final class FindConnectedComponetTestCase
{

    @Test(expected=NullPointerException.class)
    public void verifyNullGraph()
    {
        findConnectedComponent( (Graph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>>) null ).includingAllVertices().applyingMinimumSpanningTreeAlgorithm();
    }

    @Test
    public void testVerifyEmptyGraph()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        Collection<List<BaseLabeledVertex>> c =
            findConnectedComponent( graph ).includingAllVertices().applyingMinimumSpanningTreeAlgorithm();
        assertNotNull( c );
        assertEquals( 0, c.size() );
    }

    @Test
    public void testVerifyNullVerticesGraph()
    {
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph =
            newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
            {

                public void connect()
                {
                    addVertex( new BaseLabeledVertex( "B" ) );
                    addVertex( new BaseLabeledVertex( "C" ) );
                }

            } );
        Collection<List<BaseLabeledVertex>> c =
            findConnectedComponent( graph ).includingVertices().applyingMinimumSpanningTreeAlgorithm();
        assertNotNull( c );
        assertEquals( 0, c.size() );
    }

    @Test
    public void testVerifyConnectedComponents()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph =
        newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            public void connect()
            {
                addVertex( a );
                addVertex( new BaseLabeledVertex( "B" ) );
                addVertex( new BaseLabeledVertex( "C" ) );
                addVertex( new BaseLabeledVertex( "D" ) );
                addVertex( new BaseLabeledVertex( "E" ) );
                addVertex( new BaseLabeledVertex( "F" ) );
                addVertex( new BaseLabeledVertex( "G" ) );
                addVertex( new BaseLabeledVertex( "H" ) );
            }

        } );

        Collection<List<BaseLabeledVertex>> c = findConnectedComponent( graph ).includingAllVertices().applyingMinimumSpanningTreeAlgorithm();

        assertNotNull( c );
        assertFalse( c.isEmpty() );
        assertEquals( 8, c.size() );
    }

    @Test
    public void testVerifyConnectedComponents2()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph =
        newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
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
                addEdge( new BaseLabeledEdge( "B -> F" ) ).from( b ).to( f );
                addEdge( new BaseLabeledEdge( "C -> G" ) ).from( c ).to( g );
                addEdge( new BaseLabeledEdge( "D -> G" ) ).from( d ).to( g );
                addEdge( new BaseLabeledEdge( "E -> F" ) ).from( e ).to( f );
                addEdge( new BaseLabeledEdge( "H -> C" ) ).from( h ).to( c );
            }

        } );

        Collection<List<BaseLabeledVertex>> c = findConnectedComponent( graph ).includingAllVertices().applyingMinimumSpanningTreeAlgorithm();

        assertNotNull( c );
        assertFalse( c.isEmpty() );
        assertEquals( 2, c.size() );
    }

    @Test
    public void testVerifyConnectedComponents3()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph =
        newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            public void connect()
            {
                addVertex( a );
                BaseLabeledVertex b = addVertex( new BaseLabeledVertex( "B" ) );
                BaseLabeledVertex c = addVertex( new BaseLabeledVertex( "C" ) );

                addEdge( new BaseLabeledEdge( "A -> B" ) ).from( a ).to( b );
                addEdge( new BaseLabeledEdge( "B -> C" ) ).from( b ).to( c );
                addEdge( new BaseLabeledEdge( "C -> A" ) ).from( c ).to( a );
            }

        } );

        Collection<List<BaseLabeledVertex>> c = findConnectedComponent( graph ).includingAllVertices().applyingMinimumSpanningTreeAlgorithm();

        assertNotNull( c );
        assertFalse( c.isEmpty() );
        assertEquals( 1, c.size() );
    }

    @Test
    public void testVerifyConnectedComponentsIncludingVertices()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph =
        newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
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
                addEdge( new BaseLabeledEdge( "B -> F" ) ).from( b ).to( f );
                addEdge( new BaseLabeledEdge( "C -> G" ) ).from( c ).to( g );
                addEdge( new BaseLabeledEdge( "D -> G" ) ).from( d ).to( g );
                addEdge( new BaseLabeledEdge( "E -> F" ) ).from( e ).to( f );
                addEdge( new BaseLabeledEdge( "H -> C" ) ).from( h ).to( c );
            }

        } );

        Collection<List<BaseLabeledVertex>> coll = findConnectedComponent( graph ).includingVertices( a ).applyingMinimumSpanningTreeAlgorithm();

        assertNotNull( coll );
        assertFalse( coll.isEmpty() );
        assertEquals( 1, coll.size() );
    }

    @Test
    public void testVerifyConnectedComponentsIncludingVertices2()
    {
        final BaseLabeledVertex a = new BaseLabeledVertex( "A" );
        final BaseLabeledVertex e = new BaseLabeledVertex( "E" );

        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> graph =
        newUndirectedMutableGraph( new AbstractGraphConnection<BaseLabeledVertex, BaseLabeledEdge>()
        {

            public void connect()
            {
                addVertex( a );
                addVertex( new BaseLabeledVertex( "B" ) );
                addVertex( new BaseLabeledVertex( "C" ) );
                addVertex( new BaseLabeledVertex( "D" ) );
                addVertex( e );
                addVertex( new BaseLabeledVertex( "F" ) );
                addVertex( new BaseLabeledVertex( "G" ) );
                addVertex( new BaseLabeledVertex( "H" ) );

            }

        } );

        Collection<List<BaseLabeledVertex>> coll = findConnectedComponent( graph ).includingVertices( a, e ).applyingMinimumSpanningTreeAlgorithm();

        assertNotNull( coll );
        assertFalse( coll.isEmpty() );
        assertEquals( 2, coll.size() );
    }

}
