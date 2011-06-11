package org.apache.commons.graph.domain.jdepend;

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
