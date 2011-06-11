package org.apache.commons.graph.domain.jdepend;

import java.awt.Color;

import jdepend.framework.*;

import org.apache.commons.graph.*;
import org.apache.commons.graph.visualize.*;

public class ClassVertex
  implements Vertex, Named, Colored
{
  private JavaClass clazz = null;

  public ClassVertex( JavaClass clazz ) {
    this.clazz = clazz;
  }

  public JavaClass getJavaClass() {
    return clazz;
  }

  public String getName() {
    return clazz.getName();
  }

  public String toString() {
    return getName();
  }

  public Color getBackgroundColor() {
    return Color.blue;
  }

  public Color getTextColor() {
    return Color.white;
  }
}



