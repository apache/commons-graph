package org.apache.commons.graph.connectivity;

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

import static org.apache.commons.graph.CommonsGraph.visit;
import static org.apache.commons.graph.utils.Assertions.checkState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.graph.Graph;

/**
 *
 */
final class DefaultConnectivityAlgorithmsSelector<V, E>
    implements ConnectivityAlgorithmsSelector<V, E>
{

    final private Graph<V, E> graph;

    final private Iterable<V> includedVertices;

    public DefaultConnectivityAlgorithmsSelector( Graph<V, E> graph, Iterable<V> includedVertices )
    {
        this.graph = graph;
        this.includedVertices = includedVertices;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<List<V>> applyingMinimumSpanningTreeAlgorithm()
    {
        final List<V> untouchedVertices = new ArrayList<V>();

        for ( V v : includedVertices )
        {
            checkState( graph.containsVertex( v ), "Vertex %s does not exist in the Graph", v );
            untouchedVertices.add( v );
        }

        final Collection<List<V>> connectedVertices = new LinkedList<List<V>>();

        while ( untouchedVertices.size() > 0 )
        {
            V source = untouchedVertices.remove( 0 );

            connectedVertices.add( visit( graph ).from( source ).applyingDepthFirstSearch( new ConnectedComponentHandler<V, E>( untouchedVertices ) ) );
        }
        return connectedVertices;
    }

}
