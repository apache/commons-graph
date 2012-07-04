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

    /**
     * The stored node value.
     */
    private final E element;

    /**
     * The {@code DisjointSetNode} parent node, {@code this} by default.
     */
    private DisjointSetNode<E> parent = this;

    /**
     * The current node rank.
     */
    private Integer rank = 0;

    /**
     * Creates a new {@link DisjointSet} node with the given value.
     *
     * @param element the node value has to be stored.
     */
    public DisjointSetNode( E element )
    {
        this.element = element;
    }

    /**
     * Returns the adapted element by this node.
     *
     * @return the adapted element by this node.
     */
    public E getElement()
    {
        return element;
    }

    /**
     * Returns the reference to the parent node, the node itself by default.
     *
     * @return the reference to the parent node, the node itself by default.
     */
    public DisjointSetNode<E> getParent()
    {
        return parent;
    }

    /**
     * Sets the reference to a new parent node.
     *
     * @param parent the reference to a new parent node.
     */
    public void setParent( DisjointSetNode<E> parent )
    {
        this.parent = parent;
    }

    /**
     * Returns this node rank.
     *
     * @return this node rank
     */
    public Integer getRank()
    {
        return rank;
    }

    /**
     * Increases this node rank.
     */
    public void increaseRank()
    {
        rank++;
    }

    /**
     * Sets a new different rank.
     *
     * @param rank the new rank to this node.
     */
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
