package org.apache.commons.graph.weight;

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
 * A {@link Semigroup} defines an associative binary operation
 * on a set of elements of the same type.
 *
 * @param <S> the type of the elements in the {@link Semigroup}
 */
public interface Semigroup<S>
{

    /**
     * Returns the result of the associative binary operation defined by this
     * {@link Semigroup} between two elements of appropriate type.
     *
     * @param s1 the first element
     * @param s2 the second element
     * @return the result of the associative binary operation
     */
    S append( S s1, S s2 );

}
