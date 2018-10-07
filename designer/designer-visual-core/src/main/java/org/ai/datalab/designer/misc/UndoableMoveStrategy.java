/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.misc;

import org.ai.datalab.designer.DataLabGraphDesigner;
import java.awt.Point;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.MoveStrategy;
import org.netbeans.api.visual.widget.Widget;
import org.openide.awt.UndoRedo;
import org.ai.datalab.visual.DataLabVisualUtil;

/**
 *
 * @author Mohan Purushothaman
 */
public final class UndoableMoveStrategy implements MoveStrategy, MoveProvider {

    private Point originalLoc;
    private Point suggestedLoc;

    private final UndoRedo.Manager manager;

    public UndoableMoveStrategy(UndoRedo.Manager manager) {
        this.manager = manager;
    }

    @Override
    public Point locationSuggested(Widget widget, Point originalLocation, Point suggestedLocation) {
        this.originalLoc = originalLocation;
        this.suggestedLoc = suggestedLocation;
        return suggestedLocation;
    }

    @Override
    public void movementStarted(Widget widget) {
        //manager.addEdit(new MyAbstractUndoableEdit(widget));
    }

    @Override
    public void movementFinished(Widget widget) {
        MyAbstractUndoableEdit myAbstractUndoableEdit = new MyAbstractUndoableEdit(widget);
        manager.undoableEditHappened(new UndoableEditEvent(widget, myAbstractUndoableEdit));
        ((DataLabGraphDesigner) widget.getScene()).setModified();
    }

    class MyAbstractUndoableEdit extends AbstractUndoableEdit {

        private final Widget widget;

        private MyAbstractUndoableEdit(Widget widget) {
            this.widget = widget;
        }

        @Override
        public boolean canRedo() {
            return true;
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() throws CannotUndoException {
            widget.setPreferredLocation(originalLoc);
            DataLabVisualUtil.validateScene(widget.getScene());
        }

        @Override
        public void redo() throws CannotUndoException {
            widget.setPreferredLocation(suggestedLoc);
            DataLabVisualUtil.validateScene(widget.getScene());
        }

    }

    @Override
    public Point getOriginalLocation(Widget widget) {
        return ActionFactory.createDefaultMoveProvider().getOriginalLocation(widget);
    }

    @Override
    public void setNewLocation(Widget widget, Point location) {
        ActionFactory.createDefaultMoveProvider().setNewLocation(widget, location);

    }

}
