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

import org.apache.commons.graph.Graph;

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
     * Performs operations on the input vertex and determines the behavior of the visit algorithm
     * based on the return value:
     * <ul>
     *   <li>{@link VisitState.CONTINUE} continues the visit normally;</li> 
     *   <li>{@link VisitState.SKIP} continues the visit skipping the input vertex;</li>
     *   <li>{@link VisitState.ABORT} terminates the visit.</li>
     * </ul>
     * @return the state of the visit after operations on the vertex
     */
    VisitState discoverVertex( V vertex );

    /**
     * Performs operations on the input edge and determines the behavior of the visit algorithm
     * based on the return value:
     * <ul>
     *   <li>{@link VisitState.CONTINUE} continues the visit normally;</li> 
     *   <li>{@link VisitState.SKIP} continues the visit skipping the input edge;</li>
     *   <li>{@link VisitState.ABORT} terminates the visit.</li>
     * </ul>
     * @return the state of the visit after operations on the edge
     */
    VisitState discoverEdge( V head, E edge, V tail );

    /**
     * Checks if the search algorithm should be terminated. Called after the search algorithm has finished
     * visiting the input edge.
     * @return {@link VisitState.ABORT} if the search algorithm should be terminated after visiting the input edge, {@link VisitState.CONTINUE} otherwise
     */
    VisitState finishEdge( V head, E edge, V tail );

    /**
     * Checks if the search algorithm should be terminated. Called after the search algorithm has finished
     * visiting the input vertex.
     * @return {@link VisitState.ABORT} if the search algorithm should be terminated after visiting the input vertex, {@link VisitState.CONTINUE} otherwise
     */
    VisitState finishVertex( V vertex );

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
