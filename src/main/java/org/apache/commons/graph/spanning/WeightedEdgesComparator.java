package org.apache.commons.graph.spanning;

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

import java.util.Comparator;

import org.apache.commons.graph.Mapper;

/**
 *
 * @param <W>
 * @param <WE>
 */
public class WeightedEdgesComparator<W, WE>
    implements Comparator<WE>
{

    private final Comparator<W> weightComparator;

    private final Mapper<WE, W> weightedEdges;

    public WeightedEdgesComparator( Comparator<W> weightComparator, Mapper<WE, W> weightedEdges )
    {
        this.weightComparator = weightComparator;
        this.weightedEdges = weightedEdges;
    }

    public int compare( WE o1, WE o2 )
    {
        return weightComparator.compare( weightedEdges.map( o1 ), weightedEdges.map( o2 ) );
    }

}
