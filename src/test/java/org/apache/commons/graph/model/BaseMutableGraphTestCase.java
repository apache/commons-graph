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

import static java.lang.String.valueOf;
import static org.apache.commons.graph.utils.GraphUtils.buildCompleteGraph;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.graph.CommonsGraph;
import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.MutableGraph;
import org.apache.commons.graph.utils.MultiThreadedTestRunner;
import org.apache.commons.graph.utils.TestRunner;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class BaseMutableGraphTestCase
{

    // Utility class.
    private final class GraphInsert
        extends TestRunner
    {

        private MutableGraph<BaseLabeledVertex, BaseLabeledEdge> g;

        private int start;

        private int end;

        private GraphInsert( MutableGraph<BaseLabeledVertex, BaseLabeledEdge> g, int start, int end )
        {
            this.g = g;
            this.start = start;
            this.end = end;
        }

        @Override
        public void runTest()
        {

            // building a complete Graph
            for ( int i = start; i < end; i++ )
            {
                BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
                g.addVertex( v );
            }
            synchronized ( g )
            {
                for ( BaseLabeledVertex v1 : g.getVertices() )
                {
                    for ( BaseLabeledVertex v2 : g.getVertices() )
                    {
                        if ( !v1.equals( v2 ) )
                        {
                            try
                            {
                                BaseLabeledEdge e = new BaseLabeledEdge( v1 + " -> " + v2 );
                                g.addEdge( v1, e, v2 );
                            }
                            catch ( GraphException e )
                            {
                                // ignore
                            }
                        }
                    }
                }

            }
        }
    }

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
        assertFalse( gSimple.containsEdge( new BaseLabeledEdge( "Not Exist Edge" ) ) );
        assertFalse( gSimple.containsVertex( new BaseLabeledVertex( "Not exist vertex" ) ) );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseMutableGraph#removeEdge(org.apache.commons.graph.Edge)}
     */
    @Test
    public final void testDirectedGraphRemoveEdge()
    {
        // Test a complete undirect graph.
        final DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        final BaseLabeledVertex source = new BaseLabeledVertex( valueOf( 1 ) );
        final BaseLabeledVertex target = new BaseLabeledVertex( valueOf( 2 ) );

        buildCompleteGraph( 10, g );

        BaseLabeledEdge e = g.getEdge( source, target );
        g.removeEdge( e );

        BaseLabeledEdge edge = g.getEdge( source, target );
        assertNull( edge );
    }

    /**
     * Test method for {@link org.apache.commons.graph.model.BaseMutableGraph#removeEdge(org.apache.commons.graph.Edge)}
     */
    @Test
    public final void testDirectedGraphRemoveEdgeNotExists()
    {
        // Test a complete undirect graph.
        DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 10, g );
        BaseLabeledEdge e = new BaseLabeledEdge( "NOT EXIST" );

        assertThrows(GraphException.class, () -> g.removeEdge( e ));
    }

    /**
     * Test Graph model in a multi-thread enviroment.
     */
    @Test
    public final void testDirectedMultiTh()
        throws Throwable
    {
        final MutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            (MutableGraph<BaseLabeledVertex, BaseLabeledEdge>) CommonsGraph.synchronize( (MutableGraph<BaseLabeledVertex, BaseLabeledEdge>) new DirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>() );

        TestRunner tr1, tr2, tr3;
        tr1 = new GraphInsert( g, 0, 10 );
        tr2 = new GraphInsert( g, 10, 20 );
        tr3 = new GraphInsert( g, 20, 30 );

        TestRunner[] trs = { tr1, tr2, tr3 };
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner( trs );
        mttr.runRunnables();

        assertEquals( 30, g.getOrder() );

        // test the # of edges = n (n-1)
        assertEquals( ( 30 * ( 30 - 1 ) ), g.getSize() );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getConnectedVertices(org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetConnectedVertices()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 10, g );

        final BaseLabeledVertex testVertex = new BaseLabeledVertex( valueOf( 1 ) );
        Iterable<BaseLabeledVertex> connectedVertices = g.getConnectedVertices( testVertex );
        assertNotNull( connectedVertices );

        final List<BaseLabeledVertex> v = new ArrayList<BaseLabeledVertex>();
        for ( BaseLabeledVertex baseLabeledVertex : connectedVertices )
        {
            v.add( baseLabeledVertex );
        }

        assertEquals( 9, v.size() );
        assertFalse( v.contains( testVertex ) );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getConnectedVertices(org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetConnectedVerticesNPE()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 10, g );
        BaseLabeledVertex notExistsVertex = new BaseLabeledVertex( valueOf( 1000 ) );

        assertThrows(GraphException.class, () -> g.getConnectedVertices( notExistsVertex ));
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getConnectedVertices(org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetConnectedVerticesOnNotConnectedGraph()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        // building a not connected Graph
        for ( int i = 0; i < 4; i++ )
        {
            BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
            g.addVertex( v );
        }

        final BaseLabeledVertex testVertex = new BaseLabeledVertex( valueOf( 1 ) );
        Iterable<BaseLabeledVertex> connectedVertices = g.getConnectedVertices( testVertex );
        assertNotNull( connectedVertices );

        final List<BaseLabeledVertex> v = new ArrayList<BaseLabeledVertex>();
        for ( BaseLabeledVertex baseLabeledVertex : connectedVertices )
        {
            v.add( baseLabeledVertex );
        }

        assertEquals( 0, v.size() );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getEdge(org.apache.commons.graph.Vertex, org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetEdge()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 10, g );

        final BaseLabeledVertex source = new BaseLabeledVertex( valueOf( 1 ) );
        final BaseLabeledVertex target = new BaseLabeledVertex( valueOf( 2 ) );
        BaseLabeledEdge edge = g.getEdge( source, target );
        assertNotNull( edge );
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getEdge(org.apache.commons.graph.Vertex, org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetEgdeNotExistsVertex()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 10, g );
        BaseLabeledVertex source = new BaseLabeledVertex( valueOf( 100 ) );
        BaseLabeledVertex target = new BaseLabeledVertex( valueOf( 2 ) );

        assertThrows(GraphException.class ,() -> g.getEdge( source, target ));
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getEdge(org.apache.commons.graph.Vertex, org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetEgdeNotExistsVertex_2()
    {

        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 10, g );
        BaseLabeledVertex source = new BaseLabeledVertex( valueOf( 1 ) );
        BaseLabeledVertex target = new BaseLabeledVertex( valueOf( 200 ) );

        assertThrows(GraphException.class, () -> g.getEdge( source, target ));
    }

    /**
     * Test method for
     * {@link org.apache.commons.graph.model.BaseGraph#getEdge(org.apache.commons.graph.Vertex, org.apache.commons.graph.Vertex)}
     */
    @Test
    public final void testGetNotExistsEdge()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        // building Graph
        for ( int i = 0; i < 4; i++ )
        {
            BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
            g.addVertex( v );
        }

        final BaseLabeledVertex source = new BaseLabeledVertex( valueOf( 1 ) );
        final BaseLabeledVertex target = new BaseLabeledVertex( valueOf( 2 ) );
        BaseLabeledEdge edge = g.getEdge( source, target );
        assertNull( edge );
    }

    /**
     * Test Graph model ina multi-thread enviroment.
     */
    @Test
    public final void testMultiThreadUndirectGraph()
        throws Throwable
    {
        final MutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            (MutableGraph<BaseLabeledVertex, BaseLabeledEdge>) CommonsGraph.synchronize( (MutableGraph<BaseLabeledVertex, BaseLabeledEdge>) new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>() );

        TestRunner tr1, tr2, tr3;
        tr1 = new GraphInsert( g, 0, 10 );
        tr2 = new GraphInsert( g, 10, 20 );
        tr3 = new GraphInsert( g, 20, 30 );

        TestRunner[] trs = { tr1, tr2, tr3 };
        MultiThreadedTestRunner mttr = new MultiThreadedTestRunner( trs );

        mttr.runRunnables();

        assertEquals( 30, g.getOrder() );

        // test the # of edges = n (n-1)/2
        assertEquals( ( 30 * ( 30 - 1 ) / 2 ), g.getSize() );
    }

    /**
     * Test method for {@link org.apache.commons.graph.model.BaseMutableGraph#removeEdge(org.apache.commons.graph.Edge)}
     */
    @Test
    public final void testUndirectedGraphRemoveEdge()
    {
        // Test a complete undirect graph.
        final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g =
            new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();

        final BaseLabeledVertex source = new BaseLabeledVertex( valueOf( 1 ) );
        final BaseLabeledVertex target = new BaseLabeledVertex( valueOf( 2 ) );

        buildCompleteGraph( 10, g );

        BaseLabeledEdge e = g.getEdge( source, target );
        g.removeEdge( e );

        BaseLabeledEdge edge = g.getEdge( source, target );

        assertNull( edge );
    }

    /**
     * Test method for {@link org.apache.commons.graph.model.BaseMutableGraph#removeEdge(org.apache.commons.graph.Edge)}
     */
    @Test
    public final void testUndirectedGraphRemoveEdgeNotExists()
    {
        // Test a complete undirect graph.
        UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g = new UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>();
        buildCompleteGraph( 10, g );
        BaseLabeledEdge e = new BaseLabeledEdge( "NOT EXIST" );

        assertThrows(GraphException.class, () -> g.removeEdge( e ));
    }
}
