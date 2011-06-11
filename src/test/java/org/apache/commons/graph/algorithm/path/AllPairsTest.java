package org.apache.commons.graph.algorithm.path;

/*
 * Copyright 2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * AllPairsTest This tests the All Pairs Shortest Path solution.
 */

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

import java.util.List;

/**
 * Description of the Class
 */
public class AllPairsTest
     extends WeightedGraphTest
{
    /**
     * Constructor for the AllPairsTest object
     *
     * @param name
     */
    public AllPairsTest(String name)
    {
        super(name);
    }

    /**
     * A unit test for JUnit
     */
    public void testAPWDirectedEdge()
        throws Throwable
    {
        DirectedGraph g = makeWDirectedEdge();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            5.0, 2, 1);
        try
        {
            IUT.getShortestPath(V2, V1);
            fail("NoPathException not thrown.");
        }
        catch (NoPathException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testAPPositiveCycle()
        throws Throwable
    { 
        DirectedGraph g = makePositiveCycle();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            2.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            4.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V1),
            V3, V1, V3, V3_V1,
            1.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V1_V2,
            6.0, 3, 2);
        verifyPath(g, IUT.getShortestPath(V2, V1),
            V2, V1, V3, V2_V3,
            5.5, 3, 2);
        verifyPath(g, IUT.getShortestPath(V3, V2),
            V3, V2, V1, V1_V2,
            3.5, 3, 2);
    }

    /**
     * A unit test for JUnit
     */
    public void testAPPositivePartNegCycle()
        throws Throwable
    {
        DirectedGraph g = makePositivePartNegCycle();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            2.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            4.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V1),
            V3, V1, V3, V3_V1,
            -1.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V2_V3,
            6.0, 3, 2);
        verifyPath(g, IUT.getShortestPath(V2, V1),
            V2, V1, V3, V2_V3,
            2.5, 3, 2);
        verifyPath(g, IUT.getShortestPath(V3, V2),
            V3, V2, V1, V1_V2,
            0.5, 3, 2);
    }

    /**
     * A unit test for JUnit
     */
    public void testAPNegativeCycle()
        throws Throwable
    {
        try
        {
            AllPairsShortestPath IUT = new AllPairsShortestPath(makeNegativeCycle());
            fail("NegativeCycleException not thrown.");
        }
        catch (NegativeCycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testAPNegativePartPosCycle()
        throws Throwable
    {
        try
        {
            AllPairsShortestPath IUT = new AllPairsShortestPath(makeNegativePartPosCycle());
            fail("NegativeCycleException not thrown.");
        }
        catch (NegativeCycleException ex)
        {}
    }

    /*
     * Test Pipes now. . .
     */
    /**
     * A unit test for JUnit
     */
    public void testAPPositivePipe()
        throws Throwable
    {
        DirectedGraph g = makePositivePipe();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            3.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V1_V2,
            5.0, 3, 2);

    }

    /**
     * A unit test for JUnit
     */
    public void testAPPositivePartNegPipe()
        throws Throwable
    {
        DirectedGraph g = makePositivePartNegPipe();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            -1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            3.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V2_V3,
            2.0, 3, 2);

    }

    /**
     * A unit test for JUnit
     */
    public void testAPNegativePipe()
        throws Throwable
    {
        DirectedGraph g = makeNegativePipe();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            -1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            -3.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V1_V2,
            -5.0, 3, 2);

    }

    /**
     * A unit test for JUnit
     */
    public void testAPNegativePartPosPipe()
        throws Throwable
    {
        DirectedGraph g = makeNegativePartPosPipe();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V3),
            V2, V3, V2, V2_V3,
            -3.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V2, V1_V2,
            -2.0, 3, 2);

    }

    /**
     * A unit test for JUnit
     */
    public void testMultiplePathL()
        throws Throwable
    {
        DirectedGraph g = makeMultiplePathL();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V4, V4),
            V4, V4, V4,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V1, V1_V3,
            2.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V4),
            V2, V4, V2, V2_V4,
            1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V4),
            V3, V4, V3, V3_V4,
            3.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V4),
            V1, V4, V2, V1_V2,
            3.0, 3, 2);
    }

    /**
     * A unit test for JUnit
     */
    public void testMultiplePathR()
        throws Throwable
    {
        DirectedGraph g = makeMultiplePathR();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V4, V4),
            V4, V4, V4,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            3.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V1, V1_V3,
            1.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V4),
            V2, V4, V2, V2_V4,
            2.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V4),
            V3, V4, V3, V3_V4,
            1.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V4),
            V1, V4, V3, V1_V3,
            3.0, 3, 2);
    }

    /**
     * A unit test for JUnit
     */
    public void testMultiplePathEarlyLow()
        throws Throwable
    {
        DirectedGraph g = makeMultiplePathEarlyLow();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V4, V4),
            V4, V4, V4,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            10.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V1, V1_V3,
            0.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V4),
            V2, V4, V2, V2_V4,
            10.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V4),
            V3, V4, V3, V3_V4,
            10.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V4),
            V1, V4, V3, V3_V4,
            11.0, 3, 2);
    }

    /**
     * A unit test for JUnit
     */
    public void testMultiplePathEarlyHigh()
        throws Throwable
    {
        DirectedGraph g = makeMultiplePathEarlyHigh();
        AllPairsShortestPath IUT = new AllPairsShortestPath(g);

        verifyPath(g, IUT.getShortestPath(V1, V1),
            V1, V1, V1,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V2, V2),
            V2, V2, V2,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V3, V3),
            V3, V3, V3,
            0.0, 1, 0);
        verifyPath(g, IUT.getShortestPath(V4, V4),
            V4, V4, V4,
            0.0, 1, 0);

        verifyPath(g, IUT.getShortestPath(V1, V2),
            V1, V2, V1, V1_V2,
            10.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V1, V3),
            V1, V3, V1, V1_V3,
            10.5, 2, 1);
        verifyPath(g, IUT.getShortestPath(V2, V4),
            V2, V4, V2, V2_V4,
            10.0, 2, 1);
        verifyPath(g, IUT.getShortestPath(V3, V4),
            V3, V4, V3, V3_V4,
            0.5, 2, 1);

        verifyPath(g, IUT.getShortestPath(V1, V4),
            V1, V4, V3, V1_V3,
            11.0, 3, 2);
    }

    /**
     * Description of the Method
     */
    public void verifyPath(DirectedGraph g, WeightedPath wp,
                           Vertex start, Vertex end, Vertex mid,
                           double cost, int vertexCount, int edgeCount)
        throws Throwable
    {
        verifyPath(g, wp, start, end, mid,
            null, cost, vertexCount, edgeCount);
    }

    /**
     * Description of the Method
     */
    public void verifyPath(DirectedGraph g, WeightedPath wp,
                           Vertex start, Vertex end, Vertex mid, Edge midE,
                           double cost, int vertexCount, int edgeCount)
        throws Throwable
    {
        assertEquals("Wrong Start",
            start, wp.getStart());
        assertEquals("Wrong End",
            end, wp.getEnd());
        assertEquals("Wrong Cost of Path: " + start + "->" + end,
            cost, wp.getWeight(), 0.0001);
        assertEquals("Wrong number of Vertices in " + start + "->" + end,
            vertexCount, wp.getVertices().size());
        assertEquals("Wrong number of Edges in " + start + "->" + end,
            edgeCount, wp.getEdges().size());
        assertTrue("Path " + start + "->" + end + " doesn't contain: " + mid,
            wp.getVertices().contains(mid));
        if (midE != null)
        {
            assertTrue("Path " + start + "-> " + end + " doesn't contain edge: " + midE,
                wp.getEdges().contains(midE));
        }

        List edgeList = wp.getEdges();
        List vertList = wp.getVertices();

        for (int i = 0; i < edgeList.size(); i++)
        {
            assertEquals("Edge: " + edgeList.get(i) + " doesn't use " +
                vertList.get(i) + " as source.",
                g.getSource((Edge) edgeList.get(i)),
                (Vertex) vertList.get(i));
            assertEquals("Edge: " + edgeList.get(i) + " doesn't use " +
                vertList.get(i) + " as target.",
                g.getTarget((Edge) edgeList.get(i)),
                (Vertex) vertList.get(i + 1));
        }
    }
}
