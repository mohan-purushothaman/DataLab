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
public class RhombusBorder implements Border {

    private final Insets insetRect;
    private final Color drawColor;
    private final Color fillColor;

    private static final int THICKNESS = 1;

    public RhombusBorder(int insets, Color fillColor, Color drawColor) {
        insetRect = new Insets(insets * 3, insets, insets * 3, insets);
        this.drawColor = drawColor;
        this.fillColor = fillColor;
    }

    @Override
    public Insets getInsets() {
        return insetRect;
    }

    @Override
    public void paint(Graphics2D gr, Rectangle bounds) {
        Path2D fillPath = new Path2D.Double();

        int heightMid = bounds.height / 2;
        int widthMid = bounds.width / 2;

        fillPath.moveTo(bounds.x + widthMid, bounds.y + 1);
        fillPath.lineTo(bounds.x + 1, bounds.y + heightMid - 1.0);
        fillPath.lineTo(bounds.x + widthMid, bounds.y + bounds.height - 1.0);
        fillPath.lineTo(bounds.x + bounds.width - 1.0, bounds.y + heightMid - 1.0);
        fillPath.closePath();
        gr.setColor(fillColor);
        gr.fill(fillPath);
        Stroke s = gr.getStroke();
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
