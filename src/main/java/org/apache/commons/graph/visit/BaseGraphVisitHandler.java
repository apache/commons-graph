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

import static org.apache.commons.graph.visit.VisitState.CONTINUE;

import org.apache.commons.graph.Graph;

/**
 * Base NOOP {@link GraphVisitHandler} implementation.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public class BaseGraphVisitHandler<V, E, G extends Graph<V, E>, O>
    implements GraphVisitHandler<V, E, G, O>
{

    /**
     * {@inheritDoc}
     */
    public VisitState discoverEdge( V head, E edge, V tail )
    {
        // do nothing
        return CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    public void discoverGraph( G graph )
    {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    public VisitState discoverVertex( V vertex )
    {
        // do nothing
        return CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    public VisitState finishEdge( V head, E edge, V tail )
    {
        // do nothing
        return CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    public void finishGraph( G graph )
    {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    public VisitState finishVertex( V vertex )
    {
        // do nothing
        return CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    public O onCompleted()
    {
        return null;
    }

}
