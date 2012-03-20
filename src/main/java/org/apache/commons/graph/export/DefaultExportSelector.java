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

import org.apache.commons.graph.Graph;

public final class DefaultExportSelector<V, E>
    implements ExportSelector<V, E>
{

    private final Graph<V, E> graph;

    public DefaultExportSelector( Graph<V, E> graph )
    {
        this.graph = graph;
    }

    public DotExporter<V, E> usingDotNotation()
    {
        return new DotExporter<V, E>( graph, null );
    }

    public DotExporter<V, E> usingDotNotation( String name )
    {
        return new DotExporter<V, E>( graph, name );
    }

    public GraphMLExporter<V, E> usingGraphMLFormat()
    {
        return new GraphMLExporter<V, E>( graph, null );
    }

    public GraphMLExporter<V, E> usingGraphMLFormat( String name )
    {
        return new GraphMLExporter<V, E>( graph, name );
    }

}
