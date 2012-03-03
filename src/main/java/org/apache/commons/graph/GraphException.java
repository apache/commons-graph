package org.apache.commons.graph;

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

/**
 * GraphException This is the superclass of all exceptions that can be thrown.
 */
public class GraphException
    extends RuntimeException
{
    /** The serial version UID. */
    private static final long serialVersionUID = 6356965258279945475L;

    /**
     * Constructs a new graph exception.
     */
    public GraphException()
    {
        super();
    }

    /**
     * Constructs a new graph exception with the specified detail message.
     *
     * @param messagePattern The error message text pattern
     * @param arguments Arguments referenced by the format specifiers in the format string
     * @see java.lang.String#format(String, Object...)
     */
    public GraphException( String messagePattern, Object...arguments )
    {
        super( format( messagePattern, arguments ) );
    }

    /**
     * Constructs a new graph exception with the specified cause.
     *
     * @param cause the cause
     */
    public GraphException( Throwable cause )
    {
        super( cause );
    }

    /**
     * Constructs a new graph exception with the specified detail message and cause.
     *
     * @param msg the detail message.
     * @param cause the cause
     */
    public GraphException( String msg, Throwable cause )
    {
        super( msg, cause );
    }

}
