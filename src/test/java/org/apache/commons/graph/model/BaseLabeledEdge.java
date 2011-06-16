package org.apache.commons.graph.model;

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

import static java.lang.String.format;

import org.apache.commons.graph.LabeledEdge;

public class BaseLabeledEdge
    implements LabeledEdge<BaseLabeledVertex>
{

    private final String label;

    private final BaseLabeledVertex head;

    private final BaseLabeledVertex tail;

    public BaseLabeledEdge( String label, BaseLabeledVertex head, BaseLabeledVertex tail )
    {
        this.label = label;
        this.head = head;
        this.tail = tail;
    }

    /**
     * {@inheritDoc}
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * {@inheritDoc}
     */
    public BaseLabeledVertex getHead()
    {
        return head;
    }

    /**
     * {@inheritDoc}
     */
    public BaseLabeledVertex getTail()
    {
        return tail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( head == null ) ? 0 : head.hashCode() );
        result = prime * result + ( ( label == null ) ? 0 : label.hashCode() );
        result = prime * result + ( ( tail == null ) ? 0 : tail.hashCode() );
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( obj == null )
        {
            return false;
        }

        if ( getClass() != obj.getClass() )
        {
            return false;
        }

        BaseLabeledEdge other = (BaseLabeledEdge) obj;

        if ( head == null )
        {
            if ( other.getHead() != null )
            {
                return false;
            }
        }
        else if ( !head.equals( other.getHead() ) )
        {
            return false;
        }

        if ( tail == null )
        {
            if ( other.getTail() != null )
            {
                return false;
            }
        }
        else if ( !tail.equals( other.getTail() ) )
        {
            return false;
        }

        if ( label == null )
        {
            if ( other.getLabel() != null )
            {
                return false;
            }
        }
        else if ( !label.equals( other.label ) )
        {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return format( "%s[ %s -> %s ]", label, head.getLabel(), tail.getLabel() );
    }

}
