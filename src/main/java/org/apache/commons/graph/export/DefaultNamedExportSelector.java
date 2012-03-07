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

import org.apache.commons.graph.Graph;

final class DefaultNamedExportSelector<V, E, G extends Graph<V, E>>
    implements NamedExportSelctor<V, E, G>
{

    private final G graph;

    private final Writer writer;

    private final String name;

    public DefaultNamedExportSelector( G graph, Writer writer )
    {
        this( graph, writer, null );
    }

    public DefaultNamedExportSelector( G graph, Writer writer, String name )
    {
        this.graph = graph;
        this.writer = writer;
        this.name = name;
    }

    public void usingDotNotation()
        throws GraphExportException
    {
        new DotExporter<V, E, G>( graph, writer, name ).export();
    }

    public void usingGraphMLFormat()
        throws GraphExportException
    {
        new GraphMLExporter<V, E, G>( graph, writer, name ).export();
    }

    public ExportSelctor<V, E, G> withName( String name )
    {
        return new DefaultNamedExportSelector<V, E, G>( graph, writer, name );
    }

}
