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

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.Vertex;

/**
 * Description of the Interface
 */
public interface Contract
{

    /**
     * The impl that gets passed in is read-only. This is the representation of
     * the graph you should work off of. If an edge or vertex addition is
     * illegal to the contract, raise a GraphException with and explanation.
     */
    void setImpl( DirectedGraph impl );

    /**
     * getInterface This returns the marker interface which is associated with
     * the Contract. For instance, AcyclicContract will return AcyclicGraph
     * here.
     */
    Class getInterface();

    /**
     * verify - This verifies that the graph it is working on complies.
     */
    void verify()
        throws GraphException;

    /**
     * Adds a feature to the Edge attribute of the Contract object
     */
    void addEdge( Edge e, Vertex start, Vertex end )
        throws GraphException;

    /**
     * Adds a feature to the Vertex attribute of the Contract object
     */
    void addVertex( Vertex v )
        throws GraphException;

    /**
     * Description of the Method
     */
    void removeEdge( Edge e )
        throws GraphException;

    /**
     * Description of the Method
     */
    void removeVertex( Vertex v )
        throws GraphException;

}
