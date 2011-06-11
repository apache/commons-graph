package org.apache.commons.graph.visualize;

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

import java.util.Random;
import java.util.Iterator;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.awt.Color;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class TouchGraph
{
    private Color vertexColor = Color.yellow;
    private Color textColor = Color.black;
    private Color edgeColor = Color.red;

    private double lengthFactor = 500.0;
    private double defaultLength = 1.0;
    private int fontSize = 18;

    private Random random = new Random();

    /**
     * Constructor for the TouchGraph object
     */
    public TouchGraph() { }

    /**
     * Gets the colorText attribute of the TouchGraph object
     */
    private String getColorText(Color color)
    {
      String RC = Integer.toHexString( color.getRGB()).toUpperCase();
      return RC.substring( 2, 8 );
    }

    /**
     * Sets the vertexColor attribute of the TouchGraph object
     */
    public void setVertexColor(Color vertexColor)
    {
        this.vertexColor = vertexColor;
    }

    /**
     * Gets the vertexColor attribute of the TouchGraph object
     */
    public Color getVertexColor()
    {
        return this.vertexColor;
    }

    /**
     * Gets the vertexColorText attribute of the TouchGraph object
     */
    private String getVertexColorText()
    {
        return getColorText(getVertexColor());
    }

    /**
     * Sets the textColor attribute of the TouchGraph object
     */
    public void setTextColor(Color vertexColor)
    {
        this.vertexColor = vertexColor;
    }

    /**
     * Gets the textColor attribute of the TouchGraph object
     */
    public Color getTextColor()
    {
        return this.vertexColor;
    }

    /**
     * Gets the textColorText attribute of the TouchGraph object
     */
    private String getTextColorText()
    {
        return getColorText(getTextColor());
    }

    /**
     * Sets the edgeColor attribute of the TouchGraph object
     */
    public void setEdgeColor(Color edgeColor)
    {
        this.edgeColor = edgeColor;
    }

    /**
     * Gets the edgeColor attribute of the TouchGraph object
     */
    public Color getEdgeColor()
    {
        return this.edgeColor;
    }

    /**
     * Gets the edgeColorText attribute of the TouchGraph object
     */
    private String getEdgeColorText()
    {
        return getColorText(getEdgeColor());
    }

    /**
     * Sets the fontSize attribute of the TouchGraph object
     */
    public void setFontSize(int size)
    {
        this.fontSize = size;
    }

    /**
     * Gets the fontSize attribute of the TouchGraph object
     */
    public int getFontSize()
    {
        return fontSize;
    }

    /**
     * Sets the defaultEdgeLength attribute of the TouchGraph object
     */
    public void setDefaultEdgeLength(int length)
    {
        this.defaultLength = length;
    }

    /**
     * Gets the defaultEdgeLength attribute of the TouchGraph object
     */
    public double getDefaultEdgeLength()
    {
        return defaultLength;
    }

    /**
     * Description of the Method
     */
    protected void writeNodeset(PrintWriter pw,
                                DirectedGraph graph)
    {
        pw.println("<NODESET>");
        Iterator vertices =
            graph.getVertices().iterator();
        while (vertices.hasNext())
        {
            Vertex v = (Vertex) vertices.next();

            pw.println("<NODE nodeID=\"" + v.toString() + "\">");
            pw.println("<NODE_LOCATION x=\"" + random.nextInt(200) +
                "\" y = \"" + random.nextInt(200) +
                "\" visible=\"true\" />");

            String label;
            if (v instanceof Named)
            {
                label = ((Named) v).getName();
            }
            else
            {
                label = v.toString();
            }

	    String backColor = null;
	    String textColor = null;

	    if (v instanceof Colored) {
	      backColor = getColorText(((Colored) v).getBackgroundColor());
	      textColor = getColorText(((Colored) v).getTextColor() );
	    } else {
	      backColor = getVertexColorText();
	      textColor = getTextColorText();
	    }

            pw.println("<NODE_LABEL label=\"" + label + "\" " +
                "shape=\"2\" " +
                "backColor=\"" + backColor + "\" " +
                "textColor=\"" + textColor + "\" " +
                "fontSize=\"" + fontSize + "\" />");

            pw.println("</NODE>");
        }

        pw.println("</NODESET>");
    }

    /**
     * Description of the Method
     */
    protected void writeEdgeset(PrintWriter pw, DirectedGraph graph)
    {
        pw.println("<EDGESET>");

        Iterator edges = graph.getEdges().iterator();
        while (edges.hasNext())
        {
            Edge next = (Edge) edges.next();

            int length = new Double(lengthFactor *
                defaultLength).intValue();

            if (graph instanceof WeightedGraph)
            {
                length =
                    new Double(lengthFactor *
                    ((WeightedGraph) graph)
                    .getWeight(next)).intValue();
            }

            pw.println("<EDGE fromID=\"" + graph.getSource(next) + "\" " +
                "toID=\"" + graph.getTarget(next) + "\" " +
                "type=\"2\" " +
                "visible=\"true\" " +
                "length=\"" + length + "\" />");

        }
        pw.println("</EDGESET>");
    }

    /**
     * Description of the Method
     */
    public void toXML(DirectedGraph graph,
                      OutputStream os)
    {
        PrintWriter pw = new PrintWriter(os);
        pw.println("<?xml version=\"1.0\"?>");
        pw.println("<TOUCHGRAPH_LB version=\"1.20\">");
        writeNodeset(pw, graph);
        writeEdgeset(pw, graph);
        pw.println("</TOUCHGRAPH_LB>");
        pw.flush();

        return;
    }
}


