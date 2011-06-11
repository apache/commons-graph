package org.apache.commons.graph.contract;

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
import java.util.Iterator;

import org.apache.commons.graph.*;
import org.apache.commons.graph.algorithm.search.*;
import org.apache.commons.graph.exception.*;
import org.apache.commons.graph.decorator.*;

/**
 * Description of the Class
 */
public class AcyclicContract
     implements Contract
{
    private DDirectedGraph graph = null;

    /**
     * Description of the Class
     */
    public class CycleDetector
         implements Visitor
    {
        private DFS dfs = null;
        private boolean isCyclic = false;
        private DirectedGraph graph = null;

        /**
         * Constructor for the CycleDetector object
         *
         * @param graph
         */
        public CycleDetector(DirectedGraph graph)
        {
            this.dfs = new DFS();
            this.graph = graph;
            Iterator verts = graph.getVertices().iterator();

            if (verts.hasNext())
            {
                dfs.visit(graph, (Vertex) verts.next(), this);
            }
        }

        /**
         * Description of the Method
         */
        public void discoverGraph(Graph graph) { }

        /**
         * Description of the Method
         */
        public void discoverVertex(Vertex v) { }

        /**
         * Description of the Method
         */
        public void discoverEdge(Edge e)
        {
            if (dfs.getColor(graph.getTarget(e)) == DFS.GRAY)
            {
                this.isCyclic = true;
            }
        }

        /**
         * Description of the Method
         */
        public void finishEdge(Edge e) { }

        /**
         * Description of the Method
         */
        public void finishVertex(Vertex v) { }

        /**
         * Description of the Method
         */
        public void finishGraph(Graph graph) { }

        /**
         * Description of the Method
         */
        public boolean hasCycle()
        {
            return isCyclic;
        }
    }

    /**
     * Constructor for the AcyclicContract object
     */
    public AcyclicContract() { }

    /**
     * Sets the impl attribute of the AcyclicContract object
     */
    public void setImpl(DirectedGraph graph)
    {
        this.graph = DDirectedGraph.decorateGraph(graph);
    }

    /**
     * Gets the interface attribute of the AcyclicContract object
     */
    public Class getInterface()
    {
        return org.apache.commons.graph.contract.Acyclic.class;
    }

    /**
     * Description of the Method
     */
    public void verify()
        throws CycleException
    {
        CycleDetector cd = new CycleDetector(graph);
        if (cd.hasCycle())
        {
            throw new CycleException("Cycle detected in Graph.");
        }
    }

    /**
     * Adds a feature to the Vertex attribute of the AcyclicContract object
     */
    public void addVertex(Vertex v) { }

    /**
     * Adds a feature to the Edge attribute of the AcyclicContract object
     */
    public void addEdge(Edge e,
                        Vertex start,
                        Vertex end)
        throws GraphException
    {
        if (graph.hasConnection(end, start))
        {
            throw new CycleException("Introducing edge will cause a Cycle.");
        }
    }

    /**
     * Description of the Method
     */
    public void removeVertex(Vertex v) { }

    /**
     * Description of the Method
     */
    public void removeEdge(Edge e) { }
}
