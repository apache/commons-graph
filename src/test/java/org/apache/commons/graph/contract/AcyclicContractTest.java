package org.apache.commons.graph.contract;

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

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class AcyclicContractTest
     extends GraphTest
{
    /**
     * Constructor for the AcyclicContractTest object
     *
     * @param name
     */
    public AcyclicContractTest(String name)
    {
        super(name);
    }

    /**
     * A unit test for JUnit
     */
    public void testDirNullGraph()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirNullGraph());

        IUT.verify();
    }

    /**
     * A unit test for JUnit
     */
    public void testDirSingleVertex()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirSingleVertex());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testSelfLoop()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeSelfLoop());

        try
        {
            IUT.verify();
            fail("No CycleException thrown on Verification.");
        }
        catch (CycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testDirDoubleVertex()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirDoubleVertex());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V1_V2, V1, V2);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V1->V2");
        }
    }


    /**
     * A unit test for JUnit
     */
    public void testDirectedEdge()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirectedEdge());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V1_V2_, V1, V2);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V1->V2'");
        }

        try
        {
            IUT.addEdge(V2_V1, V2, V1);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testDirParallelEdges()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirParallelEdges());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V1_V2__, V1, V2);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V1->V2'");
        }

        try
        {
            IUT.addEdge(V2_V1, V2, V1);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}
    }


    /**
     * A unit test for JUnit
     */
    public void testTwoCycle()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeTwoCycle());

        try
        {
            IUT.verify();
            fail("No CycleException thrown on Verification.");
        }
        catch (CycleException ex)
        {}
    }


    /**
     * A unit test for JUnit
     */
    public void testDirectedCycle()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDirectedCycle());

        try
        {
            IUT.verify();
            fail("No CycleException thrown on Verification.");
        }
        catch (CycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testPipe()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makePipe());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V1_V2_, V1, V2);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V1->V2'");
        }

        try
        {
            IUT.addEdge(V3_V1, V3, V1);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}

    }

    /**
     * A unit test for JUnit
     */
    public void testDiamond()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeDiamond());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V2_V3, V2, V3);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V2->V3");
        }

        try
        {
            IUT.addEdge(V4_V1, V4, V1);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testPipelessCycle()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makePipelessCycle());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V2_V3, V2, V3);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V2->V3");
        }

        try
        {
            IUT.addEdge(V3_V4, V3, V4);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}

    }


    /**
     * A unit test for JUnit
     */
    public void testParentTree()
        throws Throwable
    {
        System.err.println("---- PARENT TREE ----");
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeParentTree());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V2_V3, V2, V3);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V2->V3");
        }

        try
        {
            IUT.addEdge(V1_V5, V1, V5);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}
    }


    /**
     * A unit test for JUnit
     */
    public void testChildTree()
        throws Throwable
    {
        AcyclicContract IUT =
            new AcyclicContract();
        IUT.setImpl(makeChildTree());
        IUT.verify();

        try
        {
            IUT.addEdge(V1_V1, V1, V1);
            fail("No GraphException thrown when Self-Cycle introduced.");
        }
        catch (CycleException ex)
        {}

        try
        {
            IUT.addEdge(V2_V3, V2, V3);
        }
        catch (GraphException ex)
        {
            fail("Contract prevented adding of valid edge. V2->V3");
        }

        try
        {
            IUT.addEdge(V5_V1, V5, V1);
            fail("Contract allowed cycle to be introduced.");
        }
        catch (CycleException ex)
        {}

    }
}
