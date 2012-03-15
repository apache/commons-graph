package org.apache.commons.graph.export;

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
 * This exception is used to wrap any {@link Exception} that occurs while exporting a
 * {@link Graph} instance.
 */
public final class GraphExportException
    extends Exception
{

    /** The serial version UID. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new graph exception with the specified detail message.
     *
     * @param cause the cause
     * @param messagePattern the error message text pattern
     * @param messageArguments arguments referenced by the format specifiers in the format string
     * @see java.lang.String#format(String, Object...)
     */
    public GraphExportException( Throwable cause, String messagePattern, Object... messageArguments )
    {
        super( format( messagePattern, messageArguments ), cause );
    }

}
