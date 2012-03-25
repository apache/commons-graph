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

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.visit.BaseGraphVisitHandler;
import org.apache.commons.graph.visit.VisitState;

final class ConnectedComponentHandler<V, E>
    extends BaseGraphVisitHandler<V, E, Graph<V, E>, List<V>>
{

    private final List<V> touchedVertices = new LinkedList<V>();

    private final List<V> untouchedVertices;

    public ConnectedComponentHandler( List<V> untouchedVertices )
    {
        this.untouchedVertices = untouchedVertices;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VisitState finishVertex( V vertex )
    {
        untouchedVertices.remove( vertex );
        touchedVertices.add( vertex );
        return VisitState.CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<V> onCompleted()
    {
        return touchedVertices;
    }

}
