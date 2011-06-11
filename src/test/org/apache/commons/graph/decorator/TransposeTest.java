package org.apache.commons.graph.decorator;

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

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class TransposeTest
     extends GraphTest
{
    /**
     * Constructor for the TransposeTest object
     *
     * @param name
     */
    public TransposeTest(String name)
    {
        super(name);
    }

    /**
     * A unit test for JUnit
     */
    public void testParentTree()
        throws Throwable
    {
        DDirectedGraph graph =
            DDirectedGraph.decorateGraph(makeParentTree());

        DirectedGraph IUT = graph.transpose();

        // Copied from DirGraphTest.testChildTree
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
}
