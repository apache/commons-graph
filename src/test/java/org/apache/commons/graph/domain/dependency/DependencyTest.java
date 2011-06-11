package org.apache.commons.graph.domain.dependency;

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

import java.util.Set;
import java.util.List;
import java.util.HashSet;

import org.apache.commons.graph.*;
import org.apache.commons.graph.domain.dependency.exception.*;

/**
 * Description of the Class
 */
public class DependencyTest
     extends GraphTest
{
    private DependencyGraph IUT = null;

    /**
     * Constructor for the DependencyTest object
     *
     * @param name
     */
    public DependencyTest(String name)
    {
        super(name);
    }

    /**
     * The JUnit setup method
     */
    public void setUp()
    {
        IUT = new DependencyGraph();
    }

    /**
     * Description of the Class
     */
    private class Task
    {
        private String name;

        /**
         * Constructor for the Task object
         *
         * @param name
         */
        public Task(String name)
        {
            this.name = name;
        }

        /**
         * Description of the Method
         */
        public String toString()
        {
            return name;
        }
    }

    /**
     * Description of the Field
     */
    public Task T1 = new Task("T1");
    /**
     * Description of the Field
     */
    public Task T2 = new Task("T2");
    /**
     * Description of the Field
     */
    public Task T3 = new Task("T3");
    /**
     * Description of the Field
     */
    public Task T4 = new Task("T4");
    /**
     * Description of the Field
     */
    public Task T5 = new Task("T5");

    /**
     * A unit test for JUnit
     */
    public void testNoDeps()
        throws Throwable
    {
        Set deps = new HashSet();

        IUT.addDependencies(T1, deps);
    }

    /**
     * A unit test for JUnit
     */
    public void testTwoTasksNoDeps()
        throws Throwable
    {
        Set deps = new HashSet();
        IUT.addDependencies(T1, deps);
        IUT.addDependencies(T2, deps);
    }

    /**
     * A unit test for JUnit
     */
    public void testSelfDep()
        throws Throwable
    {
        Set deps = new HashSet();
        deps.add(T1);

        try
        {
            IUT.addDependencies(T1, deps);
            fail("Self Dependency added without exception.");
        }
        catch (CircularDependencyException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testTwoCyclicDeps()
        throws Throwable
    {
        Set t1Deps = new HashSet();
        t1Deps.add(T2);

        Set t2Deps = new HashSet();
        t2Deps.add(T1);

        IUT.addDependencies(T1, t1Deps);
        try
        {
            IUT.addDependencies(T2, t2Deps);
            fail("No CircularDependencyException thrown.");
        }
        catch (CircularDependencyException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testThreeCyclicDeps()
        throws Throwable
    {
        Set t1Deps = new HashSet();
        t1Deps.add(T2);

        Set t2Deps = new HashSet();
        t2Deps.add(T3);

        Set t3Deps = new HashSet();
        t3Deps.add(T1);

        IUT.addDependencies(T1, t1Deps);
        IUT.addDependencies(T2, t2Deps);

        try
        {
            IUT.addDependencies(T3, t3Deps);
            fail("No CircularDependencyException Thrown.");
        }
        catch (CircularDependencyException ex)
        {}
    }

    /**
     * A unit test for JUnit
     */
    public void testSortedDepsEasy()
        throws Throwable
    {
        Set t1Deps = new HashSet();
        t1Deps.add(T2);

        Set t2Deps = new HashSet();
        t2Deps.add(T3);

        IUT.addDependencies(T1, t1Deps);
        IUT.addDependencies(T2, t2Deps);
        IUT.addDependencies(T3, new HashSet());

        // T3
        List t3SDeps =
            IUT.getSortedDependencies(T3);
        assertEquals("T3: Wrong number of Deps for T3",
            1, t3SDeps.size());

        assertEquals("T3: Wrong thing at pos 0",
            T3, t3SDeps.get(0));

        // T2
        List t2SDeps =
            IUT.getSortedDependencies(T2);
        assertEquals("T2: Wrong number of Deps for T2",
            2, t2SDeps.size());

        try
        {
            assertEquals("T2: Wrong thing at pos 0",
                T3, t2SDeps.get(0));
            assertEquals("T2: Wrong thing at pos 1",
                T2, t2SDeps.get(1));
        }
        catch (Throwable ex)
        {
            System.err.println(t2SDeps);
            throw ex;
        }

        // T1
        List t1SDeps =
            IUT.getSortedDependencies(T1);
        assertEquals("T1: Wrong number of Deps for T1",
            3, t1SDeps.size());

        assertEquals("T1: Wrong thing at pos 0",
            T3, t1SDeps.get(0));
        assertEquals("T1: Wrong thing at pos 1",
            T2, t1SDeps.get(1));
        assertEquals("T1: Wrong thing at pos 2",
            T1, t1SDeps.get(2));

    }

    /**
     * A unit test for JUnit
     */
    public void testSortedDepsHard()
    {
        Set t1Deps = new HashSet();
        t1Deps.add(T2);
        t1Deps.add(T3);
        t1Deps.add(T5);

        Set t2Deps = new HashSet();
        t2Deps.add(T4);
        t2Deps.add(T5);

        IUT.addDependencies(T1, t1Deps);
        IUT.addDependencies(T2, t2Deps);

        List t1SDeps = IUT.getSortedDependencies(T1);
        assertEquals("T1: Wrong number of dependents.",
            5, t1SDeps.size());

        List t2SDeps = IUT.getSortedDependencies(T2);
        assertEquals("T2: Wrong number of dependents.",
            3, t2SDeps.size());

        List t3SDeps = IUT.getSortedDependencies(T3);
        assertEquals("T1: Wrong number of dependents.",
            1, t3SDeps.size());

        List t4SDeps = IUT.getSortedDependencies(T4);
        assertEquals("T4: Wrong number of dependents.",
            1, t4SDeps.size());

        List t5SDeps = IUT.getSortedDependencies(T5);
        assertEquals("T5: Wrong number of dependents.",
            1, t5SDeps.size());

    }
}
