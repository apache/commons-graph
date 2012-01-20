package org.apache.commons.graph.scc;

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

import java.util.LinkedList;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.visit.BaseGraphVisitHandler;

/**
 * Kosaraju's strongly connected component algorithm is based on DFS (or BFS) algorithm
 * and needs to execute specific actions during the visit.
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 */
final class KosarajuSharirVisitHandler<V extends Vertex, E extends Edge>
    extends BaseGraphVisitHandler<V, E>
{

    final V startVisit;

    public KosarajuSharirVisitHandler( final V startVisit )
    {
        this.startVisit = startVisit;
    }

    final LinkedList<V> vertices = new LinkedList<V>();

    /**
     * {@inheritDoc}
     */
    public boolean finishVertex( V vertex )
    {
        if ( !startVisit.equals( vertex ) )
        {
            vertices.add( vertex );
        }
        return false;
    }

    @Override
    public void finishGraph( Graph<V, E> graph )
    {
        vertices.add( startVisit );

        // FIXME make sure the vertices order is the one needed by the algorithm
        System.out.println( "Visit complete: " + vertices );
    }

}
