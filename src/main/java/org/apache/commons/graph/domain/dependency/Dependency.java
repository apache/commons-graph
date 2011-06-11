package org.apache.commons.graph.domain.dependency;

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

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class Dependency implements Edge
{
    private Object head = null;
    private Object dep = null;

    /**
     * Constructor for the Dependency object
     *
     * @param head
     * @param dep
     */
    public Dependency(Object head,
                      Object dep)
    {
        this.head = head;
        this.dep = dep;
    }

    /**
     * Description of the Method
     */
    public String description()
    {
        return head + " depends on " + dep;
    }
}
