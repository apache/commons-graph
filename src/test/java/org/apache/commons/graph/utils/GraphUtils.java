package org.apache.commons.graph.utils;

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

import static java.lang.String.format;
import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.graph.GraphException;
import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseMutableGraph;
import org.apache.commons.graph.model.UndirectedMutableGraph;

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
    public static void buildCompleteGraph(final int nVertices, final BaseMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g )
    {
        // building Graph
        for ( int i = 0; i < nVertices; i++ )
        {
            final BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
            g.addVertex( v );
        }

        for ( final BaseLabeledVertex v1 : g.getVertices() )
        {
            for ( final BaseLabeledVertex v2 : g.getVertices() )
            {
                if ( !v1.equals( v2 ) )
                {
                    try
                    {
                        g.addEdge( v1, new BaseLabeledEdge( format( "%s -> %s", v1, v2 ) ), v2 );
                    }
                    catch ( final GraphException e )
                    {
                        // ignore
                    }
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
    public static void buildBipartedGraph(final int nVertices, final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g )
    {
        // building Graph
        for ( int i = 0; i < nVertices; i++ )
        {
            final BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
            g.addVertex( v );
        }

        final List<BaseLabeledVertex> fistPartition = new ArrayList<BaseLabeledVertex>();
        final List<BaseLabeledVertex> secondPartition = new ArrayList<BaseLabeledVertex>();

        int count = 0;
        for ( final BaseLabeledVertex v1 : g.getVertices() )
        {
            if ( count++ == nVertices / 2 )
            {
                break;
            }
            fistPartition.add( v1 );
        }

        count = 0;
        for ( final BaseLabeledVertex v2 : g.getVertices() )
        {
            if ( count++ < nVertices / 2 )
            {
                continue;
            }
            secondPartition.add( v2 );
        }

        for ( final BaseLabeledVertex v1 : fistPartition )
        {
            for ( final BaseLabeledVertex v2 : secondPartition )
            {
                if ( !v1.equals( v2 ) )
                {
                    try
                    {
                        g.addEdge( v1, new BaseLabeledEdge( format( "%s -> %s", v1, v2 ) ), v2 );
                    }
                    catch ( final GraphException e )
                    {
                        // ignore
                    }
                }
            }
        }
    }

    public static void buildCrownGraph(final int nVertices, final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> g )
    {
        final List<BaseLabeledVertex> tmp = new ArrayList<BaseLabeledVertex>();

        for ( int i = 0; i < nVertices; i++ )
        {
            final BaseLabeledVertex v = new BaseLabeledVertex( valueOf( i ) );
            g.addVertex( v );
            tmp.add( v );
        }

        for ( int i = 0; i < nVertices; i++ )
        {
            int next = i + 1;
            if ( i == ( nVertices - 1 ) )
            {
                next = 0;
            }
            final BaseLabeledEdge e = new BaseLabeledEdge( format( "%s -> %s", i, next ) );
            try
            {
                g.addEdge( tmp.get( i ), e, tmp.get( next ) );
            }
            catch ( final GraphException ge )
            {
                // ignore
            }
        }
    }

    /**
     * Creates a graph that contains all classic sudoku contratints.
     *
     * @return
     */
    public static BaseLabeledVertex[][] buildSudokuGraph(final UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge> sudoku )
    {
        final BaseLabeledVertex[][] grid = new BaseLabeledVertex[9][9];
        // build sudoku grid.
        for ( int row = 0; row < 9; row++ )
        {
            for ( int col = 0; col < 9; col++ )
            {
                grid[row][col] = new BaseLabeledVertex( format( "%s, %s", row, col ) );
                sudoku.addVertex( grid[row][col] );
            }
        }

        final int[] rowsOffsets = new int[] { 0, 3, 6 };
        final int[] colsOffsets = new int[] { 0, 3, 6 };

        // build constraint.
        for ( int rof = 0; rof < 3; rof++ )
        {
            for ( int cof = 0; cof < 3; cof++ )
            {
                final List<BaseLabeledVertex> boxes = new ArrayList<BaseLabeledVertex>();
                for ( int row = rowsOffsets[rof]; row < 3 + rowsOffsets[rof]; row++ )
                {
                    for ( int col = colsOffsets[cof]; col < 3 + colsOffsets[cof]; col++ )
                    {
                        boxes.add( grid[row][col] );
                    }
                }

                for ( final BaseLabeledVertex v1 : boxes )
                {
                    for ( final BaseLabeledVertex v2 : boxes )
                    {

                        final BaseLabeledEdge e = new BaseLabeledEdge( format( "%s -> %s", v1, v2 ) );
                        if ( !v1.equals( v2 ) )
                        {
                            try
                            {
                                sudoku.addEdge( v1, e, v2 );
                            }
                            catch ( final GraphException ge )
                            {
                                // ignore
                            }
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
                    final BaseLabeledVertex v1 = grid[j][i];
                    final BaseLabeledVertex v2 = grid[j][h];

                    if ( !v1.equals( v2 ) )
                    {
                        final BaseLabeledEdge e = new BaseLabeledEdge( format( "%s -> %s", v1, v2 ) );
                        try
                        {
                            sudoku.addEdge( v1, e, v2 );
                        }
                        catch ( final GraphException ge )
                        {
                            // ignore
                        }
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
                    final BaseLabeledVertex v1 = grid[i][j];
                    final BaseLabeledVertex v2 = grid[h][j];

                    if ( !v1.equals( v2 ) )
                    {
                        final BaseLabeledEdge e = new BaseLabeledEdge( format( "%s -> %s", v1, v2 ) );
                        try
                        {
                            sudoku.addEdge( v1, e, v2 );
                        }
                        catch ( final GraphException ge )
                        {
                            // ignore
                        }
                    }

                }
            }
        }
        return grid;
    }

    /**
     * This class can't be instantiated
     */
    private GraphUtils()
    {
        // do nothing
    }

}
