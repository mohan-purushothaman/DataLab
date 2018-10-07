/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.misc;

import org.ai.datalab.designer.DataLabGraphDesigner;
import java.awt.Point;
import java.util.Collection;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import org.ai.datalab.core.builder.ExecutionUnit;
import org.ai.datalab.visual.DataLabVisualUtil;
import org.ai.datalab.visual.impl.widget.DescriptiveExecutionUnit;

/**
 *
 * @author Mohan Purushothaman
 */
public class UndoableDeleteEdit extends AbstractUndoableEdit {

    private final DataLabGraphDesigner graph;
    private final DescriptiveExecutionUnit node;
    private final DescriptiveExecutionUnit parent;

    private final Point preferredLocation;
    private final Boolean flowCondition;

    public UndoableDeleteEdit(DataLabGraphDesigner graph, DescriptiveExecutionUnit node, DescriptiveExecutionUnit parent, Point preferredLocation, Boolean flowCondition) {
        this.graph = graph;
        this.node = node;
        this.parent = parent;
        this.preferredLocation = preferredLocation;
        this.flowCondition = flowCondition;
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
        graph.createNode(node, preferredLocation.x, preferredLocation.y, parent, flowCondition);
        DataLabVisualUtil.validateScene(graph);
    }

    @Override
    public void redo() throws CannotRedoException {
        graph.removeNodeWithDependency(node, null,true);
        DataLabVisualUtil.validateScene(graph);
    }

}
