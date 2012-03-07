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

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.graph.Graph;

abstract class AbstractExporter<V, E, G extends Graph<V, E>>
{

    private static final String G = "G";

    private final G graph;

    private final Writer writer;

    private final String name;

    public AbstractExporter( G graph, Writer writer, String name )
    {
        this.graph = graph;
        this.writer = writer;
        this.name = name != null ? name : G;
    }

    protected final G getGraph()
    {
        return graph;
    }

    protected final Writer getWriter()
    {
        return writer;
    }

    protected final String getName()
    {
        return name;
    }

    public final void export()
        throws GraphExportException
    {
        try
        {
            internalExport();
        }
        catch ( Exception e )
        {
            throw new GraphExportException( e, "an error occurred while exporting graph %s (named %s) to writer %s",
                                            graph,
                                            name,
                                            writer );
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

    protected abstract void internalExport()
        throws Exception;

}
