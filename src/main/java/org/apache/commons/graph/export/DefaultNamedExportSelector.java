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

import static org.apache.commons.graph.utils.Assertions.*;

import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Mapper;

final class DefaultNamedExportSelector<V, E>
    implements NamedExportSelctor<V, E>
{

    private final Graph<V, E> graph;

    private final Writer writer;

    private final Map<String, Mapper<V, ?>> vertexProperties = new HashMap<String, Mapper<V,?>>();

    private final Map<String, Mapper<E, ?>> edgeProperties = new HashMap<String, Mapper<E,?>>();

    private final String name;

    public DefaultNamedExportSelector( Graph<V, E> graph, Writer writer )
    {
        this( graph, writer, null );
    }

    public DefaultNamedExportSelector( Graph<V, E> graph, Writer writer, String name )
    {
        this.graph = graph;
        this.writer = writer;
        this.name = name;
    }

    public void usingDotNotation()
        throws GraphExportException
    {
        new DotExporter<V, E>( graph, writer, vertexProperties, edgeProperties, name ).export();
    }

    public void usingGraphMLFormat()
        throws GraphExportException
    {
        new GraphMLExporter<V, E>( graph, writer, vertexProperties, edgeProperties, name ).export();
    }

    public ExportSelctor<V, E> withName( String name )
    {
        return new DefaultNamedExportSelector<V, E>( graph, writer, name );
    }

    public EdgeMapperSelector<V, E> withEdgeProperty( String name )
    {
        final String checkedName = checkNotNull( name, "Null Edge property not admitted" );
        return new EdgeMapperSelector<V, E>()
        {

            public ExportSelctor<V, E> expandedBy( Mapper<E, ?> mapper )
            {
                mapper = checkNotNull( mapper, "Null Edge mapper for property %s not admitted", checkedName );
                edgeProperties.put( checkedName, mapper );
                return DefaultNamedExportSelector.this;
            }

        };
    }

    public VertexMapperSelector<V, E> withVertexProperty( String name )
    {
        final String checkedName = checkNotNull( name, "Null Vertex property not admitted" );
        return new VertexMapperSelector<V, E>()
        {

            public ExportSelctor<V, E> expandedBy( Mapper<V, ?> mapper )
            {
                mapper = checkNotNull( mapper, "Null Vertex mapper for property %s not admitted", checkedName );
                vertexProperties.put( checkedName, mapper );
                return DefaultNamedExportSelector.this;
            }

        };
    }

}
