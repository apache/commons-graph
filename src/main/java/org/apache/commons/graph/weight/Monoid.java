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

import java.io.Serializable;

/**
 * A {@link Monoid} is a {@link Semigroup} with an identity value.
 *
 * @param <E> the type of the elements in the {@link Monoid}
 */
public interface Monoid<E>
    extends Serializable
{

    /**
     * Returns the identity value.
     *
     * @return the identity value
     */
    E identity();

    /**
     * Returns the result of the associative binary operation defined by this
     * {@link Semigroup} between two elements of appropriate type.
     *
     * @param e1 the first element
     * @param e2 the second element
     * @return the result of the associative binary operation
     */
    E append( E e1, E e2 );

    /**
     * Returns the inverse of the input element.
     * @param element the input element
     * @return the inverse of the input element
     */
    E inverse( E element );

}
