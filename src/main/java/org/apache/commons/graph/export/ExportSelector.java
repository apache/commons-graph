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

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;

public interface ExportSelector<V extends Vertex, E extends Edge, G extends Graph<V, E>>
{

    /**
     * Export Graphs in <a href="http://en.wikipedia.org/wiki/DOT_language">DOT language</a>.
     * @throws GraphExportException in case an error occurred while exporting the graph
     */
    void usingDotNotation()
        throws GraphExportException;

    /**
     * Export Graphs in <a href="http://graphml.graphdrawing.org/">GraphML file format</a>.
     * @throws GraphExportException in case an error occurred while exporting the graph
     */
    void usingGraphMLFormat()
        throws GraphExportException;

}