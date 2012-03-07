package org.apache.commons.graph.visit;

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

/**
 * A {@link GraphVisitHandler} controls the execution of breadth-first and depth-first search
 * algorithms in {@link Visit}.
 */
public interface GraphVisitHandler<V, E, G extends Graph<V, E>, O>
{

    /**
     * Called at the beginning of breadth-first and depth-first search.
     */
    void discoverGraph( G graph );

    /**
     * Performs operations on the input {@link Vertex} and checks if it should be expanded
     * in the search algorithm.
     * @return true if the input {@link Vertex} should be expanded, false otherwise
     */
    boolean discoverVertex( V vertex );

    /**
     * Performs operations on the input {@link Edge} and checks if it should be expanded
     * in the search algorithm.
     * @return true if the input {@link Edge} should be expanded, false otherwise
     */
    boolean discoverEdge( V head, E edge, V tail );

    /**
     * Checks if the search algorithm should be terminated. Called after the search algorithm has finished
     * visiting the input {@link Edge}.
     * @return true if the search algorithm should be terminated after visiting the input {@link Edge}, false otherwise
     */
    boolean finishEdge( V head, E edge, V tail );

    /**
     * Checks if the search algorithm should be terminated. Called after the search algorithm has finished
     * visiting the input {@link Vertex}.
     * @return true if the search algorithm should be terminated after visiting the input {@link Vertex}, false otherwise
     */
    boolean finishVertex( V vertex );

    /**
     * Called upon termination of the search algorithm.
     */
    void finishGraph( G graph );

    /**
     * Invoked once the visit is finished.
     *
     * @return Value that will be returned by the visit
     */
    O onCompleted();

}
