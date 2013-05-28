package org.apache.commons.graph.model;

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

import static org.apache.commons.graph.Graphs.populate;

import org.apache.commons.graph.builder.GraphConnection;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.apache.commons.graph.model.UndirectedMutableGraph;

/**
 * Utilities graph class.
 */
public class GraphUtils
{

    /**
     * Creates a new {@link DirectedMutableGraph} instance where vertices
     * are connected as described in the input {@link GraphConnection} instance.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graphConnection the {@link GraphConnection} instance that describes vertices
     * @return a new {@link DirectedMutableGraph} instance
     */
    public static <V, E> DirectedMutableGraph<V, E> newDirectedMutableGraph( GraphConnection<V, E> graphConnection )
    {
        return populate( new DirectedMutableGraph<V, E>() ).withConnections( graphConnection );
    }

    /**
     * Creates a new {@link UndirectedMutableGraph} instance where vertices
     * are connected as described in the input {@link GraphConnection} instance.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graphConnection the {@link GraphConnection} instance that describes vertices
     * @return a new {@link UndirectedMutableGraph} instance
     */
    public static <V, E> UndirectedMutableGraph<V, E> newUndirectedMutableGraph( GraphConnection<V, E> graphConnection )
    {
        return populate( new UndirectedMutableGraph<V, E>() ).withConnections( graphConnection );
    }

    /**
     * This class can't be instantiated
     */
    private GraphUtils()
    {
        // do nothing
    }

}
