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

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;

public final class DefaultToStreamBuilder<V extends Vertex, E extends Edge, G extends Graph<V, E>>
    implements ToStreamBuilder<V, E, G>
{

    private final G graph;

    public DefaultToStreamBuilder( G graph )
    {
        this.graph = graph;
    }

    public NamedExportSelctor<V, E, G> to( File outputFile )
    {
        try
        {
            return to( new FileOutputStream( checkNotNull( outputFile, "Impossibe to export the graph in a null file" ) ) );
        }
        catch ( FileNotFoundException e )
        {
            throw new RuntimeException( e );
        }
    }

    public NamedExportSelctor<V, E, G> to( OutputStream outputStream )
    {
        return to( new OutputStreamWriter( checkNotNull( outputStream, "Impossibe to export the graph in a null stream" ) ) );
    }

    public NamedExportSelctor<V, E, G> to( Writer writer )
    {
        writer = checkNotNull( writer, "Impossibe to export the graph in a null stream" );
        return new DefaultNamedExportSelector<V, E, G>( graph, writer );
    }

}
