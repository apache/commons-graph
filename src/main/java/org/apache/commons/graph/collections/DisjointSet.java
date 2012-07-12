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

import java.util.HashMap;
import java.util.Map;

/**
 * Simple <a href="http://en.wikipedia.org/wiki/Disjoint-set_data_structure">Disjoint-set</a> implementation.
 *
 * @param <E> the type of elements held in this collection.
 */
public final class DisjointSet<E>
{

    /**
     * The map to store the related {@link DisjointSetNode} for each added element.
     */
    private final Map<E, DisjointSetNode<E>> disjointSets = new HashMap<E, DisjointSetNode<E>>();

    /**
     * Performs the {@code find} operation applying the <i>path compression</i>.
     *
     * @param e the element has to be find in this {@code DisjointSet} instance
     * @return the value found 
     */
    public E find( E e )
    {
        DisjointSetNode<E> node = find( getNode( e ) );

        if ( node == node.getParent() )
        {
            return node.getElement();
        }

        node.setParent( find( node.getParent() ) );

        return node.getParent().getElement();
    }

    /**
     * Performs the @code{ find} operation by applying the <i>path compression</i>.
     *
     * @param node the input DisjointSet node for the @code{ find} operation
     * @return the root node of the path
     */
    private DisjointSetNode<E> find( DisjointSetNode<E> node )
    {
        if ( node == node.getParent() )
        {
            return node;
        }
        return find( node.getParent() );
    }

    /**
     * Join two subsets into a single subset, performing the merge by applying the <i>union by rank</i>.
     *
     * @param e1 the first element which related subset has to be merged
     * @param e2 the second element which related subset has to be merged
     */
    public void union( E e1, E e2 )
    {
        DisjointSetNode<E> e1Root = find( getNode( e1 ) );
        DisjointSetNode<E> e2Root = find( getNode( e2 ) );

        if ( e1Root == e2Root )
        {
            return;
        }

        int comparison = e1Root.compareTo( e2Root );
        if ( comparison < 0 )
        {
            e1Root.setParent( e2Root );
        }
        else if ( comparison > 0 )
        {
            e2Root.setParent( e1Root );
        }
        else
        {
            e2Root.setParent( e1Root );
            e1Root.increaseRank();
        }
    }

    /**
     * Retrieves the {@code DisjointSetNode} from the {@link #disjointSets},
     * if already previously set, creates a new one and push it in {@link #disjointSets} otherwise.
     *
     * @param e the element which related subset has to be returned
     * @return the input element {@code DisjointSetNode}
     */
    private DisjointSetNode<E> getNode( E e )
    {
        DisjointSetNode<E> node = disjointSets.get( e );

        if ( node == null )
        {
            node = new DisjointSetNode<E>( e );
            disjointSets.put( e, node );
        }

        return node;
    }

}
