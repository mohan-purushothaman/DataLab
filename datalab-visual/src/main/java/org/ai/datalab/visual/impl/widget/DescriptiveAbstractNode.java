/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;

/**
 *
 * @author Mohan Purushothaman
 */
public class DescriptiveAbstractNode extends AbstractNode {

    private final DescriptiveExecutionUnit propertyNode;

    public DescriptiveAbstractNode(DescriptiveExecutionUnit propertyNode) {
        super(Children.LEAF);
        this.propertyNode = propertyNode;
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = Sheet.createDefault();

        for (Sheet.Set set : propertyNode.getBasicPropertiesSheet()) {
            if (set != null) {
                sheet.put(set);
            }
        }

        Sheet.Set newSet = propertyNode.createPropertySheet();
        if (newSet != null) {
            sheet.put(newSet);
        }

        return sheet;
    }

//    @Override
//    public Transferable clipboardCut() throws IOException {
//
//        return new ListTransferable(propertyNode.getGraphScene().cut((ExecutionUnit) propertyNode));
//    }
//
//    @Override
//    public Transferable clipboardCopy() throws IOException {
//        return new ListTransferable(propertyNode.getGraphScene().copy((ExecutionUnit) propertyNode));
//    }

}

//class ListTransferable implements Transferable {
//
//    List<UndoableDeleteEdit> events;
//
//    public ListTransferable(List<UndoableDeleteEdit> events) {
//        this.events = events;
//    }
//
//    @Override
//    public DataFlavor[] getTransferDataFlavors() {
//        return new DataFlavor[]{new DataFlavor(List.class, "events copy")};
//    }
//
//    @Override
//    public boolean isDataFlavorSupported(DataFlavor flavor) {
//        return flavor instanceof ListFlavor || flavor.isFlavorTextType();
//    }
//
//    @Override
//    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
//        if (flavor instanceof ListFlavor) {
//            return events;
//        }
//
//        if (flavor.isFlavorTextType()) {
//            return events.toString();
//        }
//        return events;
//    }
//
//}
//
//class ListFlavor extends DataFlavor {
//
//    public ListFlavor() {
//        super(List.class, "list flavor");
//    }
//
//}
