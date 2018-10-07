package org.ai.datalab.designer.editor;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.ai.datalab.designer.editor;
//
//import javax.swing.text.Document;
//import org.netbeans.api.editor.mimelookup.MimeRegistration;
//import org.netbeans.spi.editor.highlighting.HighlightsLayer;
//import org.netbeans.spi.editor.highlighting.HighlightsLayerFactory;
//import org.netbeans.spi.editor.highlighting.ZOrder;
//
///**
// *
// * @author Mohan Purushothaman
// */
//@MimeRegistration(mimeType = SimpleEditor.LANG_MIME_TYPE, service = HighlightsLayerFactory.class)
//public class VariableHighlighterFactory implements HighlightsLayerFactory {
//
//    public static VariableHighlighter getMarkOccurrencesHighlighter(Document doc) {
//        VariableHighlighter highlighter
//                = (VariableHighlighter) doc.getProperty(VariableHighlighter.class);
//        if (highlighter == null) {
//            doc.putProperty(VariableHighlighterFactory.class,
//                    highlighter = new VariableHighlighter(doc));
//        }
//        return highlighter;
//    }
//
//    @Override
//    public HighlightsLayer[] createLayers(Context context) {
//        return new HighlightsLayer[]{
//            HighlightsLayer.create(
//            VariableHighlighterFactory.class.getName(),
//            ZOrder.CARET_RACK.forPosition(2000),
//            true,
//            getMarkOccurrencesHighlighter(context.getDocument()).getHighlightsBag())
//        };
//    }
//}
