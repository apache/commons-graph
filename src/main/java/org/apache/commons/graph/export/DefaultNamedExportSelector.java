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

import java.io.Writer;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;

final class DefaultNamedExportSelector<V extends Vertex, E extends Edge, G extends Graph<V, E>>
    implements NamedExportSelector<V, E, G>
{

    private final G graph;

    private final Writer writer;

    private final String name;

    public DefaultNamedExportSelector( final G graph, final Writer writer )
    {
        this( graph, writer, null );
    }

    public DefaultNamedExportSelector( final G graph, final Writer writer, final String name )
    {
        this.graph = graph;
        this.writer = writer;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public void usingDotNotation()
        throws GraphExportException
    {
        new DotExporter<V, E, G>( graph, writer, name ).export();
    }

    /**
     * {@inheritDoc}
     */
    public void usingGraphMLFormat()
        throws GraphExportException
    {
        new GraphMLExporter<V, E, G>( graph, writer, name ).export();
    }

    /**
     * {@inheritDoc}
     */
    public ExportSelector<V, E, G> withName( final String graphName )
    {
        return new DefaultNamedExportSelector<V, E, G>( graph, writer, name );
    }

}
