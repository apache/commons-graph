package org.apache.commons.graph;

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
 * DirGraphTest This test will ensure that we can represent a Directed Graph.
 */

/**
 * Description of the Class
 */
public class DirGraphTest
     extends GraphTest
{
    private String testName = null;

    /**
     * Constructor for the DirGraphTest object
     *
     * @param name
     */
    public DirGraphTest(String name)
    {
        super(name);
        this.testName = name;
    }

    /**
     * A unit test for JUnit
     */
    public void testDirNull()
        throws Throwable
    {
        verifyGraph(makeDirNullGraph(), 0, 0);
    }

    /**
     * A unit test for JUnit
     */
    public void testSingleVertex()
        throws Throwable
    {
        verifyGraph(makeDirSingleVertex(), 1, 0);
    }

    /**
     * A unit test for JUnit
     */
    public void testDoubleVertex()
        throws Throwable
    {
        verifyGraph(makeDirDoubleVertex(), 2, 0);
    }

    /**
     * A unit test for JUnit
     */
    public void testSelfLoop()
        throws Throwable
    {
        DirectedGraph IUT = makeSelfLoop();
        try
        {

            verifyGraph(IUT, 1, 1);

            verifyAdjVertices(IUT, V1,
                makeSet(V1));

            verifyAdjVertices(IUT, V1,
                makeSet(V1),
                makeSet(V1));
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDirectedEdge()
        throws Throwable
    {
        DirectedGraph IUT = makeDirectedEdge();
        try
        {

            verifyGraph(IUT, 2, 1);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet());
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDirParallelEdges()
        throws Throwable
    {
        DirectedGraph IUT = makeDirParallelEdges();
        try
        {

            verifyGraph(IUT, 2, 2);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet());

        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testTwoCycle()
        throws Throwable
    {
        DirectedGraph IUT = makeTwoCycle();
        try
        {

            verifyGraph(IUT, 2, 2);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2));

            verifyAdjVertices(IUT, V1,
                makeSet(V2),
                makeSet(V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet(V1));
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDirectedCycle()
        throws Throwable
    {
        DirectedGraph IUT = makeDirectedCycle();
        try
        {

            verifyGraph(IUT, 3, 3);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V2, V3));

            verifyAdjVertices(IUT, V1,
                makeSet(V3),
                makeSet(V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet(V3));
            verifyAdjVertices(IUT, V3,
                makeSet(V2),
                makeSet(V1));
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }


    /**
     * A unit test for JUnit
     */
    public void testPipe()
        throws Throwable
    {
        DirectedGraph IUT = makePipe();
        try
        {

            verifyGraph(IUT, 3, 2);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V3,
                makeSet(V2, V3));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet(V3));
            verifyAdjVertices(IUT, V3,
                makeSet(V2),
                makeSet());
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDiamond()
        throws Throwable
    {
        DirectedGraph IUT = makeDiamond();
        try
        {

            verifyGraph(IUT, 4, 4);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2, V4));
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V3, V4));
            verifyAdjVertices(IUT, V4,
                makeSet(V2, V3, V4));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet(V4));
            verifyAdjVertices(IUT, V3,
                makeSet(V1),
                makeSet(V4));
            verifyAdjVertices(IUT, V4,
                makeSet(V2, V3),
                makeSet());
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testPipelessCycle()
        throws Throwable
    {
        DirectedGraph IUT = makePipelessCycle();
        try
        {

            verifyGraph(IUT, 4, 4);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2, V4));
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V3, V4));
            verifyAdjVertices(IUT, V4,
                makeSet(V2, V3, V4));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V4),
                makeSet());
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V4),
                makeSet());
            verifyAdjVertices(IUT, V4,
                makeSet(),
                makeSet(V2, V3));
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testParentTree()
        throws Throwable
    {
        DirectedGraph IUT = makeParentTree();
        try
        {

            verifyGraph(IUT, 5, 4);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V3, V4, V5));
            verifyAdjVertices(IUT, V4,
                makeSet(V3, V4));
            verifyAdjVertices(IUT, V5,
                makeSet(V3, V5));

            verifyAdjVertices(IUT, V1,
                makeSet(V2, V3),
                makeSet());
            verifyAdjVertices(IUT, V2,
                makeSet(),
                makeSet(V1));
            verifyAdjVertices(IUT, V3,
                makeSet(V4, V5),
                makeSet(V1));
            verifyAdjVertices(IUT, V4,
                makeSet(),
                makeSet(V3));
            verifyAdjVertices(IUT, V5,
                makeSet(),
                makeSet(V3));
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testChildTree()
        throws Throwable
    {
        DirectedGraph IUT = makeChildTree();
        try
        {

            verifyGraph(IUT, 5, 4);

            verifyAdjVertices(IUT, V1,
                makeSet(V1, V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1, V2));
            verifyAdjVertices(IUT, V3,
                makeSet(V1, V3, V4, V5));
            verifyAdjVertices(IUT, V4,
                makeSet(V3, V4));
            verifyAdjVertices(IUT, V5,
                makeSet(V3, V5));

            verifyAdjVertices(IUT, V1,
                makeSet(),
                makeSet(V2, V3));
            verifyAdjVertices(IUT, V2,
                makeSet(V1),
                makeSet());
            verifyAdjVertices(IUT, V3,
                makeSet(V1),
                makeSet(V4, V5));
            verifyAdjVertices(IUT, V4,
                makeSet(V3),
                makeSet());
            verifyAdjVertices(IUT, V5,
                makeSet(V3),
                makeSet());
        }
        catch (Throwable t)
        {
            printGraph(t, IUT);
            throw t;
        }
    }

}
