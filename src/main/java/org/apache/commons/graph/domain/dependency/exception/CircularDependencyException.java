package org.apache.commons.graph.domain.dependency.exception;

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

import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class CircularDependencyException
     extends CycleException
{
    /**
     * Constructor for the CircularDependencyException object
     */
    public CircularDependencyException()
    {
        super();
    }

    /**
     * Constructor for the CircularDependencyException object
     *
     * @param msg
     */
    public CircularDependencyException(String msg)
    {
        super(msg);
    }

    /**
     * Constructor for the CircularDependencyException object
     *
     * @param cause
     */
    public CircularDependencyException(Throwable cause)
    {
        super(cause);
    }
}
