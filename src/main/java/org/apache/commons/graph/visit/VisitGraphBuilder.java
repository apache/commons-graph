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

import org.apache.commons.graph.DirectedGraph;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.model.BaseMutableGraph;
import org.apache.commons.graph.model.DirectedMutableGraph;
import org.apache.commons.graph.model.UndirectedMutableGraph;

/**
 * Internal Visitor helper that produces the search tree.
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 */
final class VisitGraphBuilder<V, E, G extends Graph<V, E>>
    extends BaseGraphVisitHandler<V, E, G, Graph<V, E>>
{

    private BaseMutableGraph<V, E> visitGraph;

    /**
     * {@inheritDoc}
     */
    @Override
    public void discoverGraph( G graph )
    {
        if ( graph instanceof DirectedGraph )
        {
            visitGraph = new DirectedMutableGraph<V, E>();
        }
        else
        {
            visitGraph = new UndirectedMutableGraph<V, E>();
        }

        for ( V vertex : graph.getVertices() )
        {
            visitGraph.addVertex( vertex );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VisitState discoverEdge( V head, E edge, V tail )
    {
        visitGraph.addEdge( head, edge, tail );
        return VisitState.CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Graph<V, E> onCompleted()
    {
        return visitGraph;
    }

}
