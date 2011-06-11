package org.apache.commons.graph.algorithm.path;

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
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.AbstractList;

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class AllPairsShortestPath
{
    private int pred[][];
    private double cost[][];
    private Vertex vArray[];
    private DirectedGraph graph;
    private Map vertexIndex = new HashMap();// VERTEX X INTEGER

    /**
     * Description of the Class
     */
    public class EdgeList
         extends AbstractList
    {
        private DirectedGraph graph;
        private List vertices;

        /**
         * Constructor for the EdgeList object
         *
         * @param graph
         * @param vertices
         */
        public EdgeList(DirectedGraph graph,
                        List vertices)
        {
            this.graph = graph;
            this.vertices = vertices;
        }

        /**
         * Description of the Method
         */
        public int size()
        {
            return vertices.size() - 1;
        }

        /**
         * Description of the Method
         */
        public Object get(int index)
        {
            Edge RC = null;

            Vertex source = (Vertex) vertices.get(index);
            Vertex target = (Vertex) vertices.get(index + 1);

            Set outboundSet = graph.getOutbound(source);
            if (outboundSet == null)
            {
                return null;
            }

            Iterator outbound = outboundSet.iterator();
            while (outbound.hasNext())
            {
                RC = (Edge) outbound.next();
                if (graph.getTarget(RC) == target)
                {
                    break;
                }
            }

            if (graph.getTarget(RC) != target)
            {
                return null;
            }

            return RC;
        }
    }

    /**
     * Description of the Class
     */
    public class WPath
         implements WeightedPath
    {
        private Vertex start;
        private Vertex finish;
        private List vertexList = new ArrayList();
        private DirectedGraph graph;
        private double cost;

        /**
         * Constructor for the WPath object
         *
         * @param graph
         * @param vArray
         * @param pred
         * @param start
         * @param finish
         * @param cost
         * @exception GraphException
         */
        public WPath(DirectedGraph graph,
                     Vertex vArray[], int pred[][],
                     int start, int finish, double cost)
            throws GraphException
        {
            this.start = vArray[start];
            this.finish = vArray[finish];
            this.cost = cost;
            this.graph = graph;

            vertexList.addAll(segment(vArray, pred,
                start, finish));
            vertexList.add(vArray[finish]);
        }

        /**
         * Returns a List of Vectors in order. Includes the start, but not the
         * finish.
         */
        private List segment(Vertex vArray[], int pred[][],
                             int start, int finish)
            throws GraphException
        {
            int mid = pred[start][finish];
            if (mid == -1)
            {
                throw new NoPathException("No SubPath Available: " +
                    vArray[start] + " -> " +
                    vArray[finish]);
            }
            List RC = new ArrayList();

            if (start == finish)
            {
                return RC;
            }

            if (start == mid)
            {
                RC.add(vArray[start]);

            }
            else
            {
                RC.addAll(segment(vArray, pred,
                    start, mid));
                RC.add(vArray[mid]);
            }

            if (mid == pred[mid][finish])
            {
            }
            else
            {
                RC.addAll(segment(vArray, pred,
                    mid, finish));
            }
            return RC;
        }

        /**
         * Gets the weight attribute of the WPath object
         */
        public double getWeight()
        {
            return cost;
        }

        /**
         * Gets the vertices attribute of the WPath object
         */
        public List getVertices()
        {
            return vertexList;
        }

        /**
         * Gets the edges attribute of the WPath object
         */
        public List getEdges()
        {
            return new EdgeList(graph, vertexList);
        }

        /**
         * Gets the start attribute of the WPath object
         */
        public Vertex getStart()
        {
            return start;
        }

        /**
         * Gets the end attribute of the WPath object
         */
        public Vertex getEnd()
        {
            return finish;
        }

	public int size() {
	    return vertexList.size();
	}
    }

    /**
     * Constructor for the AllPairsShortestPath object
     *
     * @param graph
     * @exception NegativeCycleException
     */
    public AllPairsShortestPath(DirectedGraph graph)
        throws NegativeCycleException
    {
        update(graph);
    }


    /**
     * Description of the Method
     */
    private void initIndex(Vertex vArray[])
    {
        for (int i = 0; i < vArray.length; i++)
        {
            vertexIndex.put(vArray[i], new Integer(i));
        }
    }

    /**
     * Description of the Method
     */
    public void update(DirectedGraph graph)
    {
        this.graph = graph;

        Set vertexSet = graph.getVertices();
        vArray = (Vertex[]) vertexSet.toArray(new Vertex[vertexSet.size()]);

        initIndex(vArray);

        pred = new int[vArray.length][vArray.length];
        cost = new double[vArray.length][vArray.length];

        for (int i = 0; i < vArray.length; i++)
        {
            for (int j = 0; j < vArray.length; j++)
            {
                pred[i][j] = -1;
                cost[i][j] = Double.POSITIVE_INFINITY;
            }

            // First round values need to be in the matrix.
            Iterator edgeSet = graph.getOutbound(vArray[i]).iterator();
            while (edgeSet.hasNext())
            {
                Edge e = (Edge) edgeSet.next();
                int j = index(graph.getTarget(e));
                pred[i][j] = i;
                if (graph instanceof WeightedGraph)
                {
                    cost[i][j] = ((WeightedGraph) graph).getWeight(e);
                }
                else
                {
                    cost[i][j] = 1.0;
                }
            }

            // Cost from any node to itself is 0.0
            cost[i][i] = 0.0;
            pred[i][i] = i;
        }

        compute(graph, vArray);

    }

    /**
     * Description of the Method
     */
    private int index(Vertex v)
    {
        return ((Integer) vertexIndex.get(v)).intValue();
    }

    /**
     * Description of the Method
     */
    private void compute(DirectedGraph graph, Vertex vArray[])
        throws NegativeCycleException
    {
        for (int k = 0; k < vArray.length; k++)
        {// Mid Point
            for (int i = 0; i < vArray.length; i++)
            {// Source
                for (int j = 0; j < vArray.length; j++)
                {// Target
                    if (cost[i][k] + cost[k][j] < cost[i][j])
                    {
                        if (i == j)
                        {
                            throw new NegativeCycleException();
                        }

                        // It is cheaper to go through K.
                        cost[i][j] = cost[i][k] + cost[k][j];
                        pred[i][j] = k;
                    }
                }
            }
        }
    }

    /**
     * Gets the shortestPath attribute of the AllPairsShortestPath object
     */
    public WeightedPath getShortestPath(Vertex start, Vertex end)
        throws GraphException
    {
        return new WPath(graph, vArray, pred,
            index(start), index(end),
            cost[index(start)][index(end)]);
    }

    /**
     * Determines if a path exists or not.
     */
    public boolean hasPath( Vertex start, Vertex end ) 
    {
	return cost[index(start)][index(end)] < Double.POSITIVE_INFINITY;
    }
}
