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
import static org.apache.commons.graph.utils.Objects.eq;
import static org.apache.commons.graph.utils.Objects.hash;

import java.io.Serializable;

public class BaseLabeledEdge
    implements Serializable
{

    private static final long serialVersionUID = -4985890761880816592L;
    private final String label;

    public BaseLabeledEdge(final String label )
    {
        this.label = checkNotNull( label, "Argument 'label' must not be null" );
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
    @Override
    public int hashCode()
    {
        return hash( 1, 31, label );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( obj == null || getClass() != obj.getClass() )
        {
            return false;
        }

        final BaseLabeledEdge other = (BaseLabeledEdge) obj;

        return eq( label, other.label );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return format( "%s()", getLabel() );
    }

}
