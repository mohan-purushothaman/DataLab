/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget.misc.border;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import org.netbeans.api.visual.border.Border;

/**
 *
 * @author Mohan Purushothaman
 */
public class ParallelogramBorder implements Border {

    private final Insets insetRect;
    private final Color drawColor;
    private final Color fillColor;
    private final int slant;

    private static final int THICKNESS = 1;

    public ParallelogramBorder(int insets, int slant, Color fillColor, Color drawColor) {
        insetRect = new Insets(insets, insets + slant, insets, insets + slant);
        this.drawColor = drawColor;
        this.fillColor = fillColor;
        this.slant = slant;
    }

    @Override
    public Insets getInsets() {
        return insetRect;
    }

    @Override
    public void paint(Graphics2D gr, Rectangle bounds) {

        Path2D fillPath = new Path2D.Double();
        fillPath.moveTo(bounds.x + 1 + slant, bounds.y + 1);
        fillPath.lineTo(bounds.x + 1, bounds.y + bounds.height - 1.0);
        fillPath.lineTo(bounds.x + bounds.width - 1.0 - slant, bounds.y + bounds.height - 1.0);
        fillPath.lineTo(bounds.x + bounds.width - 1.0, bounds.y + 1);
        fillPath.closePath();
        gr.setColor(fillColor);
        gr.fill(fillPath);
        
        Stroke s=gr.getStroke();
        gr.setColor(drawColor);
        gr.setStroke(new BasicStroke(THICKNESS));
        gr.draw(fillPath);
        gr.setStroke(s);
    }

    @Override
    public boolean isOpaque() {
        return true;
    }

}
