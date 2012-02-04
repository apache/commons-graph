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
import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import org.apache.commons.graph.Labeled;
import org.apache.commons.graph.Vertex;

public class BaseLabeledVertex
    implements Labeled, Vertex
{

    private static final long serialVersionUID = 1895831541078075882L;

    private final String label;

    public BaseLabeledVertex( String label )
    {
        this.label = checkNotNull( label, "Argument 'label' must not be null" );
    }

    /**
     * {@inheritDoc}
     */
    public final String getLabel()
    {
        return label;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + label.hashCode();
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

        BaseLabeledVertex other = (BaseLabeledVertex) obj;
        if ( !label.equals( other.getLabel() ) )
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
        return format( "{ %s }", label );
    }

}
