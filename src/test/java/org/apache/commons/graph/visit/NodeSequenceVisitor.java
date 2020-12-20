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

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.graph.model.BaseLabeledEdge;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.UndirectedMutableGraph;

public final class NodeSequenceVisitor
    extends BaseGraphVisitHandler<BaseLabeledVertex, BaseLabeledEdge, UndirectedMutableGraph<BaseLabeledVertex, BaseLabeledEdge>, List<BaseLabeledVertex>>
{

    private final List<BaseLabeledVertex> vertices = new ArrayList<BaseLabeledVertex>();

    /**
     * {@inheritDoc}
     */
    @Override
    public VisitState discoverVertex(final BaseLabeledVertex vertex )
    {
        vertices.add( vertex );
        return VisitState.CONTINUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BaseLabeledVertex> onCompleted()
    {
        return unmodifiableList( vertices );
    }

}
