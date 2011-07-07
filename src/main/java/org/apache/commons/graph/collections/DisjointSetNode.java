package org.apache.commons.graph.collections;

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

/**
 * The {@link DisjointSet} internal node representation.
 *
 * @param <E> the type of elements held in this node
 */
final class DisjointSetNode<E>
    implements Comparable<DisjointSetNode<E>>
{

    private final E vertex;

    private DisjointSetNode<E> parent = this;

    private Integer rank = 0;

    /**
     * 
     *
     * @param vertex
     */
    public DisjointSetNode( E vertex )
    {
        this.vertex = vertex;
    }

    public E getVertex()
    {
        return vertex;
    }

    public DisjointSetNode<E> getParent()
    {
        return parent;
    }

    public void setParent( DisjointSetNode<E> parent )
    {
        this.parent = parent;
    }

    public Integer getRank()
    {
        return rank;
    }

    public void increaseRank()
    {
        rank++;
    }

    public void setRank( int rank )
    {
        this.rank = rank;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo( DisjointSetNode<E> o )
    {
        return rank.compareTo( o.getRank() );
    }

}
