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
 * Description of the Interface
 */
public interface GraphVisitHandler<V extends Vertex, E extends Edge<V>>
{

    /**
     * Description of the Method
     */
    void discoverGraph( Graph<V, E> graph );

    /**
     * Description of the Method
     */
    void discoverVertex( V vertex );

    /**
     * Description of the Method
     */
    void discoverEdge( E edge );

    /**
     * Description of the Method
     */
    void finishEdge( E edge );

    /**
     * Description of the Method
     */
    void finishVertex( V vertex );

    /**
     * Description of the Method
     */
    void finishGraph( Graph<V, E> graph );

}
