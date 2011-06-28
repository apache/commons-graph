package org.apache.commons.graph.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.UndirectedMutableGraph;

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
 * Utilities graph class.
 */
public class GraphUtils
{

    /**
     * Creates a complete graph with nVertices
     *
     * @param nVertices number of vertices
     * @param g graph
     */
    public static void buildCompleteGraph( int nVertices, UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g )
    {
        // building Graph
        for ( int i = 0; i < nVertices; i++ )
        {
            BaseLabeledVertex v = new BaseLabeledVertex( "" + i );
            g.addVertex( v );
        }

        for ( BaseLabeledVertex v1 : g.getVertices() )
        {
            for ( BaseLabeledVertex v2 : g.getVertices() )
            {
                if ( !v1.equals( v2 ) )
                {
                    g.addEdge( v1, new BaseLabeledEdge( "" ), v2 );
                }
            }
        }
    }

    /**
     * Create a Biparted graph
     *
     * @param nVertices number of vertices
     * @param g graph
     */
    public static void buildBipartedGraph( int nVertices, UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g )
    {
        // building Graph
        for ( int i = 0; i < nVertices; i++ )
        {
            BaseLabeledVertex v = new BaseLabeledVertex( "" + i );
            g.addVertex( v );
        }

        List<BaseLabeledVertex> fistPartition = new ArrayList<BaseLabeledVertex>();
        List<BaseLabeledVertex> secondPartition = new ArrayList<BaseLabeledVertex>();

        int count = 0;
        for ( BaseLabeledVertex v1 : g.getVertices() )
        {
            if ( count++ == nVertices / 2 )
            {
                break;
            }
            fistPartition.add( v1 );
        }

        count = 0;
        for ( BaseLabeledVertex v2 : g.getVertices() )
        {
            if ( count++ < nVertices / 2 )
            {
                continue;
            }
            secondPartition.add( v2 );
        }

        for ( BaseLabeledVertex v1 : fistPartition )
        {
            for ( BaseLabeledVertex v2 : secondPartition )
            {
                if ( !v1.equals( v2 ) )
                {
                    g.addEdge( v1, new BaseLabeledEdge( "" ), v2 );
                }
            }
        }
    }

    /**
     * This class can't be instantiated
     */
    private GraphUtils()
    {
        // do nothing
    }

}
