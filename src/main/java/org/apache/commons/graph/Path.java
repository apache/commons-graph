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

/**
 * A {@code Path} in a {@link Graph} is a sequence of vertex such that from each of its vertices there is an
 * edge to the next vertex in the sequence.
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public interface Path<V, E>
    extends Graph<V, E>
{

    /**
     * Returns the source of the path.
     *
     * @return the source of the path.
     */
    V getSource();

    /**
     * Returns the target of the path.
     *
     * @return the target of the path.
     */
    V getTarget();

}
