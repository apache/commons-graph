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
 * An <i>edge labeling</i> is a function mapping {@link Edge}s of a {@link Graph} to a set of <i>labels</i>.
 * A {@link Graph} with such a function defined is called an <i>edge-labeled graph</i>.
 *
 * @param <V> the Graph vertices type
 */
public interface LabeledEdge<V extends Vertex>
    extends Edge<V>, Labeled
{

}
