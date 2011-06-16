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
import org.apache.commons.graph.contract.*;
import org.apache.commons.graph.exception.*;
import org.apache.commons.graph.factory.*;

/**
 * Description of the Class
 */
public class DAGTest
     extends GraphTest
{
    private Contract[] dagContracts = new Contract[1];
    private GraphFactory factory = new GraphFactory();
    private String testName = null;

    /**
     * Constructor for the DAGTest object
     *
     * @param name
     */
    public DAGTest(String name)
    {
        super(name);
        this.testName = name;
    }

    /**
     * The JUnit setup method
     */
    public void setUp()
    {
        dagContracts[0] = new AcyclicContract();
    }

    /**
     * A unit test for JUnit
     */
    public void testDAGSelfLoop()
        throws Throwable
    {
        DirectedGraph dg = null;
        try
        {
            try
            {
                dg = factory.makeDirectedGraph(dagContracts,
                    true,
                    makeSelfLoop());
                fail("Should not have created DAG.");
            }
            catch (CycleException ex)
            {}
        }
        catch (Throwable ex)
        {
            printGraph(ex, dg);
            throw ex;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDAGTwoLoop()
        throws Throwable
    {
        DirectedGraph dg = null;
        try
        {
            try
            {
                dg = factory.makeDirectedGraph(dagContracts,
                    true,
                    makeTwoCycle());
                fail("Should not have created DAG.");
            }
            catch (CycleException ex)
            {}
        }
        catch (Throwable ex)
        {
            printGraph(ex, dg);
            throw ex;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testMakeDAGDirCycle()
        throws Throwable
    {
        MutableDirectedGraph mdg = null;
        try
        {
            mdg = factory.makeMutableDirectedGraph(dagContracts,
                true,
                null);
            mdg.addVertex(V1);
            mdg.addVertex(V2);
            mdg.addEdge(V1_V2, V1, V2);

            mdg.addVertex(V3);
            mdg.addEdge(V2_V3, V2, V3);

            try
            {
                mdg.addEdge(V3_V1, V3, V1);
                fail("No CycleException thrown on introduction of cycle.");
            }
            catch (CycleException ex)
            {}
        }
        catch (Throwable t)
        {
            printGraph(t, mdg);
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDAGDirCycle()
        throws Throwable
    {
        DirectedGraph dg = null;
        try
        {
            try
            {
                dg = factory.makeDirectedGraph(dagContracts,
                    true,
                    makeDirectedCycle());
                fail("Should not have created DAG.");
            }
            catch (CycleException ex)
            {}
        }
        catch (Throwable ex)
        {
            printGraph(ex, dg);
            throw ex;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDAGAddCycleToPipe()
        throws Throwable
    {
        MutableDirectedGraph mdg = null;
        try
        {
            try
            {
                mdg = factory.makeMutableDirectedGraph(dagContracts,
                    true,
                    makePipe());
                mdg.addEdge(V3_V1, V3, V1);
                fail("No Exception thrown on adding of invalid edge.");
            }
            catch (CycleException e)
            {}
        }
        catch (Throwable ex)
        {
            printGraph(ex, mdg);
            throw ex;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDAGAddCycleToDirEdge()
        throws Throwable
    {
        MutableDirectedGraph mdg = null;
        try
        {
            try
            {
                mdg = factory.makeMutableDirectedGraph(dagContracts,
                    true,
                    makeDirectedEdge());
                mdg.addEdge(V2_V1, V2, V1);
                fail("Failed to throw exception on introducing Cycle.");
            }
            catch (CycleException ex)
            {}
        }
        catch (Throwable ex)
        {
            printGraph(ex, mdg);
            throw ex;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDAGAddSelfLoop()
        throws Throwable
    {
        MutableDirectedGraph mdg = null;
        try
        {
            try
            {
                mdg = factory.makeMutableDirectedGraph(dagContracts,
                    true,
                    makeDirSingleVertex());
                mdg.addEdge(V1_V1, V1, V1);
                fail("Failed to throw exception on introducing Self Loop.");
            }
            catch (CycleException ex)
            {}
        }
        catch (Throwable ex)
        {
            printGraph(ex, mdg);
            throw ex;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDAGValidEdge()
        throws Throwable
    {
        MutableDirectedGraph mdg = null;
        try
        {
            mdg = factory.makeMutableDirectedGraph(dagContracts,
                true,
                makeParentTree());
            mdg.addEdge(V2_V3, V2, V3);
        }
        catch (Throwable ex)
        {
            printGraph(ex, mdg);
            throw ex;
        }
    }

    /**
     * A unit test for JUnit
     */
    public void testDAGValidEdge2()
        throws Throwable
    {
        MutableDirectedGraph mdg = null;
        try
        {
            mdg = factory.makeMutableDirectedGraph(dagContracts,
                true,
                makeDirDoubleVertex());
            mdg.addEdge(V1_V2, V1, V2);
        }
        catch (Throwable ex)
        {
            printGraph(ex, mdg);
            throw ex;
        }
    }
}
