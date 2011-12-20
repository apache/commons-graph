package org.apache.commons.graph.export;

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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Labeled;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;

/**
 * Utility methods to export Graphs in <a href="http://en.wikipedia.org/wiki/DOT_language">DOT language</a>.
 */
public final class DotExporter
{

    private static final String GRAPH = "graph";

    private static final String DIGRAPH = "digraph";

    private static final String CONNECTOR = "--";

    private static final String DICONNECTOR = "->";

    /**
     * Export the input Graph to a {@link File}.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph has to be exported
     * @param outputFile the file where exporting the Graph to
     * @throws IOException if any error occurs while serializing the input Graph
     */
    public static <V extends Vertex, E extends Edge> void dotExport( Graph<V, E> graph, File outputFile )
        throws IOException
    {
        dotExport( graph, null, outputFile );
    }

    /**
     * Export the input Graph to a {@link File}.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph has to be exported
     * @param name the Graph name (can be null)
     * @param outputFile the file where exporting the Graph to
     * @throws IOException if any error occurs while serializing the input Graph
     */
    public static <V extends Vertex, E extends Edge> void dotExport( Graph<V, E> graph, String name,
                                                                     File outputFile )
        throws IOException
    {
        if ( outputFile == null )
        {
            throw new IllegalArgumentException( "DOT exporter requires a not null file" );
        }
        dotExport( graph, new FileOutputStream( outputFile ) );
    }

    /**
     * Export the input Graph to an {@link OutputStream}.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph has to be exported
     * @param outputStream the output stream where exporting the Graph to
     * @throws IOException if any error occurs while serializing the input Graph
     */
    public static <V extends Vertex, E extends Edge> void dotExport( Graph<V, E> graph, OutputStream outputStream )
        throws IOException
    {
        dotExport( graph, null, outputStream );
    }

    /**
     * Export the input Graph to an {@link OutputStream}.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph has to be exported
     * @param name the Graph name (can be null)
     * @param outputStream the output stream where exporting the Graph to
     * @throws IOException if any error occurs while serializing the input Graph
     */
    public static <V extends Vertex, E extends Edge> void dotExport( Graph<V, E> graph, String name,
                                                                     OutputStream outputStream )
        throws IOException
    {
        if ( outputStream == null )
        {
            throw new IllegalArgumentException( "DOT exporter requires a not null output stream" );
        }
        dotExport( graph, new OutputStreamWriter( outputStream ) );
    }

    /**
     * Export the input Graph to an {@link Writer}.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph has to be exported
     * @param writer the writer where exporting the Graph to
     * @throws IOException if any error occurs while serializing the input Graph
     */
    public static <V extends Vertex, E extends Edge> void dotExport( Graph<V, E> graph, Writer writer )
        throws IOException
    {
        dotExport( graph, null, writer );
    }

    /**
     * Export the input Graph to an {@link Writer}.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph has to be exported
     * @param name the Graph name (can be null)
     * @param writer the writer where exporting the Graph to
     * @throws IOException if any error occurs while serializing the input Graph
     */
    public static <V extends Vertex, E extends Edge> void dotExport( Graph<V, E> graph, String name, Writer writer )
        throws IOException
    {
        if ( graph == null )
        {
            throw new IllegalArgumentException( "Impossible to export a null Graph to DOT notation" );
        }
        if ( writer == null )
        {
            throw new IllegalArgumentException( "DOT exporter requires a not null writer" );
        }

        String graphDeclaration;
        String connector;

        if ( graph instanceof DirectedGraph )
        {
            graphDeclaration = DIGRAPH;
            connector = DICONNECTOR;
        }
        else
        {
            graphDeclaration = GRAPH;
            connector = CONNECTOR;
        }

        PrintWriter printWriter = new PrintWriter( writer );
        try
        {
            printWriter.format( "# Graph generated by Apache Commons Graph on %s%n", new Date() );
            printWriter.format( "%s %s {%n", graphDeclaration, name != null ? name : 'G' );

            for ( V vertex : graph.getVertices() )
            {
                printWriter.format( "  %s [label=\"%s\"];%n",
                                    vertex.hashCode(),
                                    ( vertex instanceof Labeled) ? ( (Labeled) vertex).getLabel() : vertex.toString() );
            }

            for ( E edge : graph.getEdges() )
            {
                VertexPair<V> vertexPair = graph.getVertices( edge );

                printWriter.format( "  %s %s %s",
                                    vertexPair.getHead().hashCode(),
                                    connector,
                                    vertexPair.getTail().hashCode() );

                boolean attributesFound = false;

                if ( edge instanceof Labeled )
                {
                    attributesFound = true;
                    printWriter.format( " [label=\"%s\"", ( (Labeled) edge ).getLabel() );
                }
                if ( edge instanceof WeightedEdge )
                {
                    Object weight = ( (WeightedEdge<?>) edge ).getWeight();

                    if ( weight instanceof Number )
                    {
                        printWriter.format( " " );

                        if ( !attributesFound )
                        {
                            printWriter.format( "[" );
                            attributesFound = true;
                        }

                        printWriter.format( "weight=%f", ( (Number) weight ).doubleValue() );
                    }
                }

                if ( attributesFound )
                {
                    printWriter.format( "]" );
                }

                printWriter.format( "%n" );
            }

            printWriter.write( '}' );
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch ( IOException e )
            {
                // swallow it
            }
        }
    }
}
