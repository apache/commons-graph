package org.apache.commons.graph.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Vertex;
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
     * Creates a graph that contains all classic sudoku contratints.
     *
     * @return
     */
    public static UndirectedMutableGraph<Vertex, Edge> buildSudokuGraph()
    {
        UndirectedMutableGraph<Vertex, Edge> sudoku = new UndirectedMutableGraph<Vertex, Edge>();

        BaseLabeledVertex[][] grid = new BaseLabeledVertex[9][9];

        // build sudoku grid.
        for ( int row = 0; row < 9; row++ )
        {
            for ( int col = 0; col < 9; col++ )
            {
                grid[row][col] = new BaseLabeledVertex( row + "," + col );
                sudoku.addVertex( grid[row][col] );
            }
        }

        int[] rowsOffsets = new int[] { 0, 3, 6 };
        int[] colsOffsets = new int[] { 0, 3, 6 };

        // build constraint.
        for ( int rof = 0; rof < 3; rof++ )
        {
            for ( int cof = 0; cof < 3; cof++ )
            {
                List<Vertex> boxes = new ArrayList<Vertex>();
                for ( int row = rowsOffsets[rof]; row < 3 + rowsOffsets[rof]; row++ )
                {
                    for ( int col = colsOffsets[cof]; col < 3 + colsOffsets[cof]; col++ )
                    {
                        boxes.add( grid[row][col] );
                    }
                }

                for ( Vertex v1 : boxes )
                {
                    for ( Vertex v2 : boxes )
                    {

                        Edge e = new BaseLabeledEdge( v1 + " -> " + v2 );
                        if ( !v1.equals( v2 ) )
                        {
                            sudoku.addEdge( v1, e, v2 );
                        }
                    }
                }
            }
        }

        // create rows constraint
        for ( int j = 0; j < 9; j++ )
        {
            for ( int i = 0; i < 9; i++ )
            {
                for ( int h = 0; h < 9; h++ )
                {
                    Vertex v1 = grid[j][i];
                    Vertex v2 = grid[j][h];

                    if ( !v1.equals( v2 ) )
                    {
                        Edge e = new BaseLabeledEdge( v1 + " -> " + v2 );
                        sudoku.addEdge( v1, e, v2 );
                    }

                }
            }
        }

        // create cols constraint
        for ( int j = 0; j < 9; j++ )
        {
            for ( int i = 0; i < 9; i++ )
            {
                for ( int h = 0; h < 9; h++ )
                {
                    Vertex v1 = grid[i][j];
                    Vertex v2 = grid[h][j];

                    if ( !v1.equals( v2 ) )
                    {
                        Edge e = new BaseLabeledEdge( v1 + " -> " + v2 );
                        sudoku.addEdge( v1, e, v2 );
                    }

                }
            }
        }
        return sudoku;
    }

    /**
     * This class can't be instantiated
     */
    private GraphUtils()
    {
        // do nothing
    }

}
