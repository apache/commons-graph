package org.apache.commons.graph.domain.jdepend;

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

import java.awt.Color;

import org.apache.commons.graph.*;
import org.apache.commons.graph.visualize.*;

import jdepend.framework.*;

public class PackageVertex
  implements Vertex, Named, Colored
{
  private JavaPackage pkg = null;

  public PackageVertex( JavaPackage pkg ) {
    this.pkg = pkg;
  }

  public JavaPackage getJavaPackage() {
    return pkg;
  }

  public Color getBackgroundColor() {
    return Color.red;
  }

  public Color getTextColor() {
    return Color.white;
  }

  public String getName() {
    return pkg.getName();
  }

  public String toString() {
    return getName();
  }
}
