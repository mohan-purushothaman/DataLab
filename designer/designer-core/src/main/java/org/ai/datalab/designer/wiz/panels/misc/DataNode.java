/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.designer.wiz.panels.misc;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.apache.commons.lang.StringEscapeUtils;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.actions.Presenter;
import org.ai.datalab.core.adx.misc.SingleMapping;
import org.ai.datalab.core.misc.Type;
import org.ai.datalab.visual.impl.DescriptiveSingleMapping;

/**
 *
 * @author Mohan Purushothaman
 * @param <ID> Identifier used by adapter
 */
public class DataNode<ID> extends AbstractNode {

    private final DataVisualView panel;
    private final SingleMapping mapping;

    private final Type originalType;

    //TODO remove visual associateion with , deassociate Node implmentation from this class
    public DataNode(DataVisualView view, SingleMapping mapping) {
        super(Children.LEAF, Lookup.EMPTY);
        this.mapping = mapping;
        this.panel = view;
        this.originalType = mapping.getConverter().getResultType();

    }

    @Override
    public String getHtmlDisplayName() {
        String value;
        try {
            value = " <font color='#AAAAAA'>" + StringEscapeUtils.escapeHtml(String.valueOf(mapping.getConverter().convert(mapping.getSampleValue()))) + "</font> ";
        } catch (Exception ex) {
            value = " <font color='#FF0000'>Error Happened (" + StringEscapeUtils.escapeHtml(String.valueOf(ex.getMessage())) + ")" + "</font> ";
        }
        return "<font color='#0000FF'><b>" + mapping.getFieldKey() + "</b></font> :"
                + value + "<i>" + mapping.getConverter().getResultType() + "</i>";
    }

    @Override
    public Image getIcon(int type) {
        Image img = ImageUtilities.loadImage("org/ai/datalab/designer/wizard/data/" + (mapping.getConverter().getResultType().name().toLowerCase()) + ".png");
        return img == null ? super.getIcon(type) : img;
    }

//    @Override
//    public Transferable clipboardCopy() throws IOException {
//        return new ExTransferable.Single(DataFlavor.stringFlavor) {
//
//            @Override
//            protected Object getData() throws IOException, UnsupportedFlavorException {
//                return panel.getConverter().convert(idMap.getFieldKey());
//            }
//        };
//    }
    @Override
    public Action[] getActions(boolean context) {
        return createChangeActions();
    }

    private Action[] createChangeActions() {

        List<Action> l = new LinkedList<>();

        if (mapping != null) {
            boolean canChange = true;
            if (mapping instanceof DescriptiveSingleMapping) {
                canChange = ((DescriptiveSingleMapping) mapping).isChangable();
            }

            if (canChange) {
                List<Action> list = new LinkedList<>();

                for (final Type t : Type.values()) {
                    list.add(new AbstractAction(t.name(), null) {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                mapping.setConverter(t.getConverter());
                                for (Action action : list) {
                                    if (!action.isEnabled()) {
                                        action.setEnabled(true);
                                    }
                                }
                                setEnabled(false);

                                fireDisplayNameChange(null, null);
                                fireIconChange();
                            } catch (Exception ex) {
                                Exceptions.printStackTrace(ex);
                            }
                        }
                    });
                }

                l.add(new SubMenuPresenter("Change type to ", list));

                l.add(new AbstractAction("Change Field Name") {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NotifyDescriptor.InputLine in = new NotifyDescriptor.InputLine("Enter field name", "Field Name");
                        if (DialogDisplayer.getDefault().notify(in) == NotifyDescriptor.OK_OPTION) {
                            String newName = in.getInputText();
                            try {
                                //TODO if newName already exists in data have to alert and get confirmation before update
                                mapping.setFieldKey(newName);

                            } catch (Exception ex) {
                                panel.handleException(ex);
                            }
                            fireDisplayNameChange(null, null);
                        }
                    }
                });
                l.add(new AbstractAction("Remove") {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            mapping.getParent().delete(mapping);
                            panel.setDataFields(mapping.getParent());
                        } catch (Exception ex) {
                            panel.handleException(ex);
                        }
                    }
                });
            }
        }
        return l.toArray(new Action[0]);

    }
}

class SubMenuPresenter extends AbstractAction implements ActionListener, Presenter.Popup {

    private final String actionName;
    private final List<? extends Action> actions;

    public SubMenuPresenter(String actionName, List<? extends Action> actions) {
        this.actionName = actionName;
        this.actions = actions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //NOT REQUIRED
    }

    @Override
    public JMenuItem getPopupPresenter() {
        JMenu jm = new JMenu(actionName);
        for (Action action : actions) {
            jm.add(action);
        }
        return jm;
    }

}
