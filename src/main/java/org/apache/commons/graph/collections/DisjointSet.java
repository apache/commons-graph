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

    private final Map<E, DisjointSetNode<E>> disjointSets = new HashMap<E, DisjointSetNode<E>>();

    /**
     * Performs the find applying the <i>path compression</i>.
     *
     * @param vertex
     * @return
     */
    public E find( E vertex )
    {
        DisjointSetNode<E> vertexNode = find( getNode( vertex ) );

        if ( vertexNode == vertexNode.getParent() )
        {
            return vertexNode.getVertex();
        }

        vertexNode.setParent( find( vertexNode.getParent() ) );

        return vertexNode.getParent().getVertex();
    }

    /**
     * Performs the find applying the <i>path compression</i>.
     *
     * @param node
     * @return
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
     * Performs the merge by applying the <i>union by rank</i>.
     *
     * @param u
     * @param v
     */
    public void union( E u, E v )
    {
        DisjointSetNode<E> uRoot = find( getNode( u ) );
        DisjointSetNode<E> vRoot = find( getNode( v ) );

        if ( uRoot == vRoot )
        {
            return;
        }

        int comparison = uRoot.compareTo( vRoot );
        if ( comparison < 0 )
        {
            uRoot.setParent( vRoot );
        }
        else if ( comparison > 0 )
        {
            vRoot.setParent( uRoot );
        }
        else
        {
            vRoot.setParent( uRoot );
            uRoot.increaseRank();
        }
    }

    private DisjointSetNode<E> getNode( E vertex )
    {
        DisjointSetNode<E> node = disjointSets.get( vertex );

        if ( node == null )
        {
            node = new DisjointSetNode<E>( vertex );
            disjointSets.put( vertex, node );
        }

        return node;
    }

}
