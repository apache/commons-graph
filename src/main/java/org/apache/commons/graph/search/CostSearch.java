package org.apache.commons.graph.search;

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

/**
 * This is a Cost searching algorithm. It will basically follow edges/paths with
 * minimal cost first, and then go to the later costs.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.BinaryHeap;
import org.apache.commons.collections.PriorityQueue;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedGraph;

/**
 * Description of the Class
 */
public class CostSearch
{
    /**
     * Description of the Class
     */
    public class ComparableEdge
         implements Edge, Comparable
    {
        private Edge e;
        private double cost;

        /**
         * Constructor for the ComparableEdge object
         *
         * @param e
         * @param cost
         */
        public ComparableEdge(Edge e, double cost)
        {
            this.e = e;
            this.cost = cost;
        }

        /**
         * Gets the edge attribute of the ComparableEdge object
         */
        public Edge getEdge()
        {
            return e;
        }

        /**
         * Gets the cost attribute of the ComparableEdge object
         */
        public double getCost()
        {
            return cost;
        }

        /**
         * Description of the Method
         */
        public int compareTo(Object o)
        {
            ComparableVertex cv = (ComparableVertex) o;
            if (cv.cost > cost)
            {
                return 1;
            }
            if (cv.cost == cost)
            {
                return 0;
            }
            if (cv.cost < cost)
            {
                return -1;
            }
            return 0;
        }
    }

    /**
     * Description of the Class
     */
    public class ComparableVertex
         implements Vertex, Comparable
    {
        private Vertex v;
        private double cost;

        /**
         * Constructor for the ComparableVertex object
         *
         * @param v
         * @param cost
         */
        public ComparableVertex(Vertex v, double cost)
        {
            this.v = v;
            this.cost = cost;
        }

        /**
         * Description of the Method
         */
        public int compareTo(Object o)
        {
            ComparableVertex cv = (ComparableVertex) o;
            if (cv.cost > cost)
            {
                return 1;
            }
            if (cv.cost == cost)
            {
                return 0;
            }
            if (cv.cost < cost)
            {
                return -1;
            }
            return 0;
        }

        /**
         * Gets the vertex attribute of the ComparableVertex object
         */
        public Vertex getVertex()
        {
            return v;
        }
    }

    private Map colors = new HashMap();// VERTEX X COLOR
    private PriorityQueue tasks = null;

    private String WHITE = "white";
    private String BLACK = "black";
    private String GRAY = "gray";

    /**
     * Constructor for the CostSearch object
     */
    public CostSearch()
    {
        tasks = new BinaryHeap(true);
    }

    /**
     * Constructor for the CostSearch object
     *
     * @param isMin
     */
    public CostSearch(boolean isMin)
    {
        tasks = new BinaryHeap(isMin);
    }

    /**
     * Description of the Method
     */
    public void visitVertex(WeightedGraph graph,
                            Vertex vertex,
                            double cost,
                            Visitor visitor)
    {
        colors.remove(vertex);
        colors.put(vertex, GRAY);

        visitor.discoverVertex(vertex);
        Iterator edges = graph.getEdges(vertex).iterator();
        while (edges.hasNext())
        {
            Edge edge = (Edge) edges.next();

            double edgeCost = graph.getWeight(edge);
            ComparableEdge wEdge =
                new ComparableEdge(edge, edgeCost + cost);
            tasks.insert(wEdge);
        }

        visitor.finishVertex(vertex);
        colors.remove(vertex);
        colors.put(vertex, BLACK);
    }

    /**
     * Description of the Method
     */
    public void visitEdge(WeightedGraph graph,
                          Edge e,
                          double cost,
                          Visitor visitor)
    {
        visitor.discoverEdge(e);

        Iterator vertices = graph.getVertices(e).iterator();
        while (vertices.hasNext())
        {
            Vertex v = (Vertex) vertices.next();
            if (colors.get(v) == WHITE)
            {
                visitVertex(graph, v, cost, visitor);
            }
        }

        visitor.finishEdge(e);
    }


    /**
     * Description of the Method
     */
    public void visit(WeightedGraph graph,
                      Vertex root,
                      Visitor visitor)
    {
        Iterator vertices = graph.getVertices().iterator();
        while (vertices.hasNext())
        {
            colors.put(vertices.next(), WHITE);
        }

        visitor.discoverGraph(graph);

        visitVertex(graph, root, 0.0, visitor);

        while (!tasks.isEmpty())
        {
            ComparableEdge wEdge = (ComparableEdge) tasks.pop();
            visitEdge(graph, wEdge.getEdge(), wEdge.getCost(), visitor);
        }

        visitor.finishGraph(graph);
    }
}


