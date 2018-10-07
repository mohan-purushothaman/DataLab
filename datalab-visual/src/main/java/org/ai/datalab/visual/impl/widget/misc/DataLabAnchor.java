/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget.misc;

import java.awt.Point;
import java.awt.Rectangle;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.layout.LayoutFactory.ConnectionWidgetLayoutAlignment;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Mohan Purushothaman
 */
public class DataLabAnchor extends Anchor {

    private final ConnectionWidgetLayoutAlignment alignment;

    public DataLabAnchor(Widget relatedWidget, ConnectionWidgetLayoutAlignment alignment) {
        super(relatedWidget);
        this.alignment = alignment;
    }

    @Override
    public Result compute(Entry entry) {
        Rectangle bounds = getRelatedWidget().getBounds();

        return new Result(getRelatedWidget().convertLocalToScene(getConnectedPoint(bounds)), getDirection());
    }

    private Point getConnectedPoint(Rectangle bounds) {
        switch (alignment) {
            case BOTTOM_CENTER:
                return new Point(centerX(bounds), bounds.y + bounds.height);
            case TOP_CENTER:
                return new Point(centerX(bounds), bounds.y);
            case CENTER_RIGHT:
                return new Point(bounds.x+bounds.width, centerY(bounds));
        }
        throw new UnsupportedOperationException();
    }

    private Direction getDirection() {
        switch (alignment) {
            case BOTTOM_CENTER:
                return Direction.BOTTOM;
            case TOP_CENTER:
                return Direction.TOP;
            case CENTER_RIGHT:
                return Direction.RIGHT;
        }
        throw new UnsupportedOperationException();
    }
    
      /**
     * Returns a x-axis center of rectangle.
     * @param rectangle the rectangle
     * @return the x-axis center
     */
    public static int centerX (Rectangle rectangle) {
        return rectangle.x + rectangle.width / 2;
    }

    /**
     * Returns a y-axis center of rectangle.
     * @param rectangle the rectangle
     * @return the y-axis center
     */
    public static int centerY (Rectangle rectangle) {
        return rectangle.y + rectangle.height / 2;
    }

}
